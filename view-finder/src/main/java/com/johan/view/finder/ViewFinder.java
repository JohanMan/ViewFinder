package com.johan.view.finder;

import android.app.Activity;
import android.view.View;

/**
 * Created by Johan on 2018/9/9.
 */

public interface ViewFinder {
    /**
     * Context 查找 View
     * @param activity
     */
    void find(Activity activity);

    /**
     * View 查找 View
     * @param view
     */
    void find(View view);
}
