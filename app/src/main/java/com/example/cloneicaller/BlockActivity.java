package com.example.cloneicaller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BlockActivity extends AppCompatActivity {
    ImageView imgBackFrBlock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);

        imgBackFrBlock = findViewById(R.id.imgBackFrBlock);

        imgBackFrBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}