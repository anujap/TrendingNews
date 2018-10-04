package com.example.anuja.trendingnews.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This is the class for Articles model
 */
public class Articles implements Parcelable {

    @SerializedName("source")
    private Source source;

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String urlToImage;

    @SerializedName("publishedAt")
    private String publishedAt;

    @SerializedName("content")
    private String content;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeO(this.sourceArrayList);
        parcel.writeString(this.author);
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeString(this.url);
        parcel.writeString(this.urlToImage);
        parcel.writeString(this.publishedAt);
        parcel.writeString(this.content);
    }

    private Articles(Parcel in) {
        this.author = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.urlToImage = in.readString();
        this.publishedAt = in.readString();
        this.content = in.readString();
    }

    /**
     * generates instances of the Parcelable class from a Parcel
     */
    public static final Parcelable.Creator<Articles> CREATOR = new Parcelable.Creator<Articles>() {

        /**
         * create a new instance of the Parcelable class, instantiating it
         * from the given Parcel whose data had previously been written by
         */
        @Override
        public Articles createFromParcel(Parcel parcel) {
            return new Articles(parcel);
        }

        // create a new array of the Parcelable class.
        @Override
        public Articles[] newArray(int size) {
            return new Articles[size];
        }
    };

    /**
     * describe the kinds of special objects contained in this
     * Parcelable instance's marshaled representation.
     */
    @Override
    public int describeContents() {
        return 0;
    }
}