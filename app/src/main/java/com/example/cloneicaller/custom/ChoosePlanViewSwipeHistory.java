package com.example.cloneicaller.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cloneicaller.R;

import static java.security.AccessController.getContext;

public class ChoosePlanViewSwipeHistory extends LinearLayout {
    public ChoosePlanViewSwipeHistory(Context context) {
        super(context);
        init();
    }

    public ChoosePlanViewSwipeHistory(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChoosePlanViewSwipeHistory(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        View v = inflate(getContext(), R.layout.choose_plan_view_3, null);
        addView(v);
    }
}
