package com.progect.in_service.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.progect.in_service.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Oapp extends AppCompatActivity {
    RecyclerView recyclerView;

    ArrayList<DataList> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_o_priloshenii);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.RV);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayList = new ArrayList<DataList>();

        DataList dataList = new DataList("Оферта");
        arrayList.add(dataList);
        dataList = new DataList("Политика");
        arrayList.add(dataList);


        DataAdapter dataAdapter = new DataAdapter(new Myclick() {
            @Override
            public void onMyClick(View view, int Possition) {

                String name = arrayList.get(Possition).getName();
                if (name == "Оферта") {
                    Intent i = new Intent(Oapp.this, FragmentOferta.class);
                    i.putExtra("name", name);
                    startActivity(i);
                }
                if (name == "Политика") {
                    Intent i = new Intent(Oapp.this, FragmentPolitica.class);
                    i.putExtra("name", name);
                    startActivity(i);
                }
            }
        }, getApplicationContext(), arrayList);

        recyclerView.setAdapter(dataAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }else if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
