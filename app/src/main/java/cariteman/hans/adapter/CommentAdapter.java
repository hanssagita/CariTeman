package cariteman.hans.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cariteman.hans.cariteman.R;
import cariteman.hans.datamodel.CommentModel;
import cariteman.hans.tools.CaviarTextView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HansSagita on 17/10/17.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<CommentModel> commentModel = new ArrayList<>();
    private Context context;

    public CommentAdapter(List<CommentModel> commentModel, Context context) {
        this.commentModel = commentModel;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_one_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CommentModel oneModel = commentModel.get(position);
        Glide.with(context).load(oneModel.getHostImg()).into(holder.commentHostedByImg);
        holder.commentHostedByName.setText(oneModel.getHostedBy());
        holder.commentDate.setText(oneModel.getCommentDate());
        holder.commentContent.setText(oneModel.getCommentContent());
        holder.commentTotalLike.setText(oneModel.getLikes().toString());
    }

    @Override
    public int getItemCount() {
        return commentModel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView commentHostedByImg;
        private CaviarTextView commentHostedByName;
        private CaviarTextView commentContent;
        private CaviarTextView commentTotalLike;
        private CaviarTextView commentDate;

        public ViewHolder(View itemView) {
            super(itemView);
            commentHostedByImg = (CircleImageView)itemView.findViewById(R.id.commentHostedByImg);
            commentHostedByName = (CaviarTextView)itemView.findViewById(R.id.commentHostedByName);
            commentContent = (CaviarTextView)itemView.findViewById(R.id.commentContent);
            commentTotalLike = (CaviarTextView)itemView.findViewById(R.id.commentTotalLike);
            commentDate = (CaviarTextView)itemView.findViewById(R.id.commentDate);
        }
    }
}
