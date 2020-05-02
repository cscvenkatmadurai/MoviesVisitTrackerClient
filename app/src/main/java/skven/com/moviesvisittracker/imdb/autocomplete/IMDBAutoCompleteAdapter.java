package skven.com.moviesvisittracker.imdb.autocomplete;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.imdb.autocomplete.dao.IMDBSuggestions;


@Singleton
public class IMDBAutoCompleteAdapter extends ArrayAdapter<IMDBSuggestions> {
    private List<IMDBSuggestions> imdbSuggestionsList;
    ImdbAutoCompleteFetcher imdbAutoCompleteFetcher;

    @Inject
    public IMDBAutoCompleteAdapter(@NonNull Context context, final ImdbAutoCompleteFetcher imdbAutoCompleteFetcher) {
        super(context, 0);
        this.imdbAutoCompleteFetcher = imdbAutoCompleteFetcher;

    }

    @Override
    public int getCount() {
        return imdbSuggestionsList.size();
    }

    @Nullable
    @Override
    public IMDBSuggestions getItem(int position) {
        return imdbSuggestionsList.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_suggestion_row, parent, false);
        }
        final ImageView movieImage = convertView.findViewById(R.id.movie_suggestion_image);
        final TextView movieNameTextView = convertView.findViewById(R.id.movie_suggestion_movie_name);
        IMDBSuggestions movieSuggestion = getItem(position);

        if(movieSuggestion != null) {
            if(StringUtils.isNotEmpty(movieSuggestion.getMovieName())) {
                movieNameTextView.setText(movieSuggestion.getMovieName());
            }
            if(movieSuggestion.getImage() != null && StringUtils.isNotEmpty(movieSuggestion.getImage().getImageUrl())) {
                Picasso.get().load(movieSuggestion.getImage().getImageUrl()).
                        resize(150, 150)
                        .into(movieImage);
            }
        }


        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return imdbAutocompleteFilter;
    }

    private Filter imdbAutocompleteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (TextUtils.isEmpty(charSequence)) {
                return null;
            }
            List<IMDBSuggestions> suggestions = imdbAutoCompleteFetcher.getSuggestions(charSequence.toString());
            FilterResults filterResults = new FilterResults();
            filterResults.values = suggestions;
            filterResults.count = suggestions.size();
            imdbSuggestionsList = suggestions;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            if (filterResults == null || filterResults.count == 0) {
                notifyDataSetInvalidated();
            } else {
                notifyDataSetChanged();
            }

        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            IMDBSuggestions movieSuggestion = (IMDBSuggestions) resultValue;
            return movieSuggestion.getMovieName();
        }
    };
}
