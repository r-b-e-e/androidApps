package android_apps.rakeshbalan.githubrailsopenissues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by rakeshbalan on 4/7/2016.
 */
public class JSONUtility {
    public static ArrayList<RailsRepo> parseJsonRailsRepo(JSONArray jsonArrayRailsRepo){
        ArrayList<RailsRepo> railsRepoList = new ArrayList<RailsRepo>();
        SimpleDateFormat parseDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");    //issue's updated date format

        try
        {
            for (int i = 0; i < jsonArrayRailsRepo.length(); i++)
            {
                JSONObject jsonObjectRailsRepo = jsonArrayRailsRepo.getJSONObject(i);
                RailsRepo railsRepo = new RailsRepo();

                //filtering out only the open issues
                if (jsonObjectRailsRepo.getString("state").equals("open"))
                {
                    railsRepo.setIssueTitle(jsonObjectRailsRepo.getString("title").trim());
                    railsRepo.setIssueBody(jsonObjectRailsRepo.getString("body").trim());
                    railsRepo.setIssueUpdatedDate(parseDateFormat.parse(jsonObjectRailsRepo.getString("updated_at")));
                    //Log.d("d", parseDateFormat.parse(jsonObjectRailsRepo.getString("updated_at")).toString());
                    railsRepo.setCommentsURL(jsonObjectRailsRepo.getString("comments_url").trim());
                    railsRepoList.add(railsRepo);
                }
            }

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        // arranging the list with the most recent first
        Collections.sort(railsRepoList, new Comparator<RailsRepo>() {
            @Override
            public int compare(RailsRepo lhs, RailsRepo rhs) {
                if (lhs.getIssueUpdatedDate() == null || rhs.getIssueUpdatedDate() == null) {
                    return 0;
                } else {
                    return rhs.getIssueUpdatedDate().compareTo(lhs.getIssueUpdatedDate());
                }
            }
        });

        return railsRepoList;
    }


    public static ArrayList<RailsRepoComment> parseJsonRailsRepoComments(JSONArray jsonArrayRailsRepoComment){
        ArrayList<RailsRepoComment> railsRepoCommentsList = new ArrayList<RailsRepoComment>();

        try
        {
            for (int i = 0; i < jsonArrayRailsRepoComment.length(); i++)
            {
                JSONObject jsonObject = jsonArrayRailsRepoComment.getJSONObject(i);
                RailsRepoComment railsRepoComment = new RailsRepoComment();
                railsRepoComment.setCommentBody(jsonObject.getString("body").trim());

                railsRepoComment.setUserName(jsonObject.getJSONObject("user").getString("login"));
                railsRepoCommentsList.add(railsRepoComment);
            }

        }catch (JSONException e)
        {
            e.printStackTrace();
        }


        return railsRepoCommentsList;
    }
}
