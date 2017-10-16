package cariteman.hans.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cariteman.hans.cariteman.DetailEventPageActivity;
import cariteman.hans.cariteman.R;
import cariteman.hans.datamodel.EventModel;
import cariteman.hans.tools.CaviarTextView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hans.sagita on 12/10/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<EventModel> eventModel = new ArrayList<>();
    Context context;

    public EventAdapter(List<EventModel> eventModel, Context context){
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final EventModel oneEvent = eventModel.get(position);
        Glide.with(context).load(oneEvent.getBackgroundImg()).into(holder.eventCardImage);
        holder.eventCardTitle.setText(oneEvent.getEventName());
        holder.eventCardHostedBy.setText(oneEvent.getHostedBy());
        holder.eventCardLocation.setText(oneEvent.getLocation());
        holder.eventCardCategory.setText(oneEvent.getCategory().toUpperCase());
        holder.eventCardDate.setText(oneEvent.getDateResponse().toString());
        holder.relativeEventCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailEventPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("eventId",oneEvent.getEventId());
                context.startActivity(intent);
            }
        });
        Glide.with(context).load(oneEvent.getHostImg()).into(holder.eventHostImg);
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
        CaviarTextView eventCardTitle;
        CaviarTextView eventCardDate;
        CaviarTextView eventCardHostedBy;
        CaviarTextView eventCardLocation;
        CaviarTextView eventCardCategory;
        RelativeLayout relativeEventCard;
        CircleImageView eventHostImg;
        public ViewHolder(View itemView) {
            super(itemView);
            eventCardImage = (ImageView)itemView.findViewById(R.id.eventCardImage);
            eventCardTitle = (CaviarTextView)itemView.findViewById(R.id.eventCardTitle);
            eventCardDate = (CaviarTextView)itemView.findViewById(R.id.eventCardDate);
            eventCardHostedBy = (CaviarTextView)itemView.findViewById(R.id.eventCardHostedBy);
            eventCardLocation = (CaviarTextView)itemView.findViewById(R.id.eventCardLocation);
            eventCardCategory = (CaviarTextView)itemView.findViewById(R.id.eventCardCategory);
            relativeEventCard = (RelativeLayout)itemView.findViewById(R.id.relativeEventCard);
            eventHostImg = (CircleImageView)itemView.findViewById(R.id.eventCardHostedImg);
        }
    }
}
