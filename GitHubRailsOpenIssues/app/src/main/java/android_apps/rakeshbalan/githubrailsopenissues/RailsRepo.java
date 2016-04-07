package android_apps.rakeshbalan.githubrailsopenissues;

import java.util.Date;

/**
 * Created by rakeshbalan on 4/7/2016.
 */
public class RailsRepo {
    private String issueTitle, issueBody, commentsURL;
    private Date issueUpdatedDate;

    public RailsRepo() {
        this.issueTitle = null;
        this.issueBody = null;
        this.commentsURL = null;
        this.issueUpdatedDate = null;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(String issueBody) {
        this.issueBody = issueBody;
    }

    public String getCommentsURL() {
        return commentsURL;
    }

    public void setCommentsURL(String commentsURL) {
        this.commentsURL = commentsURL;
    }

    public Date getIssueUpdatedDate() {
        return issueUpdatedDate;
    }

    public void setIssueUpdatedDate(Date issueUpdatedDate) {
        this.issueUpdatedDate = issueUpdatedDate;
    }
}
