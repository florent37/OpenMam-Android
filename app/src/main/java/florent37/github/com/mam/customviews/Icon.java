package florent37.github.com.mam.customviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import florent37.github.com.mam.MainApplication;
import florent37.github.com.mam.R;
import florent37.github.com.mam.common.ColorGenerator;
import florent37.github.com.mam.dagger.AppComponent;

/**
 * Created by florentchampigny on 28/07/2017.
 */

public class Icon extends FrameLayout {

    private TextView letter;
    private ImageView imageView;
    private ImageView iconBackground;

    @Inject
    ColorGenerator colorGenerator;

    public Icon(@NonNull Context context) {
        this(context, null);
    }

    public Icon(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Icon(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        addView(LayoutInflater.from(context).inflate(R.layout.layout_icon, this, false));

        AppComponent.from(context).inject(this);

        iconBackground = (ImageView) findViewById(R.id.iconBackground);
        letter = (TextView) findViewById(R.id.iconLetter);
        imageView = (ImageView) findViewById(R.id.iconImage);
    }

    public void loadText(String text){
        letter.setText(String.valueOf(text.charAt(0)));
        iconBackground.setColorFilter(colorGenerator.generateColor(text));
    }

    public void loadUrl(String url){
        letter.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);

        if (url != null) {
            Glide.with(getContext()).load(url).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    imageView.setVisibility(View.INVISIBLE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    letter.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    return false;
                }
            }).into(imageView);
        }
    }


}
