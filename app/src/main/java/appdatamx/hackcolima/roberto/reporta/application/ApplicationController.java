package appdatamx.hackcolima.roberto.reporta.application;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by Robert Avalos on 18/10/15.
 */
public class ApplicationController extends Application {
    private ImageLoader imageLoader = null;
    private static ApplicationController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());


        mInstance = this;
    }

    public static synchronized ApplicationController getInstance() {
        return mInstance;
    }

    public ImageLoader getImageLoader(Context ctx){
        if (imageLoader == null)
            imageLoader = ImageLoader.getInstance();
        File cacheDir = StorageUtils.getCacheDirectory(ctx);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                ctx).threadPriority(5)
                //.diskCache(new UnlimitedDiscCache(cacheDir))
                        //.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        if (!imageLoader.isInited())
            imageLoader.init(config);
        return imageLoader;
    }

}
