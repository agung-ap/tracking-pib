package id.developer.trackingpib;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TabHost;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import id.developer.trackingpib.adapter.ChatListAdapter;
import id.developer.trackingpib.model.Message;

public class ChatListActivity extends AppCompatActivity implements ChatListAdapter.Listener {
    private static final String TAG = ChatListActivity.class.getName();
    private RecyclerView recyclerView;
    private ChatListAdapter adapter;
    private List<Message> messageList = new ArrayList<>();
    private ProgressBar progressBar;

    private  String username;

    private FirebaseAuth auth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        bindView();
        getChatList();
    }

    private void bindView(){
        adapter = new ChatListAdapter(this, this);

        progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#455ede"),android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.message_lis);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getChatList(){
        database.getReference("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (messageList.size() == 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String uid = snapshot.getKey();
                        Log.i(TAG, "UId " + uid);
                        Message m = new Message();
                        m.setName(uid);
                        messageList.add(m);

                    }
                }else {
                    messageList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        String uid = snapshot.getKey();
                        Log.i(TAG, "UId " + uid);
                        Message m = new Message();
                        m.setName(uid);
                        messageList.add(m);
                    }
                }

                adapter.setData(messageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(Message dataPosition) {
        Bundle bundle = new Bundle();
        ArrayList<Message> magangModel = new ArrayList<>();
        magangModel.add(dataPosition);
        //put parcelable
        bundle.putParcelableArrayList(getString(R.string.GET_SELECTED_ITEM), magangModel);
        //send data via intent
        Intent intent = new Intent(ChatListActivity.this, ChatRoom.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
