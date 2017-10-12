package cariteman.hans.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cariteman.hans.cariteman.R;
import cariteman.hans.datamodel.EventModel;

/**
 * Created by hans.sagita on 12/10/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private ArrayList<EventModel> eventModel = new ArrayList<>();
    Context context;

    public EventAdapter(ArrayList<EventModel> eventModel, Context context){
        this.eventModel = eventModel;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_one_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final EventModel oneEvent = eventModel.get(position);
        Glide.with(context).load(oneEvent.getPhotoUrl()).into(holder.eventCardImage);
        holder.eventCardTitle.setText(oneEvent.getTitle());
        holder.eventCardDate.setText(oneEvent.getEventDate().toString());
    }

    @Override
    public int getItemCount() {
        return eventModel.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView eventCardImage;
        TextView eventCardTitle;
        TextView eventCardDate;
        public ViewHolder(View itemView) {
            super(itemView);
            eventCardImage = (ImageView)itemView.findViewById(R.id.eventCardImage);
            eventCardTitle = (TextView)itemView.findViewById(R.id.eventCardTitle);
            eventCardDate = (TextView)itemView.findViewById(R.id.eventCardDate);
        }
    }
}
