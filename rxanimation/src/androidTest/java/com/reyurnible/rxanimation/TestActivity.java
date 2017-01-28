package com.reyurnible.rxanimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class TestActivity extends Activity {

    View child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout parent = new FrameLayout(this);
        child = new View(this);
        parent.addView(child);
        setContentView(parent);
    }
}
