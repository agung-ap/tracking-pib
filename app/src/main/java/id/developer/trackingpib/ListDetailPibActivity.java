package id.developer.trackingpib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class ListDetailPibActivity extends AppCompatActivity {
    private String flag;
    private String pib;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_detail_pib);

        flag = getIntent().getStringExtra("flag");
        pib = getIntent().getStringExtra("pib");


    }
}
