
package skven.com.moviesvisittracker.imdb.autocomplete.dao;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IMDBSuggestions {

    @SerializedName("i")
    @Expose
    private ImdbImage image;
    @SerializedName("id")
    @Expose
    private String imdbId;
    @SerializedName("l")
    @Expose
    private String movieName;

    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("rank")
    @Expose
    private Integer rank;
    @SerializedName("s")
    @Expose
    private String s;
    @SerializedName("v")
    @Expose
    private List<V> v = null;
    @SerializedName("vt")
    @Expose
    private Integer vt;
    @SerializedName("y")
    @Expose
    private Integer y;

    /**
     * No args constructor for use in serialization
     * 
     */
    public IMDBSuggestions() {
    }

    /**
     * 
     * @param q
     * @param s
     * @param v
     * @param image
     * @param rank
     * @param y
     * @param imdbId
     * @param movieName
     * @param vt
     */
    public IMDBSuggestions(ImdbImage image, String imdbId, String movieName, String q, Integer rank, String s, List<V> v, Integer vt, Integer y) {
        super();
        this.image = image;
        this.imdbId = imdbId;
        this.movieName = movieName;
        this.q = q;
        this.rank = rank;
        this.s = s;
        this.v = v;
        this.vt = vt;
        this.y = y;
    }

    public ImdbImage getImage() {
        return image;
    }

    public void setImage(ImdbImage image) {
        this.image = image;
    }

    public IMDBSuggestions withI(ImdbImage imdbImage) {
        this.image = imdbImage;
        return this;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public IMDBSuggestions withId(String id) {
        this.imdbId = id;
        return this;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public IMDBSuggestions withL(String l) {
        this.movieName = l;
        return this;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public IMDBSuggestions withQ(String q) {
        this.q = q;
        return this;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public IMDBSuggestions withRank(Integer rank) {
        this.rank = rank;
        return this;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public IMDBSuggestions withS(String s) {
        this.s = s;
        return this;
    }

    public List<V> getV() {
        return v;
    }

    public void setV(List<V> v) {
        this.v = v;
    }

    public IMDBSuggestions withV(List<V> v) {
        this.v = v;
        return this;
    }

    public Integer getVt() {
        return vt;
    }

    public void setVt(Integer vt) {
        this.vt = vt;
    }

    public IMDBSuggestions withVt(Integer vt) {
        this.vt = vt;
        return this;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public IMDBSuggestions withY(Integer y) {
        this.y = y;
        return this;
    }

}
