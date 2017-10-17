package cariteman.hans.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cariteman.hans.cariteman.DetailEventThreadPageActivity;
import cariteman.hans.cariteman.R;
import cariteman.hans.datamodel.ThreadModel;
import cariteman.hans.tools.CaviarTextView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hans.sagita on 16/10/2017.
 */

public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ViewHolder> {

    private List<ThreadModel> threadModel = new ArrayList<>();
    private Context context;

    public ThreadAdapter(List<ThreadModel> threadModel, Context context) {
        this.threadModel = threadModel;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.thread_one_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ThreadModel oneThread = threadModel.get(position);
        Glide.with(context).load(oneThread.getHostImg()).into(holder.threadCardMemberPicture);
        holder.threadCardMemberName.setText(oneThread.getHostedBy());
        holder.threadCardDateCreate.setText(oneThread.getPostinganDate());
        holder.threadCardContent.setText(oneThread.getMessage());
        holder.eventDetailTotalLike.setText(oneThread.getLikes().toString());
        holder.eventDetailTotalComment.setText(oneThread.getComments().toString());
        holder.relativeLayoutThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailEventThreadPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("threadId",oneThread.getThreadId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return threadModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView threadCardMemberPicture;
        private CaviarTextView threadCardMemberName;
        private CaviarTextView threadCardDateCreate;
        private CaviarTextView threadCardContent;
        private CaviarTextView eventDetailTotalLike;
        private CaviarTextView eventDetailTotalComment;
        private RelativeLayout relativeLayoutThread;
        public ViewHolder(View itemView) {
            super(itemView);
            threadCardMemberPicture = (CircleImageView)itemView.findViewById(R.id.threadCardMemberPicture);
            threadCardMemberName = (CaviarTextView)itemView.findViewById(R.id.threadCardMemberName);
            threadCardDateCreate = (CaviarTextView)itemView.findViewById(R.id.threadCardDateCreate);
            threadCardContent = (CaviarTextView)itemView.findViewById(R.id.threadCardContent);
            eventDetailTotalLike = (CaviarTextView)itemView.findViewById(R.id.eventDetailTotalLike);
            eventDetailTotalComment = (CaviarTextView)itemView.findViewById(R.id.eventDetailTotalComment);
            relativeLayoutThread = (RelativeLayout)itemView.findViewById(R.id.relativeLayoutThread);
        }
    }
}
