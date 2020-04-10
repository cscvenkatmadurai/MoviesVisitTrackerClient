package skven.com.moviesvisittracker.ui.home;

import android.os.Bundle;
import android.util.Log;

import java.sql.Date;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.date.DateUtil;

public class MovieVisitByDate {

    private static final String TAG = "MovieVisitByDate";


    public static void byMonth(final FragmentManager fragmentManager) {
        customRange(fragmentManager, DateUtil.getMonthStartInMilliSeconds(), System.currentTimeMillis());
    }

    public static void byYear(final FragmentManager fragmentManager) {
        customRange(fragmentManager, DateUtil.getYearStartInMilliSeconds(), System.currentTimeMillis());
    }


    public static void customRange(final FragmentManager fragmentManager, final long startTime, long endTime) {
        Log.i(TAG, "createOrReplaceMovieVisitByDateFragment: startTime: " + startTime + "  endTime " + endTime );
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MovieVisitByDateFragment fragment = new MovieVisitByDateFragment();
        if(fragmentManager.findFragmentById(R.id.home_recycler_view) == null ) {
            Log.i(TAG, "createOrReplaceMovieVisitByDateFragment: adding recyclerview ");
            fragmentTransaction.add(R.id.home_recycler_view, fragment);

        } else {
            Log.i(TAG, "createOrReplaceMovieVisitByDateFragment: replacing recycler view");
            fragmentTransaction.replace(R.id.home_recycler_view, fragment);
        }
        fragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putLong("startTime", startTime);
        bundle.putLong("endTime", endTime);

        fragment.setArguments(bundle);

        fragmentTransaction.commit();
    }
}
