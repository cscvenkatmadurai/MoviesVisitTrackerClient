package skven.com.moviesvisittracker.date;

import java.time.LocalDate;
import java.util.Calendar;

public class DateUtil {


    public static long getMonthStartInMilliSeconds() {
        LocalDate now = LocalDate.now();
        Calendar instance = Calendar.getInstance();
        instance.set(now.getYear(), now.getMonthValue()-1, 1, 0, 0,0);
        return instance.getTimeInMillis();

    }

    public static long getYearStartInMilliSeconds() {
        LocalDate now = LocalDate.now();
        Calendar instance = Calendar.getInstance();
        instance.set(now.getYear(), 0, 1,0,0 ,0);
        return instance.getTimeInMillis();

    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getMonthStartInMilliSeconds());
        System.out.println(DateUtil.getYearStartInMilliSeconds());

    }
}
