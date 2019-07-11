package id.developer.trackingpib;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import id.developer.trackingpib.adapter.ListFileAdapter;
import id.developer.trackingpib.model.ListFileModel;

public class PickMultipleFileActivity extends AppCompatActivity {
    private static final String TAG = PickMultipleFileActivity.class.getName();
    private static final int PICK_FILE = 10;
    private RecyclerView recyclerView;
    private ListFileAdapter adapter;
    private List<ListFileModel> listFile = new ArrayList<>();

    private Button chooseFile, uploadFile;

    private int count;
    private String [] downloadUrl;



    private StorageReference storage;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_multiple_file);
        getSupportActionBar().setTitle("Pick a File");



        bindView();

        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listFile.size() == 3){
                    Toast.makeText(PickMultipleFileActivity.this,
                            "File terpilih tidak bisa lebih dari " + listFile.size()
                            , Toast.LENGTH_SHORT).show();
                }else {
                    pickAfile();
                }
            }
        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listFile.size() == 0){
                    Toast.makeText(PickMultipleFileActivity.this,
                            "Silahkan pilih File terlebih dahulu",
                            Toast.LENGTH_SHORT).show();
                }else {
                    setUploadFile();
                }
            }
        });

    }

    private void bindView(){
        chooseFile = findViewById(R.id.choose_file);
        uploadFile = findViewById(R.id.upload_file);

        adapter = new ListFileAdapter(this);

        recyclerView =  findViewById(R.id.list_file);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void pickAfile(){
        try{
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FILE);
        }catch (Exception e){
            Log.e(TAG, "chose image exception = " + e.getLocalizedMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE && resultCode == RESULT_OK
                && data != null && data.getData() != null){

            // Getting the URI of the selected file and logging into logcat at debug level
            Log.d("fileUri: ", String.valueOf(data.getData()));

            File file = new File(data.getData().getPath());

            ListFileModel fileModel = new ListFileModel();
            fileModel.setFilename(file.getName());
            fileModel.setFilepath(file.getPath());
            fileModel.setUri(data.getData());

            listFile.add(fileModel);
            adapter.setData(listFile);
        }
    }

    private void setUploadFile(){
        progressDialog = new ProgressDialog(PickMultipleFileActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        downloadUrl = new String[0];
        count = 0;
        for (int i = 0; i < listFile.size(); i++){

            storage = FirebaseStorage.getInstance().getReference()
                    .child("eform/" + UUID.randomUUID().toString());

            storage.putFile(listFile.get(i).getUri())
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //hide progress bar
//                    for (int j = 0; j < listFile.size(); j++) {
////                     get download url
//                        storage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                Log.i(TAG, "onSuccess: " + uri.toString());
//                            }
//                        });
//                    }
                    count++;
                    if (count >= listFile.size()){
                        progressDialog.dismiss();
                        listFile.clear();
                        adapter.setData(listFile);

                        Bundle b=new Bundle();
                        Intent i = new Intent(PickMultipleFileActivity.this ,AddTahapanActivity.class);
                        b.putStringArray("url",downloadUrl);
                        i.putExtras(b);
                        setResult(1, i);

                        Toast.makeText(PickMultipleFileActivity.this, "Upload Berhasil", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int)progress + " %");
                }
            });
        }
    }
}
