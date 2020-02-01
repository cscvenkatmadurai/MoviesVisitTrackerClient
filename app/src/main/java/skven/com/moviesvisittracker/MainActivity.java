package skven.com.moviesvisittracker;

import android.os.Bundle;

import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.gloxey.gnm.interfaces.VolleyResponse;
import io.gloxey.gnm.managers.ConnectionManager;
import io.gloxey.gnm.parser.GloxeyJsonParser;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMini;
import skven.com.moviesvisittracker.movieVisit.MovieVisitMiniAdapter;


import android.view.Menu;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "###MainActivity";

    private AppBarConfiguration mAppBarConfiguration;


    private RecyclerView recyclerView;
    private MovieVisitMiniAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ConnectionManager.volleyStringRequest(this, false, null, "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/movievisit?userName=skven", new VolleyResponse() {
            @Override
            public void onResponse(String _response) {

                /**
                 * Handle Response
                 */
                Log.i(TAG, "response \n" + _response );


                try {
                    MovieVisitMini[] parse = GloxeyJsonParser.getInstance().parse(_response, MovieVisitMini[].class);
                    Log.i(TAG+"parse", parse[0].toString());
                    mAdapter.setMoviesArray(parse);
                    mAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

                /**
                 * handle Volley Error
                 */
                Log.e(TAG, "exception", error);
            }

            @Override
            public void isNetwork(boolean connected) {
                Log.i(TAG, "isNetworkConnected" + connected);
                /**
                 * True if internet is connected otherwise false
                 */
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MovieVisitMiniAdapter();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
