package info.mizoguche.mytwitterclient.application.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ViewHelper {
    private static float dpScale = -1.0f;

    public static int calculatePixelFromDp(int dp, Context context){
        if(dpScale == -1.0f){
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
            dpScale = metrics.scaledDensity;
        }
        return (int) (dp * dpScale);
    }
}
