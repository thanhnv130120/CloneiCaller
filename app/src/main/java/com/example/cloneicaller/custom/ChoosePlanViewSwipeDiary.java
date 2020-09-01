package com.example.cloneicaller.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.example.cloneicaller.R;

public class ChoosePlanViewSwipeDiary extends LinearLayout {
    public ChoosePlanViewSwipeDiary(Context context) {
        super(context);
        init();
    }

    public ChoosePlanViewSwipeDiary(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChoosePlanViewSwipeDiary(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        View v = inflate(getContext(), R.layout.choose_plan_view_2, null);
        addView(v);
    }
}

