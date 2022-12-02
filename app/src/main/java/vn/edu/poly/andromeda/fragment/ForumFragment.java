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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import vn.edu.poly.andromeda.R;
import vn.edu.poly.andromeda.model.CommentModel;

public class ForumFragment extends Fragment {
    TextView tvUser;
    EditText edtComment;
    ImageButton send;

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



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double random = Math.random();
                String randomStr = random + account.getId();
                String userId = randomStr.substring(2);
                writeNewUser(userId, account.getGivenName(),edtComment.getText().toString());
                Log.d("bbbb", "onClick: forum "+ userId);
            }
        });
    }

    public void writeNewUser(String userId, String username, String comment) {
        CommentModel commentModel = new CommentModel(username,comment);
        myRef.child(userId).setValue(commentModel);
    }

}
