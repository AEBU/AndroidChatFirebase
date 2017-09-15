package ec.uce.lex.androidchatproject.lib;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import ec.uce.lex.androidchatproject.lib.ImageLoader;

/**
 * Created by Alexis on 15/09/2017.
 */

public class GlideImageLoader implements ImageLoader {

    private RequestManager requestManager;

    public GlideImageLoader(Context context) {

        this.requestManager = Glide.with(context);
    }

    @Override
    public void load(ImageView imgAvatar, String url) {
        requestManager.load(url).into(imgAvatar);

    }
}
