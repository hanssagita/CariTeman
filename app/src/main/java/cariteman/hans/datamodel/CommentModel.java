package cariteman.hans.datamodel;

/**
 * Created by HansSagita on 17/10/17.
 */

public class CommentModel {

    private String threadId;
    private String eventId;
    private String commentContent;
    private String memberId;
    private Integer likes;
    private String commentDate;
    private String hostImg;
    private String hostedBy;

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getHostImg() {
        return hostImg;
    }

    public void setHostImg(String hostImg) {
        this.hostImg = hostImg;
    }

    public String getHostedBy() {
        return hostedBy;
    }

    public void setHostedBy(String hostedBy) {
        this.hostedBy = hostedBy;
    }
}
