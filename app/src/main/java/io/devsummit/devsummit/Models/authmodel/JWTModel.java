package io.devsummit.devsummit.Models.authmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ganesh on 27/09/17.
 */

public class JWTModel {
    @SerializedName("alg")
    @Expose
    private String alg;
    @SerializedName("iat")
    @Expose
    private Integer iat;
    @SerializedName("exp")
    @Expose
    private Integer exp;

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public Integer getIat() {
        return iat;
    }

    public void setIat(Integer iat) {
        this.iat = iat;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

}
