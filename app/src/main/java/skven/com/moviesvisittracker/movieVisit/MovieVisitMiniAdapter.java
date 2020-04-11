package skven.com.moviesvisittracker.movieVisit;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.recyclerview.widget.RecyclerView;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.movieVisit.dao.MovieVisitMini;

public class MovieVisitMiniAdapter extends RecyclerView.Adapter<MovieVisitMiniAdapter.MyViewHolder> {

    static DateFormat df = new SimpleDateFormat("dd MMM yyyy");


    private MovieVisitMini[] moviesArray;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, rating, relDate, watchedDate, watchedLang, watchedLocation;
        public ImageView movieImage;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            relDate = (TextView) view.findViewById(R.id.relDate);
            rating = (TextView) view.findViewById(R.id.rating);
            movieImage = (ImageView) view.findViewById(R.id.movieImage);
            watchedDate = view.findViewById(R.id.watchedDate);
            watchedLang = view.findViewById(R.id.watchedLang);
            watchedLocation = view.findViewById(R.id.watchedLocation);
        }
    }


    public MovieVisitMiniAdapter() {
        this.moviesArray  = new MovieVisitMini[0];
    }

    public void setMoviesArray(MovieVisitMini[] moviesArray) {
        this.moviesArray = moviesArray;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_visit_mini, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieVisitMini movie = moviesArray[position];
        holder.title.setText(movie.getMovieName());
        holder.relDate.setText("Release Date: " + movie.getReleaseDate());
        holder.rating.setText(Double.toString(movie.getRating()) );
        Picasso.get().load(movie.getImageUrl()).
                resize(300, 400)
                .into(holder.movieImage);
        holder.watchedLocation.setText(movie.getTheatreName() + "," + movie.getTheatreLocation());
        holder.watchedLang.setText(movie.getWatchedLang());
        Date watchedDate = new Date(movie.getWatchedDateInMillisecond());
        holder.watchedDate.setText(df.format(watchedDate));

    }


    @Override
    public int getItemCount() {
        return moviesArray.length;
    }
}