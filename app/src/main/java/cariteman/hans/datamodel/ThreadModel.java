package cariteman.hans.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HansSagita on 15/10/17.
 */

public class ThreadModel {

    @SerializedName("threadId")
    private String threadId;
    @SerializedName("eventId")
    private String eventId;
    @SerializedName("message")
    private String message;
    @SerializedName("memberId")
    private String memberId;
    @SerializedName("comments")
    private Integer comments;
    @SerializedName("likes")
    private Integer likes;
    @SerializedName("postinganDate")
    private String postinganDate;
    @SerializedName("hostImg")
    private String hostImg;
    @SerializedName("hostedBy")
    private String hostedBy;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getPostinganDate() {
        return postinganDate;
    }

    public void setPostinganDate(String postinganDate) {
        this.postinganDate = postinganDate;
    }

}
