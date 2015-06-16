package androidpath.ll.material.Utils;

import android.os.Build;

/**
 * Created by Le on 2015/6/16.
 */
public class SDKVersionUtil {

    public static boolean isLolipopOrGreater() {
        return Build.VERSION.SDK_INT >= 21;
    }

    public static boolean isJellyBeanOrGreater() {
        return Build.VERSION.SDK_INT >= 16;
    }

}
