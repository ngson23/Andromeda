package vn.edu.poly.andromeda.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.activities.DetailsActivity;
import vn.edu.poly.andromeda.model.FavoriteModel;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    List<FavoriteModel> favoriteModels;
    private Context context;

    public FavoriteAdapter(List<FavoriteModel> favoriteModels, Context context) {
        this.favoriteModels = favoriteModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView.getContext()).load(favoriteModels.get(position).getFavcover()).into(holder.imageView);
        holder.title.setText(favoriteModels.get(position).getFavtitle());
        holder.des.setText(favoriteModels.get(position).getFavdes());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendDataToDetailsActivity = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                sendDataToDetailsActivity.putExtra("title",favoriteModels.get(position).getFavtitle());
                sendDataToDetailsActivity.putExtra("link",favoriteModels.get(position).getFavlink());
                sendDataToDetailsActivity.putExtra("cover", favoriteModels.get(position).getFavcover());
                sendDataToDetailsActivity.putExtra("thumb",favoriteModels.get(position).getFavthumb());
                sendDataToDetailsActivity.putExtra("desc",favoriteModels.get(position).getFavdes());
                sendDataToDetailsActivity.putExtra("cast",favoriteModels.get(position).getFavcast());
                sendDataToDetailsActivity.putExtra("t_link",favoriteModels.get(position).getFavtlink());
                sendDataToDetailsActivity.putExtra("time",favoriteModels.get(position).getFavtime());

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity)holder.itemView.getContext(),holder.imageView,
                                "imageMain");

                holder.itemView.getContext().startActivity(sendDataToDetailsActivity,optionsCompat.toBundle());

            }
        });

    }

    @Override
    public int getItemCount() {
        return favoriteModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title,des;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fav_image);
            title = itemView.findViewById(R.id.fav_title);
            des = itemView.findViewById(R.id.fav_des);
            cardView = itemView.findViewById(R.id.fav_card);
        }
    }
}
