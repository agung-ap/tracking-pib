package id.developer.trackingpib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddPibActivity extends AppCompatActivity {
    private EditText noPib, kpbc, namaImportir, namaPpjk, status, deskripsi;
    private Button addPib;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pib);
    }
}
