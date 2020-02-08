package skven.com.moviesvisittracker.ui.addMovieVisit;

import android.app.DownloadManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;


import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;
import skven.com.moviesvisittracker.R;

public class AddMovieVisitFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    Button date, addMovieVisitButton;
    long showTimeInMilliSeconds;
    EditText imdbId, theatreId, langWatched, rating;

    private static final String TAG = "AddMovieVisitFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");

        View root = inflater.inflate(R.layout.fragment_add_movie_visit, container, false);

        Calendar now = Calendar.getInstance();

        date = root.findViewById(R.id.button_add_date);
        imdbId = root.findViewById(R.id.edit_text_add_movie_visit_imdbId);
        theatreId = root.findViewById(R.id.edit_text_theatreId);
        langWatched = root.findViewById(R.id.edit_text_lang_watched);
        rating = root.findViewById(R.id.edit_text_rating);
        addMovieVisitButton = root.findViewById(R.id.button_add_movie_visit);


        date.setOnClickListener(view -> {
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

                        params.put("userName", "skven");
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


                    ConnectionManager.volleyJSONRequest(getContext(), true, null, "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit", Request.Method.POST, params, headers, new VolleyResponse() {
                        @Override
                        public void onResponse(String _response) {
                            Toast.makeText(getContext(), "movie visit Added successfully",  Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Response is " + _response);
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Error in adding  movieVisit, please try again later",  Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Exception during calling addTheatre for params" + params , error);

                        }

                        @Override
                        public void isNetwork(boolean connected) {

                        }
                    });

                }




            }
        });





        return root;
    }



    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, monthOfYear, year);
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