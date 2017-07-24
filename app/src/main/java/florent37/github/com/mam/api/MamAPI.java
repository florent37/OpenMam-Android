package florent37.github.com.mam.api;

import florent37.github.com.mam.model.AppResponse;
import florent37.github.com.mam.model.AppsResponse;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by florentchampigny on 18/06/2017.
 */

public interface MamAPI {

    @GET("apps/")
    Single<AppsResponse> apps();

    @GET("apps/{apkName}")
    Single<AppResponse> app(@Path("apkName") String name);

}
