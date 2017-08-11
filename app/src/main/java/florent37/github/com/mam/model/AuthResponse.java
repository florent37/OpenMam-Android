package florent37.github.com.mam.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by florentchampigny on 11/08/2017.
 */

public class AuthResponse {

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
