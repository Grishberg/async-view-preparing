package com.grishberg.rvmenu.common;

import android.content.Context;
import android.util.DisplayMetrics;

public class UiUtils {
    private final Context context;

    public UiUtils(Context context) {
        this.context = context;
    }

    public int dpToPix(int dp) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi /
                DisplayMetrics.DENSITY_DEFAULT) + 0.5f);
    }
}
