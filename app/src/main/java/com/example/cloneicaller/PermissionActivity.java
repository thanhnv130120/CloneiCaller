package com.example.cloneicaller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PermissionActivity extends AppCompatActivity {

    private Button btnPermisson;

    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_CONTACTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        btnPermisson = findViewById(R.id.btnPermission);

        btnPermisson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
                }
            }
        });
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_request_permission);

        Button btnCancelDialogPer = dialog.findViewById(R.id.btnCancelDialogPer);
        Button btnSettingsDialogPer = dialog.findViewById(R.id.btnSettingsDialogPer);

        btnCancelDialogPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnSettingsDialogPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirect user to app Settings
                Intent i = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                i.addCategory(Intent.CATEGORY_DEFAULT);
                i.setData(Uri.parse("package:" + getPackageName()));
                startActivity(i);
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CALL: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS);
                        Toast.makeText(PermissionActivity.this, "OK", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(PermissionActivity.this, "The app was not allowed to get your phone state. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
//            case REQUEST_CONTACTS: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent intent = new Intent(PermissionActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(PermissionActivity.this, "bbb", Toast.LENGTH_LONG).show();
//                }
//            }
        }

//        switch (requestCode) {
//            case REQUEST_CALL: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(PermissionActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACTS);
//                    }
//                } else {
//                    openDialog();
//                    break;
//                }
//            }
//            case REQUEST_CONTACTS: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent intent = new Intent(PermissionActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                } else {
//                    openDialog();
//                }
//            }
//        }
    }
    }



