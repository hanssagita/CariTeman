package cariteman.hans.cariteman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cariteman.hans.adapter.ThreadAdapter;
import cariteman.hans.datamodel.DetailEventModel;
import cariteman.hans.datamodel.ThreadModel;
import cariteman.hans.response.DetailEventResponse;
import cariteman.hans.rest.ApiClient;
import cariteman.hans.rest.ApiInterface;
import cariteman.hans.tools.CaviarTextView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventPageActivity extends AppCompatActivity {

    private ImageView detailEventPosterImage;
    private CaviarTextView textViewDetailEventTitle;
    private CaviarTextView textViewDetailEventDescription;
    private CaviarTextView detailEventTotalPeople;
    private CaviarTextView detailEventCategory;
    private CaviarTextView detailEventDate;
    private CaviarTextView detailEventLocation;
    private CaviarTextView detailEventHostedByText;
    private CircleImageView detailEventHostedByImg;
    private String eventId;
    private DetailEventModel detailEventData;
    private List<ThreadModel> threadEventData;
    private RecyclerView recViewThreadInDetail;
    private ThreadAdapter threadAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_page);
        detailEventPosterImage = (ImageView)findViewById(R.id.detailEventPosterImage);
        textViewDetailEventTitle = (CaviarTextView)findViewById(R.id.textViewDetailEventTitle);
        textViewDetailEventDescription = (CaviarTextView)findViewById(R.id.textViewDetailEventDescription);
        detailEventTotalPeople = (CaviarTextView)findViewById(R.id.detailEventTotalPeople);
        detailEventCategory = (CaviarTextView)findViewById(R.id.detailEventCategory);
        detailEventDate = (CaviarTextView)findViewById(R.id.detailEventDate);
        detailEventHostedByText = (CaviarTextView)findViewById(R.id.detailEventHostedByText);
        detailEventHostedByImg = (CircleImageView) findViewById(R.id.detailEventHostedByImg);
        detailEventLocation = (CaviarTextView)findViewById(R.id.detailEventLocation);
        recViewThreadInDetail = (RecyclerView)findViewById(R.id.recViewThreadInDetail);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventId = null;
            } else {
                eventId= extras.getString("eventId");
//                fetchDetailEventData();
                fetchDataDetailDummy();
            }
        } else {
            eventId = (String) savedInstanceState.getSerializable("eventId");
//            fetchDetailEventData();
            fetchDataDetailDummy();
        }

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recViewThreadInDetail.setHasFixedSize(true);
        recViewThreadInDetail.setLayoutManager(llm);
        fetchDataThreadDummy();
        threadAdapter = new ThreadAdapter(threadEventData, DetailEventPageActivity.this);
        recViewThreadInDetail.setAdapter(threadAdapter);

    }

    private void fetchDetailEventData(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<DetailEventResponse> call = apiService.getEventById(eventId);
        call.enqueue(new Callback<DetailEventResponse>() {
            @Override
            public void onResponse(Call<DetailEventResponse> call, Response<DetailEventResponse> response) {
                detailEventData = response.body().getResult();
//                fillDetailEvent(detailEventData);
            }

            @Override
            public void onFailure(Call<DetailEventResponse> call, Throwable t) {
                Log.e("Retrovit Error",t.toString());
                System.out.println(t.toString());
            }
        });
    }

    private void fetchDataDetailDummy(){
        detailEventData = new DetailEventModel();
        if(eventId.equals("one")){
            detailEventData.setEventId("1");
            detailEventData.setBackgroundImg("http://popspoken.com/wp-content/uploads/2015/01/download.jpeg");
            detailEventData.setEventName("Taylor Swift World Tour");
            detailEventData.setHostedBy("Hosted By Taylor Swift");
            detailEventData.setDateResponse("Today at 19.45 PM");
            detailEventData.setLocation("Grha Niaga Thamrin");
            detailEventData.setCategory("Music");
            detailEventData.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            detailEventData.setDescription("Taylor Swift World Tour, Taylor Swift Cantik Seksi Kaya cici CF");
            detailEventData.setTotalPeople(100);
        }
        if(eventId.equals("two")){
            detailEventData.setEventId("2");
            detailEventData.setBackgroundImg("https://www.airasia.com/cdn/aa-images/en-ID/blibli.jpg?sfvrsn=0");
            detailEventData.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            detailEventData.setEventName("Blibli Starlight");
            detailEventData.setHostedBy("Hosted By Maroon 5");
            detailEventData.setDateResponse("Tomorrow at 10.45 AM");
            detailEventData.setLocation("Thamrin Residence");
            detailEventData.setCategory("Music");
            detailEventData.setDescription("lorem ipsun dolor si amet dolor dolor dolor baymax kucing");
            detailEventData.setTotalPeople(80);
        }
        fillDetailEvent(detailEventData);
    }
    private void fetchDataThreadDummy(){
        threadEventData = new ArrayList<ThreadModel>();
        ThreadModel threadData1 = new ThreadModel();
        threadData1.setHostedBy("Hans Sagita");
        threadData1.setComments(5);
        threadData1.setEventId("one");
        threadData1.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
        threadData1.setLikes(10);
        threadData1.setMessage("Do you think diko has neck?");
        threadData1.setPostinganDate("One Hour Ago");
        threadData1.setThreadId("threadOne");
        threadEventData.add(threadData1);

        ThreadModel threadData2 = new ThreadModel();
        threadData2.setHostedBy("Yan Firdaus");
        threadData2.setComments(6);
        threadData2.setEventId("two");
        threadData2.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
        threadData2.setLikes(20);
        threadData2.setMessage("Do you think I'm wibu ?");
        threadData2.setPostinganDate("Yesterday");
        threadData2.setThreadId("threadTwo");
        threadEventData.add(threadData2);
    }

    private void fillDetailEvent(DetailEventModel detailEventData){
        Glide.with(DetailEventPageActivity.this)
                .load(detailEventData.getBackgroundImg())
                .into(detailEventPosterImage);
        textViewDetailEventTitle.setText(detailEventData.getEventName());
        textViewDetailEventDescription.setText(detailEventData.getDescription());
        detailEventTotalPeople.setText(detailEventData.getTotalPeople()+ " Going ");
        detailEventCategory.setText(detailEventData.getCategory().toUpperCase());
        detailEventDate.setText(detailEventData.getDateResponse());
        detailEventLocation.setText(detailEventData.getLocation());
        detailEventHostedByText.setText(detailEventData.getHostedBy());
        Glide.with(DetailEventPageActivity.this).load(detailEventData.getHostImg()).into(detailEventHostedByImg);
    }
}
