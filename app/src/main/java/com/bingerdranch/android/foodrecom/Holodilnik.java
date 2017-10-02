package com.bingerdranch.android.foodrecom;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Holodilnik extends Activity {

    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_EDIT_TEXT = "edit_text";

    private ListView list_product_freezer;
    private ArrayList<String> listWithProduct;
    private ArrayList<String> listWithMass;
    private SimpleAdapter adapter;
    private Button button_add;
    private String newProduct;
    private int startMass = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holodilnik);
        init();
    }

    private void init() {
        list_product_freezer = (ListView)findViewById(R.id.list_product_freezer);
        listWithProduct = new ArrayList<>();
        listWithMass = new ArrayList<>();

        updateAdapter();

        button_add = (Button) findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAdd();
            }
        });
    }

    private void updateAdapter() {

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> m;

        for (int i = 0; i < listWithProduct.size(); i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_TEXT, listWithProduct.get(i));
            m.put(ATTRIBUTE_NAME_EDIT_TEXT, listWithMass.get(i));
            data.add(m);
        }

        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_EDIT_TEXT};
        int[] to = { R.id.tv_name_product, R.id.et_name_product};

        adapter = new SimpleAdapter(this, data, R.layout.item, from, to);
        list_product_freezer.setAdapter(adapter);

        list_product_freezer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Holodilnik.this.onItemClick(position);
            }
        });
        list_product_freezer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                buttonRemove(position);
                return true;
            }
        });
    }

    private void onItemClick(final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_change_mass_product);
        final EditText editText = (EditText) dialog.findViewById(R.id.editTextNewMassProduct);
        Button buttonOK = (Button)dialog.findViewById(R.id.button_ok_change_mass_product);
        Button buttonCANCEL = (Button)dialog.findViewById(R.id.button_cancel_change_mass_product);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listWithMass.set(position,editText.getText().toString());
                updateAdapter();
                dialog.cancel();
            }
        });
        buttonCANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void buttonAdd() {
        Intent intent = new Intent(this,DialogAddition.class);
        startActivityForResult(intent,1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null||data.getStringExtra("choiceText").equals("")){
            return;
        }else{
            newProduct = data.getStringExtra("choiceText");
            listWithProduct.add(newProduct);

            startMass = data.getIntExtra("much_mass",0);
            listWithMass.add(startMass+"");

            updateAdapter();
            //adapter.add(newProduct);

            // ниже нужно добавить выбранный продукт в адаптер листа
        }
    }

    private void buttonRemove(final int position) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete_item);

        Button bOK = (Button)dialog.findViewById(R.id.button_delete_item_ok);
        Button bCancel = (Button) dialog.findViewById(R.id.button_delete_item_cancel);

        bOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listWithProduct.remove(position);
                listWithMass.remove(position);
                updateAdapter();

                dialog.cancel();
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
