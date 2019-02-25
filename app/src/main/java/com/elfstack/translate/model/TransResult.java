package com.elfstack.translate.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TransResult {

    @SerializedName("from")
    public String from;

    @SerializedName("to")
    public String to;

    @SerializedName("trans_result")
    public List<TransLine> transResult;
}
