package pw.rabit.xposed.oneplusextension;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by tim on 2017/12/20.
 *
 * WARNING: This file is only archived for reference. It will NOT be loaded by xposed.
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

        // Another workaround for Oneplus 5/5T Oreo OPSms ver 1.2 (reported from #1)
        try {
            XposedHelpers.findAndHookMethod("com.oneplus.lib.util.ReflectUtil", lpparam.classLoader,
                    "isFeatureSupported", String.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            String featureName = (String) param.args[0];
                            if(featureName.equals("OP_FEATURE_SKU_CHINA"))
                                param.setResult(true);
                            else if(featureName.equals("OP_FEATURE_SKU_GLOBAL"))
                                param.setResult(false);
                        }
                    });
            XposedBridge.log("Found com.oneplus.lib.util.ReflectUtil");
        } catch (XposedHelpers.ClassNotFoundError e) {
            XposedBridge.log("NOT Found com.oneplus.lib.util.ReflectUtil");
        }

    }
}
