package skven.com.moviesvisittracker.ui.addTheatre;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;


import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.getTheatre.TheatreAdapter;
import skven.com.moviesvisittracker.getTheatre.TheatreDTO;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMini;

public class AddTheatreFragment extends Fragment {
    private static final String TAG = "AddTheatreFragment";

    private EditText theatreName;
    private EditText theatreLocation;

    private RecyclerView recyclerView;
    private TheatreAdapter theatreAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_add_theatre, container, false);
        theatreName = root.findViewById(R.id.theatreNameEditText);
        theatreLocation = root.findViewById(R.id.theatreCityEditText);
        Button addTheatreButton = root.findViewById(R.id.addTheatreButton);





        addTheatreButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(TextUtils.isEmpty(theatreLocation.getText())) {
                Toast.makeText(getContext(), "Theatre Location is empty", Toast.LENGTH_SHORT).show();
                return;
               }

               if(TextUtils.isEmpty(theatreName.getText())) {
                   Toast.makeText(getContext(), "Theatre Name is empty", Toast.LENGTH_SHORT).show();
                   return;
               }

               if(ConnectionManager.isNetwork(getContext()) ) {
                   final JSONObject params = new JSONObject();
                   try {

                       params.put("theatreName", theatreName.getText().toString());
                       params.put("theatreLocation", theatreLocation.getText().toString());
                   }catch (Exception e) {
                       Log.e(getTag(), "Error in parsing json");
                   }

                   HashMap<String, String> headers = new HashMap<String, String>();
                   headers.put("Content-Type", "application/json");



                   ConnectionManager.volleyJSONRequest(getContext(), true, null, " https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/theatre", Request.Method.POST, params , headers, new VolleyResponse() {
                       @Override
                       public void onResponse(String _response) {
                           Toast.makeText(getContext(), "Theatre Added successfully",  Toast.LENGTH_LONG).show();
                           Log.i(TAG, "Response is " + _response);


                       }

                       @Override
                       public void onErrorResponse(VolleyError error) {
                           Toast.makeText(getContext(), "Error in adding theatre, please try again later",  Toast.LENGTH_SHORT).show();
                           Log.e(TAG, "Exception during calling addTheatre for params" + params , error);



                       }

                       @Override
                       public void isNetwork(boolean connected) {

                           /**
                            * True if internet is connected otherwise false
                            */
                       }
                   });



               }else {
                   Toast.makeText(getContext(), "Network not connected", Toast.LENGTH_SHORT).show();
               }



           }
       });

        recyclerView = (RecyclerView) root.findViewById(R.id.theatreListRecyclerView);
        theatreAdapter = new TheatreAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(theatreAdapter);





        ConnectionManager.volleyStringRequest(getContext(), true, null, "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/theatre", new VolleyResponse() {

            @Override
            public void onResponse(String _response) {
                try {
                    Toast.makeText(getContext(), _response, Toast.LENGTH_LONG).show();
                    TheatreDTO[] theatreDTOS = GloxeyJsonParser.getInstance().parse(_response, TheatreDTO[].class);
                    theatreAdapter.setTheatresArray(theatreDTOS);
                    theatreAdapter.notifyDataSetChanged();
                }catch (Exception e) {
                    Log.e(TAG, "onResponse: ", e );
                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"exception in getting theatre list", error);

            }

            @Override
            public void isNetwork(boolean connected) {

            }
        });




        return root;
    }
}
