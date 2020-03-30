package skven.com.moviesvisittracker.date;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class DatePicker implements DatePickerDialog.OnDateSetListener {

    private long dateInMilliSecond;
    private String date;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        dateInMilliSecond = c.getTimeInMillis();
        date = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;


    }

    public long getDateInMilliSecond() {
        return dateInMilliSecond;
    }

    public String getDate() {
        return date;
    }
}
