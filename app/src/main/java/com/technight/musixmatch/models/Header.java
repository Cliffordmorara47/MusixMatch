
package com.technight.musixmatch.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Header {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("execute_time")
    @Expose
    private Float executeTime;
    @SerializedName("available")
    @Expose
    private Integer available;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Header() {
    }

    /**
     * 
     * @param available
     * @param statusCode
     * @param executeTime
     */
    public Header(Integer statusCode, Float executeTime, Integer available) {
        super();
        this.statusCode = statusCode;
        this.executeTime = executeTime;
        this.available = available;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Float getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Float executeTime) {
        this.executeTime = executeTime;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

}
