
package com.example.snakechat.data.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllClientData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("pin_code_for_test")
    @Expose
    private Integer pinCodeForTest;

    public Integer getPinCodeForTest() {
        return pinCodeForTest;
    }

    public void setPinCodeForTest(Integer pinCodeForTest) {
        this.pinCodeForTest = pinCodeForTest;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
