package skven.com.moviesvisittracker.ui.addMovie;

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

import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import io.gloxey.gnm.interfaces.GloxeyCallback;
import io.gloxey.gnm.managers.ConnectionManager;
import skven.com.moviesvisittracker.R;

public class AddMovieFragment extends Fragment {

    private static final String TAG = "AddMovieFragment";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_movie, container, false);
        Button button = (Button) root.findViewById(R.id.button_add_movie);
        final EditText imdbId = (EditText) root.findViewById(R.id.edit_text_imdbId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(imdbId.getText())) {
                    Toast.makeText(getContext(), "ImdbId is empty", Toast.LENGTH_LONG).show();
                    return;
                }

                if (ConnectionManager.isNetwork(getContext())) {
                    final JSONObject params = new JSONObject();
                    try {
                        params.put("imdbId", imdbId.getText().toString());

                    }catch (Exception e) {
                        Log.e(getTag(), "Error in parsing json");
                    }

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");

                    ConnectionManager.volleyJSONRequest(getContext(), true, null, "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movie", Request.Method.POST, params, headers, "add-movie", new GloxeyCallback.JSONResponse() {
                        @Override
                        public void onResponse(JSONObject _response, String _tag) {
                            Toast.makeText(getContext(), "Added Movie successfully: " + _response ,  Toast.LENGTH_LONG).show();
                            Log.i(TAG, "Response is " + _response);

                        }

                        @Override
                        public void isConnected(boolean _connected, String _tag) {

                        }

                        @Override
                        public void onErrorResponse(VolleyError _error, boolean _onErrorResponse, String _tag) {
                            Toast.makeText(getContext(), "Error in adding  movie, please try again later",  Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Exception during calling addTheatre for params:  " + params , _error);

                        }

                    });


                } else {
                    Toast.makeText(getContext(), "Network not connected", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return root;
    }
}