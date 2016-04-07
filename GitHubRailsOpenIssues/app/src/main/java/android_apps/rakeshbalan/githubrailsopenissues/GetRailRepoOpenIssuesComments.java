package android_apps.rakeshbalan.githubrailsopenissues;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by rakeshbalan on 4/7/2016.
 */
// using AsyncTask to Minimize network bandwidth.
public class GetRailRepoOpenIssuesComments extends AsyncTask<String, Void, String> {
    private ProgressDialog pd;
    private Context context;
    private RailsRepo railsRepo;
    private List<RailsRepoComment> railsRepoCommentsList;

    private Dialog commentDialog;
    private View commentView;
    private LayoutInflater layoutInflater;
    private TextView textViewComments;

    public GetRailRepoOpenIssuesComments(Context context, RailsRepo railsRepo) {
        //super(context, railsRepo);
        this.context = context;
        this.railsRepo = railsRepo;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);
        pd.setTitle("Loading Comments...Please Wait");
        pd.setMessage("Title: \r\n" + railsRepo.getIssueTitle());
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();

        try{
            URL url = new URL(params[0]);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            httpsURLConnection.setDoOutput(false);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.connect();

            int httpResponseCode = httpsURLConnection.getResponseCode();

            //checking for valid API
            if(httpResponseCode == 200)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
                String eachLine = null;
                while((eachLine = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(eachLine);
                }
                bufferedReader.close();
            }
            else if (httpResponseCode == 404)
            {
                Toast.makeText(context, "Comments API is not reachable", Toast.LENGTH_SHORT).show();
            }

            httpsURLConnection.disconnect();

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();
        //Log.d("debug", s);

        try
        {
            JSONArray jsonArray = new JSONArray(s);
            railsRepoCommentsList = new ArrayList<RailsRepoComment>();
            railsRepoCommentsList = JSONUtility.parseJsonRailsRepoComments(jsonArray);

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ContextThemeWrapper commentTheme = new ContextThemeWrapper(context, R.style.CommentStyle);
            commentDialog = new Dialog(commentTheme);
            commentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            if(!railsRepoCommentsList.isEmpty())
            {
                //used dynamic layout because the number of comments is unknown.
                RelativeLayout relativeLayout = new RelativeLayout(context);
                RelativeLayout.LayoutParams  relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                relativeLayout.setLayoutParams(relativeLayoutParams);

                //scroll bar is used to check the content outside the phone's screen
                ScrollView scrollView = new ScrollView(context);
                RelativeLayout.LayoutParams  relativeLayoutParamsScrollView = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams linerLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                for (int i=0; i<railsRepoCommentsList.size(); i++)
                {
                    TextView textView = new TextView(context);
                    textView.setText("Username: " + railsRepoCommentsList.get(i).getUserName() + "\r\n" + railsRepoCommentsList.get(i).getCommentBody());
                    LinearLayout.LayoutParams linerLayoutParamsTextView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    View lineView = new View(context);
                    lineView.setBackgroundResource(R.color.colorBlack);
                    LinearLayout.LayoutParams linerLayoutParamsRadioLineView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 8);

                    linearLayout.addView(textView, linerLayoutParamsTextView);
                    linearLayout.addView(lineView, linerLayoutParamsRadioLineView);
                }

                scrollView.addView(linearLayout, linerLayoutParams);
                relativeLayout.addView(scrollView, relativeLayoutParamsScrollView);


                //commentView = layoutInflater.inflate(relativeLayout, null);

//            textViewComments = (TextView) commentView.findViewById(R.id.textViewComments);
//            textViewComments.setText(sb.toString());

                commentDialog.setContentView(relativeLayout);
            }
            else
            {   //incase if the comment is empty
                commentView = layoutInflater.inflate(R.layout.comments_issue_nil, null);
                commentDialog.setContentView(commentView);
                textViewComments = (TextView) commentView.findViewById(R.id.textViewComments);
                textViewComments.setText(R.string.nil_comments);
            }

            commentDialog.show();

        }catch (JSONException e)
        {
            e.printStackTrace();
        }


    }
}
