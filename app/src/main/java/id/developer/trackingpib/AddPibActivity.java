package id.developer.trackingpib;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.developer.trackingpib.api.PibApi;
import id.developer.trackingpib.api.RetrofitBuilder;
import id.developer.trackingpib.model.request.PibRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPibActivity extends AppCompatActivity {

    private EditText noPib, kpbc, namaImportir, namaPpjk, deskripsi;
    private String pibStatus;
    private AppCompatSpinner status;
    private Button addPib;

    private String [] pibStatusList = {"DITERIMA", "DITOLAK"};
    private List<String> statusName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pib);

        bindView();
        loadSpinner();
        addPib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePib();
            }
        });
    }

    private void loadSpinner(){
        statusName.addAll(Arrays.asList(pibStatusList));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AddPibActivity.this,android.R.layout.simple_spinner_item,
                statusName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(adapter);

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pibStatus = statusName.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void bindView(){
        noPib = findViewById(R.id.no_pib_add);
        kpbc = findViewById(R.id.kpbc_add);
        namaImportir = findViewById(R.id.nama_importir_add);
        namaPpjk = findViewById(R.id.nama_ppjk_add);
        status = findViewById(R.id.status_add);
        deskripsi = findViewById(R.id.no_pib_add);

        addPib = findViewById(R.id.save_pib);
    }

    private void savePib(){
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        final PibRequest request = new PibRequest(
                noPib.getText().toString().trim(),
                kpbc.getText().toString().trim(),
                namaImportir.getText().toString().trim(),
                namaPpjk.getText().toString().trim(),
                pibStatus,
                deskripsi.getText().toString().trim()
        );

        PibApi pibApi = RetrofitBuilder.getApiService().create(PibApi.class);
        Call<ResponseBody> callPibApi = pibApi.insertPib(request);
        callPibApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray array = new JSONArray(response.body().string());
                    if (array.length() == 0){
                        dialog.dismiss();
                        Toast.makeText(AddPibActivity.this, "Pib berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }
}
