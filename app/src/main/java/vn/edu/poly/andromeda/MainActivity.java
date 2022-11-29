package vn.edu.poly.andromeda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import vn.edu.poly.andromeda.fragment.DownloadFragment;
import vn.edu.poly.andromeda.fragment.ForumFragment;
import vn.edu.poly.andromeda.fragment.HomeFragment;
import vn.edu.poly.andromeda.fragment.ProfileFragment;
import vn.edu.poly.andromeda.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    SettingFragment settingsFragment = new SettingFragment();
    ForumFragment forumFragment = new ForumFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    DownloadFragment downloadFragment = new DownloadFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView  = findViewById(R.id.bottom_navigation);

//      getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.forum:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,forumFragment).commit();
                        return true;
                    case R.id.download:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,downloadFragment).commit();
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingsFragment).commit();
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                        return true;
                }

                return false;
            }
        });

    }
}