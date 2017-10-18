package cariteman.hans.cariteman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private CaviarTextView rsvpTextView;
    private RecyclerView recyclerView;
    private ThreadModel threadModel;
    private String threadId;
    private CommentAdapter commentAdapter;
    private List<CommentModel> commentModelList  = new ArrayList<>();;
    private EditText editTextCommentPost;
    private String newComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_event_thread_page);
        detailEventThreadHostedByImg = (CircleImageView)findViewById(R.id.detailEventThreadHostedByImg);
        detailEventThreadHostedByName = (CaviarTextView)findViewById(R.id.detailEventThreadHostedByName);
        detailEventThreadDate = (CaviarTextView)findViewById(R.id.detailEventThreadDate);
        detailEventThreadContent = (CaviarTextView)findViewById(R.id.detailEventThreadContent);
        rsvpTextView = (CaviarTextView)findViewById(R.id.rsvpTextView);
        recyclerView = (RecyclerView)findViewById(R.id.recViewDetailEventThreadComment);
        editTextCommentPost = (EditText)findViewById(R.id.editTextCommentPost);

        editTextCommentPost.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            newComment = editTextCommentPost.getText().toString().trim();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d hh:mm a");
                            CommentModel commentModelNew = new CommentModel();
                            commentModelNew.setThreadId("threadNew");
                            commentModelNew.setCommentContent(newComment);
                            commentModelNew.setCommentDate(dateFormat.format(new Date()));
                            commentModelNew.setEventId("one");
                            commentModelNew.setHostedBy("Hans Sagita");
                            commentModelNew.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
                            commentModelNew.setLikes(0);
                            commentModelNew.setMemberId("memberOne");
                            commentModelList.add(commentModelNew);
                            commentAdapter = new CommentAdapter(commentModelList, getBaseContext());
                            recyclerView.setAdapter(commentAdapter);
                            editTextCommentPost.setText("");
                            Toast.makeText(DetailEventThreadPageActivity.this, "Success Post Comment", Toast.LENGTH_SHORT).show();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


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
            threadModel.setComments(3);
            threadModel.setEventId("one");
            threadModel.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            threadModel.setLikes(10);
            threadModel.setMessage("Does anyone come from  Kelapa Gading ?");
            threadModel.setPostinganDate("One Hour Ago");
            threadModel.setThreadId("threadOne");
            fillDataThread();
        }
        if(threadId.equals("threadTwo")){
            threadModel.setHostedBy("Yan Firdaus");
            threadModel.setComments(3);
            threadModel.setEventId("two");
            threadModel.setHostImg("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAmXAAAAJGExNzQ4NWUwLTc4MzUtNDU1ZC1hMjk0LWVlNDVmZTNjNWY5Mg.jpg");
            threadModel.setLikes(20);
            threadModel.setMessage("Harga tiket nya berapa ya kak ?");
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
        if(threadId.equals("threadOne")){
            CommentModel commentModel1 = new CommentModel();
            commentModel1.setThreadId("threadOne");
            commentModel1.setCommentContent("I'm come from Kelapa Gading Hans.");
            commentModel1.setCommentDate("October 11 at 10.30 AM");
            commentModel1.setEventId("one");
            commentModel1.setHostedBy("Diko Raditya");
            commentModel1.setHostImg("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAlYAAAAJDVkNTVlOTgyLWE3ZGQtNDEzNi1hNjhkLWZlYTRhZDdhMTA5OA.jpg");
            commentModel1.setLikes(1);
            commentModel1.setMemberId("memberOne");
            commentModelList.add(commentModel1);
            CommentModel commentModel2 = new CommentModel();
            commentModel2.setThreadId("threadOne");
            commentModel2.setCommentContent("Me too, wanna join ?");
            commentModel2.setCommentDate("October 11 at 10.30 AM");
            commentModel2.setEventId("one");
            commentModel2.setHostedBy("Jevon Averill");
            commentModel2.setHostImg("http://jevonave.azurewebsites.net/img/jevonaverillbinus.jpg");
            commentModel2.setLikes(2);
            commentModel2.setMemberId("memberOne");
            commentModelList.add(commentModel2);
        }else if(threadId.equals("threadTwo")){
            CommentModel commentModel1 = new CommentModel();
            commentModel1.setThreadId("threadTwo");
            commentModel1.setCommentContent("Yes I Agree Test");
            commentModel1.setCommentDate("October 11 at 10.30 AM");
            commentModel1.setEventId("one");
            commentModel1.setHostedBy("Bina Nusantara Admin");
            commentModel1.setHostImg("https://udemy-images.udemy.com/user/200_H/23364412_337e.jpg");
            commentModel1.setLikes(1);
            commentModel1.setMemberId("memberOne");
            commentModelList.add(commentModel1);
        }
            commentAdapter = new CommentAdapter(commentModelList, getBaseContext());
            recyclerView.setAdapter(commentAdapter);
    }
}
