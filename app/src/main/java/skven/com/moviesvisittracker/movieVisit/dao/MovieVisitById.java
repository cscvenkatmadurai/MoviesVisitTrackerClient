package skven.com.moviesvisittracker.movieVisit.dao;

import java.util.List;

import lombok.Data;

@Data
public class MovieVisitById {
    private int numberOfMovieOfMoviesWatched;
    private int numberOfDistinctMoviesWatched;
    private String id;
    private List<MovieVisitMini> movieList;

}
