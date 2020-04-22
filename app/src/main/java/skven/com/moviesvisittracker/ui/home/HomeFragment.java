package skven.com.moviesvisittracker.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.date.DateUtil;
import skven.com.moviesvisittracker.imdb.autocomplete.ImdbAutoCompleteFetcher;

public class HomeFragment extends Fragment implements CustomDateRangeSelectorAlertDialog.CustomDateRangeSelectorDTO {


    private static final String TAG = "###HomeFragment";
    private MaterialButtonToggleGroup toggleDuration;
    private MaterialButtonToggleGroup toggleBy;
    private long startTime, endTime;
    private static final String BY_COUNT = "count";
    private static final String BY_DATE = "date";
    private static final String BY_THEATRE = "theatre";
    private static final String BY_LANG = "lang";

    private static Map<Integer, String> idToByString;
    private static Map<Integer, Fragment> idStringToFragment;

    static {

        idToByString = new HashMap<>();
        idToByString.put(R.id.by_count, BY_COUNT);
        idToByString.put(R.id.by_date, BY_DATE);
        idToByString.put(R.id.by_lang, BY_LANG);
        idToByString.put(R.id.by_theatre, BY_THEATRE);

        idStringToFragment = new HashMap<>();
        MovieVisitByIdFragment movieVisitByIdFragment = new MovieVisitByIdFragment();
        idStringToFragment.put(R.id.by_count, movieVisitByIdFragment);
        idStringToFragment.put(R.id.by_theatre, movieVisitByIdFragment);
        idStringToFragment.put(R.id.by_lang, movieVisitByIdFragment);
        idStringToFragment.put(R.id.by_date, new MovieVisitByDateFragment());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: called" );

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        toggleDuration = root.findViewById(R.id.toggleDuration);
        toggleBy = root.findViewById(R.id.toggleBy);

        toggleBy.check(R.id.by_date);
        toggleDuration.check(R.id.this_year);

        ImdbAutoCompleteFetcher.fetch("big", getContext());

        toggleDuration.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if(checkedId == R.id.this_month ) {
                Log.d(TAG, "onCreateView: this_month selected");
                startTime = DateUtil.getMonthStartInMilliSeconds();
                endTime = System.currentTimeMillis();

                createOrReplaceMovieVisitFragment(idToByString.get(toggleBy.getCheckedButtonId()), idStringToFragment.get(toggleBy.getCheckedButtonId()));


            }
            if(checkedId == R.id.this_year) {

                startTime = DateUtil.getYearStartInMilliSeconds();
                endTime = System.currentTimeMillis();
                Log.d(TAG, "onCreateView: this_year selected");
                createOrReplaceMovieVisitFragment(idToByString.get(toggleBy.getCheckedButtonId()), idStringToFragment.get(toggleBy.getCheckedButtonId()));

            }

            if(checkedId == R.id.custom_range) {

                Log.d(TAG, "onCreateView: custom_range");
                openCustomDateRangeSelectAlertDialog();

            }

        });

        toggleBy = root.findViewById(R.id.toggleBy);

        toggleBy.addOnButtonCheckedListener(((group, checkedId, isChecked) -> {
            if(R.id.by_date == checkedId) {
                createOrReplaceMovieVisitFragment("date", new MovieVisitByDateFragment());
            }


            if(R.id.by_lang == checkedId) {

                createOrReplaceMovieVisitFragment("lang", new MovieVisitByIdFragment());


            }

            if(R.id.by_theatre == checkedId) {
                createOrReplaceMovieVisitFragment("theatre", new MovieVisitByIdFragment());

            }

            if(R.id.by_count == checkedId) {
                createOrReplaceMovieVisitFragment("count", new MovieVisitByIdFragment());
            }



        }));

        createOrReplaceMovieVisitFragment("date", new MovieVisitByDateFragment());




        return root;
    }

    private void createOrReplaceMovieVisitFragment(final String by, final Fragment fragment) {
        Log.i(TAG, "createOrReplaceMovieVisitFragment: startTime: " + startTime + "  endTime " + endTime  + "  by: " + by);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(fragmentManager.findFragmentById(R.id.home_recycler_view) == null ) {
            Log.i(TAG, "createOrReplaceMovieVisitFragment: adding recyclerview ");
            fragmentTransaction.add(R.id.home_recycler_view, fragment);

        } else {
            Log.i(TAG, "createOrReplaceMovieVisitFragment: replacing recycler view");
            fragmentTransaction.replace(R.id.home_recycler_view, fragment);
        }
        fragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putLong("startTime", startTime);
        bundle.putLong("endTime", endTime);
        bundle.putString("by", by);

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
        createOrReplaceMovieVisitFragment(idToByString.get(toggleBy.getCheckedButtonId()), idStringToFragment.get(toggleBy.getCheckedButtonId()));

    }

}