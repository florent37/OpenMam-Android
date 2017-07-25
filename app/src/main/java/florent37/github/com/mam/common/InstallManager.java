package florent37.github.com.mam.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import java.io.File;

import florent37.github.com.mam.BuildConfig;

/**
 * Created by florentchampigny on 24/07/2017.
 */

public class InstallManager {

    private Context appContext;

    public InstallManager(Context appContext) {
        this.appContext = appContext;
    }

    public void installApk(String appName) {
        final String appDirectory = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOWNLOADS;
        final File toInstall = new File(appDirectory, appName + ".apk");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final Uri apkUri = FileProvider.getUriForFile(appContext, BuildConfig.APPLICATION_ID + ".provider", toInstall);
            appContext.startActivity(new Intent(Intent.ACTION_INSTALL_PACKAGE)
                    .setData(apkUri)
                    .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION));
        } else {
            final Uri apkUri = Uri.fromFile(toInstall);
            appContext.startActivity(new Intent(Intent.ACTION_VIEW)
                    .setDataAndType(apkUri, "application/vnd.android.package-archive")
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }
}
