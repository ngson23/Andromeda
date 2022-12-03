package vn.edu.poly.andromeda.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.model.CommentModel;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    List<CommentModel> commentModels;

    public CommentAdapter(List<CommentModel> commentModels) {
        this.commentModels = commentModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvName.setText(commentModels.get(position).getUsername());
        holder.tvComment.setText(commentModels.get(position).getComment());
        Glide.with(holder.itemView).load(commentModels.get(position).getUrl()).into(holder.comment_image);
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView comment_image;
        TextView tvName,tvComment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_image = itemView.findViewById(R.id.comment_img);
            tvName = itemView.findViewById(R.id.comment_tvname);
            tvComment = itemView.findViewById(R.id.comment_tvcomment);
        }
    }

}
