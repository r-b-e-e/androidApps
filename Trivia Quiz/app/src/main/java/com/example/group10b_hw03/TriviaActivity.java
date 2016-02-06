package com.example.group10b_hw03;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
/**
 * Ganesh Ramamoorthy, Rakesh Balan
 */
public class TriviaActivity extends AppCompatActivity {
    ProgressDialog dialog;
    ArrayList<Question> questions = new ArrayList<Question>();
    ArrayList<String> answersProgress = new ArrayList<String>();
    String value = "";
    int counter = 0;
    Button qView;
    Button nextButton;
    TextView quesText;
    ProgressDialog pd;
    RadioGroup rgroup;
    RadioButton rb;
    int radioID = -1;
    String radioText = "";
    String questionID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        if (isConnected()) {
            new getHttpCon().execute("http://dev.theappsdr.com/apis/trivia_fall15/getAll.php");
            rgroup = (RadioGroup) findViewById(R.id.RadioGroup);
            rgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    rb = (RadioButton) findViewById(checkedId);
                    radioID = checkedId;
                    radioText = rb.getText().toString();
                    if (checkedId != -1) {
                        int buttonId = group.getCheckedRadioButtonId();
                        RadioButton selectedButton = (RadioButton) findViewById(buttonId);
                        selectedButton.toggle();
                    }

                }
            });

            nextButton = (Button) findViewById(R.id.next);
            qView = (Button) findViewById(R.id.qIDButton);
            qView.setClickable(false);
            quesText = (TextView) findViewById(R.id.questionText);
            Button quitBtn = (Button) findViewById(R.id.quit);
            quitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Internet Connection Failure", Toast.LENGTH_SHORT).show();
        }
    }


    private class getHttpCon extends AsyncTask<String, Void, ArrayList<Question>> {
        BufferedReader reader = null;
        ArrayList<String> answers;
        RadioButton rbTest;

        @Override
        protected ArrayList<Question> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    answers = new ArrayList<String>();
                    sb.append(line + "\n");
                    String test = line + "\n";
                    boolean quesFormat = false;
                    String[] values = test.split(";");
                    if(values.length>3){
                    if (values[1].equals("") || values[2].equals("") || values[3].equals("")) {
                        quesFormat = true;
                    }}

                    if (!quesFormat) {
                        int x = values.length - 2;
                        for (int y = 2; y <= values.length - 3; y++) {
                            answers.add(values[y]);
                        }
                        Question question = null;
                        if ((Patterns.WEB_URL.matcher((values[x]).toString()).matches())) {
                            question = new Question(values[0], values[1], answers, values[x]);
                        } else {
                            question = new Question(values[0], values[1], answers, null);
                        }
                        questions.add(question);
                    }
                }
                return questions;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(TriviaActivity.this);
            dialog.setTitle("Loading Questions");
            dialog.setMessage("    Loading ...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<Question> s) {
            super.onPostExecute(s);
            for (Question ques : s) {
                Log.d("demoxx", "" + ques.getqID());
            }
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (radioID == -1) {

                        Toast.makeText(TriviaActivity.this, "Please Select Any", Toast.LENGTH_SHORT).show();
                    } else {
                        if (counter < questions.size() - 1) {
                            Log.d("Demotest", questionID + radioText + radioID);
                            CheckAnswerType answerCheck = new CheckAnswerType(questionID, radioID);
                            new CheckAnswer().execute(answerCheck);
                            Question quest = questions.get(counter);
                            Log.d("DemoRadio Value", rb.getText().toString() + quest.getqID());
                            counter = counter + 1;
                            Question ques = questions.get(counter);
                            qView.setText(ques.getqID());
                            questionID = ques.getqID();
                            quesText.setText(ques.getqName());
                            if (null != ques.getImageURL()) {
                                new getHttpConImage().execute(ques.getImageURL());
                            } else {
                                getDefaultImage();
                            }
                            rgroup.removeAllViews();

                            for (int i = 0; i < ques.getOpt().size(); i++) {
                                rButtonCreation(ques, i);
                                radioID = -1;
                            }

                        } else if (counter == questions.size() - 1) {
                            CheckAnswerType answerCheck = new CheckAnswerType(questionID, radioID);
                            new CheckAnswer().execute(answerCheck);
                            Intent i = new Intent(getBaseContext(), ResultsActivity.class);
                            int answerScore = 0;
                            if (answersProgress.size() != 0) {
                                int x = answersProgress.size() * 100;
                                int y = questions.size() * 1;
                                answerScore = ((x / y));

                            }
                            i.putExtra("answerTotal", answerScore);
                            i.putExtra("TotalProgress", questions.size());
                            i.putExtra("answerProgress", answersProgress.size());
                            startActivity(i);
                        }
                    }
                }
            });

            Question ques = s.get(counter);
            qView.setText(ques.getqID());
            questionID = ques.getqID();
            quesText.setText(ques.getqName());
            if (null != ques.getImageURL()) {
                new getHttpConImage().execute(ques.getImageURL());
            } else {
                getDefaultImage();
            }
            for (int i = 0; i < ques.getOpt().size(); i++) {
                rButtonCreation(ques, i);
            }
            dialog.dismiss();
        }
    }

    private void getDefaultImage() {
        ImageView iv = (ImageView) findViewById(R.id.createImage);
        iv.setImageResource(R.drawable.image);
    }

    private void rButtonCreation(Question ques, int i) {
        RadioButton radioButtonView = new RadioButton(TriviaActivity.this);
        radioButtonView.setText(ques.getOpt().get(i));
        radioButtonView.setId(i);
        rgroup.addView(radioButtonView);
    }

    private class getHttpConImage extends AsyncTask<String, Void, Bitmap> {
        BufferedReader reader = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(TriviaActivity.this);
            pd.setMessage("Loading Image");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            ImageView iv = (ImageView) findViewById(R.id.createImage);
            iv.setImageBitmap(s);
            pd.dismiss();
        }
    }


    private class CheckAnswer extends AsyncTask<CheckAnswerType, Void, String> {

        @Override
        protected String doInBackground(CheckAnswerType... params) {
            BufferedReader reader = null;
            URL url = null;
            try {
                url = new URL("http://dev.theappsdr.com/apis/trivia_fall15/checkAnswer.php");
                String encodedURL = "gid=" + URLEncoder.encode("0b766a6018aed23562e8e34383439f92", "UTF-8") +
                        "&qid=" + params[0].getqId() + "&" + "a=" + params[0].getAnswer();
                Log.d("DemoURL", encodedURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
                out.write(encodedURL);
                out.flush();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                return reader.readLine();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("1")) {
                Log.d("DemoAnswerSuccess", s);
                Toast.makeText(TriviaActivity.this, "Correct Answer", Toast.LENGTH_SHORT).show();
                answersProgress.add("1");
            } else {
                Log.d("DemoAnswerFailure", s);
                Toast.makeText(TriviaActivity.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}