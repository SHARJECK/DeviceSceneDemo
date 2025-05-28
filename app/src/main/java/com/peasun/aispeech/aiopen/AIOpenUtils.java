package com.peasun.aispeech.aiopen;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

public class AIOpenUtils {

    /**
     * 注册时，需要同时提交要接收的指令集，仅供参考，因根据自己需求自行调整。
     * 注意数据类型为String[]
     */
    static String[] keywords = {"打开氛围灯", "关闭氛围灯", "打开车窗", "打开按摩", "关闭电视", "打开信号源"};

    public static void registerSceneApp(Context context) {
        Intent localIntent = new Intent();
        localIntent.setAction(AIOpenConstant.AI_OPEN_ACTION_APP_REGISTER);
        Bundle data = new Bundle();
        data.putString("package_name", context.getPackageName());
        data.putLong("category", AIOpenConstant.SEMANTIC_DEVICE_SCENE);
        /**
         * 需要同时注册指令集，不能为null，不能为空，否则无效。数据类型为String[]。
         */
        data.putStringArray("keywords", keywords);
        localIntent.putExtras(data);

        /**
         * 注意对接的语音版本，将setPackage的参数改为对应语音软件的package name.
         * 国际版"com.peasun.aispeechgl"
         * 大陆版"com.peasun.aispeech"
         */
        localIntent.setPackage("com.peasun.aispeech");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(localIntent);
        } else {
            context.startService(localIntent);
        }
    }

    public static AIOpenReceiver registerSceneReciver(Context context) {
        AIOpenReceiver receiver = new AIOpenReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(AIOpenConstant.AI_OPEN_ACTION_DEVICE_SCENE);
        //context.registerReceiver(receiver, filter, AIOpenConstant.AI_OPEN_ACTION_PERMISSION, null);
        context.registerReceiver(receiver, filter);
        return receiver;
    }
}
