package vn.edu.poly.andromeda.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import vn.edu.poly.andromeda.R;


public class SettingFragment extends Fragment {
    Switch aSwitch;
    boolean aBoolean;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView chinhsangtoi,ngongu ,huongdan;
    RadioGroup language;
    RadioButton englist,vietnamese;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting,container,false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        aSwitch = view.findViewById(R.id.switcher);

        sharedPreferences = requireActivity().getSharedPreferences("MODE", Context.MODE_PRIVATE);
        aBoolean = sharedPreferences.getBoolean("night",false);
        if(aBoolean){
            aSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aBoolean){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",false);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night",true);
                }
                editor.apply();
            }
        });
        chinhsangtoi = view.findViewById(R.id.idchinhsangtoi);
        ngongu = view.findViewById(R.id.ngonngu);
        language= view.findViewById(R.id.language);
        englist = view.findViewById(R.id.tienganh);
        vietnamese = view.findViewById(R.id.tiengviet);
        huongdan = view.findViewById(R.id.huongdan);
        language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tienganh:
                        String language ="en";
                        setLocale(language);
                        break;
                    case R.id.tiengviet:
                        setLocale("vi");
                        break;
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
    public void  setLocale(String language){
        Resources resources = getResources();

        DisplayMetrics metrics = resources.getDisplayMetrics();

        Configuration configuration = resources.getConfiguration();

        configuration.locale= new Locale(language);

        resources.updateConfiguration(configuration,metrics);

        onConfigurationChanged(configuration);
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        chinhsangtoi.setText(R.string.ch_nh_s_ng_t_i);
        ngongu.setText(R.string.ng_n_ng);
        englist.setText(R.string.englist);
        vietnamese.setText(R.string.ti_ng_vi_t);
        huongdan.setText(R.string.h_ng_d_n_s_d_ng);

    }
}