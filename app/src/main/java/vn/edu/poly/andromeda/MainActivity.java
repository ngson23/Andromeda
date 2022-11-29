package vn.edu.poly.andromeda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.edu.poly.andromeda.fragment.DownloadFragment;
import vn.edu.poly.andromeda.fragment.ForumFragment;
import vn.edu.poly.andromeda.fragment.HomeFragment;
import vn.edu.poly.andromeda.fragment.NotificationFragment;
import vn.edu.poly.andromeda.fragment.ProfileFragment;
import vn.edu.poly.andromeda.fragment.SearchFragment;
import vn.edu.poly.andromeda.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    SettingFragment settingsFragment = new SettingFragment();
    ForumFragment forumFragment = new ForumFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    DownloadFragment downloadFragment = new DownloadFragment();
    NotificationFragment   notificationFragment = new NotificationFragment();
    SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView  = findViewById(R.id.bottom_navigation);


        // Action Bar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        // Định dạng màu cho Actionbar
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        // Hiện Thị Logo
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);



//      getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        setTitle("Trang Chủ");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.forum:
                        setTitle("Diễn Đàn");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,forumFragment).commit();
                        return true;
                    case R.id.download:
                        setTitle("Tải Xuống");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,downloadFragment).commit();
                        return true;
                    case R.id.setting:
                        setTitle("Cài Đặt");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                        return true;
                    case R.id.profile:
                        setTitle("Hồ Sơ");
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    // Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.icon_search:
                setTitle("Tìm Kiếm");
                getSupportFragmentManager().beginTransaction().replace(R.id.container,searchFragment).commit();
                return true;
            case R.id.icon_notìication:
                setTitle("Thông Báo");
                getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //

}