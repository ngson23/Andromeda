package vn.edu.poly.andromeda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import vn.edu.poly.andromeda.MainActivity;
import vn.edu.poly.andromeda.R;

public class ProfileFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        if(account != null){
            LoginedFragment loginedFragment = new LoginedFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.layout_profile,loginedFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }else{
            NotLoginFragment notLoginFragment = new NotLoginFragment();
            FragmentManager manager = getFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.layout_profile,notLoginFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }

        return view;
    }
}
