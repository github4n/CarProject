package com.littleant.carrepair.activies.datetime;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.littleant.carrepair.request.utils.DataHelper;

import java.util.Calendar;

public class DateActivity extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private final static String city="广州市";

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
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        DatePicker datePicker = datePickerDialog.getDatePicker();
        if(DataHelper.getContractCity(getActivity()).equals(city)){
            datePicker.setMinDate(System.currentTimeMillis() + 259200000);
        }else{
            datePicker.setMinDate(System.currentTimeMillis() + 86400000);

        }

        return datePickerDialog;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        if(this.selectDateCallback != null) {
            selectDateCallback.onSelectDate(i, i1, i2);
        }
    }


}
