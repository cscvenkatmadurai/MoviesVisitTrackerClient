
package skven.com.moviesvisittracker.imdb.autocomplete.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class V {

    @SerializedName("i")
    @Expose
    private I_ i;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("l")
    @Expose
    private String l;
    @SerializedName("s")
    @Expose
    private String s;

    /**
     * No args constructor for use in serialization
     * 
     */
    public V() {
    }

    /**
     * 
     * @param s
     * @param i
     * @param id
     * @param l
     */
    public V(I_ i, String id, String l, String s) {
        super();
        this.i = i;
        this.id = id;
        this.l = l;
        this.s = s;
    }

    public I_ getI() {
        return i;
    }

    public void setI(I_ i) {
        this.i = i;
    }

    public V withI(I_ i) {
        this.i = i;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public V withId(String id) {
        this.id = id;
        return this;
    }

    public String getL() {
        return l;
    }

    public void setL(String l) {
        this.l = l;
    }

    public V withL(String l) {
        this.l = l;
        return this;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public V withS(String s) {
        this.s = s;
        return this;
    }

}
