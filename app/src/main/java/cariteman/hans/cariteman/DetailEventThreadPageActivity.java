package cariteman.hans.cariteman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cariteman.hans.adapter.CommentAdapter;
import cariteman.hans.datamodel.CommentModel;
import cariteman.hans.datamodel.ThreadModel;
import cariteman.hans.tools.CaviarTextView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DetailEventThreadPageActivity extends AppCompatActivity {

    private CircleImageView detailEventThreadHostedByImg;
    private CaviarTextView detailEventThreadHostedByName;
    private CaviarTextView detailEventThreadDate;
    private CaviarTextView detailEventThreadContent;
    private RecyclerView recyclerView;
    private ThreadModel threadModel;
    private String threadId;
    private CommentAdapter commentAdapter;
    private List<CommentModel> commentModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_event_thread_page);
        detailEventThreadHostedByImg = (CircleImageView)findViewById(R.id.detailEventThreadHostedByImg);
        detailEventThreadHostedByName = (CaviarTextView)findViewById(R.id.detailEventThreadHostedByName);
        detailEventThreadDate = (CaviarTextView)findViewById(R.id.detailEventThreadDate);
        detailEventThreadContent = (CaviarTextView)findViewById(R.id.detailEventThreadContent);
        recyclerView = (RecyclerView)findViewById(R.id.recViewDetailEventThreadComment);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                threadId = null;
            } else {
                threadId= extras.getString("threadId");
//                fetchDetailEventData();
                fetchDataDummyThread();
            }
        } else {
            threadId = (String) savedInstanceState.getSerializable("eventId");
//            fetchDetailEventData();
            fetchDataDummyThread();
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        fillListCommentDummy();
    }
    private void fetchDataDummyThread(){
        threadModel = new ThreadModel();
        if(threadId.equals("threadOne")){
            threadModel.setHostedBy("Hans Sagita");
            threadModel.setComments(5);
            threadModel.setEventId("one");
            threadModel.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            threadModel.setLikes(10);
            threadModel.setMessage("Do you think diko has neck?");
            threadModel.setPostinganDate("One Hour Ago");
            threadModel.setThreadId("threadOne");
            fillDataThread();
        }
        if(threadId.equals("threadTwo")){
            threadModel.setHostedBy("Yan Firdaus");
            threadModel.setComments(6);
            threadModel.setEventId("two");
            threadModel.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            threadModel.setLikes(20);
            threadModel.setMessage("Do you think I'm wibu ?");
            threadModel.setPostinganDate("Yesterday");
            threadModel.setThreadId("threadTwo");
            fillDataThread();
        }
    }
    private void fillDataThread(){
        Glide.with(DetailEventThreadPageActivity.this).load(threadModel.getHostImg()).into(detailEventThreadHostedByImg);
        detailEventThreadHostedByName.setText(threadModel.getHostedBy());
        detailEventThreadDate.setText(threadModel.getPostinganDate());
        detailEventThreadContent.setText(threadModel.getMessage());
    }
    private void fillListCommentDummy(){
        commentModelList = new ArrayList<>();
        if(threadId.equals("threadOne")){
            CommentModel commentModel1 = new CommentModel();
            commentModel1.setThreadId("threadOne");
            commentModel1.setCommentContent("Yes I Agree");
            commentModel1.setCommentDate("October 11 at 10.30 AM");
            commentModel1.setEventId("one");
            commentModel1.setHostedBy("Hans Sagita");
            commentModel1.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            commentModel1.setLikes(1);
            commentModel1.setMemberId("memberOne");
            commentModelList.add(commentModel1);
            CommentModel commentModel2 = new CommentModel();
            commentModel2.setThreadId("threadOne");
            commentModel2.setCommentContent("Yes I Not Agree");
            commentModel2.setCommentDate("October 11 at 10.30 AM");
            commentModel2.setEventId("one");
            commentModel2.setHostedBy("Hans Sagita");
            commentModel2.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            commentModel2.setLikes(2);
            commentModel2.setMemberId("memberOne");
            commentModelList.add(commentModel2);
        }else if(threadId.equals("threadTwo")){
            CommentModel commentModel1 = new CommentModel();
            commentModel1.setThreadId("threadTwo");
            commentModel1.setCommentContent("Yes I Agree Test");
            commentModel1.setCommentDate("October 11 at 10.30 AM");
            commentModel1.setEventId("one");
            commentModel1.setHostedBy("Hans Sagita");
            commentModel1.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            commentModel1.setLikes(1);
            commentModel1.setMemberId("memberOne");
            commentModelList.add(commentModel1);
            CommentModel commentModel2 = new CommentModel();
            commentModel2.setThreadId("threadTwo");
            commentModel2.setCommentContent("Yes I Not Agree Test");
            commentModel2.setCommentDate("October 11 at 10.30 AM");
            commentModel2.setEventId("one");
            commentModel2.setHostedBy("Hans Sagita");
            commentModel2.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            commentModel2.setLikes(2);
            commentModel2.setMemberId("memberOne");
            commentModelList.add(commentModel2);
        }
            commentAdapter = new CommentAdapter(commentModelList, getBaseContext());
            recyclerView.setAdapter(commentAdapter);
    }
}
