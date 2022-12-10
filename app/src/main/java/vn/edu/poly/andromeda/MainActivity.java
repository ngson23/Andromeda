package vn.edu.poly.andromeda;


import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.edu.poly.andromeda.activities.SearchActivity;
import vn.edu.poly.andromeda.fragment.DownloadFragment;
import vn.edu.poly.andromeda.fragment.ForumFragment;
import vn.edu.poly.andromeda.fragment.HomeFragment;
import vn.edu.poly.andromeda.fragment.NotificationFragment;
import vn.edu.poly.andromeda.fragment.ProfileFragment;
import vn.edu.poly.andromeda.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Checknetwork checknetwork = new Checknetwork();
    HomeFragment homeFragment = new HomeFragment();
    SettingFragment settingsFragment = new SettingFragment();
    ForumFragment forumFragment = new ForumFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    DownloadFragment downloadFragment = new DownloadFragment();
    NotificationFragment notificationFragment = new NotificationFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        // Hiện Thị Logo
        ab.setDisplayUseLogoEnabled(true);
        ab.setTitle("Andromeda");


        bottomNavigationView  = findViewById(R.id.bottom_navigation);

//      getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        getSupportActionBar().setTitle("Andromeda");
                        return true;
                    case R.id.forum:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,forumFragment).commit();
                        getSupportActionBar().setTitle(R.string.text_dien_dan);
                        return true;
                    case R.id.download:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,downloadFragment).commit();
                        getSupportActionBar().setTitle(R.string.text_yeu_thich);
                        return true;
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                        getSupportActionBar().setTitle(R.string.text_cai_dat);
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        getSupportActionBar().setTitle(R.string.text_ho_so);
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
        bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
        return super.onCreateOptionsMenu(menu);
//ắedrfgthj
    }


    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(checknetwork, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(checknetwork);
        super.onStop();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.icon_search:
                setTitle("Tìm Kiếm");
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
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