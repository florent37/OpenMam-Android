package florent37.github.com.mam.ui.apps;

import java.util.List;

import florent37.github.com.mam.model.App;

public interface AppsAdapter<ME> {

    ME onClick(ClickListener clickListener);

    void setItems(List<App> items);
}
