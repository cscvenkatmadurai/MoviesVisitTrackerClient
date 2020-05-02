package skven.com.moviesvisittracker.getTheatre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import skven.com.moviesvisittracker.R;

@Singleton
public class GetTheatreArrayAdapter extends ArrayAdapter<TheatreDTO> {
    final TheatreFetcher theatreFetcher;
    List<TheatreDTO> theatreList;

    @Inject
    public GetTheatreArrayAdapter(@NonNull Context context, final TheatreFetcher theatreFetcher) {
        super(context, 0);
        this.theatreFetcher = theatreFetcher;
        theatreList = Collections.emptyList();
    }

    @Override
    public int getCount() {
        return theatreList.size();
    }

    @Nullable
    @Override
    public TheatreDTO getItem(int position) {
        return theatreList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.theatre_suggestion_row, parent, false);
        }

        final TheatreDTO item = getItem(position);
        TextView theatreNameTextView = convertView.findViewById(R.id.theatre_suggestion_theatre_name);
        if(item != null) {
            theatreNameTextView.setText(item.getTheatreName() + " " + item.getTheatreLocation());
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return theatreNameFilter;
    }

    final Filter theatreNameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if(StringUtils.isEmpty(charSequence)) {
                return null;
            }
            List<TheatreDTO> theatres = theatreFetcher.getTheatre();
            List<TheatreDTO> filteredTheatres = theatres.stream().
                    filter(Objects::nonNull).
                    filter(theatre -> (StringUtils.isNotEmpty(theatre.getTheatreName()) && StringUtils.isNotEmpty(theatre.getTheatreLocation()))).
                    filter(theatre -> (theatre.getTheatreName().toLowerCase() + " " + theatre.getTheatreLocation().toLowerCase()).contains(charSequence.toString().toLowerCase())).
                    collect(Collectors.toList());

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredTheatres;
            filterResults.count = filteredTheatres.size();
            theatreList = filteredTheatres;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if(filterResults == null || filterResults.count == 0){
                notifyDataSetInvalidated();
            } else {
                notifyDataSetChanged();
            }

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            final TheatreDTO theatre = (TheatreDTO) resultValue;
            return theatre.getTheatreName() + " " + theatre.getTheatreLocation();
        }
    };
}
