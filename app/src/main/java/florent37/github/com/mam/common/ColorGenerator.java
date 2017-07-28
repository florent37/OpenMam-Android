package florent37.github.com.mam.common;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import florent37.github.com.mam.R;

/**
 * Created by florentchampigny on 28/07/2017.
 */

public class ColorGenerator {

    private Context appContext;

    private List<Integer> colors = new ArrayList<>();

    public ColorGenerator(Context appContext) {
        this.appContext = appContext;

        colors.add(ContextCompat.getColor(appContext,R.color.md_blue_500));
        colors.add(ContextCompat.getColor(appContext,R.color.md_green_500));
        colors.add(ContextCompat.getColor(appContext,R.color.md_red_500));
        colors.add(ContextCompat.getColor(appContext,R.color.md_orange_500));
        colors.add(ContextCompat.getColor(appContext,R.color.md_grey_500));
        colors.add(ContextCompat.getColor(appContext,R.color.md_purple_500));
        colors.add(ContextCompat.getColor(appContext,R.color.md_brown_500));
        colors.add(ContextCompat.getColor(appContext,R.color.md_indigo_500));
    }

    public int generateColor(String text){
        final int hashCode = text.hashCode();
        return colors.get(hashCode % colors.size());
    }
}
