package skven.com.moviesvisittracker.movieVisit.dao;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MovieVisitByIdResponse {
    private int numberOfMovieOfMoviesWatched;
    private int numberOfDistinctMoviesWatched;
    private List<MovieVisitById> movieVisitByIdList;

}

