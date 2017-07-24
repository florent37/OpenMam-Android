package florent37.github.com.mam.common;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v4.app.Fragment;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by florentchampigny on 20/06/2017.
 */

public class BaseFragment extends Fragment implements LifecycleRegistryOwner {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    protected void call(Disposable disposable){
        compositeDisposable.add(disposable);
    }
}