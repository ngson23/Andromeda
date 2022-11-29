package vn.edu.poly.andromeda.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.activities.PlayActivity;
import vn.edu.poly.andromeda.model.DataModel;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.MyViewHolder> {

    private Context context;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    private List<DataModel> dataModels = new ArrayList<>();

    public void renewItems(List<DataModel> dataModels){
        this.dataModels = dataModels;
        notifyDataSetChanged();
    }

    public void deleteItems(int position){
        this.dataModels.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        viewHolder.trailer_title.setText(dataModels.get(position).getTtitle());
        Glide.with(viewHolder.itemView).load(dataModels.get(position).getTurl()).into(viewHolder.sliderImage);

        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trailer_video = new Intent(context, PlayActivity.class);
                trailer_video.putExtra("vid",dataModels.get(position).getTvid());
                trailer_video.putExtra("title",dataModels.get(position).getTtitle());
                v.getContext().startActivity(trailer_video);
            }
        });
    }

    @Override
    public int getCount() {
        return dataModels.size();
    }

    public class MyViewHolder extends SliderViewAdapter.ViewHolder{
        ImageView sliderImage;
        TextView trailer_title;
        FloatingActionButton button;

        public MyViewHolder(View view){
            super(view);
            sliderImage = view.findViewById(R.id.trailer_thumbnail);
            trailer_title = view.findViewById(R.id.trailer_title);
            button = view.findViewById(R.id.floatingActionButton);
        }

    }
}