package id.developer.trackingpib;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import id.developer.trackingpib.api.DataApi;
import id.developer.trackingpib.api.RetrofitBuilder;
import id.developer.trackingpib.util.ConstantUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDetailPibActivity extends AppCompatActivity {
    private String flag;
    private String pib;

    private RelativeLayout layoutPib, layoutTahap1, layoutTahap2, layoutTahap3, layoutTahap4, layoutTahap5;
    private TextView messagePib, messageTahap1, messageTahap2, messageTahap3, messageTahap4, messageTahap5;

    private TextView noPib, kpbc, namaImportir, namaPpjk, status, deskripsi;
    private TextView noPibTahap1, invoiceTahap1;
    private TextView noPibTahap2, statusTahap2;
    private TextView noPibTahap3, hargaPajakTahap3;
    private TextView noPibTahap4, statusTahap4;
    private TextView noPibTahap5, statusTahap5;

    private Button formETahap1, packingListTahap1, billOfladingTahap1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail_pib);

        flag = getIntent().getStringExtra("flag");
        pib = getIntent().getStringExtra("pib");

        getSupportActionBar().setTitle("Detail PIB " + pib);
        bindView();
        getDetailPib(pib);
    }

    private void bindView(){
        noPib = findViewById(R.id.no_pib_detail);
        kpbc = findViewById(R.id.kpbc_detail);
        namaImportir = findViewById(R.id.nama_importir_detail);
        namaPpjk = findViewById(R.id.nama_ppjk_detail);
        status = findViewById(R.id.status_detail);
        deskripsi = findViewById(R.id.deskripsi_detail);

        noPibTahap1 = findViewById(R.id.no_pib_tahap2);
        invoiceTahap1 = findViewById(R.id.invoice_tahap1);
        packingListTahap1 = findViewById(R.id.packing_list_tahap1);
        billOfladingTahap1 = findViewById(R.id.bill_of_lading_tahap1);
        formETahap1 = findViewById(R.id.e_form_tahap1);

        noPibTahap2 = findViewById(R.id.no_pib_tahap2);
        statusTahap2 = findViewById(R.id.status_tahap2);

        noPibTahap3 = findViewById(R.id.no_pib_tahap3);
        hargaPajakTahap3 = findViewById(R.id.harga_pajak_tahap3);

        noPibTahap4 = findViewById(R.id.no_pib_tahap4);
        statusTahap4 = findViewById(R.id.status_tahap4);

        noPibTahap5 = findViewById(R.id.no_pib_tahap5);
        statusTahap5 = findViewById(R.id.status_tahap5);
//        layout
        layoutPib = findViewById(R.id.layout_pib);
        layoutTahap1 = findViewById(R.id.layout_tahap1);
        layoutTahap2 = findViewById(R.id.layout_tahap2);
        layoutTahap3 = findViewById(R.id.layout_tahap3);
        layoutTahap4 = findViewById(R.id.layout_tahap4);
        layoutTahap5 = findViewById(R.id.layout_tahap5);
//        message
        messagePib = findViewById(R.id.message_pib);
        messageTahap1 = findViewById(R.id.message_tahap1);
        messageTahap2 = findViewById(R.id.message_tahap2);
        messageTahap3 = findViewById(R.id.message_tahap3);
        messageTahap4 = findViewById(R.id.message_tahap4);
        messageTahap5 = findViewById(R.id.message_tahap5);
    }

    private void getDetailPib(String pib){
        DataApi dataApi = RetrofitBuilder.getApiService().create(DataApi.class);
        Call<ResponseBody> callDataApi = dataApi.getDetailPib(pib);
        callDataApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject object = new JSONObject(response.body().string());

                    JSONObject pib = object.getJSONObject("pib");
                    JSONObject tahap1 = object.getJSONObject("tahap1");
                    JSONObject tahap2 = object.getJSONObject("tahap2");
                    JSONObject tahap3 = object.getJSONObject("tahap3");
                    JSONObject tahap4 = object.getJSONObject("tahap4");
                    JSONObject tahap5 = object.getJSONObject("tahap5");

                    if (pib.length() == 0){
                        messageTahap1.setVisibility(View.VISIBLE);
                        messageTahap1.setText("Data Pib tidak tersedia");
                        layoutTahap1.setVisibility(View.GONE);
                    }else {
                        noPib.setText(pib.getString("no_pib"));
                        kpbc.setText(pib.getString("kpbc"));
                        namaImportir.setText(pib.getString("nama_importir"));
                        namaPpjk.setText(pib.getString("nama_ppjk"));
                        status.setText(pib.getString("status"));
                        deskripsi.setText(pib.getString("deskripsi"));

                    }

                    if (tahap1.length() == 0){
                        messageTahap1.setVisibility(View.VISIBLE);
                        messageTahap1.setText("Belum Sampai Tahapan Ini");
                        layoutTahap1.setVisibility(View.GONE);
                    }else {
                        noPibTahap1.setText(tahap1.getString("no_pib"));
                        invoiceTahap1.setText(tahap1.getString("invoice"));
                        final String url_packinglist = tahap1.getString("packing_list");
                        final String url_billoflading = tahap1.getString("bill_of_lading");
                        final String url_forme = tahap1.getString("form_E");

                        packingListTahap1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                                httpIntent.setData(Uri.parse(url_packinglist));

                                startActivity(httpIntent);
                            }
                        });

                        billOfladingTahap1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                                httpIntent.setData(Uri.parse(url_billoflading));

                                startActivity(httpIntent);
                            }
                        });

                        formETahap1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                                httpIntent.setData(Uri.parse(url_forme));

                                startActivity(httpIntent);
                            }
                        });

                    }

                    if (tahap2.length() == 0){
                        messageTahap2.setVisibility(View.VISIBLE);
                        messageTahap2.setText("Belum Sampai Tahapan Ini");
                        layoutTahap2.setVisibility(View.GONE);
                    }else {
                        noPibTahap2.setText(tahap2.getString("no_pib"));
                        statusTahap2.setText(tahap2.getString("status"));
                    }

                    if (tahap3.length() == 0){
                        messageTahap3.setVisibility(View.VISIBLE);
                        messageTahap3.setText("Belum Sampai Tahapan Ini");
                        layoutTahap3.setVisibility(View.GONE);
                    }else {
                        noPibTahap3.setText(tahap3.getString("no_pib"));
                        hargaPajakTahap3.setText(tahap3.getString("harga_pajak"));
                    }

                    if (tahap4.length() == 0){
                        messageTahap4.setVisibility(View.VISIBLE);
                        messageTahap4.setText("Belum Sampai Tahapan Ini");
                        layoutTahap4.setVisibility(View.GONE);
                    }else {
                        noPibTahap4.setText(tahap4.getString("no_pib"));
                        statusTahap4.setText(tahap4.getString("status"));
                    }

                    if (tahap5.length() == 0){
                        messageTahap5.setVisibility(View.VISIBLE);
                        messageTahap5.setText("Belum Sampai Tahapan Ini");
                        layoutTahap5.setVisibility(View.GONE);
                    }else {
                        noPibTahap5.setText(tahap5.getString("no_pib"));
                        statusTahap5.setText(tahap5.getString("status"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


}
