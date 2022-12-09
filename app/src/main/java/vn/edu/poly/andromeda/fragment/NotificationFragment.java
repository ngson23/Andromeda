package vn.edu.poly.andromeda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.adapter.NotificationFeatureAdapter;
import vn.edu.poly.andromeda.model.FearturedModel;

public class NotificationFragment extends Fragment {
    TextView textView;
    RecyclerView rcv_notification;
    NotificationFeatureAdapter notificationFeatureAdapter;
    ArrayList<FearturedModel> listFearture = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        rcv_notification = v.findViewById(R.id.rcv_notification);
        textView = v.findViewById(R.id.tv_newMovie);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rcv_notification.setLayoutManager(layoutManager);

        listFearture.clear();
        getListDataSearch();

        notificationFeatureAdapter = new NotificationFeatureAdapter(listFearture, getActivity());
        rcv_notification.setAdapter(notificationFeatureAdapter);


        return v;
    }


    public void getListDataSearch() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");

        DatabaseReference myRef = database.getReference("featured");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    FearturedModel dfearturedModel = dataSnapshot.getValue(FearturedModel.class);
//                    listFearture.add(dfearturedModel);
//                    int i =  listFearture.size() - 1;
//                    int x = listFearture.lastIndexOf(dfearturedModel) +1;
//                    listFearture.subList(i,x);
//                }if(validate() > 0){
//                    textView.setVisibility(View.INVISIBLE);
//                }else{
//                    textView.setVisibility(View.VISIBLE);
//                }
//                notificationFeatureAdapter.notifyItemInserted(listFearture.size());
//                rcv_notification.smoothScrollToPosition(listFearture.size());
//                notificationFeatureAdapter.setFilteredList(listFearture);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    FearturedModel dfearturedModel = snapshot.getValue(FearturedModel.class);
                    listFearture.add(dfearturedModel);
                    notificationFeatureAdapter.setFilteredList(listFearture);

                    if(validate() > 0){
                        textView.setVisibility(View.INVISIBLE);
                    }else{
                        textView.setVisibility(View.VISIBLE);
                    }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public int validate(){
        int check = -1;
        if(listFearture.size() > 0){
            check = 1;
        }
        return check;
    }
}