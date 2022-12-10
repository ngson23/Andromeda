package vn.edu.poly.andromeda.activities;

import static vn.edu.poly.andromeda.R.string.text_bo_like;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.adapter.CastAdapter;
import vn.edu.poly.andromeda.adapter.PartAdapter;
import vn.edu.poly.andromeda.model.CastModel;
import vn.edu.poly.andromeda.model.CommentModel;
import vn.edu.poly.andromeda.model.FavoriteModel;
import vn.edu.poly.andromeda.model.PartModel;

public class DetailsActivity extends AppCompatActivity {
    private List<CastModel> castModels;
    private List<PartModel> partModels;
    private CastAdapter castAdapter;
    private PartAdapter partAdapter;

    private RecyclerView part_recycle_view, cast_recycle_view;

    private ImageView thumb,cover;
    private TextView title,desc;
    private FloatingActionButton actionButton;
    private ToggleButton toggleButton_favorite;
    private String title_movies;
    private String des_movies;
    private String thumb_movies;
    private String link_movies;
    private String cover_movies;
    private String cast_movies;
    private String trailer_movies;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        thumb = findViewById(R.id.thumb);
        cover =findViewById(R.id.cover);
        title = findViewById(R.id.title_details);
        desc = findViewById(R.id.tv_desc);
        actionButton = findViewById(R.id.floatingActionButton2);
        toggleButton_favorite = findViewById(R.id.toggle_button);
        part_recycle_view = findViewById(R.id.recyclerView_parts);
        cast_recycle_view = findViewById(R.id.recyclerView_casts);



        title_movies = getIntent().getStringExtra("title");
        des_movies = getIntent().getStringExtra("desc");
        thumb_movies= getIntent().getStringExtra("thumb");
        link_movies= getIntent().getStringExtra("link");
        cover_movies= getIntent().getStringExtra("cover");
        cast_movies= getIntent().getStringExtra("cast");
        trailer_movies= getIntent().getStringExtra("t_link");


        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title_movies);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(this).load(cover_movies).into(thumb);
        Glide.with(this).load(thumb_movies).into(cover);

        thumb.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        cover.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));

        title.setText(title_movies);
        desc.setText(des_movies);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(DetailsActivity.this);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");
                DatabaseReference myRef = database.getReference();
                myRef.child("link").child(trailer_movies).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String vidUrl = snapshot.getValue(String.class);
                        Intent intent = new Intent(DetailsActivity.this,PlayActivity.class);
                        intent.putExtra("vid",vidUrl);
                        Log.d("bbbb", "onCreate: "+ vidUrl);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(DetailsActivity.this, "Error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        List<FavoriteModel> favoriteModels = new ArrayList<>();
        try{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("favorite");
            myRef.child(account.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot content:snapshot.getChildren()){
                        FavoriteModel favoriteModel = content.getValue(FavoriteModel.class);
                        favoriteModels.add(favoriteModel);
                        if (favoriteModel.getFavcast().equals(cast_movies)){
                            toggleButton_favorite.setChecked(true);
                            return;
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){

        }

        toggleButton_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(toggleButton_favorite.isChecked()){
                    Log.d("bbbbb", "onClick: else");
                    try{
                        Date currenttime = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                        String strDate = dateFormat.format(currenttime);
                        writeNewFavorite(title_movies,des_movies,thumb_movies,link_movies,cover_movies,cast_movies,trailer_movies,strDate,account.getId());
                        toggleButton_favorite.setChecked(true);
                        Toast.makeText(DetailsActivity.this, R.string.text_da_thich, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(DetailsActivity.this, R.string.h_y_ng_nh_p_s_d_ng_ch_c_n_ng_n_y, Toast.LENGTH_SHORT).show();
                        toggleButton_favorite.setChecked(false);
                    }

                }else {
                    try {
                        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference();
                        Query query = dbref.child("favorite").child(account.getId()).orderByChild("favcast").equalTo(cast_movies);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                    snapshot.getRef().removeValue();
                                    toggleButton_favorite.setChecked(false);
                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Toast.makeText(DetailsActivity.this, R.string.text_bo_like, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(DetailsActivity.this, R.string.h_y_ng_nh_p_s_d_ng_ch_c_n_ng_n_y, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        loadPart();
        loadCast();
    }


    private void writeNewFavorite(String title, String des, String thumb, String link, String cover, String cast, String trailer, String strDate, String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("favorite");
        FavoriteModel favoriteModel = new FavoriteModel(cast,cover,des,link,thumb,title,trailer,strDate);
        Log.d("bbbbb", "writeNewFavorite: time" + favoriteModel.getFavtime() );
        myRef.child(id).push().setValue(favoriteModel);
    }

    private void loadCast() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference castRef = database.getReference();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        cast_recycle_view.setLayoutManager(layoutManager);

        castModels = new ArrayList<>();
        castAdapter = new CastAdapter(castModels);
        cast_recycle_view.setAdapter(castAdapter);

        castRef.child("cast").child(cast_movies).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot content:snapshot.getChildren()){
                    CastModel castModel = content.getValue(CastModel.class);
                    castModels.add(castModel);
//                    Log.d("bbbb", "onDataChange: " +castModel.getCname());
//                    Log.d("bbbb", "onDataChange: " +castModel.getCurl());
                }
                castAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadPart() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://fir-movies-by-toan-default-rtdb.asia-southeast1.firebasedatabase.app");
        DatabaseReference partRef = database.getReference();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        part_recycle_view.setLayoutManager(layoutManager);

        partModels = new ArrayList<>();
        partAdapter = new PartAdapter(partModels);
        part_recycle_view.setAdapter(partAdapter);
        Log.d("bbbb", "loadPart: da load ");

        partRef.child("link").child(link_movies).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot content:snapshot.getChildren()){
                    PartModel partModel = content.getValue(PartModel.class);
                    partModels.add(partModel);
                }
                partAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}