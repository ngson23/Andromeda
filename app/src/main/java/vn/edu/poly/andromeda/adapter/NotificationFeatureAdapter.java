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
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.activities.DetailsActivity;
import vn.edu.poly.andromeda.model.FearturedModel;

public class NotificationFeatureAdapter extends  RecyclerView.Adapter<NotificationFeatureAdapter.MyViewHolder>{
    private List<FearturedModel> dataModels;
    private Context mContext;

    public NotificationFeatureAdapter(List<FearturedModel> dataModels, Context context) {
        mContext = context;
        this.dataModels = dataModels;
    }

    public void setFilteredList(ArrayList<FearturedModel> listFearture) {
        this.dataModels = listFearture;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationFeatureAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card,parent,false);

        return new NotificationFeatureAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationFeatureAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_tilte_movie.setText(dataModels.get(position).getFtitle());

        holder.tv_dsc.setText(dataModels.get(position).getFdes());
        Glide.with(holder.itemView.getContext()).load(dataModels.get(position).getFcover()).into(holder.notification_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendDataToDetailsActivity = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                sendDataToDetailsActivity.putExtra("title",dataModels.get(position).getFtitle());
                sendDataToDetailsActivity.putExtra("link",dataModels.get(position).getFlink());
                sendDataToDetailsActivity.putExtra("cover",dataModels.get(position).getFcover());
                sendDataToDetailsActivity.putExtra("thumb",dataModels.get(position).getFthumb());
                sendDataToDetailsActivity.putExtra("desc",dataModels.get(position).getFdes());
                sendDataToDetailsActivity.putExtra("cast",dataModels.get(position).getFcast());
                sendDataToDetailsActivity.putExtra("t_link",dataModels.get(position).getFtlink());

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity)holder.itemView.getContext(),holder.notification_image,
                                "imageMain");

                holder.itemView.getContext().startActivity(sendDataToDetailsActivity,optionsCompat.toBundle());

            }
        });

        if (holder.itemView.isClickable() == true ){
            holder.img_trangthai.setVisibility(View.INVISIBLE);
        }else{
            holder.img_trangthai.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public int getItemCount() {
        return dataModels.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView notification_image,img_trangthai;
        private TextView tv_tilte_movie,tv_type_movie,tv_dsc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            notification_image = itemView.findViewById(R.id.notification_image);
            tv_tilte_movie = itemView.findViewById(R.id.tv_tilte_movie);
            tv_dsc = itemView.findViewById(R.id.tv_dsc);
            img_trangthai = itemView.findViewById(R.id.img_trangthai);

        }
    }
}
