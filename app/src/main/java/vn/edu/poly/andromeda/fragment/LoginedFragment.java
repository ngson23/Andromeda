package vn.edu.poly.andromeda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import vn.edu.poly.andromeda.R;


public class LoginedFragment extends Fragment {
    ImageView imgAvata;
    TextView txtTenNguoiDung, txtDangXuat,txtYeuthich;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_logined, container, false);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());

        if(account != null) {
            imgAvata = v.findViewById(R.id.imgAvata);
            txtTenNguoiDung = v.findViewById(R.id.txtTenNguoiDung);
            txtDangXuat = v.findViewById(R.id.txtDangXuat);

            //

            txtTenNguoiDung.setText(account.getGivenName());
            Picasso.with(getActivity()).load(account.getPhotoUrl()).error(R.drawable.ic_baseline_person_24).fit().into(imgAvata);

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail().requestProfile().requestId()
                    .build();
            GoogleSignInClient googleApiClient = GoogleSignIn.getClient(getActivity(), gso);

            txtDangXuat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    googleApiClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            NotLoginFragment notLoginFragment = new NotLoginFragment();
                            FragmentManager manager = getFragmentManager();
                            manager.beginTransaction()
                                    .replace(R.id.container,notLoginFragment)
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                    .commit();
                        }
                    });
                }
            });
        }
        return  v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            if(account == null) {
                NotLoginFragment notLoginFragment = new NotLoginFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container,notLoginFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();
            }
        } catch (ApiException e) {
            Toast.makeText(getActivity(), "Kết Nối Thất Bại! Vui Lòng Thử Lại.", Toast.LENGTH_SHORT).show();
        }
    }

}