package id.developer.trackingpib;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import id.developer.trackingpib.util.ConstantUtil;

public class MenuPibActivity extends AppCompatActivity {
    private CardView cardListPib1, cardListPib2, cardListPib3, cardListPib4, cardListPib5;

    private SharedPreferences preferences;
    private String userStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pib);
        getSupportActionBar().setTitle("Menu Tahap PIB");

        preferences = this.getSharedPreferences(getString(R.string.CREDENTIAL), Context.MODE_PRIVATE);
        userStatus = preferences.getString(getString(R.string.GET_USER_STATUS),null);

        bindView();
        listPib();
    }

    private void bindView(){
        cardListPib1 = findViewById(R.id.list_pib_1);
        cardListPib2 = findViewById(R.id.list_pib_2);
        cardListPib3 = findViewById(R.id.list_pib_3);
        cardListPib4 = findViewById(R.id.list_pib_4);
        cardListPib5 = findViewById(R.id.list_pib_5);
    }

    private void listPib(){
        cardListPib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPibActivity.this, ListPibActivity.class);
                intent.putExtra("flag", ConstantUtil.tahap1);
                startActivity(intent);

            }
        });

        cardListPib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPibActivity.this, ListPibActivity.class);
                intent.putExtra("flag", ConstantUtil.tahap2);
                startActivity(intent);

            }
        });

        cardListPib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPibActivity.this, ListPibActivity.class);
                intent.putExtra("flag", ConstantUtil.tahap3);
                startActivity(intent);

            }
        });

        cardListPib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPibActivity.this, ListPibActivity.class);
                intent.putExtra("flag", ConstantUtil.tahap4);
                startActivity(intent);

            }
        });

        cardListPib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuPibActivity.this, ListPibActivity.class);
                intent.putExtra("flag", ConstantUtil.tahap5);
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (userStatus.equals("USER")){
            getMenuInflater().inflate(R.menu.pib_menu, menu);
        }else {
            getMenuInflater().inflate(R.menu.pib_menu_admin, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search){
            startActivity(new Intent(MenuPibActivity.this, SearchListPib.class));
        }

        if (item.getItemId() == R.id.add){
            startActivity(new Intent(MenuPibActivity.this, AddPibActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
