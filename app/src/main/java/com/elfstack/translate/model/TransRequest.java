package com.elfstack.translate.model;

import com.google.gson.annotations.SerializedName;

public class TransRequest {

    @SerializedName("from")
    public String from;

    @SerializedName("q")
    public String q;

    @SerializedName("to")
    public String to;

    public TransRequest (String from, String q, String to) {
        this.from = from;
        this.q = q;
        this.to = to;
    }
}
