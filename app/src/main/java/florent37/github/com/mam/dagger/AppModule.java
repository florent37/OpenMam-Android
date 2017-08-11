package florent37.github.com.mam.dagger;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import florent37.github.com.mam.BuildConfig;
import florent37.github.com.mam.api.MamAPI;
import florent37.github.com.mam.common.ColorGenerator;
import florent37.github.com.mam.common.DownloadManager;
import florent37.github.com.mam.common.InstallManager;
import florent37.github.com.mam.common.StorageManager;
import florent37.github.com.mam.repository.AppRepository;
import mam.repository.AppRepositoryImpl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by florentchampigny on 18/05/2017.
 */

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public AppRepository provideAppsRepository(MamAPI mamAPI, StorageManager storageManager) {
        return new AppRepositoryImpl(mamAPI, storageManager);
    }

    @Provides
    @Singleton
    public InstallManager provideInstallManager() {
        return new InstallManager(application);
    }

    @Provides
    @Singleton
    public DownloadManager provideDownloadManager() {
        return new DownloadManager(application);
    }

    @Provides
    @Singleton
    public StorageManager provideStorageManager() {
        return new StorageManager(application);
    }

    @Provides
    @Singleton
    public OkHttpClient provideMainOkHttp(StorageManager storageManager) {
        return new OkHttpClient.Builder()
                .addInterceptor(new StethoInterceptor())
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final Request original = chain.request();

                        // Request customization: add request headers
                        final Request.Builder requestBuilder = original.newBuilder();

                        final String token = storageManager.getToken();
                        if (token != null) {
                            requestBuilder.header("Authorization", "Bearer " + token);
                        }

                        return chain.proceed(requestBuilder.build());
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Provides
    @Singleton
    public ColorGenerator provideColorGenerator() {
        return new ColorGenerator(application);
    }

    @Provides
    @Singleton
    public MamAPI providesGithubApi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.MAM_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MamAPI.class);
    }
}
