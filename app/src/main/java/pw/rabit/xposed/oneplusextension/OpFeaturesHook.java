package pw.rabit.xposed.oneplusextension;

import android.os.Build;

import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by tim on 2017/12/20.
 *
 * Decompiled reference:
 * https://github.com/joshuous/oneplus_blobs_decompiled/blob/e1ab1f2dd111f905ff1eee18b6a072606c01c518/com_oneplus_sdk_utils/com/oneplus/sdk/utils/OpFeatures.java
 */

public class OpFeaturesHook implements IXposedHookLoadPackage {
    private static final String[] features = {
            "OP_FEATURE_SKU_CHINA",
            "OP_FEATURE_SKU_GLOBAL",
            "OP_FEATURE_MDM",
            "OP_FEATURE_BUGREPORT",
            "OP_FEATURE_AUTO_STARTUP",
            "OP_FEATURE_OP_KEYGUARD",
            "OP_FEATURE_MMS_NOTI_SOUND",
            "OP_FEATURE_MULTI_SIM_RINGTONES",
            "OP_FEATURE_SWAP_KEYS",
            "OP_FEATURE_BACK_COVER_THEME",
            "OP_FEATURE_TRI_STATE_KEY",
            "OP_FEATURE_KEY_LOCK",
            "OP_FEATURE_CTA_PERMISSION_CONTROL",
            "OP_FEATURE_GESTURE_SCREENSHOT",
            "OP_FEATURE_BG_DETECTION",
            "OP_FEATURE_RINGTONE_ALIAS",
            "OP_FEATURE_RINGTONE_BKP",
            "OP_FEATURE_BLACK_GESTURE",
            "OP_FEATURE_RESERVE_APP",
            "OP_FEATURE_EXT_AUDIO_DECODER",
            "OP_FEATURE_POST_INSTALL_AMAZON_APPS"
    };

    private static final List<String> packages = Arrays.asList(
            "com.android.mms",          // SMS content recognition, blacklist
            "com.oneplus.security",     // Traffic auto calibration, SMS smart block
            "com.android.dialer",       // Settings related to call recording
            "com.android.incallui"      // Call recording
    );

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!packages.contains(lpparam.packageName)) return;

        XposedHelpers.findAndHookMethod("android.util.OpFeatures", lpparam.classLoader,
                "isSupport", int[].class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        int[] paramVarArgs = (int[]) param.args[0];
                        if (paramVarArgs[0] > 1)
                            return;

                        int feature = paramVarArgs[0];
                        if (paramVarArgs[0] == 1)
                            paramVarArgs[0] = 0;
                        else
                            paramVarArgs[0] = 1;
                        param.args[0] = paramVarArgs;

                        if (BuildConfig.DEBUG) {
                            StringBuilder msg = new StringBuilder("OpFeatures.isSupport: FAKED ");
                            msg.append(features[feature]);

//                            for (int i : paramVarArgs) {
//                                if (i >= features.length) {
//                                    msg.append("UNKNOWN_FEAT_");
//                                    msg.append(i);
//                                } else
//                                    msg.append(features[i]);
//                                msg.append(' ');
//                            }
//                            for (StackTraceElement st : Thread.currentThread().getStackTrace()) {
//                                XposedBridge.log("  " + st.getClassName() + "->" + st.getMethodName());
//                            }
                            msg.append(" Caller: ");
                            msg.append(lpparam.packageName);
//                            StackTraceElement callerFrame = Thread.currentThread().getStackTrace()[6];
//                            msg.append(" ");
//                            msg.append(callerFrame.getClassName());
//                            msg.append(" -> ");
//                            msg.append(callerFrame.getMethodName());
                            XposedBridge.log(msg.toString());
                        }
                    }
                });
    }
}
