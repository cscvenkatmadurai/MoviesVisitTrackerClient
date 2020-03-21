package skven.com.moviesvisittracker.getTheatre;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import skven.com.moviesvisittracker.R;


public class TheatreAdapter extends  RecyclerView.Adapter<TheatreAdapter.MyViewHolder> {

    private static final String TAG = "TheatreAdapter";

    private TheatreDTO[] theatresArray = new TheatreDTO[0];

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.theatre_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.theatreId.setText("TheatreId: " + Integer.toString(theatresArray[position].theatreId));
        holder.theatreName.setText("Theatre Name: " + theatresArray[position].theatreName);
        holder.theatreLocation.setText("Theatre Loacation: " + theatresArray[position].theatreLocation);

    }

    public void setTheatresArray(TheatreDTO[] theatresArray) {
        this.theatresArray = theatresArray;
    }

    @Override
    public int getItemCount() {
        return theatresArray.length;
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder {
        TextView theatreId, theatreName, theatreLocation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            theatreId = (TextView) itemView.findViewById(R.id.theatreCard_theatreId);
            theatreName = (TextView)itemView.findViewById(R.id.theatreCard_theatreName);
            theatreLocation = (TextView)itemView.findViewById(R.id.theatreCard_theatreLocation);
        }
    }
}
