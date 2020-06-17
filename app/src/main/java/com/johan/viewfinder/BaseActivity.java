package com.johan.viewfinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.johan.view.finder.ViewFinder;
import com.johan.view.finder.ViewFinderFactory;

/**
 * Created by johan on 2020/6/17.
 */

public abstract class BaseActivity <F extends ViewFinder> extends AppCompatActivity {

    protected F finder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        finder = ViewFinderFactory.create(this);
        finder.find(this);
    }

    protected abstract int getLayout();

}
