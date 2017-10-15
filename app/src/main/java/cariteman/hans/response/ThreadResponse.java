package cariteman.hans.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cariteman.hans.datamodel.ThreadModel;

/**
 * Created by HansSagita on 15/10/17.
 */

public class ThreadResponse {

    @SerializedName("count")
    private Integer count;
    @SerializedName("results")
    private List<ThreadModel> results = null;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ThreadModel> getResults() {
        return results;
    }

    public void setResults(List<ThreadModel> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
