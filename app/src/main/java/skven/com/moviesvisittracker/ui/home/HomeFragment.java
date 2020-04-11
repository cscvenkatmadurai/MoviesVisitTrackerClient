package skven.com.moviesvisittracker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.date.DateUtil;

public class HomeFragment extends Fragment implements CustomDateRangeSelectorAlertDialog.CustomDateRangeSelectorDTO {


    private static final String TAG = "###HomeFragment";
    private MaterialButtonToggleGroup toggleDuration;
    private MaterialButtonToggleGroup toggleBy;
    long startTime, endTime;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: called" );

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        toggleDuration = root.findViewById(R.id.toggleDuration);
        toggleDuration.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if(checkedId == R.id.this_month ) {
                Log.d(TAG, "onCreateView: this_month selected");
                startTime = DateUtil.getMonthStartInMilliSeconds();
                endTime = System.currentTimeMillis();

                    MovieVisitByDate.byMonth(getActivity().getSupportFragmentManager());




            }
            if(checkedId == R.id.this_year) {
                MovieVisitByDate.byYear(getActivity().getSupportFragmentManager());
                startTime = DateUtil.getYearStartInMilliSeconds();
                endTime = System.currentTimeMillis();
                Log.d(TAG, "onCreateView: this_year selected");

            }

            if(checkedId == R.id.custom_range) {

                Log.d(TAG, "onCreateView: custom_range");
                openCustomDateRangeSelectAlertDialog();

            }

        });

        toggleDuration = root.findViewById(R.id.toggleBy);

        toggleDuration.addOnButtonCheckedListener(((group, checkedId, isChecked) -> {
            if(R.id.by_date == checkedId) {
                Toast.makeText(getContext(), "By Date", Toast.LENGTH_SHORT ).show();
            }

            if(R.id.by_count == checkedId) {
                Toast.makeText(getContext(), "By count", Toast.LENGTH_SHORT ).show();
            }

            if(R.id.by_lang == checkedId) {
                Toast.makeText(getContext(), "By lang", Toast.LENGTH_SHORT ).show();


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                MovieVisitByIdFragment fragment = new MovieVisitByIdFragment();

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
                bundle.putString("by", "lang");


                fragment.setArguments(bundle);

                fragmentTransaction.commit();






            }

            if(R.id.by_theatre == checkedId) {
                Toast.makeText(getContext(), "By theatre", Toast.LENGTH_SHORT ).show();
            }



        }));

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
        toggleDuration.check(R.id.custom_range);
        startTime = startDateInMilliSeconds;
        endTime = endDateInMilliSeconds;
        MovieVisitByDate.customRange(getActivity().getSupportFragmentManager(), startDateInMilliSeconds, endDateInMilliSeconds);

    }

}