package com.bingerdranch.android.foodrecom;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class Grafic_pitania extends Activity {

    private Button button_time_zavtrak;
    private Button button_time_obed;
    private Button button_time_uzin;

    private int DIALOG_TIME_Z = 1;
    private int DIALOG_TIME_O = 2;
    private int DIALOG_TIME_U = 3;
    private int myHour = 14;
    private int myMinute = 35;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafic_pitania);

        button_time_zavtrak = (Button) findViewById(R.id.button_time_zavtrak);
        button_time_obed = (Button) findViewById(R.id.button_time_obed);
        button_time_uzin = (Button) findViewById(R.id.button_time_uzin);

        button_time_zavtrak.setText(Calendar.getInstance().getTime().getHours() + ":"
                + Calendar.getInstance().getTime().getMinutes());
        button_time_obed.setText(Calendar.getInstance().getTime().getHours() + ":"
                + Calendar.getInstance().getTime().getMinutes());
        button_time_uzin.setText(Calendar.getInstance().getTime().getHours() + ":"
                + Calendar.getInstance().getTime().getMinutes());
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME_Z) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack_1, myHour, myMinute, true);
            return tpd;
        }
        if (id == DIALOG_TIME_O) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack_2, myHour, myMinute, true);
            return tpd;
        }
        if (id == DIALOG_TIME_U) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack_3, myHour, myMinute, true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener myCallBack_1 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            button_time_zavtrak.setText("В " + myHour + " часов " + myMinute + " минут");
        }
    };
    TimePickerDialog.OnTimeSetListener myCallBack_2 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            button_time_obed.setText("В " + myHour + " часов " + myMinute + " минут");
        }
    };
    TimePickerDialog.OnTimeSetListener myCallBack_3 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            button_time_uzin.setText("В " + myHour + " часов " + myMinute + " минут");
        }
    };
    public void click_1(View view) {
        showDialog(DIALOG_TIME_Z);
    }

    public void click_2(View view) {
        showDialog(DIALOG_TIME_O);
    }

    public void click_3(View view) {
        showDialog(DIALOG_TIME_U);
    }
}
