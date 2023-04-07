package comluchsoft.whitelist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;



/**
 * Time：2023/4/3
 * Author：feng
 * Description：
 */
public class WhiteListManage {
    private Context context;
    public WhiteListManage(Context context){
        this.context = context;
    }

    public boolean openAuto(){
        Intent intents = null;
        for (Intent intent : AutoStartAppUtil.POWERMANAGER_INTENTS)
            if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                intents = intent;
                LogUtils.dXlog("  =======================> "+intents.getComponent().getClassName());
                break;
            }
        LogUtils.dXlog("手机品牌 device brand：" + CommonUtil.getDeviceBrand());
        if (intents != null) {
            if (CommonUtil.getDeviceBrand().equalsIgnoreCase("Infinix")) {
                LogUtils.dXlog("  =======================> DeviceBrand: Infinix");
                whiteDialog();
                return false;
            }
            if (CommonUtil.getDeviceBrand().equalsIgnoreCase("OPPO") || CommonUtil.getDeviceBrand().equalsIgnoreCase("realme")) {
                if (ContextCompat.checkSelfPermission(context, "oppo.permission.OPPO_COMPONENT_SAFE") != PackageManager.PERMISSION_GRANTED) {
                    LogUtils.dXlog("  =======================> ");
                    // oppo 为系统权限不对外开放
                    whiteDialog();
                    return false;
                } else {
                   context.startActivity(intents);
                    LogUtils.dXlog("  =======================>打开 oppo 自启动页面成功");
                    return true;
                }
            }
            if (CommonUtil.getDeviceBrand().equalsIgnoreCase("HUAWEI")) {
                LogUtils.dXlog("  =======================> ");
                whiteDialog();
                return false;
            }
            context.startActivity(intents);
            LogUtils.dXlog("  =======================> ");
            return true;
        } else {
            if (CommonUtil.getDeviceBrand().equalsIgnoreCase("OnePlus")) {
                intents = new Intent("com.android.settings.action.BACKGROUND_OPTIMIZE");
                context.startActivity(intents);
                LogUtils.dXlog("  =======================> OnePlus");
                return true;
            }
            whiteDialog();
            LogUtils.dXlog("  =======================> ");
            return false;
        }
    }


    public  String getTutorials(){
        String brand = CommonUtil.getDeviceBrand();
        String htmlName = "infinix";
        if (brand.equalsIgnoreCase("huawei")) {
            htmlName = "huawei";
        } else if (brand.equalsIgnoreCase("honor")) {
            htmlName = "honor";
        } else if (brand.equalsIgnoreCase("infinix")) {
            htmlName = "infinix";
        } else if (brand.equalsIgnoreCase("meizu")) {
            htmlName = "meizu";
        } else if (brand.equalsIgnoreCase("xiaomi")) {
            htmlName = "xiaomi";
        } else if (brand.equalsIgnoreCase("redmi")) {
            htmlName = "redmi";
        } else if (brand.equalsIgnoreCase("oppo")) {
            htmlName = "oppo";
        } else if (brand.equalsIgnoreCase("realme")) {
            htmlName = "realme";
        } else if (brand.equalsIgnoreCase("onePlus")) {
            htmlName = "onePlus";
        } else if (brand.equalsIgnoreCase("vivo")) {
            htmlName = "vivo";
        } else if (brand.equalsIgnoreCase("iqoo")) {
            htmlName = "iqoo";
        } else if (brand.equalsIgnoreCase("smartisan")) {
            htmlName = "smartisan";
        } else if (brand.equalsIgnoreCase("sumsang")) {
            htmlName = "sumsang";
        } else if (brand.equalsIgnoreCase("asus")) {
            htmlName = "asus";
        } else if (brand.equalsIgnoreCase("lenovo")) {
            htmlName = "lenovo";
        } else if (brand.equalsIgnoreCase("nubia")) {
            htmlName = "nubia";
        }
        if (!CommonUtil.getSystemLanguage().contains("zh")) {// 非中文
            htmlName = htmlName + "_e";
        }
        LogUtils.dXlog(" v  htmlName: " + htmlName);
        return "http://mhelp.600vip.cn/guide/autostart/autostart_" + htmlName + ".html";
    }

    public void batteryOptimization(){
        String showinfo = "参考说明:\n在手机---设置--->电池--->耗电管理 中找到[ " + context.getString(R.string.app_name) + " ],设置允许后台运行或允许后台启动 等";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_battery_dialog)
                .setTitle("忽略电池优化")
                .setMessage(showinfo)
                .setPositiveButton("设置忽略", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        if (!CommonUtil.isIgnoringBatteryOptimizations(context)) {
                            try {
                                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                                intent.setData(Uri.parse("package:" + context.getPackageName()));
                                //跳转之前判断intent是否存在，否则有的机型会报找不到activity
                                if (intent.resolveActivity(context.getPackageManager()) == null) {
                                    intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                                    if (intent.resolveActivity(context.getPackageManager()) == null) {
                                        Toast.makeText(context,"无法启动系统界面,请以参考说明操作", Toast.LENGTH_LONG);
                                        LogUtils.dXlog("  =======================> ");
                                    } else {
                                        context.startActivity(intent);
                                        LogUtils.dXlog("  =======================> ");
                                    }
                                } else {
                                    context.startActivity(intent);
                                    LogUtils.dXlog("  =======================> ");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            LogUtils.dXlog("  =======================> 已开启忽略电池优化");
                        }
                    }
                })
                .show();
    }
    private void whiteDialog(){
        String showinfo = "无权打开系统界面, 请点击查看教程手动设置";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_battery_dialog)
                .setTitle("白名单")
                .setMessage(showinfo)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {

                    }
                })
                .show();
    }
}
