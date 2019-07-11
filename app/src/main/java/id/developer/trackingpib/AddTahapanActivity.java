package id.developer.trackingpib;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;

import id.developer.trackingpib.util.ConstantUtil;

public class AddTahapanActivity extends AppCompatActivity {
    private static final String TAG = AddTahapanActivity.class.getName();
    private static final int PICK_PDF_REQUEST = 1;

    private TextInputLayout pibLayout, invoiceLayout, packingListLayout, billLadingLayout, hargaPajakLayout;
    private TextView statusTitle;
    private EditText noPib, invoice, packingList, billOfLading, hargaPajak;
    private AppCompatSpinner status;
    private Button chooseFile, tahapanSave;

    private String flag;

    private ProgressDialog progressDialog;
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tahapan);

        flag = getIntent().getStringExtra("flag");
        bindView();

        progressDialog = new ProgressDialog(this);

        tahapanSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals(ConstantUtil.tahap1)){

                }

                if (flag.equals(ConstantUtil.tahap2)){

                }

                if (flag.equals(ConstantUtil.tahap3)){

                }

                if (flag.equals(ConstantUtil.tahap4)){

                }

                if (flag.equals(ConstantUtil.tahap5)){

                }
            }
        });

        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int readExternalStoragePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                if(readExternalStoragePermission != PackageManager.PERMISSION_GRANTED)
                {
                    String requirePermission[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(AddTahapanActivity.this, requirePermission, PICK_PDF_REQUEST);
                }else {
                    chooseFile();

                }

            }
        });

    }

    private void bindView(){
        // layout
        pibLayout = findViewById(R.id.layout_pib);
        invoiceLayout = findViewById(R.id.layout_invoice);
        packingListLayout = findViewById(R.id.layout_packing_list);
        billLadingLayout = findViewById(R.id.layout_bill_lading);
        hargaPajakLayout = findViewById(R.id.layout_pajak);

        noPib = findViewById(R.id.no_pib_add);
        invoice = findViewById(R.id.invoice_add);
        packingList = findViewById(R.id.packing_list_add);
        billOfLading = findViewById(R.id.bill_of_lading_add);
        hargaPajak = findViewById(R.id.harga_pajak_add);

        status = findViewById(R.id.status_add);
        statusTitle = findViewById(R.id.status_title);

        chooseFile = findViewById(R.id.choose_file);
        tahapanSave = findViewById(R.id.tahapan_save);

        if (flag.equals(ConstantUtil.tahap1)){
            statusTitle.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
            hargaPajakLayout.setVisibility(View.GONE);
        }

        if (flag.equals(ConstantUtil.tahap2)){
            invoiceLayout.setVisibility(View.GONE);
            packingListLayout.setVisibility(View.GONE);
            billLadingLayout.setVisibility(View.GONE);
            chooseFile.setVisibility(View.GONE);
            hargaPajak.setVisibility(View.GONE);
        }

        if (flag.equals(ConstantUtil.tahap3)){
            invoiceLayout.setVisibility(View.GONE);
            packingListLayout.setVisibility(View.GONE);
            billLadingLayout.setVisibility(View.GONE);
            chooseFile.setVisibility(View.GONE);
            statusTitle.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
        }

        if (flag.equals(ConstantUtil.tahap4)){
            invoiceLayout.setVisibility(View.GONE);
            packingListLayout.setVisibility(View.GONE);
            billLadingLayout.setVisibility(View.GONE);
            chooseFile.setVisibility(View.GONE);
            hargaPajak.setVisibility(View.GONE);
        }

        if (flag.equals(ConstantUtil.tahap5)){
            invoiceLayout.setVisibility(View.GONE);
            packingListLayout.setVisibility(View.GONE);
            billLadingLayout.setVisibility(View.GONE);
            chooseFile.setVisibility(View.GONE);
            hargaPajak.setVisibility(View.GONE);
        }
    }

    private void chooseFile() {
        startActivityForResult(new Intent(AddTahapanActivity.this, PickMultipleFileActivity.class)
                , 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){

            Bundle bundle = getIntent().getExtras();
            String [] url = bundle.getStringArray("url");

            for (int i = 0; i < url.length; i++){
                Log.i(TAG, "url file : " + url);
            }


        }
    }

}
