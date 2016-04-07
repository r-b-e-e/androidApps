package android_apps.rakeshbalan.githubrailsopenissues;

/**
 * Created by rakeshbalan on 4/7/2016.
 */
public class RailsRepoComment {
    private String userName,commentBody;

    public RailsRepoComment() {
        userName = null;
        commentBody = null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }
}
