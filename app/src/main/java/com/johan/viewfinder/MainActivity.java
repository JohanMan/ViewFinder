package com.johan.viewfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.johan.view.finder.AutoFind;

@AutoFind
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainViewFinder viewFinder = new MainViewFinder();
        viewFinder.find(this);
        viewFinder.infoView.setText("hello");
    }

}
