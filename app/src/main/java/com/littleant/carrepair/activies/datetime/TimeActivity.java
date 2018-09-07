package com.littleant.carrepair.activies.datetime;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.mh.core.task.command.abstracts.MHCommand;

import java.util.Calendar;

public class TimeActivity extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private SelectTimeCallback selectTimeCallback;

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(this.selectTimeCallback != null) {
            selectTimeCallback.onSelectTime(hourOfDay, minute);
        }
    }

    public interface SelectTimeCallback {
        void onSelectTime(int hourOfDay, int minute);
    }

    public void setCallback(SelectTimeCallback callback) {
        this.selectTimeCallback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of DatePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

}
