package com.johan.viewfinder;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.johan.view.finder.AutoFind;

/**
 * Created by johan on 2020/6/17.
 */

@AutoFind
public class TestActivity extends BaseActivity <TestViewFinder> {

    @Override
    protected int getLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finder.infoView.setText("hello");
    }

}
