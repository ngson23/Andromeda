package vn.edu.poly.andromeda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.adapter.FavoriteAdapter;
import vn.edu.poly.andromeda.model.FavoriteModel;

public class DownloadFragment extends Fragment {
    RecyclerView recyclerView;
    List<FavoriteModel> favoriteModels = new ArrayList<>();;
    FavoriteAdapter favoriteAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("favorite");
    TextView textView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        textView = view.findViewById(R.id.tv_thongbao);
        recyclerView = view.findViewById(R.id.fav_recycleview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        favoriteAdapter = new FavoriteAdapter(favoriteModels,getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoriteAdapter);

        try {
            String id = account.getId();
            textView.setVisibility(View.INVISIBLE);
            myRef.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    favoriteModels.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        FavoriteModel favoriteModel = dataSnapshot.getValue(FavoriteModel.class);
                        favoriteModels.add(favoriteModel);
                    }
                    favoriteAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            favoriteModels.clear();

        }
    }
}
