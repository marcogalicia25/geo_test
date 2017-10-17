
package com.meltwater;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document_ {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("sentiment")
    @Expose
    private String sentiment;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("x")
    @Expose
    private Double x;
    @SerializedName("y")
    @Expose
    private Double y;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

}
