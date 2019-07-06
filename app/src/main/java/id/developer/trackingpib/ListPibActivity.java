package id.developer.trackingpib;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import id.developer.trackingpib.adapter.ListTahap1Adapter;
import id.developer.trackingpib.adapter.ListTahap2Adapter;
import id.developer.trackingpib.adapter.ListTahap3Adapter;
import id.developer.trackingpib.adapter.ListTahap4Adapter;
import id.developer.trackingpib.adapter.ListTahap5Adapter;
import id.developer.trackingpib.api.DataApi;
import id.developer.trackingpib.api.RetrofitBuilder;
import id.developer.trackingpib.model.Tahap1Model;
import id.developer.trackingpib.model.Tahap2Model;
import id.developer.trackingpib.model.Tahap3Model;
import id.developer.trackingpib.model.Tahap4Model;
import id.developer.trackingpib.model.Tahap5Model;
import id.developer.trackingpib.util.ConstantUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPibActivity extends AppCompatActivity implements ListTahap1Adapter.Listener,
        ListTahap2Adapter.Listener,ListTahap3Adapter.Listener,ListTahap4Adapter.Listener,ListTahap5Adapter.Listener{
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    private SharedPreferences preferences;
    private String userStatus;

    private ListTahap1Adapter tahap1Adapter;
    private ListTahap2Adapter tahap2Adapter;
    private ListTahap3Adapter tahap3Adapter;
    private ListTahap4Adapter tahap4Adapter;
    private ListTahap5Adapter tahap5Adapter;


    private List<Tahap1Model> tahap1List;
    private List<Tahap2Model> tahap2List;
    private List<Tahap3Model> tahap3List;
    private List<Tahap4Model> tahap4List;
    private List<Tahap5Model> tahap5List;

    private String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pib);
        preferences = this.getSharedPreferences(getString(R.string.CREDENTIAL), Context.MODE_PRIVATE);
        userStatus = preferences.getString(getString(R.string.GET_USER_STATUS),null);
//        get flag
        flag = getIntent().getStringExtra("flag");
        progressBar = findViewById(R.id.progres_bar);
        progressBar.setVisibility(View.GONE);


        bindView();
        getListData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equals(ConstantUtil.tahap1)){
                    Intent intent = new Intent(ListPibActivity.this, AddTahapanActivity.class);
                    intent.putExtra("flag", flag);
                    startActivity(intent);
                }

                if (flag.equals(ConstantUtil.tahap2)){
                    Intent intent = new Intent(ListPibActivity.this, AddTahapanActivity.class);
                    intent.putExtra("flag", flag);
                    startActivity(intent);
                }

                if (flag.equals(ConstantUtil.tahap3)){
                    Intent intent = new Intent(ListPibActivity.this, AddTahapanActivity.class);
                    intent.putExtra("flag", flag);
                    startActivity(intent);
                }

                if (flag.equals(ConstantUtil.tahap4)){
                    Intent intent = new Intent(ListPibActivity.this, AddTahapanActivity.class);
                    intent.putExtra("flag", flag);
                    startActivity(intent);
                }

                if (flag.equals(ConstantUtil.tahap5)){
                    Intent intent = new Intent(ListPibActivity.this, AddTahapanActivity.class);
                    intent.putExtra("flag", flag);
                    startActivity(intent);
                }
            }
        });

    }

    private void bindView(){
        tahap1Adapter = new ListTahap1Adapter(this, this);
        tahap2Adapter = new ListTahap2Adapter(this, this);
        tahap3Adapter = new ListTahap3Adapter(this, this);
        tahap4Adapter = new ListTahap4Adapter(this, this);
        tahap5Adapter = new ListTahap5Adapter(this, this);

        fab = findViewById(R.id.add_tahapan_list_data);
        if (userStatus.equals("USER")){
            fab.hide();
        }else {
            fab.show();
        }

        recyclerView =  findViewById(R.id.tahap_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void getListData(){
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        DataApi dataApi = RetrofitBuilder.getApiService().create(DataApi.class);

        if (flag.equals(ConstantUtil.tahap1)){
            Call<ResponseBody> callDataApi = dataApi.getTahap1();
            getTahap1Data(callDataApi);
        }

        if (flag.equals(ConstantUtil.tahap2)){
            Call<ResponseBody> callDataApi = dataApi.getTahap2();
            getTahap2Data(callDataApi);
        }

        if (flag.equals(ConstantUtil.tahap3)){
            Call<ResponseBody> callDataApi = dataApi.getTahap3();
            getTahap3Data(callDataApi);
        }

        if (flag.equals(ConstantUtil.tahap4)){
            Call<ResponseBody> callDataApi = dataApi.getTahap4();
            getTahap4Data(callDataApi);
        }

        if (flag.equals(ConstantUtil.tahap5)){
            Call<ResponseBody> callDataApi = dataApi.getTahap5();
            getTahap5Data(callDataApi);
        }


    }


    private void getTahap1Data(Call<ResponseBody> callDataApi){
        callDataApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONArray array = new JSONArray(response.body().string());

                    if (array.length() == 0){
                        Toast.makeText(ListPibActivity.this, "data kosong", Toast.LENGTH_SHORT).show();
                    }

                    tahap1List = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        Tahap1Model model = new Tahap1Model();
                        model.setId(object.getInt("id"));
                        model.setNoPib(object.getString("no_pib"));
                        model.setBillOfLading(object.getString("bill_of_lading"));
                        model.setInvoice(object.getString("invoice"));
                        model.setPackingList(object.getString("packing_list"));
                        model.setFormE(object.getString("form_E"));

                        tahap1List.add(model);
                    }

                    tahap1Adapter.setData(tahap1List);
                    recyclerView.setAdapter(tahap1Adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getTahap2Data(Call<ResponseBody> callDataApi){
        callDataApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONArray array = new JSONArray(response.body().string());

                    if (array.length() == 0){
                        Toast.makeText(ListPibActivity.this, "data kosong", Toast.LENGTH_SHORT).show();
                    }

                    tahap2List = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        Tahap2Model model = new Tahap2Model();
                        model.setId(object.getInt("id"));
                        model.setNoPib(object.getString("no_pib"));
                        model.setStatus(object.getString("status"));

                        tahap2List.add(model);
                    }

                    tahap2Adapter.setData(tahap2List);
                    recyclerView.setAdapter(tahap2Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getTahap3Data(Call<ResponseBody> callDataApi){
        callDataApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONArray array = new JSONArray(response.body().string());

                    if (array.length() == 0){
                        Toast.makeText(ListPibActivity.this, "data kosong", Toast.LENGTH_SHORT).show();
                    }

                    tahap3List = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        Tahap3Model model = new Tahap3Model();
                        model.setId(object.getInt("id"));
                        model.setNoPib(object.getString("no_pib"));
                        model.setHargaPajak(object.getString("harga_pajak"));

                        tahap3List.add(model);
                    }

                    tahap3Adapter.setData(tahap3List);
                    recyclerView.setAdapter(tahap3Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getTahap4Data(Call<ResponseBody> callDataApi){
        callDataApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONArray array = new JSONArray(response.body().string());

                    if (array.length() == 0){
                        Toast.makeText(ListPibActivity.this, "data kosong", Toast.LENGTH_SHORT).show();
                    }

                    tahap4List = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        Tahap4Model model = new Tahap4Model();
                        model.setId(object.getInt("id"));
                        model.setNoPib(object.getString("no_pib"));
                        model.setStatus(object.getString("status"));

                        tahap4List.add(model);
                    }

                    tahap4Adapter.setData(tahap4List);
                    recyclerView.setAdapter(tahap4Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void getTahap5Data(Call<ResponseBody> callDataApi){
        callDataApi.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    JSONArray array = new JSONArray(response.body().string());

                    if (array.length() == 0){
                        Toast.makeText(ListPibActivity.this, "data kosong", Toast.LENGTH_SHORT).show();
                    }

                    tahap5List = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        Tahap5Model model = new Tahap5Model();
                        model.setId(object.getInt("id"));
                        model.setNoPib(object.getString("no_pib"));
                        model.setStatus(object.getString("status"));

                        tahap5List.add(model);
                    }

                    tahap5Adapter.setData(tahap5List);
                    recyclerView.setAdapter(tahap5Adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

//    TAHAP 1
    @Override
    public void onClick(Tahap1Model dataPosition) {
        ArrayList<Tahap1Model> tahapList = new ArrayList<>();
        tahapList.add(dataPosition);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.GET_SELECTED_ITEM), tahapList);

    }
//    TAHAP 2
    @Override
    public void onClick(Tahap2Model dataPosition) {
        ArrayList<Tahap2Model> tahapList = new ArrayList<>();
        tahapList.add(dataPosition);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.GET_SELECTED_ITEM), tahapList);
    }
//    TAHAP 3
    @Override
    public void onClick(Tahap3Model dataPosition) {
        ArrayList<Tahap3Model> tahapList = new ArrayList<>();
        tahapList.add(dataPosition);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.GET_SELECTED_ITEM), tahapList);
    }
//    TAHAP 4
    @Override
    public void onClick(Tahap4Model dataPosition) {
        ArrayList<Tahap4Model> tahapList = new ArrayList<>();
        tahapList.add(dataPosition);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.GET_SELECTED_ITEM), tahapList);
    }
//    TAHAP 5
    @Override
    public void onClick(Tahap5Model dataPosition) {
        ArrayList<Tahap5Model> tahapList = new ArrayList<>();
        tahapList.add(dataPosition);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.GET_SELECTED_ITEM), tahapList);
    }
}
