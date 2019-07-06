package id.developer.trackingpib;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import id.developer.trackingpib.util.ConstantUtil;

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





}
