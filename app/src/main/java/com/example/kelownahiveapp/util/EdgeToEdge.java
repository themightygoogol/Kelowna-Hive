package com.example.kelownahiveapp.util;

import android.app.Activity;
import androidx.core.view.WindowCompat;

public final class EdgeToEdge {
    private EdgeToEdge() {
        // Private constructor to prevent instantiation.
    }

    public static void enable(Activity activity) {
        // Tell the window not to apply default system window insets,
        // so that you can handle them manually for an edge-to-edge UI.
        WindowCompat.setDecorFitsSystemWindows(activity.getWindow(), false);
    }
}
