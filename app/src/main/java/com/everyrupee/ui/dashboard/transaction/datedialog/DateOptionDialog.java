package com.everyrupee.ui.dashboard.transaction.datedialog;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.everyrupee.R;
import com.everyrupee.ViewModelProviderFactory;
import com.everyrupee.data.model.db.Recurrence;
import com.everyrupee.data.model.db.Reminder;
import com.everyrupee.databinding.DialogDateOptionBinding;
import com.everyrupee.ui.base.BaseDialog;
import com.everyrupee.utils.CalendarUtils;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

/**
 * Created by aman on 13-10-2018.
 */

public class DateOptionDialog extends BaseDialog implements View.OnClickListener {

    private static final String TAG = "DateOptionDialog";

    @Inject
    ViewModelProviderFactory factory;

    private DialogDateOptionBinding optionBinding;
    private DateOptionViewModel viewModel;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ReminderDialog reminderDialog;
    private RecurrenceDialog recurrenceDialog;

    private Calendar calendar;
    private Date selectedDate;
    private Reminder selectedReminder;
    private Recurrence selectedRecurrence;
    private boolean isDateChange = false;
    private DateOptionListener dateOptionListener;

    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            calendar = Calendar.getInstance();
            calendar.set(i, i1, i2);
            optionBinding.tvDateStatus.setText(CalendarUtils.getDateToStringDate(calendar.getTime()));
            datePickerDialog.cancel();

            timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), false);
            isDateChange = true;
            timePickerDialog.show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            calendar.set(Calendar.HOUR_OF_DAY, i);
            calendar.set(Calendar.MINUTE, i1);
            calendar.set(Calendar.SECOND, 0);
            optionBinding.tvDateStatus.setText(CalendarUtils.getDateToStringDate(calendar.getTime()));
            isDateChange = true;
            timePickerDialog.cancel();
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        optionBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_date_option, container, false);
        View view = optionBinding.getRoot();

        AndroidSupportInjection.inject(this);
        viewModel = ViewModelProviders.of(this, factory).get(DateOptionViewModel.class);
        optionBinding.setViewModel(viewModel);

        calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);

        datePickerDialog = new DatePickerDialog(getContext(), listener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), false);

        reminderDialog = new ReminderDialog(getContext());
        recurrenceDialog = new RecurrenceDialog(getContext());

        optionBinding.tvDateStatus.setText(CalendarUtils.getDateToStringDate(calendar.getTime()));
        optionBinding.tvReminderStatus.setText(selectedReminder.getReminderTitle());
        optionBinding.tvRecurrenceStatus.setText(selectedRecurrence.getRecurrenceTitle());

        setUpObserverRecurrence();
        setUpObserverReminder();

        setUpDateDialogButtons();

        optionBinding.layoutReminder.setOnClickListener(this);
        optionBinding.layoutRecurrence.setOnClickListener(this);
        optionBinding.layoutDate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_reminder:
                reminderDialog.show();
                break;
            case R.id.layout_recurrence:
                recurrenceDialog.show();
                break;
            case R.id.layout_date:
                calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getContext(), listener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        dateOptionListener.onDateOptionDismiss(isDateChange, calendar.getTime(), selectedReminder, selectedRecurrence);
    }

    public void setDateOptionListener(DateOptionListener listener) {
        dateOptionListener = listener;
    }

    public void setData(Date date, Recurrence recurrence, Reminder reminder) {
        selectedDate = date;
        selectedReminder = reminder;
        selectedRecurrence = recurrence;
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    private void setUpObserverReminder() {
        viewModel.getReminderMutableLiveData().observe(this, new Observer<Reminder>() {
            @Override
            public void onChanged(@Nullable Reminder reminder) {
                selectedReminder = reminder;
                optionBinding.tvReminderStatus.setText(reminder.getReminderTitle());
            }
        });
    }

    private void setUpObserverRecurrence() {
        viewModel.getRecurrenceMutableLiveData().observe(this, new Observer<Recurrence>() {
            @Override
            public void onChanged(@Nullable Recurrence recurrence) {
                selectedRecurrence = recurrence;
                optionBinding.tvRecurrenceStatus.setText(recurrence.getRecurrenceTitle());
            }
        });
    }

    private void setUpDateDialogButtons() {
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.cancel();
                            isDateChange = false;
                        }
                    }
                });

        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            isDateChange = true;
                        }
                    }
                });

        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            dialog.cancel();
                            isDateChange = false;
                        }
                    }
                });

        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (which == DialogInterface.BUTTON_POSITIVE) {
                            isDateChange = true;
                        }
                    }
                });
    }

    private class ReminderDialog extends AlertDialog {
        private RadioGroup rdGroup;

        protected ReminderDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_reminder);

            rdGroup = (RadioGroup) findViewById(R.id.rdGroupReminder);
            setTitle("Reminder");

            selectRadioButton(rdGroup, selectedReminder.getReminderTitle());
            rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    View radioButton = radioGroup.findViewById(i);
                    int idx = radioGroup.indexOfChild(radioButton);
                    viewModel.fetchReminders(idx);
                    cancel();
                }
            });
        }
    }

    private class RecurrenceDialog extends AlertDialog {
        private RadioGroup radioGroup;

        protected RecurrenceDialog(@NonNull Context context) {
            super(context);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_recurrence);
            radioGroup = (RadioGroup) findViewById(R.id.rdGroupRecurrence);
            selectRadioButton(radioGroup, selectedRecurrence.getRecurrenceTitle());
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    View radioButton = radioGroup.findViewById(i);
                    int idx = radioGroup.indexOfChild(radioButton);
                    viewModel.fetchRecurrence(idx);
                    cancel();
                }
            });
        }
    }

    private void selectRadioButton(RadioGroup radioGroup, String title) {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if (radioButton.getText().equals(title)) {
                radioGroup.check(radioButton.getId());

            }
        }
    }
}
