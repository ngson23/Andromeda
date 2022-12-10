package vn.edu.poly.andromeda.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import vn.edu.poly.andromeda.MainActivity;
import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.fragment.SettingFragment;

public class Huong_dan extends AppCompatActivity {
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huong_dan);
        back = findViewById(R.id.back_huong_dan);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new Intent(getApplicationContext(),MainActivity.class));
                startActivity(intent);
            }
        });
    }
}