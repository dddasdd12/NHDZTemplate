package org.oy.chardemo.utilslibrary.exception;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常捕捉类
 * Created by Mro on 2017/3/11.
 */
public class ExceptionCashHelper implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "Exception Message";
    private static ExceptionCashHelper instance;
    private Context context;
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    public static ExceptionCashHelper getInstance() {
        if (instance == null) {
            synchronized (ExceptionCashHelper.class) {
                if (instance == null) {
                    instance = new ExceptionCashHelper();
                }
            }
        }
        return instance;
    }

    //获取的一些信息
    public void init(Context context) {
        this.context = context;
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefaultUncaughtExceptionHandler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        //全局异常
        Log.e(TAG, "报错了");
        //保存信息到SD卡中
        String createFileName = SavaInfoToSDCard(throwable);
        cacheCrashFile(createFileName);
        //让系统默认处理
        mDefaultUncaughtExceptionHandler.uncaughtException(thread, throwable);
    }

    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> hashMap = new HashMap<>();
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
        }
        hashMap.put("versionName", packageInfo.packageName);
        hashMap.put("versionCode", packageInfo.versionCode + "");
        hashMap.put("model", Build.MODEL);
        hashMap.put("SDK_INT", Build.VERSION.SDK_INT + "");
        hashMap.put("PRODUCT", Build.PRODUCT);
        hashMap.put("MOBILE_INFO", getMobildInfo());
        return hashMap;
    }

    private void cacheCrashFile(String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("crash", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("CREASH_FILE_NAME", fileName).commit();

    }

    public File getCrashFile() {
        String crashFileName = context.getSharedPreferences("crash", Context.MODE_PRIVATE)
                .getString("CREASH_FILE_NAME", "");
        return new File(crashFileName);
    }

    private String SavaInfoToSDCard(Throwable throwable) {
        String fileName = null;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : obtainSimpleInfo(context).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        // 崩溃详细信息
        sb.append(obtainExceptionInfo(throwable));
        // 手机应用安装目录
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(context.getFilesDir() + File.separator + "cache" + File.separator);

            if (dir.exists()) {
                // 删除该目录下的子文件
                deleteDir(dir);
            }
            if (!dir.exists()) {
                // 重新创建
                dir.mkdir();
            }
            try {
                fileName = dir.toString() + File.separator + getAssignTime("yyYY-MM-dd-HH-mm") + ".txt";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return fileName;
    }

    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }

    /**
     * 利用反射获取类的所有属性
     */
    private String getMobildInfo() {
        StringBuilder sb = new StringBuilder();
        Field[] fields = Build.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                //内部属性为静态 所以传null
                String value = field.get(null).toString();
                sb.append(name + "=" + value).append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] childen = dir.listFiles();
            for (File file : childen) {
                file.delete();
            }
        }
        return true;
    }

    private String getAssignTime(String dateFormatStr) {
        DateFormat dataFormat = new SimpleDateFormat(dateFormatStr);
        long currentTime = System.currentTimeMillis();
        return dataFormat.format(currentTime);
    }

}
