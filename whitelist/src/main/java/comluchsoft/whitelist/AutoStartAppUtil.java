package comluchsoft.whitelist;

/**
 * lucksoft
 * Created by AndroidDevelop on 2023/3/27.
 * 158045632@qq.com
 */
import android.content.ComponentName;
import android.content.Intent;

/**
 * 自启动跳转界面清单
 */
public class AutoStartAppUtil {

    public static final Intent[] POWERMANAGER_INTENTS = {
            new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")),
            new Intent().setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity")),
            new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.mainscreen.MainScreenActivity")),
            //new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.privacypermissionsentry.PermissionTopActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager")),
            new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity")),
            new Intent().setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")),
            new Intent().setComponent(new ComponentName("com.samsung.android.lool", "com.samsung.android.sm.battery.ui.BatteryActivity")),
            new Intent().setComponent(new ComponentName("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity")),
            new Intent().setComponent(new ComponentName("com.htc.pitroad", "com.htc.pitroad.landingpage.activity.LandingPageActivity")),
            new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.MainActivity")),
            new Intent().setComponent(new ComponentName("com.transsion.phonemanager", "com.itel.autobootmanager.activity.AutoBootMgrActivity")),
            new Intent().setComponent(new ComponentName("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity")),
            new Intent().setComponent(new ComponentName("cn.nubia.security2", "cn.nubia.security.appmanage.selfstart.ui.SelfStartActivity")),
            new Intent().setComponent(new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity")),
            new Intent().setComponent(new ComponentName("com.transsion.phonemaster", "com.cyin.himgr.applicationmanager.view.activities.AutoStartActivity")),
            new Intent().setComponent(new ComponentName("com.yulong.android.coolsafe", "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity")),
            new Intent().setComponent(new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.cstyleboard.SmartManagerDashBoardActivity")),
    };

}