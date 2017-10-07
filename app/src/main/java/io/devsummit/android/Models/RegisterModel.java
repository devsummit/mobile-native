package io.devsummit.android.Models;
/**
 * Created by wlisrausr on 25/09/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.devsummit.android.Models.basemodel.Links;
import io.devsummit.android.Models.basemodel.Meta;
import io.devsummit.android.Models.login.Data;
import io.devsummit.android.Models.login.ProfileData;

public class RegisterModel {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("included")
    @Expose
    private ProfileData profileData;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public ProfileData getProfileData() {
        return profileData;
    }

    public void setProfileData(ProfileData profileData) {
        this.profileData = profileData;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}


