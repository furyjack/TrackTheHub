package play.android.com.trackthehub.util;



public class Event {


  public   String user;
    String branch;
   public String repo;
    String commit;
   public String type;
    String commitMessage;

    public Event(String user, String repo, String type) {
        this.user = user;
        this.repo = repo;
        this.type = type;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Event() {
    }
}
