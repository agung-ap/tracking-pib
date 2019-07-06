package id.developer.trackingpib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.developer.trackingpib.api.PibApi;
import id.developer.trackingpib.api.RetrofitBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchListPib extends AppCompatActivity {
    private EditText search;
    private Button searchButton;
    private RelativeLayout searchLayout;

    private TextView tahap, noPib, kpbc, namaImportir, namaPpjk, status, deskripsi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list_pib);

        bindView();
        searchLayout.setVisibility(View.GONE);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPib(search.getText().toString().trim());
            }
        });
    }

    private void bindView(){
        search = findViewById(R.id.search);
        searchButton = findViewById(R.id.search_button);
        searchLayout = findViewById(R.id.search_layout);

        tahap = findViewById(R.id.tahap_detail);
        noPib = findViewById(R.id.no_pib_detail);
        kpbc = findViewById(R.id.kpbc_detail);
        namaImportir = findViewById(R.id.nama_importir_detail);
        namaPpjk = findViewById(R.id.nama_ppjk_detail);
        status = findViewById(R.id.status_detail);
        deskripsi = findViewById(R.id.deskripsi_detail);
    }

    private void searchPib(String pib){
        PibApi pibApi = RetrofitBuilder.getApiService().create(PibApi.class);
        Call<ResponseBody> callPibApi = pibApi.searchPib(pib);
        callPibApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    JSONObject object = new JSONObject(response.body().string());
                    JSONObject pib = object.getJSONObject("pib");

                    searchLayout.setVisibility(View.VISIBLE);

                    tahap.setText(object.getString("tahap"));
                    noPib.setText(pib.getString("no_pib"));
                    kpbc.setText(pib.getString("kpbc"));
                    namaImportir.setText(pib.getString("nama_importir"));
                    namaPpjk.setText(pib.getString("nama_ppjk"));
                    status.setText(pib.getString("status"));
                    deskripsi.setText(pib.getString("deskripsi"));

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
