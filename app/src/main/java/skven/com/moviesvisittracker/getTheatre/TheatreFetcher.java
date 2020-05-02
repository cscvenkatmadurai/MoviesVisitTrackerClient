package skven.com.moviesvisittracker.getTheatre;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.gloxey.gnm.parser.GloxeyJsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;

@Singleton
public class TheatreFetcher {

    private static final String TAG = "TheatreFetcher";
    private OkHttpClient okHttpClient;


    @Inject
    public TheatreFetcher(final OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }


    public List<TheatreDTO> getTheatre() {
        final String url = "https://kiq5henquk.execute-api.us-east-1.amazonaws.com/test/theatre";

        Request request = new Request.Builder().url(url).build();
        try {
            String theatresListString = okHttpClient.newCall(request).execute().body().string();
            return Arrays.asList(GloxeyJsonParser.getInstance().parse(theatresListString, TheatreDTO[].class));
        }catch (final Exception e) {
            Log.e(TAG, "getTheatre: error in fetching theatre details", e);
        }
        return null;
    }
}
