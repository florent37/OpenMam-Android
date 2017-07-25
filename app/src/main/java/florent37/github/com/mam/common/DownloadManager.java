package florent37.github.com.mam.common;

import android.content.Context;

import io.reactivex.Observable;

/**
 * Created by florentchampigny on 24/07/2017.
 */
public class DownloadManager {

    private final Context appContext;

    public DownloadManager(Context appContext) {
        this.appContext = appContext;
    }

    public Observable<String> download(String url, String fileName){
        return RxDownloader.getInstance(appContext)
                .download(url, fileName);
    }
}
