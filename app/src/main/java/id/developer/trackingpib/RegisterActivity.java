package id.developer.trackingpib;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import id.developer.trackingpib.util.GlobalFunction;

public class RegisterActivity extends AppCompatActivity {
    private EditText username,email,password;
    private Button registerButton,loginButton;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("register");

        username = findViewById(R.id.uyeUsername);
        email = findViewById(R.id.uyeEmail);
        password = findViewById(R.id.uyeParola);
        registerButton = findViewById(R.id.yeniUyeButton);
        loginButton = findViewById(R.id.uyeGirisButton);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  final String username_string = username.getText().toString();
                  final String email_string = email.getText().toString();
                  final String password_string = password.getText().toString();

                  if (TextUtils.isEmpty(username_string)) {
                      Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                      return;
                  }

                  if (TextUtils.isEmpty(email_string)) {
                      Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                      return;
                  }
                  if (TextUtils.isEmpty(password_string)) {
                      Toast.makeText(getApplicationContext(), "Please fill in the required fields", Toast.LENGTH_SHORT).show();
                  }

                  if (password_string.length() < 6) {
                      Toast.makeText(getApplicationContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                  }
                  progressDialog = new ProgressDialog(RegisterActivity.this);
                  progressDialog.setMessage("Please wait...");
                  progressDialog.show();
                  progressDialog.setCanceledOnTouchOutside(false);

                  //start register on firebase
                  firebaseAuth.createUserWithEmailAndPassword(email_string, password_string)
                      .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task) {
                              if (!task.isSuccessful()) {
                                  //when failed on register
                                  //hide progressbar
                                  progressDialog.dismiss();
                                  Toast.makeText(RegisterActivity.this, "Register Failed : " + task.getException(),
                                          Toast.LENGTH_SHORT).show();
                              } else {

                                  //when success on register
                                  String inputUid = task.getResult().getUser().getUid();
                                  GlobalFunction.addUidAndUserStatusPref(RegisterActivity.this, inputUid,"USER");
                                  database.getReference("users")
                                          //input user name to database
                                          .child(inputUid)
                                          .child("username").setValue(username_string);
                                  database.getReference("users")
                                          //input email to database
                                          .child(inputUid)
                                          .child("email").setValue(email_string);
                                  database.getReference("users")
                                          //input password to database
                                          .child(inputUid)
                                          .child("password").setValue(password_string);
                                  database.getReference("users")
                                          //input user status to database
                                          .child(inputUid)
                                          .child("userStatus").setValue("USER");
                                  progressDialog.dismiss();
                                  startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                  finish();

                              }
                          }
                      });
              }
          });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
}
