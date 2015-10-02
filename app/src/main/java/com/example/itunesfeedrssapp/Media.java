package com.example.itunesfeedrssapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by batesjernigan on 10/2/15.
 */
public class Media implements Parcelable{

    String title, artist, duration, category, release_Date, link, summary;
    int price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRelease_Date() {
        return release_Date;
    }

    public void setRelease_Date(String release_Date) {
        this.release_Date = release_Date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        public Media createFromParcel(Parcel in) {
            return new Media(in);
        }

        public Media[] newArray(int size) {
            return new Media[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(duration);
        dest.writeString(category);
        dest.writeString(release_Date);
        dest.writeString(link);
        dest.writeString(summary);
        dest.writeInt(price);
    }

    private Media(Parcel in) {
        this.title = in.readString();
        this.artist = in.readString();
        this.duration = in.readString();
        this.category = in.readString();
        this.release_Date = in.readString();
        this.link = in.readString();
        this.summary = in.readString();
        this.price = in.readInt();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Media{" +
            "artist='" + artist + '\'' +
            ", title='" + title + '\'' +
            ", duration='" + duration + '\'' +
            ", category='" + category + '\'' +
            ", release_Date='" + release_Date + '\'' +
            ", link='" + link + '\'' +
            ", summary='" + summary + '\'' +
            ", price=" + price +
            '}';
    }
}
