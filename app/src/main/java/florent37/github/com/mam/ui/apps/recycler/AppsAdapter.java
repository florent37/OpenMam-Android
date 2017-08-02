package florent37.github.com.mam.ui.apps.recycler;

import java.util.List;

import florent37.github.com.mam.model.App;
import florent37.github.com.mam.ui.apps.ClickListener;

public interface AppsAdapter<ME> {

    ME onClick(ClickListener clickListener);

    void setItems(List<App> items);
}
