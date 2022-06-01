package com.progect.in_service.ui.gallery;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.progect.in_service.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FragmentPolitica extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_politica);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine);
        editTextTextMultiLine.setText(getTermsString());
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
    private String getTermsString() {
        StringBuilder termsString = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("politica.txt")));

            String str;
            while ((str = reader.readLine()) != null) {
                termsString.append(str);
            }

            reader.close();
            return termsString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}