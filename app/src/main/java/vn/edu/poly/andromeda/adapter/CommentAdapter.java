package vn.edu.poly.andromeda.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.model.CommentModel;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    List<CommentModel> commentModels;
    String id;
    Context context;

    public CommentAdapter(List<CommentModel> commentModels, String id, Context context) {
        this.commentModels = commentModels;
        this.id = id;
        this.context = context;
    }
    public CommentAdapter(List<CommentModel> commentModels){
        this.commentModels = commentModels;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(commentModels.get(position).getUsername());
        holder.tvComment.setText(commentModels.get(position).getComment());
        if (!commentModels.get(position).getUrl().equals("null")){
            Glide.with(holder.itemView).load(commentModels.get(position).getUrl()).into(holder.comment_image);
        }
        final String time = commentModels.get(position).getTime();
//        Log.d("zzzzz", "onBindViewHolder: "+ time);

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (commentModels.get(position).getId().equals(id)){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.text_xoa_binh_luan);
                    builder.setMessage(R.string.text_chac_chan);

                    builder.setNegativeButton(R.string.text_xoa, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            delete(position,time);
                        }
                    });
                    builder.setPositiveButton(R.string.text_huy, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }

                return false;
            }
        });
    }

    private void delete(int position, String time) {

        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference();
        Query query = dbref.child("forum").orderByChild("time").equalTo(time);
//        Log.d("zzzzz", "delete: "+time);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // remove the value at reference
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();

                    notifyItemRemoved(position);
                    commentModels.remove(position);
                    notifyItemRangeChanged(position,getItemCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView comment_image;
        TextView tvName,tvComment;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_image = itemView.findViewById(R.id.comment_img);
            tvName = itemView.findViewById(R.id.comment_tvname);
            tvComment = itemView.findViewById(R.id.comment_tvcomment);
            cardView = itemView.findViewById(R.id.cardview_comment);
        }
    }

}
