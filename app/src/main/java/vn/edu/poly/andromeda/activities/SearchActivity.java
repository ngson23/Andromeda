package vn.edu.poly.andromeda.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.adapter.FeaturedAdapter;
import vn.edu.poly.andromeda.adapter.ReviewFAdapter;
import vn.edu.poly.andromeda.model.FearturedModel;
import vn.edu.poly.andromeda.model.ReviewFModel;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerViewSearch;
    FeaturedAdapter featuredAdapter;
    ArrayList<FearturedModel> listFearture;
    TextView tv_noFind;
    ArrayList<ReviewFModel> listReview;
    ReviewFAdapter reviewFAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.search_view);
        recyclerViewSearch = findViewById(R.id.rcv_search);
        tv_noFind = findViewById(R.id.tv_noFind);

        tv_noFind.setVisibility(View.INVISIBLE);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerViewSearch.setLayoutManager(gridLayoutManager);

        listFearture =  new ArrayList<>();
        featuredAdapter = new FeaturedAdapter(listFearture,this);


        listReview = new ArrayList<>();
        reviewFAdapter = new ReviewFAdapter(listReview);

        ConcatAdapter  concatAdapter = new ConcatAdapter(featuredAdapter,reviewFAdapter);

        recyclerViewSearch.setAdapter(concatAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listReview.clear();
                listFearture.clear();
                getListDataSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                listReview.clear();
//                listFearture.clear();
//                getListDataSearch(newText);
                return false;
            }
        });


          // Toolbar
        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


    }

    public void getListDataSearch(String text){
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");

        DatabaseReference myRef = database.getReference("featured");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    FearturedModel dfearturedModel = dataSnapshot.getValue(FearturedModel.class);
                    if(dfearturedModel.getFtitle().toLowerCase().contains(text.toLowerCase())){
                        listFearture.add(dfearturedModel);
                    }
                }
                if(validate() > 0){
                    tv_noFind.setVisibility(View.INVISIBLE);
                }else{
                    tv_noFind.setVisibility(View.VISIBLE);
                }
                featuredAdapter.setFilteredList(listFearture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference reviewRef = database.getReference("review");
        reviewRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ReviewFModel dfearturedModel = dataSnapshot.getValue(ReviewFModel.class);
                    if(dfearturedModel.getRtitle().toLowerCase().contains(text.toLowerCase())){
                        listReview.add(dfearturedModel);
                    }
                }
                if(validate() > 0){
                    tv_noFind.setVisibility(View.INVISIBLE);
                }else{
                    tv_noFind.setVisibility(View.VISIBLE);
                }
                reviewFAdapter.setFilteredList(listReview);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public int validate(){
        int check = -1;
        if(listReview.size() > 0 || listFearture.size() > 0){
            check = 1;
        }
        return check;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}