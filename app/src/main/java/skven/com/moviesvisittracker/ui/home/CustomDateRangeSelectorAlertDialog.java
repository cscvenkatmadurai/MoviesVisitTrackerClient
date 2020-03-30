package skven.com.moviesvisittracker.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.date.DatePicker;

public class CustomDateRangeSelectorAlertDialog extends AppCompatDialogFragment {

    private long startTime, endTime;
    private Button startTimeButton, endTimeButton;
    DatePicker startTimeDatePicker,endTimeDatePicker;
    CustomDateRangeSelectorDTO customDateRangeSelectorDTO;
    private static final String TAG = "CustomDateRangeSelector";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        startTimeDatePicker = new DatePicker();
        endTimeDatePicker = new DatePicker();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_range_alert_dialog, null);
        startTimeButton = view.findViewById(R.id.custom_range_dialog_start_date_button);
        endTimeButton = view.findViewById(R.id.custom_range_dialog_end_date_button);


        startTimeButton.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    startTimeDatePicker,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );

            // If you're calling this from a support Fragment
            assert getFragmentManager() != null;
            dpd.show(getFragmentManager(), "StartDatepickerdialog");

        });

        endTimeButton.setOnClickListener(v -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    endTimeDatePicker,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );

            // If you're calling this from a support Fragment
            assert getFragmentManager() != null;
            dpd.show(getFragmentManager(), "EndDatepickerdialog");


        });

        Log.i("### CustomDateRangeSelectorAlertDialog", "in onCreateDialog");
        builder.
                setView(view).
                setTitle("Select Range").
                setNegativeButton("cancel", (a, b) -> {} ).
                setPositiveButton("submit", ((dialogInterface, i) -> {
                    Toast.makeText(getContext(), startTimeDatePicker.getDate() + " " + endTimeDatePicker.getDate(), Toast.LENGTH_SHORT).show();
                    customDateRangeSelectorDTO = (CustomDateRangeSelectorDTO) getTargetFragment();
                    customDateRangeSelectorDTO.receive(startTimeDatePicker.getDateInMilliSecond(), endTimeDatePicker.getDateInMilliSecond());

                }));



        return builder.create();
    }


    public interface CustomDateRangeSelectorDTO {
        public void receive(long startDateInMilliSeconds, long endDateInMilliSeconds);
    }

    @Override
    public void setTargetFragment(@Nullable Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }
}



