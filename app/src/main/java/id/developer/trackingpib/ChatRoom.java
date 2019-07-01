package id.developer.trackingpib;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.developer.trackingpib.model.Message;
import id.developer.trackingpib.util.GlobalFunction;

public class ChatRoom extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private SharedPreferences preferences;
    private String uid, userStatus;
    private String username;
    private List<Message> messageList;

    LinearLayout layout;
    RelativeLayout layout2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        getSupportActionBar().setTitle("chat room");

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        preferences = this.getSharedPreferences(getString(R.string.CREDENTIAL), Context.MODE_PRIVATE);
        userStatus = preferences.getString(getString(R.string.GET_USER_STATUS),null);

        if (userStatus.equals("ADMIN")){
            messageList = new ArrayList<>();
            messageList = getIntent().getParcelableArrayListExtra(getString(R.string.GET_SELECTED_ITEM));
            uid = messageList.get(0).getName();
        }else {
            uid = preferences.getString(getString(R.string.GET_UID),null);
        }

        bindView();

        getUserName();
        Firebase.setAndroidContext(this);
        userReference = new Firebase("https://trackingpib.firebaseio.com/chat/" + uid );
//        adminReference = new Firebase("https://trackingpib.firebaseio.com/chat/" + uid );

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                if (!messageText.equals("")){
                    pushChat(username, messageText);
                    messageArea.setText("");

                }
            }
        });

        getChat();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void bindView(){
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (RelativeLayout)findViewById(R.id.layout2);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
    }

    private void getChat(){
        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("username").toString();
                String status = map.get("userStatus").toString();

                if(status.equals("USER")){
                    addMessageBox(userName + " :-\n" + message, 1);
                }
                else{
                    addMessageBox("admin" + " :-\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
    private void pushChat(String username, String message){
        if(!message.equals("")){
            Map<String, String> map = new HashMap<String, String>();
            map.put("message", message);
            map.put("username", username);
            map.put("userStatus", userStatus);
            userReference.push().setValue(map);
//            adminReference.push().setValue(map);
        }

    }


    public void addMessageBox(String message, int type){
        TextView textView = new TextView(ChatRoom.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.outchat);
        }
        else{
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.inchat);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void getUserName(){
        database.getReference("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                username = dataSnapshot.child(uid)
                        .child("username").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
