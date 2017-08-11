package florent37.github.com.mam.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

/**
 * Created by florentchampigny on 11/08/2017.
 */

public class StorageManager {

    static final String STORAGE = "storage";
    static final String TOKEN = "token";
    private final SharedPreferences sharedPreferences;

    public StorageManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public void saveToken(String token){
        sharedPreferences.edit()
                .putString(TOKEN, token)
                .apply();
    }

    @Nullable
    public String getToken(){
        return sharedPreferences.getString(TOKEN, null);
    }
}
