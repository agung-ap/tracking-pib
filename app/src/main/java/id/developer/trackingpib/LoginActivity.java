package id.developer.trackingpib;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import id.developer.trackingpib.util.GlobalFunction;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private EditText loginEmail,loginPassword;
    private Button loginButton,registerButton,newPassButton;
//    private static final int RC_SIGN_IN = 9001;
//    private SignInButton signInButton;
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    private ProgressDialog progressDialog;
//    GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getSupportActionBar().setTitle("login");

        loginEmail = (EditText) findViewById(R.id.girisEmail);
        loginPassword = (EditText) findViewById(R.id.girisParola);
        loginButton = (Button) findViewById(R.id.girisButton);
        registerButton = (Button) findViewById(R.id.uyeOlButton);
        newPassButton = (Button) findViewById(R.id.yeniSifreButton);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(LoginActivity.this,this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
//                .build();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtemail = loginEmail.getText().toString();
                String txtpass = loginPassword.getText().toString();

                if (TextUtils.isEmpty(txtemail) || TextUtils.isEmpty(txtpass)){
                    Toast.makeText(LoginActivity.this,"Data Tidak Lengkap", Toast.LENGTH_SHORT).show();
                }else {

                    auth.signInWithEmailAndPassword(txtemail,txtpass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        //get user id after login success
                                        final String uid = task.getResult().getUser().getUid();
                                        database.getReference("users").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String status = dataSnapshot.child(uid)
                                                        .child("userStatus").getValue(String.class);

                                                if (status.equals("ADMIN")){

                                                    //Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                                    GlobalFunction.addUidAndUserStatusPref(LoginActivity.this, uid,"ADMIN");
                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                    finish();
                                                }else {
                                                    GlobalFunction.addUidAndUserStatusPref(LoginActivity.this, uid,"USER");
                                                    //Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                    finish();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }else {
                                        Toast.makeText(LoginActivity.this,"Email atau Password salah", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//            }
//        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                finish();
            }
        });

        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }

//    private void signIn() {
//        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signIntent,RC_SIGN_IN);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==RC_SIGN_IN){
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if(result.isSuccess()){
//                GoogleSignInAccount account = result.getSignInAccount();
//                authWithGoogle(account);
//            }
//        }
//    }

//    private void authWithGoogle(GoogleSignInAccount account) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                    finish();
//                }
//                else{
//                    Toast.makeText(getApplicationContext(),"Auth Error",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
