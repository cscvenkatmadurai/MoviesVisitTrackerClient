package skven.com.moviesvisittracker.movieVisit.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieVisitMini {

    private String movieName;
    private String releaseDate;
    private String imageUrl;
    private long watchedDateInMillisecond;
    private Double rating;
    private String watchedLang;
    private String theatreName;
    private String theatreLocation;


}
