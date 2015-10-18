package appdatamx.hackcolima.roberto.reporta.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roberto Avalos on 18/10/2015.
 */
public class ComplaintModel implements Parcelable{

    @SerializedName("id")
    int id;

    @SerializedName("description")
    String description;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("picture")
    Picture picture;

    @SerializedName("created_at")
    String created_at;

    @SerializedName("updated_at")
    String updated_at;

    @SerializedName("user_id")
    int user_id;

    @SerializedName("category_id")
    int category_id;

    protected ComplaintModel(Parcel in) {
        id = in.readInt();
        description = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        user_id = in.readInt();
        category_id = in.readInt();
    }

    public static final Creator<ComplaintModel> CREATOR = new Creator<ComplaintModel>() {
        @Override
        public ComplaintModel createFromParcel(Parcel in) {
            return new ComplaintModel(in);
        }

        @Override
        public ComplaintModel[] newArray(int size) {
            return new ComplaintModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(description);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeInt(user_id);
        dest.writeInt(category_id);
    }

    class Picture{
        @SerializedName("url")
        String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

}
