package com.littleant.carrepair.dome;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import java.util.Calendar;

public class DateActivity extends DialogFragment implements DatePickerDialog.OnDateSetListener {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_date);
//        DatePickerDialog datePicker = new DatePickerDialog(DateActivity.this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//                // TODO Auto-generated method stub
//
//            }
//        }, 2014, 1, 7);
//        datePicker.show();
//    }

    private SelectDateCallback selectDateCallback;

    public interface SelectDateCallback {
        void onSelectDate(int year, int month, int day);
    }

    public void setCallback(SelectDateCallback callback) {
        this.selectDateCallback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if(this.selectDateCallback != null) {
            selectDateCallback.onSelectDate(i, i1, i2);
        }
    }
}
