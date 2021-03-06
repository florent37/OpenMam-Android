package florent37.github.com.mam.api;

import florent37.github.com.mam.model.AppResponse;
import florent37.github.com.mam.model.AppsResponse;
import florent37.github.com.mam.model.AuthResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by florentchampigny on 18/06/2017.
 */

public interface MamAPI {

    @GET("api/")
    Single<AppsResponse> apps();

    @GET("api/{apkName}")
    Single<AppResponse> app(@Path("apkName") String name);

    @POST("api/login_check")
    @FormUrlEncoded
    Single<AuthResponse> auth(@Field("_username") String login, @Field("_password") String password);
}
