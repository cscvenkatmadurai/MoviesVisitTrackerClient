package skven.com.moviesvisittracker.ui.addMovieVisit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;


import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Qualifier;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import skven.com.moviesvisittracker.Application;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.constants.LoginConstants;
import skven.com.moviesvisittracker.getTheatre.GetTheatreArrayAdapter;
import skven.com.moviesvisittracker.getTheatre.TheatreDTO;
import skven.com.moviesvisittracker.helper.SharedPreferenceHelper;
import skven.com.moviesvisittracker.imdb.autocomplete.IMDBAutoCompleteAdapter;
import skven.com.moviesvisittracker.imdb.autocomplete.ImdbAutoCompleteFetcher;
import skven.com.moviesvisittracker.imdb.autocomplete.dao.IMDBSuggestions;

public class AddMovieVisitFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    private Button date, addMovieVisitButton;
    private long showTimeInMilliSeconds;
    private EditText imdbId, theatreId, langWatched, rating;
    private AutoCompleteTextView movieName, theatreName;
    private static final String TAG = "AddMovieVisitFragment";

    @Inject
    @Named("loginSharedPreference")
    SharedPreferences loginSharedPref;


    @Inject
    IMDBAutoCompleteAdapter imdbAutoCompleteAdapter;

    @Inject
    GetTheatreArrayAdapter getTheatreArrayAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AppIdleHistory app;

        Log.i(TAG, "onCreateView: ");
        Application.getAppComponent().inject(this);

        View root = inflater.inflate(R.layout.fragment_add_movie_visit, container, false);



        date = root.findViewById(R.id.button_add_date);
        imdbId = root.findViewById(R.id.edit_text_add_movie_visit_imdbId);
        theatreId = root.findViewById(R.id.edit_text_theatreId);
        langWatched = root.findViewById(R.id.edit_text_lang_watched);
        rating = root.findViewById(R.id.edit_text_rating);
        addMovieVisitButton = root.findViewById(R.id.button_add_movie_visit);
        movieName = root.findViewById(R.id.autocomplete_movie_name);
        movieName.setAdapter(imdbAutoCompleteAdapter);

        movieName.setOnItemClickListener((adapterView, view, i, l) -> {
            IMDBSuggestions movieSuggestion = (IMDBSuggestions)adapterView.getAdapter().getItem(i);
            imdbId.setText(movieSuggestion.getImdbId());


        });

        theatreName = root.findViewById(R.id.autocomplete_theatre_name);
        theatreName.setAdapter(getTheatreArrayAdapter);
        theatreName.setOnItemClickListener((adapterView, view, i, l) -> {
            TheatreDTO theatre = (TheatreDTO) adapterView.getAdapter().getItem(i);
            if(theatre != null) {
                theatreId.setText(Long.toString(theatre.getTheatreId()));
            }
        });


        date.setOnClickListener(view -> {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    AddMovieVisitFragment.this,
                    now.get(Calendar.YEAR), // Initial year selection
                    now.get(Calendar.MONTH), // Initial month selection
                    now.get(Calendar.DAY_OF_MONTH) // Inital day selection
            );

            // If you're calling this from a support Fragment
            assert getFragmentManager() != null;
            dpd.show(getFragmentManager(), "Datepickerdialog");

        });

        addMovieVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(imdbId.getText())) {
                    Toast.makeText(AddMovieVisitFragment.this.getContext(), "imdbId is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(theatreId.getText())) {
                    Toast.makeText(AddMovieVisitFragment.this.getContext(), "theatreId is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if (TextUtils.isEmpty(langWatched.getText())) {
                    Toast.makeText(AddMovieVisitFragment.this.getContext(), "theatreId is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if (showTimeInMilliSeconds == 0) {
                    Toast.makeText(AddMovieVisitFragment.this.getContext(), "Date is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if(ConnectionManager.isNetwork(getContext()) ) {

                    final JSONObject params = new JSONObject();
                    try {

                        params.put("userName", loginSharedPref.getString(LoginConstants.USER_ID, LoginConstants.USER_ID));
                        params.put("showTime", showTimeInMilliSeconds);
                        params.put("imdbId", imdbId.getText());
                        params.put("theatreId", theatreId.getText());
                        params.put("rating", rating.getText());
                        params.put("langWatched", langWatched.getText());

                    }catch (Exception e) {
                        Log.e(getTag(), "Error in parsing json");
                    }

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");


                    ConnectionManager.volleyJSONRequest(getContext(), true, null, "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit", Request.Method.POST, params, headers, "add-movie-visit", new GloxeyCallback.JSONResponse() {
                        @Override
                        public void onResponse(JSONObject _response, String _tag) {
                            Toast.makeText(getContext(), "movie visit Added successfully",  Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Response is " + _response);

                        }

                        @Override
                        public void isConnected(boolean _connected, String _tag) {

                        }

                        @Override
                        public void onErrorResponse(VolleyError _error, boolean _onErrorResponse, String _tag) {
                            Toast.makeText(getContext(), "Error in adding  movieVisit, please try again later",  Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Exception during calling addTheatre for params" + params , _error);

                        }
                    }
                    );

                }




            }
        });





        return root;
    }



    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, dayOfMonth);
        showTimeInMilliSeconds = c.getTimeInMillis();
        Toast.makeText(getContext(), showTimeInMilliSeconds +"", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }


}