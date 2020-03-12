package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by papahnakal on 15/11/17.
 */

public class RequestNotifModel extends BaseObject {

    @SerializedName("response_value")
    private List<Response_value> response_value;

    public List<Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(List<Response_value> response_value) {
        this.response_value = response_value;
    }

    public static class Response_value {
        @SerializedName("id_tipe_notif")
        private String id_tipe_notif;
        @SerializedName("subject")
        private String subject;
        @SerializedName("content")
        private String content;
        @SerializedName("thumbnail")
        private String thumbnail;
        @SerializedName("link")
        private String link;
        @SerializedName("url_icon")
        private String url_icon;

        public String getId_tipe_notif() {
            return id_tipe_notif;
        }

        public void setId_tipe_notif(String id_tipe_notif) {
            this.id_tipe_notif = id_tipe_notif;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getUrl_icon() {
            return url_icon;
        }

        public void setUrl_icon(String url_icon) {
            this.url_icon = url_icon;
        }
    }
}
