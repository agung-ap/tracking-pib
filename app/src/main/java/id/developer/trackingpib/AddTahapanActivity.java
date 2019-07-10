package id.developer.trackingpib;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import id.developer.trackingpib.api.PibApi;
import id.developer.trackingpib.api.RetrofitBuilder;
import id.developer.trackingpib.model.request.PibRequest;
import id.developer.trackingpib.model.request.Tahap1Request;
import id.developer.trackingpib.model.request.Tahap2Request;
import id.developer.trackingpib.model.request.Tahap3Request;
import id.developer.trackingpib.model.request.Tahap4Request;
import id.developer.trackingpib.model.request.Tahap5Request;
import id.developer.trackingpib.util.ConstantUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTahapanActivity extends AppCompatActivity {
    private TextInputLayout pibLayout, invoiceLayout, packingListLayout, billLadingLayout, hargaPajakLayout;
    private TextView statusTitle;
    private EditText noPib, invoice, packingList, billOfLading, hargaPajak;
    private AppCompatSpinner status;
    private Button chooseFile, tahapanSave;

    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tahapan);

        flag = getIntent().getStringExtra("flag");
        bindView();

        tahapanSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tahap1Request request1 = new Tahap1Request();
                Tahap2Request request2 = new Tahap2Request();
                Tahap3Request request3 = new Tahap3Request();
                Tahap4Request request4 = new Tahap4Request();
                Tahap5Request request5 = new Tahap5Request();

                int flg = 0;

                if (flag.equals(ConstantUtil.tahap1)){
                    request1.setNo_pib(noPib.getText().toString());
                    request1.setInvoice(invoice.getText().toString());
                    request1.setPacking_list(packingList.getText().toString());
                    request1.setBill_of_lading(billOfLading.getText().toString());
                    flg = 1;
                }

                if (flag.equals(ConstantUtil.tahap2)){
                    request2.setNo_pib(noPib.getText().toString());
                    request2.setStatus(status.getSelectedItem().toString());
                    flg = 2;
                }

                if (flag.equals(ConstantUtil.tahap3)){
                    request3.setNo_pib(noPib.getText().toString());
                    request3.setHarga_pajak(hargaPajak.getText().toString());
                    flg = 3;
                }

                if (flag.equals(ConstantUtil.tahap4)){
                    request4.setNo_pib(noPib.getText().toString());
                    request4.setStatus(status.getSelectedItem().toString());
                    flg = 4;
                }

                if (flag.equals(ConstantUtil.tahap5)){
                    request5.setNo_pib(noPib.getText().toString());
                    request5.setStatus(status.getSelectedItem().toString());
                    flg = 5;
                }
                savePib(request1,request2,request3,request4,request5,flg);
            }
        });

    }

    private void savePib(Tahap1Request request, Tahap2Request request2, Tahap3Request request3, Tahap4Request request4, Tahap5Request request5,int flg){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


        PibApi pibApi = RetrofitBuilder.getApiService().create(PibApi.class);
        Call<ResponseBody> callPibApi = null;

        if (flg==1){
            callPibApi = pibApi.insertTahap1(request);
        }else if(flg==2){
            callPibApi = pibApi.insertTahap2(request2);
        }else if(flg==3){
            callPibApi = pibApi.insertTahap3(request3);
        }else if(flg==4){
            callPibApi = pibApi.insertTahap4(request4);
        }else if(flg==5){
            callPibApi = pibApi.insertTahap5(request5);
        }


        callPibApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    if (array.length() == 0){
                        dialog.dismiss();
                        Toast.makeText(AddTahapanActivity.this, "Pib berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddTahapanActivity.this, "Data PIB tidak sesuai", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                } catch (IOException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
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
        ArrayList statusName = new ArrayList();


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
            statusName.add("Reject");
            statusName.add("Diterima");
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
            statusName.add("Barang Sesuai Dokumen");
            statusName.add("Barang Tidak Sesuai Dokumen");
        }

        if (flag.equals(ConstantUtil.tahap5)){
            invoiceLayout.setVisibility(View.GONE);
            packingListLayout.setVisibility(View.GONE);
            billLadingLayout.setVisibility(View.GONE);
            chooseFile.setVisibility(View.GONE);
            hargaPajak.setVisibility(View.GONE);
            statusName.add("Data PIB sudah Lengkap");
            statusName.add("Data PIB Belum Lengkap");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AddTahapanActivity.this,android.R.layout.simple_spinner_item,
                statusName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);

    }





}
