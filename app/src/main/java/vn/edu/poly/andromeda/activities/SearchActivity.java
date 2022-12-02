package vn.edu.poly.andromeda.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
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
    ArrayList<ReviewFModel> listReview;
    ReviewFAdapter reviewFAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.search_view);
        recyclerViewSearch = findViewById(R.id.rcv_search);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewSearch.setLayoutManager(layoutManager);
        listFearture =  new ArrayList<>();
        featuredAdapter = new FeaturedAdapter(listFearture,this);
        recyclerViewSearch.setAdapter(featuredAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getListDataSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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
                    }else{
                        listFearture.clear();
                    }
                }
                featuredAdapter.setFilteredList(listFearture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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