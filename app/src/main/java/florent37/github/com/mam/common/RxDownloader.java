package florent37.github.com.mam.common;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by florentchampigny on 21/07/2017.
 */

public class RxDownloader {
    @SuppressLint({"StaticFieldLeak"})
    private static RxDownloader mRxDownloader;
    private Context mContext;
    private DownloadManager mDownloadManager;
    private LongSparseArray<PublishSubject<String>> mSubjectMap = new LongSparseArray<>();

    private RxDownloader(Context context) {
        this.mContext = context.getApplicationContext();
        RxDownloader.DownloadStatusReceiver downloadStatusReceiver = new RxDownloader.DownloadStatusReceiver();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE");
        context.registerReceiver(downloadStatusReceiver, intentFilter);
    }

    public static RxDownloader getInstance(Context context) {
        if (mRxDownloader == null) {
            mRxDownloader = new RxDownloader(context.getApplicationContext());
        }

        return mRxDownloader;
    }

    public Observable<String> download(String link, String filename) {
        return this.download(link, filename, "*/*");
    }

    public Observable<String> download(String link, String filename, String mimeType) {
        return this.download(this.getDefaultRequest(link, filename, mimeType));
    }

    public Observable<String> download(DownloadManager.Request request) {
        if (this.mDownloadManager == null) {
            this.mDownloadManager = (DownloadManager) this.mContext.getSystemService("download");
        }

        long downloadId = this.mDownloadManager.enqueue(request);
        PublishSubject publishSubject = PublishSubject.create();
        this.mSubjectMap.put(downloadId, publishSubject);
        return publishSubject;
    }

    private DownloadManager.Request getDefaultRequest(String link, String filename, String mimeType) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
        request.setDescription("Downloading file...");
        request.setMimeType(mimeType);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
        request.setNotificationVisibility(1);
        return request;
    }

    private static class LongSparseArray<E> implements Cloneable {
        private static final Object DELETED = new Object();
        private boolean mGarbage;
        private long[] mKeys;
        private Object[] mValues;
        private int mSize;

        public LongSparseArray() {
            this(10);
        }

        public LongSparseArray(int initialCapacity) {
            this.mGarbage = false;
            if (initialCapacity == 0) {
                this.mKeys = ContainerHelpers.EMPTY_LONGS;
                this.mValues = ContainerHelpers.EMPTY_OBJECTS;
            } else {
                initialCapacity = ContainerHelpers.idealLongArraySize(initialCapacity);
                this.mKeys = new long[initialCapacity];
                this.mValues = new Object[initialCapacity];
            }

            this.mSize = 0;
        }

        public LongSparseArray<E> clone() {
            LongSparseArray clone = null;

            try {
                clone = (LongSparseArray) super.clone();
                clone.mKeys = (long[]) this.mKeys.clone();
                clone.mValues = (Object[]) this.mValues.clone();
            } catch (CloneNotSupportedException var3) {
                ;
            }

            return clone;
        }

        public E get(long key) {
            return this.get(key, null);
        }

        public E get(long key, E valueIfKeyNotFound) {
            int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
            return i >= 0 && this.mValues[i] != DELETED ? (E) this.mValues[i] : valueIfKeyNotFound;
        }

        public void delete(long key) {
            int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
            if (i >= 0 && this.mValues[i] != DELETED) {
                this.mValues[i] = DELETED;
                this.mGarbage = true;
            }

        }

        public void remove(long key) {
            this.delete(key);
        }

        public void removeAt(int index) {
            if (this.mValues[index] != DELETED) {
                this.mValues[index] = DELETED;
                this.mGarbage = true;
            }

        }

        private void gc() {
            int n = this.mSize;
            int o = 0;
            long[] keys = this.mKeys;
            Object[] values = this.mValues;

            for (int i = 0; i < n; ++i) {
                Object val = values[i];
                if (val != DELETED) {
                    if (i != o) {
                        keys[o] = keys[i];
                        values[o] = val;
                        values[i] = null;
                    }

                    ++o;
                }
            }

            this.mGarbage = false;
            this.mSize = o;
        }

        public void put(long key, E value) {
            int i = ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
            if (i >= 0) {
                this.mValues[i] = value;
            } else {
                i = ~i;
                if (i < this.mSize && this.mValues[i] == DELETED) {
                    this.mKeys[i] = key;
                    this.mValues[i] = value;
                    return;
                }

                if (this.mGarbage && this.mSize >= this.mKeys.length) {
                    this.gc();
                    i = ~ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
                }

                if (this.mSize >= this.mKeys.length) {
                    int n = ContainerHelpers.idealLongArraySize(this.mSize + 1);
                    long[] nkeys = new long[n];
                    Object[] nvalues = new Object[n];
                    System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
                    System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
                    this.mKeys = nkeys;
                    this.mValues = nvalues;
                }

                if (this.mSize - i != 0) {
                    System.arraycopy(this.mKeys, i, this.mKeys, i + 1, this.mSize - i);
                    System.arraycopy(this.mValues, i, this.mValues, i + 1, this.mSize - i);
                }

                this.mKeys[i] = key;
                this.mValues[i] = value;
                ++this.mSize;
            }

        }

        public int size() {
            if (this.mGarbage) {
                this.gc();
            }

            return this.mSize;
        }

        public long keyAt(int index) {
            if (this.mGarbage) {
                this.gc();
            }

            return this.mKeys[index];
        }

        public E valueAt(int index) {
            if (this.mGarbage) {
                this.gc();
            }

            return (E) this.mValues[index];
        }

        public void setValueAt(int index, E value) {
            if (this.mGarbage) {
                this.gc();
            }

            this.mValues[index] = value;
        }

        public int indexOfKey(long key) {
            if (this.mGarbage) {
                this.gc();
            }

            return ContainerHelpers.binarySearch(this.mKeys, this.mSize, key);
        }

        public int indexOfValue(E value) {
            if (this.mGarbage) {
                this.gc();
            }

            for (int i = 0; i < this.mSize; ++i) {
                if (this.mValues[i] == value) {
                    return i;
                }
            }

            return -1;
        }

        public void clear() {
            int n = this.mSize;
            Object[] values = this.mValues;

            for (int i = 0; i < n; ++i) {
                values[i] = null;
            }

            this.mSize = 0;
            this.mGarbage = false;
        }

        public void append(long key, E value) {
            if (this.mSize != 0 && key <= this.mKeys[this.mSize - 1]) {
                this.put(key, value);
            } else {
                if (this.mGarbage && this.mSize >= this.mKeys.length) {
                    this.gc();
                }

                int pos = this.mSize;
                if (pos >= this.mKeys.length) {
                    int n = ContainerHelpers.idealLongArraySize(pos + 1);
                    long[] nkeys = new long[n];
                    Object[] nvalues = new Object[n];
                    System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
                    System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
                    this.mKeys = nkeys;
                    this.mValues = nvalues;
                }

                this.mKeys[pos] = key;
                this.mValues[pos] = value;
                this.mSize = pos + 1;
            }
        }

        public String toString() {
            if (this.size() <= 0) {
                return "{}";
            } else {
                StringBuilder buffer = new StringBuilder(this.mSize * 28);
                buffer.append('{');

                for (int i = 0; i < this.mSize; ++i) {
                    if (i > 0) {
                        buffer.append(", ");
                    }

                    long key = this.keyAt(i);
                    buffer.append(key);
                    buffer.append('=');
                    Object value = this.valueAt(i);
                    if (value != this) {
                        buffer.append(value);
                    } else {
                        buffer.append("(this Map)");
                    }
                }

                buffer.append('}');
                return buffer.toString();
            }
        }
    }

    private static class ContainerHelpers {
        static final int[] EMPTY_INTS = new int[0];
        static final long[] EMPTY_LONGS = new long[0];
        static final Object[] EMPTY_OBJECTS = new Object[0];

        ContainerHelpers() {
        }

        public static int idealIntArraySize(int need) {
            return idealByteArraySize(need * 4) / 4;
        }

        public static int idealLongArraySize(int need) {
            return idealByteArraySize(need * 8) / 8;
        }

        public static int idealByteArraySize(int need) {
            for (int i = 4; i < 32; ++i) {
                if (need <= (1 << i) - 12) {
                    return (1 << i) - 12;
                }
            }

            return need;
        }

        public static boolean equal(Object a, Object b) {
            return a == b || a != null && a.equals(b);
        }

        static int binarySearch(int[] array, int size, int value) {
            int lo = 0;
            int hi = size - 1;

            while (lo <= hi) {
                int mid = lo + hi >>> 1;
                int midVal = array[mid];
                if (midVal < value) {
                    lo = mid + 1;
                } else {
                    if (midVal <= value) {
                        return mid;
                    }

                    hi = mid - 1;
                }
            }

            return ~lo;
        }

        static int binarySearch(long[] array, int size, long value) {
            int lo = 0;
            int hi = size - 1;

            while (lo <= hi) {
                int mid = lo + hi >>> 1;
                long midVal = array[mid];
                if (midVal < value) {
                    lo = mid + 1;
                } else {
                    if (midVal <= value) {
                        return mid;
                    }

                    hi = mid - 1;
                }
            }

            return ~lo;
        }
    }

    private class DownloadStatusReceiver extends BroadcastReceiver {
        private DownloadStatusReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra("extra_download_id", 0L);
            PublishSubject publishSubject = (PublishSubject) RxDownloader.this.mSubjectMap.get(id);
            if (publishSubject != null) {
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(new long[]{id});
                Cursor cursor = RxDownloader.this.mDownloadManager.query(query);
                if (!cursor.moveToFirst()) {
                    RxDownloader.this.mDownloadManager.remove(new long[]{id});
                    publishSubject.onError(new IllegalStateException("Cursor empty, this shouldn\'t happened"));
                    RxDownloader.this.mSubjectMap.remove(id);
                } else {
                    int statusIndex = cursor.getColumnIndex("status");
                    if (8 != cursor.getInt(statusIndex)) {
                        RxDownloader.this.mDownloadManager.remove(new long[]{id});
                        publishSubject.onError(new IllegalStateException("Download Failed"));
                        RxDownloader.this.mSubjectMap.remove(id);
                    } else {
                        int uriIndex = cursor.getColumnIndex("local_uri");
                        String downloadedPackageUriString = cursor.getString(uriIndex);
                        publishSubject.onNext(downloadedPackageUriString);
                        publishSubject.onComplete();
                        RxDownloader.this.mSubjectMap.remove(id);
                    }
                }
            }
        }
    }


}
