package skven.com.moviesvisittracker.getTheatre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheatreDTO {

    int theatreId;
    String theatreName;
    String theatreLocation;

}
