package cariteman.hans.response;

import com.google.gson.annotations.SerializedName;

import cariteman.hans.datamodel.DetailEventModel;

/**
 * Created by HansSagita on 14/10/17.
 */

public class DetailEventResponse {


    @SerializedName("result")
    private DetailEventModel result;
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public DetailEventModel getResult() {
        return result;
    }

    public void setResult(DetailEventModel result) {
        this.result = result;
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
