package vn.edu.poly.andromeda.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.adapter.FeaturedAdapter;
import vn.edu.poly.andromeda.adapter.ReviewFAdapter;
import vn.edu.poly.andromeda.adapter.SliderAdapter;
import vn.edu.poly.andromeda.model.DataModel;
import vn.edu.poly.andromeda.model.FearturedModel;
import vn.edu.poly.andromeda.model.ReviewFModel;

public class HomeFragment extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference();
    private List<DataModel> dataModels;
    private List<FearturedModel> fearturedModels;
    private List<ReviewFModel> reviewFModels;

    private SliderAdapter sliderAdapter;
    private RecyclerView featuredRecyclerView;
    private RecyclerView reviewFRecyclerView;

    private FeaturedAdapter featuredAdapter;
    private ReviewFAdapter reviewFAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);


        FirebaseApp.initializeApp(getContext());

        SliderView sliderView = view.findViewById(R.id.sliderView);

        sliderAdapter = new SliderAdapter(getContext());
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setScrollTimeInSec(6);
        sliderView.setAutoCycle(true);

        loadFireBaseForSlider();
        loadFeaturedData(view);
        loadReviewFData(view);

        renewItems(view);
        return view ;
    }
    private void loadFireBaseForSlider() {
        myRef.child("trailer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot contentSlider : dataSnapshot.getChildren()){
                    DataModel sliderItem = contentSlider.getValue(DataModel.class);
                    Log.d("zzzz", "onDataChange: "+sliderItem.getTurl());
                    dataModels.add(sliderItem);
                }
                sliderAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
    }

    private void loadFeaturedData(View view) {
        DatabaseReference Fref = database.getReference();
        featuredRecyclerView = view.findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        featuredRecyclerView.setLayoutManager(layoutManager);

        fearturedModels = new ArrayList<>();
        featuredAdapter = new FeaturedAdapter(fearturedModels,getContext());
        featuredRecyclerView.setAdapter(featuredAdapter);

        Fref.child("featured").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot contentDataSnapshot: snapshot.getChildren()){
                    FearturedModel dataModel = contentDataSnapshot.getValue(FearturedModel.class);
                    fearturedModels.add(dataModel);
                }

                featuredAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadReviewFData(View view) {
        DatabaseReference Fref = database.getReference();
        reviewFRecyclerView = view.findViewById(R.id.recyclerView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        reviewFRecyclerView.setLayoutManager(layoutManager);

        reviewFModels = new ArrayList<>();
        reviewFAdapter = new ReviewFAdapter(reviewFModels);
        reviewFRecyclerView.setAdapter(reviewFAdapter);

        Fref.child("review").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot contentDataSnapshot: snapshot.getChildren()){
                    ReviewFModel dataModel = contentDataSnapshot.getValue(ReviewFModel.class);
                    reviewFModels.add(dataModel);
                }
                reviewFAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void renewItems(View view) {
        dataModels = new ArrayList<>();
        DataModel dataItems = new DataModel();
        dataModels.add(dataItems);
        sliderAdapter.renewItems(dataModels);
        sliderAdapter.deleteItems(0);
    }
}