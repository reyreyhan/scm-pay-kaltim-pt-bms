package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by papahnakal on 15/11/17.
 */

public class NotifTypeModel extends BaseObject{

    @SerializedName("response_value")
    private List<Response_value> response_value;


    public List<Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(List<Response_value> response_value) {
        this.response_value = response_value;
    }

    public static class Response_value {
        @SerializedName("id_type")
        private String id_type;
        @SerializedName("nama_type")
        private String nama_type;
        @SerializedName("url_icon")
        private String url_icon;
        public Response_value(String id_type,String nama_type,String url_icon){
            this.id_type = id_type;
            this.nama_type = nama_type;
            this.url_icon = url_icon;
        }
        public String getId_type() {
            return id_type;
        }

        public void setId_type(String id_type) {
            this.id_type = id_type;
        }

        public String getNama_type() {
            return nama_type;
        }

        public void setNama_type(String nama_type) {
            this.nama_type = nama_type;
        }

        public String getUrl_icon() {
            return url_icon;
        }

        public void setUrl_icon(String url_icon) {
            this.url_icon = url_icon;
        }
    }
}
