package pw.rabit.xposed.oneplusextension;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by tim on 2017/12/20.
 */

public class MMSHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.mms")) return;

        XposedBridge.log("Hooking " + lpparam.packageName);

        Class clazz = XposedHelpers.findClass("com.android.mms.util.Utils", lpparam.classLoader);

        XposedBridge.log("Found " + clazz.toString());

        XposedHelpers.setStaticBooleanField(clazz, "IS_OVER_SEA", false);

        XposedHelpers.findAndHookMethod(clazz, "setIsCTA", boolean.class,
                new XC_MethodReplacement() {
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log("setIsCTA: " + (boolean)param.args[0]);
                        return null;
                    }
                });
    }
}
