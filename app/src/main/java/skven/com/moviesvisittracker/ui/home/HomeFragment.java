package skven.com.moviesvisittracker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.date.DateUtil;

public class HomeFragment extends Fragment implements CustomDateRangeSelectorAlertDialog.CustomDateRangeSelectorDTO {


    private static final String TAG = "###HomeFragment";
    private MaterialButtonToggleGroup toggleGroup;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: called" );

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        toggleGroup  = root.findViewById(R.id.toggleGroup);
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if(checkedId == R.id.this_month ) {
                Log.d(TAG, "onCreateView: this_month selected");

                    createOrReplaceMovieVisitByDateFragment( DateUtil.getMonthStartInMilliSeconds(), System.currentTimeMillis());



            }
            if(checkedId == R.id.this_year) {

                Log.d(TAG, "onCreateView: this_year selected");
                    createOrReplaceMovieVisitByDateFragment(DateUtil.getYearStartInMilliSeconds(), System.currentTimeMillis());


            }

            if(checkedId == R.id.custom_range) {

                Log.d(TAG, "onCreateView: custom_range");
                openCustomDateRangeSelectAlertDialog();

            }

        });


        createOrReplaceMovieVisitByDateFragment(DateUtil.getMonthStartInMilliSeconds(), System.currentTimeMillis());




        return root;
    }

    private void createOrReplaceMovieVisitByDateFragment(final long startTime, long endTime) {
        Log.i(TAG, "createOrReplaceMovieVisitByDateFragment: startTime: " + startTime + "  endTime " + endTime );
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
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

    private void openCustomDateRangeSelectAlertDialog() {
        Log.i("### HomeFragment", "on openCustomDateRangeSelectAlertDialog");
        CustomDateRangeSelectorAlertDialog alertDialog = new CustomDateRangeSelectorAlertDialog();
        alertDialog.setTargetFragment(this, 0);
        alertDialog.show(getFragmentManager(), "CustomerDateRangeSelectorAlertDialog");
    }



    @Override
    public void receive(long startDateInMilliSeconds, long endDateInMilliSeconds) {
        Log.i(TAG, "### receive method");
        toggleGroup.check(R.id.custom_range);
        createOrReplaceMovieVisitByDateFragment(startDateInMilliSeconds, endDateInMilliSeconds);

    }

}