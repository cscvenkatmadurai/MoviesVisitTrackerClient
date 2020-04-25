
package skven.com.moviesvisittracker.imdb.autocomplete.dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IMDBResponse {

    @SerializedName("d")
    @Expose
    private List<IMDBSuggestions> suggestions = null;
    @SerializedName("q")
    @Expose
    private String query;
    @SerializedName("v")
    @Expose
    private Integer v;

    /**
     * No args constructor for use in serialization
     * 
     */
    public IMDBResponse() {
    }

    /**
     * 
     * @param query
     * @param suggestions
     * @param v
     */
    public IMDBResponse(List<IMDBSuggestions> suggestions, String query, Integer v) {
        super();
        this.suggestions = suggestions;
        this.query = query;
        this.v = v;
    }

    public List<IMDBSuggestions> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<IMDBSuggestions> suggestions) {
        this.suggestions = suggestions;
    }

    public IMDBResponse withD(List<IMDBSuggestions> IMDBSuggestions) {
        this.suggestions = IMDBSuggestions;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public IMDBResponse withQ(String q) {
        this.query = q;
        return this;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public IMDBResponse withV(Integer v) {
        this.v = v;
        return this;
    }

}
