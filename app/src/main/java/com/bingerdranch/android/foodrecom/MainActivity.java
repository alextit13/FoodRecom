package com.bingerdranch.android.foodrecom;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String LOG_TAG = "MyLogs";

    private String obraz_zhizni;
    private String years;
    private String sex;
    private String want;

    private int NORMA_CALLORIY;
    private String dataBase;

    private TextView norma_calloriy_only_text;
    private TextView norma_calloriy;
    private SharedPreferences sPreferSettings;

    private ImageView image_zavtrak_add;
    private ImageView image_obed_add;
    private ImageView image_uzin_add;

    private TextView text_view_numCalloriesZavtrak;
    private TextView text_view_numCalloriesObed;
    private TextView text_view_numCalloriesUzin;

    private HashMap<String, Integer> mapWithProducts;
    private View view_click;

    private SharedPreferences sPrefs;

    private TextView water_norma;

    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        container = (LinearLayout) findViewById(R.id.container);
        container.getBackground().setAlpha(70);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadingDataBaseProduct();



        norma_calloriy_only_text = (TextView) findViewById(R.id.norma_calloriy);
        norma_calloriy = (TextView) findViewById(R.id.norma);

        image_zavtrak_add = (ImageView) findViewById(R.id.image_zavtrak_add);
        image_obed_add = (ImageView) findViewById(R.id.image_obed_add);
        image_uzin_add = (ImageView) findViewById(R.id.image_uzin_add);

        image_zavtrak_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChoise(v);
            }
        });
        image_obed_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChoise(v);
            }
        });
        image_uzin_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChoise(v);
            }
        });

        text_view_numCalloriesZavtrak = (TextView) findViewById(R.id.text_view_numCalloriesZavtrak);
        text_view_numCalloriesObed = (TextView)  findViewById(R.id.text_view_numCalloriesObed);
        text_view_numCalloriesUzin = (TextView) findViewById(R.id.text_view_numCalloriesUzin);

        water_norma = (TextView)findViewById(R.id.water_norma);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void dialogChoise(View v){
        view_click = v;
        Intent intent = new Intent(this, DialogAddition.class);
        startActivityForResult(intent,2);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d(LOG_TAG,"start_activity_settings");
            Intent intent = new Intent(this, Settings.class);
            startActivityForResult(intent,1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ниже мой код

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null){return;}
        if (requestCode == 1){
            obraz_zhizni = data.getStringExtra("obraz_zhizni");
            years = data.getStringExtra("years");
            sex = data.getStringExtra("sex");
            want = data.getStringExtra("want");

            sPreferSettings = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = sPreferSettings.edit();
            editor.putString("obraz_zhizni", obraz_zhizni);
            editor.putString("years",years);
            editor.putString("sex",sex);
            editor.putString("want",want);
            editor.commit();

            normaCallories();
        }
        if (requestCode == 2){
            if (view_click.getId()==image_zavtrak_add.getId()){
                int result = transformStringtoInt(text_view_numCalloriesZavtrak.getText().toString())
                        - data.getIntExtra("choice",0);
                text_view_numCalloriesZavtrak.setText(result+"");
                if (result<=0){
                    text_view_numCalloriesZavtrak.setText(""+0);
                    text_view_numCalloriesZavtrak.setTextColor(Color.GREEN);
                }
            }else if (view_click.getId()==image_obed_add.getId()){
                int result = transformStringtoInt(text_view_numCalloriesObed.getText().toString())
                        - data.getIntExtra("choice",0);
                text_view_numCalloriesObed.setText(result+"");
                if (result<=0){
                    text_view_numCalloriesObed.setText(""+0);
                    text_view_numCalloriesObed.setTextColor(Color.GREEN);
                }
            }else if (view_click.getId()==image_uzin_add.getId()){
                int result = transformStringtoInt(text_view_numCalloriesUzin.getText().toString())
                        - data.getIntExtra("choice",0);
                text_view_numCalloriesUzin.setText(result+"");
                if (result<=0){
                    text_view_numCalloriesUzin.setText(""+0);
                    text_view_numCalloriesUzin.setTextColor(Color.GREEN);
                }
            }
            //Log.d(LOG_TAG,"requestCode = " + data.getIntExtra("choice",0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void normaCallories(){
        if (sex.equals("Мужской")){
            if (years.equals("19 - 30")){
                water_norma.setText("Суточная норма воды : 2,5 л");
                if (obraz_zhizni.equals("Пассивный")){
                    NORMA_CALLORIY = 2400;
                }else if (obraz_zhizni.equals("Умеренный")){
                    NORMA_CALLORIY = 2700;
                }else if (obraz_zhizni.equals("Активный")){
                    NORMA_CALLORIY = 3000;
                }
            }else if (years.equals("31 - 50")){
                water_norma.setText("Суточная норма воды : 2,2 л");
                if (obraz_zhizni.equals("Пассивный")){
                    NORMA_CALLORIY = 2200;
                }else if (obraz_zhizni.equals("Умеренный")){
                    NORMA_CALLORIY = 2400;
                }else if (obraz_zhizni.equals("Активный")){
                    NORMA_CALLORIY = 2600;
                }
            }else if (years.equals("51 - 70")){
                water_norma.setText("Суточная норма воды : 2,0 л");
                if (obraz_zhizni.equals("Пассивный")){
                    NORMA_CALLORIY = 2000;
                }else if (obraz_zhizni.equals("Умеренный")){
                    NORMA_CALLORIY = 2200;
                }else if (obraz_zhizni.equals("Активный")){
                    NORMA_CALLORIY = 2400;
                }
            }
        }if (sex.equals("Женский")){
            water_norma.setText("Суточная норма воды : 2,2 л");
            if (years.equals("19 - 30")){
                if (obraz_zhizni.equals("Пассивный")){
                    NORMA_CALLORIY = 2000;
                }else if (obraz_zhizni.equals("Умеренный")){
                    NORMA_CALLORIY = 2200;
                }else if (obraz_zhizni.equals("Активный")){
                    NORMA_CALLORIY = 2400;
                }
            }else if (years.equals("31 - 50")){
                water_norma.setText("Суточная норма воды : 2,0 л");
                if (obraz_zhizni.equals("Пассивный")){
                    NORMA_CALLORIY = 1800;
                }else if (obraz_zhizni.equals("Умеренный")){
                    NORMA_CALLORIY = 2200;
                }else if (obraz_zhizni.equals("Активный")){
                    NORMA_CALLORIY = 2400;
                }
            }else if (years.equals("51 - 70")){
                water_norma.setText("Суточная норма воды : 1,85 л");
                if (obraz_zhizni.equals("Пассивный")){
                    NORMA_CALLORIY = 1600;
                }else if (obraz_zhizni.equals("Умеренный")){
                    NORMA_CALLORIY = 1800;
                }else if (obraz_zhizni.equals("Активный")){
                    NORMA_CALLORIY = 2000;
                }
            }
        }
        if (want.equals("Похудеть")){
            NORMA_CALLORIY = NORMA_CALLORIY - 200;
        }else if (want.equals("Поддерживать массу")){
            NORMA_CALLORIY = NORMA_CALLORIY + 10;
        }else if (want.equals("Набираем массу")){
            NORMA_CALLORIY = NORMA_CALLORIY + 200;
        }
        norma_calloriy.setText(NORMA_CALLORIY+" ккал");
        norma();
    }

    void norma(){
        int normZavtrak = NORMA_CALLORIY*45/100;
        text_view_numCalloriesZavtrak.setText(normZavtrak+"");

        int normObed = NORMA_CALLORIY*35/100;
        text_view_numCalloriesObed.setText(normObed+"");

        int normUzin = NORMA_CALLORIY*20/100;
        text_view_numCalloriesUzin.setText(normUzin+"");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // график питания
            Intent intent = new Intent(this,Grafic_pitania.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            //Холодильник
            Intent intent = new Intent(this, Holodilnik.class);
            startActivity(intent);
            Toast.makeText(this,"nav_gallery",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_slideshow) {
            // что поесть
            Intent intent = new Intent(this,Chto_Poest.class);
            startActivity(intent);
            Toast.makeText(this,"nav_slideshow",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            //поделится
            Toast.makeText(this,"nav_share",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_send) {
            //о нас
            Toast.makeText(this,"nav_send",Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void loadingDataBaseProduct(){
        Resources res = getResources();
        InputStream in_s = null;
        in_s = res.openRawResource(R.raw.convertcsv);
        try {
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            dataBase = new String(b);
            JSONArray array = new JSONArray(dataBase);
            String key = "";
            int value = 0;
            mapWithProducts = new HashMap<>();
            for (int i = 0; i<array.length();i++){
                key = array.getJSONObject(i).getString("A");
                value = array.getJSONObject(i).getInt("B");
                mapWithProducts.put(key,value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int transformStringtoInt(String text){
        int result = Integer.valueOf(text);
        return result;
    }
}