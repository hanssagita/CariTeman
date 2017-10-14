package cariteman.hans.datamodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hans.sagita on 12/10/2017.
 */

public class EventModel {


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
    private Object backgroundImg;
    @SerializedName("hostImg")
    private Object hostImg;
    @SerializedName("hostedBy")
    private String hostedBy;
    @SerializedName("dateResponse")
    private String dateResponse;
    @SerializedName("markForDelete")
    private Boolean markForDelete;
    @SerializedName("private")
    private Boolean _private;

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

    public Object getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(Object backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public Object getHostImg() {
        return hostImg;
    }

    public void setHostImg(Object hostImg) {
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

    public Boolean getMarkForDelete() {
        return markForDelete;
    }

    public void setMarkForDelete(Boolean markForDelete) {
        this.markForDelete = markForDelete;
    }

    public Boolean getPrivate() {
        return _private;
    }

    public void setPrivate(Boolean _private) {
        this._private = _private;
    }
}
