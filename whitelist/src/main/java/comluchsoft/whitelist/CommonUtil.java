package comluchsoft.whitelist;

import android.content.Context;
import android.os.Build;
import android.os.PowerManager;

import java.util.Locale;

/**
 * Time：2023/4/3
 * Author：feng
 * Description：
 */
public class CommonUtil {
    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 确定应用程序是否已经忽略电池优化
     *
     * @return
     */
    public static boolean isIgnoringBatteryOptimizations(Context context) {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                isIgnoring = powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
            }
        }
        return isIgnoring;
    }
}
