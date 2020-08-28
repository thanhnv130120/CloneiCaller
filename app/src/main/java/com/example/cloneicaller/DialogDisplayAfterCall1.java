package com.example.cloneicaller;

import androidx.annotation.Nullable;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.cloneicaller.databinding.DisplayAfterCall1Binding;

public class DialogDisplayAfterCall1 extends Service {

    //Thanhnv
    DisplayAfterCall1Binding binding;
    WindowManager windowManager;
public class DialogOutgoingActivity extends Service {
    ActivityDialogOutgoingBinding binding;
//    WindowManager windowManager;
    GroupView groupView;
    WindowManager.LayoutParams winLayoutParams;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initView();
        return START_STICKY;
    }
    private void initView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        createDialog();
        showDialog();
    }

    private void showDialog() {
        windowManager.addView(groupView, winLayoutParams);
    }

    public void removeView() {
        windowManager.removeView(groupView);
    }

    private void createDialog() {
        groupView = new GroupView(this);
        View view = View.inflate(this, R.layout.activity_dialog_outgoing, groupView);

        ImageView btnClose = view.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView();
            }
        });

        groupView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = winLayoutParams.x;
                        initialY = winLayoutParams.y;
                        initialTouchX = motionEvent.getRawX();
                        initialTouchY = motionEvent.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        winLayoutParams.x = initialX + (int) (motionEvent.getRawX() - initialTouchX);
                        winLayoutParams.y = initialY + (int) (motionEvent.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(view, winLayoutParams);
                        break;
                }

                return false;
            }
        });

        winLayoutParams = new WindowManager.LayoutParams();
        winLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        winLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        winLayoutParams.gravity = Gravity.CENTER;
        winLayoutParams.format = PixelFormat.TRANSLUCENT;
        winLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        winLayoutParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

    }
}