package com.example.cloneicaller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.cloneicaller.fragment.FragmentCallKeyboard;
import com.example.cloneicaller.fragment.FragmentHome;
import com.example.cloneicaller.fragment.FragmentListBlock;
import com.example.cloneicaller.fragment.FragmentListHistory;
import com.example.cloneicaller.fragment.FragmentSetting;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_view,
                new FragmentHome()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener botListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case R.id.menu_keyboard:
                    selectedFragment = new FragmentCallKeyboard();
                    break;
                case R.id.menu_history:
                    selectedFragment = new FragmentListHistory();
                    break;
                case R.id.menu_block:
                    selectedFragment = new FragmentListBlock();
                    break;
                case R.id.menu_setting:
                    selectedFragment = new FragmentSetting();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container_view,selectedFragment).commit();
            return true;
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_pad:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_view, new FragmentCallKeyboard()).commit();
                break;
            case R.id.img_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_view, new FragmentListHistory()).commit();
                break;
            case R.id.img_block:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_view, new FragmentListBlock()).commit();
                break;
            case R.id.img_setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.container_view, new FragmentSetting()).commit();
                break;
        }
    }
}