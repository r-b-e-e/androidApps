package android_apps.rakeshbalan.githubrailsopenissues;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rakeshbalan on 4/7/2016.
 */
public class RailsRepoAdapter implements ListAdapter {
    private Context context;
    private List<RailsRepo> railsRepoList;
    private int railsResource;

    private TextView textViewIssueTitle;
    private TextView textViewIssueBody;

    public RailsRepoAdapter(Context context, int railsResource, List<RailsRepo> railsRepoList)
    {
        this.context = context;
        this.railsResource = railsResource;
        this.railsRepoList = railsRepoList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int positionInList = position;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(railsResource, null);
        }

        textViewIssueTitle = (TextView) convertView.findViewById(R.id.textViewIssueTitle);
        textViewIssueTitle.setText(getItem(position).getIssueTitle());
        textViewIssueBody = (TextView) convertView.findViewById(R.id.textViewIssueBody);


        if (getItem(position).getIssueBody().length() > 140) //limiting  to 140 characters
        {
            textViewIssueBody.setText(getItem(position).getIssueBody().substring(0, 140)); //140th position excluded
        }
        else
        {
            textViewIssueBody.setText(getItem(position).getIssueBody().trim());
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //comment URL of the the clicked issue
                new GetRailRepoOpenIssuesComments(context, getItem(positionInList)).execute(getItem(positionInList).getCommentsURL());
            }
        });

        return convertView;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return railsRepoList.size();
    }

    @Override
    public RailsRepo getItem(int position) {
        return railsRepoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return railsRepoList.size();
    }

    @Override
    public boolean isEmpty() {
        return railsRepoList.isEmpty();
    }
}
