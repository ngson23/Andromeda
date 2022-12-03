package vn.edu.poly.andromeda.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.adapter.CommentAdapter;
import vn.edu.poly.andromeda.model.CommentModel;

public class ForumFragment extends Fragment {
    TextView tvUser;
    EditText edtComment;
    ImageButton send;
    RecyclerView recyclerView;
    List<CommentModel> commentModels;
    CommentAdapter commentAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("forum");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forum,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvUser = view.findViewById(R.id.forum_user);
        edtComment =view.findViewById(R.id.forum_comment);
        send = view.findViewById(R.id.forum_send);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        tvUser.setText(account.getGivenName());

        loadData(view);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                writeNewUser(account.getGivenName(),edtComment.getText().toString(),account.getPhotoUrl()+"");
                loadData(view);
                edtComment.setText("");
            }
        });
    }


    public void writeNewUser(String username, String comment, String url) {
        CommentModel commentModel = new CommentModel(username,comment,url);
        myRef.push().setValue(commentModel);
    }

    public void loadData(View view){
        DatabaseReference CommentRef = database.getReference();
        recyclerView = view.findViewById(R.id.forum_recycleView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        commentModels = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentModels);
        recyclerView.setAdapter(commentAdapter);


        CommentRef.child("forum").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    CommentModel commentModel = dataSnapshot.getValue(CommentModel.class);
                    commentModels.add(commentModel);
                }

                commentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
