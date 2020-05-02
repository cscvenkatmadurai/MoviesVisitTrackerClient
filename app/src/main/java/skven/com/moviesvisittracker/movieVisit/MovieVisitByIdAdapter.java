package skven.com.moviesvisittracker.movieVisit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitById;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitMini;


public class MovieVisitByIdAdapter extends RecyclerView.Adapter<MovieVisitByIdAdapter.MyViewHolder> {

    private List<MovieVisitById> movieVisitByIds;
    private final String idName;

    public MovieVisitByIdAdapter(final String idName) {
        movieVisitByIds = new ArrayList<>();
        this.idName = idName;
    }

    public void setMovieVisitByIds(final List<MovieVisitById> movieVisitByIds) {
        this.movieVisitByIds = movieVisitByIds;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_by_id, parent, false);

        return new MovieVisitByIdAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id_name.setText(idName + ": " + movieVisitByIds.get(position).getId());
        holder.numberOfMoviesWatched.setText("Number of movies watched: " + movieVisitByIds.get(position).getNumberOfMovieOfMoviesWatched());
        holder.numberOfDistinctMoviesWatched.setText("Number of distinct movies watched: " + movieVisitByIds.get(position).getNumberOfDistinctMoviesWatched());
        MovieVisitMiniAdapter movieVisitMiniAdapter = (MovieVisitMiniAdapter) holder.recyclerView.getAdapter();
        MovieVisitMini[] moviesArray = movieVisitByIds.get(position).getMovieList().toArray(new MovieVisitMini[0]);
        assert movieVisitMiniAdapter != null;
        movieVisitMiniAdapter.setMoviesArray(moviesArray);
        movieVisitMiniAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return movieVisitByIds.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id_name,numberOfMoviesWatched, numberOfDistinctMoviesWatched;
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_name = itemView.findViewById(R.id.movie_by_id_id_name);
            numberOfMoviesWatched = itemView.findViewById(R.id.movie_by_id_number_of_movies_watched);
            numberOfDistinctMoviesWatched = itemView.findViewById(R.id.movie_by_id_number_of_distinct_movies_watched);
            recyclerView = itemView.findViewById(R.id.movie_visits_by_id_recycler_view);

            MovieVisitMiniAdapter mAdapter = new MovieVisitMiniAdapter();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(itemView.getContext(), RecyclerView.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
    }
}
