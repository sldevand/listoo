package sldevand.fr.listoo.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.List;

public class PermissionsTool{
    private static final int PERMISSIONS_REQUESTS = 1;
    private static List<String> perms;
    public interface PermissionResultListener{
        void onPermissionSuccess();
        void onPermissionFailure();
    }

    private final static String TAG = "PermissionsTool";

    private static PermissionResultListener permissionResultListener;

    public static void requestPermissions(Activity a,List<String> p_perms){
        perms=p_perms;
        if (ContextCompat.checkSelfPermission(a, perms.get(0))
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(a,
                    perms.get(0))) {
            } else {
                String[] permsStr = new String[perms.size()];
                perms.toArray(permsStr);
                ActivityCompat.requestPermissions(a,
                        permsStr,
                        PERMISSIONS_REQUESTS);
            }
        } else {
            if(permissionResultListener != null){
                permissionResultListener.onPermissionSuccess();

            }
        }
    }

    public static void permissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){

        boolean result = requestCode == PERMISSIONS_REQUESTS
                && (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED);

        if(permissionResultListener != null){
            if(result) permissionResultListener.onPermissionSuccess();
            else permissionResultListener.onPermissionFailure();
        }
    }

    public static void setPermissionResultListener(PermissionResultListener listener) {
        permissionResultListener = listener;
    }
}
