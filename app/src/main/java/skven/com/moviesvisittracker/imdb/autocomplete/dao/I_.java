
package skven.com.moviesvisittracker.imdb.autocomplete.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class I_ {

    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("width")
    @Expose
    private Integer width;

    /**
     * No args constructor for use in serialization
     * 
     */
    public I_() {
    }

    /**
     * 
     * @param imageUrl
     * @param width
     * @param height
     */
    public I_(Integer height, String imageUrl, Integer width) {
        super();
        this.height = height;
        this.imageUrl = imageUrl;
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public I_ withHeight(Integer height) {
        this.height = height;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public I_ withImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public I_ withWidth(Integer width) {
        this.width = width;
        return this;
    }

}
