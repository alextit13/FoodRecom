package com.bingerdranch.android.foodrecom;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DialogAddition extends AppCompatActivity {

    private ListView list_product;
    private Button btn_ok;
    private Button btn_cancel;

    private ArrayList <String> list;
    private ArrayAdapter<String> adapter;
    private JSONArray array;

    private EditText edit_text_massa;

    private int result = 0;
    private String nameProduct = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_addition);

        list_product = (ListView) findViewById(R.id.list_product);

        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOK();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancel();
            }
        });
        loadingDataBaseProduct();
        edit_text_massa = (EditText) findViewById(R.id.edit_text_massa);
        result = 0;
        list_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (edit_text_massa.getText().toString().equals("")){
                    Toast.makeText(DialogAddition.this,"Введите вес потребленных продуктов",Toast.LENGTH_LONG).show();
                    edit_text_massa.animate().rotationY(360).start();
                }else{
                    try {
                        int mass = Integer.parseInt(edit_text_massa.getText().toString());

                        nameProduct = null;
                        nameProduct = list.get(position);

                                result = result + (array.getJSONObject(position).getInt("B") * mass/100);
                        view.animate().rotationY(360f).start();
                        Toast.makeText(DialogAddition.this,"Добавлено " + result + " ккал", Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    void btnOK(){
        Intent intent = new Intent();
        intent.putExtra("choice",result);
        intent.putExtra("choiceText",nameProduct);

        intent.putExtra("much_mass",Integer.parseInt(edit_text_massa.getText().toString()));


        setResult(RESULT_OK,intent);
        finish();
    }

    void btnCancel(){
        finish();
    }

    void loadingDataBaseProduct(){
        list = new ArrayList<>();
        String dataBase;
        Resources res = getResources();
        InputStream in_s = null;
        in_s = res.openRawResource(R.raw.convertcsv);
        try {
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            dataBase = new String(b);
            array = new JSONArray(dataBase);
            String key = "";
            int value = 0;
            list = new ArrayList<>();
            for (int i = 0; i<array.length();i++){
                String position_product = array.getJSONObject(i).getString("A") + " ("
                        + array.getJSONObject(i).getInt("B") + " ккал)";
                list.add(position_product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<>(DialogAddition.this,android.R.layout.simple_list_item_1,list);
        list_product.setAdapter(adapter);
    }
}
