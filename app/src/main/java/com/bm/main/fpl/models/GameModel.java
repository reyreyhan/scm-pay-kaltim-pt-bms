package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GameModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private GameModel(Parcel in) {
    }

    public static final Creator<GameModel> CREATOR = new Creator<GameModel>() {
        @NonNull
        @Override
        public GameModel createFromParcel(Parcel in) {
            return new GameModel(in);
        }

        @NonNull
        @Override
        public GameModel[] newArray(int size) {
            return new GameModel[size];
        }
    };

    public ArrayList<GameModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<GameModel.Response_value> response_value) {
        this.response_value = response_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Response_value implements Parcelable {

        @Nullable
        @SerializedName("game_url")
        private String game_url;
        @Nullable
        @SerializedName("is_gangguan")
        private String is_gangguan;
        @Nullable
        @SerializedName("is_promo")
        private String is_promo;
        @Nullable
        @SerializedName("is_flag")
        private String is_flag;
        @Nullable
        @SerializedName("game_code")
        private String game_code;
        @Nullable
        @SerializedName("game_name")
        private String game_name;



        protected Response_value(@NonNull Parcel in) {
            game_url = in.readString();
            is_gangguan = in.readString();
            is_promo = in.readString();
            is_flag = in.readString();
            game_code = in.readString();
            game_name = in.readString();

        }

        public static final Creator<GameModel.Response_value> CREATOR = new Creator<GameModel.Response_value>() {
            @NonNull
            @Override
            public GameModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new GameModel.Response_value(in);
            }

            @NonNull
            @Override
            public GameModel.Response_value[] newArray(int size) {
                return new GameModel.Response_value[size];
            }
        };

        @Nullable
        public String getGame_url() {
            return game_url;
        }

        public void setGame_url(String game_url) {
            this.game_url = game_url;
        }

        @Nullable
        public String getIs_gangguan() {
            return is_gangguan;
        }

        public void setIs_gangguan(String is_gangguan) {
            this.is_gangguan = is_gangguan;
        }

        @Nullable
        public String getIs_promo() {
            return is_promo;
        }

        public void setIs_promo(String is_promo) {
            this.is_promo = is_promo;
        }

        @Nullable
        public String getGame_code() {
            return game_code;
        }

        public void setGame_code(String game_code) {
            this.game_code = game_code;
        }

        @Nullable
        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        @Nullable
        public String getIs_flag() {
            return is_flag;
        }

        public void setIs_flag(String is_flag) {
            this.is_flag = is_flag;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.game_url);
            dest.writeString(this.is_gangguan);
            dest.writeString(this.is_promo);
            dest.writeString(this.is_flag);
            dest.writeString(this.game_code);
            dest.writeString(this.game_name);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
