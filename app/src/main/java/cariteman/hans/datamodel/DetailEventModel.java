package cariteman.hans.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HansSagita on 14/10/17.
 */

public class DetailEventModel {

    @SerializedName("eventId")
    private String eventId;
    @SerializedName("category")
    private String category;
    @SerializedName("status")
    private String status;
    @SerializedName("eventName")
    private String eventName;
    @SerializedName("location")
    private String location;
    @SerializedName("totalPeople")
    private Integer totalPeople;
    @SerializedName("quota")
    private Integer quota;
    @SerializedName("backgroundImg")
    private String backgroundImg;
    @SerializedName("hostImg")
    private String hostImg;
    @SerializedName("hostedBy")
    private String hostedBy;
    @SerializedName("dateResponse")
    private String dateResponse;
    @SerializedName("description")
    private String description;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(Integer totalPeople) {
        this.totalPeople = totalPeople;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
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

    public String getDateResponse() {
        return dateResponse;
    }

    public void setDateResponse(String dateResponse) {
        this.dateResponse = dateResponse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
