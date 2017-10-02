package com.bingerdranch.android.foodrecom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Settings extends AppCompatActivity {

    private Spinner spinner_obraz_zhizni;
    ArrayList <String> list_obraz_zhizni;

    private Spinner spinner_years;
    ArrayList <String> list_years;

    private Spinner spinner_sex;
    ArrayList <String> list_sex;

    private Spinner spinner_want;
    ArrayList<String>list_want;

    private Button ok_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        spinner_obraz_zhizni = (Spinner) findViewById(R.id.spinner_obraz_zhizni);
        spinner_years = (Spinner) findViewById(R.id.spinner_years);
        spinner_sex = (Spinner) findViewById(R.id.spinner_sex);
        spinner_want = (Spinner) findViewById(R.id.spinner_want);

        ok_button = (Button)findViewById(R.id.ok_button);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
        addToSpinners();
    }

    void addToSpinners(){
        list_obraz_zhizni = new ArrayList<>();
        list_obraz_zhizni.add("Пассивный");
        list_obraz_zhizni.add("Умеренный");
        list_obraz_zhizni.add("Активный");

        list_years = new ArrayList<>();
        list_years.add("19 - 30");
        list_years.add("31 - 50");
        list_years.add("51 - 70");

        list_sex = new ArrayList<>();
        list_sex.add("Мужской");
        list_sex.add("Женский");

        list_want = new ArrayList<>();
        list_want.add("Похудеть");
        list_want.add("Поддерживать массу");
        list_want.add("Набираем массу");

        ArrayAdapter<String> adapter_1 = new ArrayAdapter<>(Settings.this,android.R.layout.simple_list_item_1,list_obraz_zhizni);
        spinner_obraz_zhizni.setAdapter(adapter_1);

        ArrayAdapter<String> adapter_2 = new ArrayAdapter<>(Settings.this,android.R.layout.simple_list_item_1,list_years);
        spinner_years.setAdapter(adapter_2);

        ArrayAdapter <String> adapter_3 = new ArrayAdapter<String>(Settings.this,android.R.layout.simple_list_item_1,list_sex);
        spinner_sex.setAdapter(adapter_3);

        ArrayAdapter <String> adapter_4 = new ArrayAdapter<String>(Settings.this,android.R.layout.simple_list_item_1,list_want);
        spinner_want.setAdapter(adapter_4);
    }

    void exit(){
        Intent intent = new Intent();
        intent.putExtra("obraz_zhizni",spinner_obraz_zhizni.getSelectedItem().toString());
        intent.putExtra("years",spinner_years.getSelectedItem().toString());
        intent.putExtra("sex",spinner_sex.getSelectedItem().toString());
        intent.putExtra("want",spinner_want.getSelectedItem().toString());
        setResult(RESULT_OK,intent);

        finish();
    }
}
