package florent37.github.com.mam.repository;

import java.util.List;

import florent37.github.com.mam.model.App;
import florent37.github.com.mam.model.AppVersion;
import florent37.github.com.mam.model.AuthResponse;
import io.reactivex.Single;

/**
 * Created by florentchampigny on 18/06/2017.
 */

public interface AppRepository {
    Single<AuthResponse> auth(String login, String password);
    Single<List<App>> applications();
    Single<List<AppVersion>> application(String name);
}
