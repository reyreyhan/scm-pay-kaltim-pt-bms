package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by papahnakal on 13/11/17.
 */

public class NotificationModel extends BaseObject implements Parcelable{

    @SerializedName("response_value")
    private List<Response_value> response_value;


    protected NotificationModel(Parcel in) {
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @NonNull
        @Override
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        @NonNull
        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };

    public List<Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(List<Response_value> response_value) {
        this.response_value = response_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Response_value implements Parcelable{
        @Nullable
        @SerializedName("id_tipe_notif")
        private String id_tipe_notif;
        @Nullable
        @SerializedName("subject")
        private String subject;
        @Nullable
        @SerializedName("content")
        private String content;
        @Nullable
        @SerializedName("thumbnail")
        private String thumbnail;
        @Nullable
        @SerializedName("link")
        private String link;
        @Nullable
        @SerializedName("url_icon")
        private String url_icon;
        @Nullable
        @SerializedName("date_created")
        private String date_created;

        protected Response_value(@NonNull Parcel in) {
            id_tipe_notif = in.readString();
            subject = in.readString();
            content = in.readString();
            thumbnail = in.readString();
            link = in.readString();
            url_icon = in.readString();
            date_created = in.readString();
        }

        public static final Creator<Response_value> CREATOR = new Creator<Response_value>() {
            @NonNull
            @Override
            public Response_value createFromParcel(@NonNull Parcel in) {
                return new Response_value(in);
            }

            @NonNull
            @Override
            public Response_value[] newArray(int size) {
                return new Response_value[size];
            }
        };

        @Nullable
        public String getId_tipe_notif() {
            return id_tipe_notif;
        }

        public void setId_tipe_notif(String id_tipe_notif) {
            this.id_tipe_notif = id_tipe_notif;
        }

        @Nullable
        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        @Nullable
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Nullable
        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Nullable
        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        @Nullable
        public String getUrl_icon() {
            return url_icon;
        }

        public void setUrl_icon(String url_icon) {
            this.url_icon = url_icon;
        }

        @Nullable
        public String getDate_created() {
            return date_created;
        }

        public void setDate_created(String date_created) {
            this.date_created = date_created;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(id_tipe_notif);
            dest.writeString(subject);
            dest.writeString(content);
            dest.writeString(thumbnail);
            dest.writeString(link);
            dest.writeString(url_icon);
            dest.writeString(date_created);
        }
    }
}
