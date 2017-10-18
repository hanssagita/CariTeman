package cariteman.hans.cariteman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
    private LinearLayout linearLayoutDetailShareEvent;

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
        linearLayoutDetailShareEvent = (LinearLayout)findViewById(R.id.linearLayoutDetailShareEvent);

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

        linearLayoutDetailShareEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpShareDetail();
            }
        });

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
            detailEventData.setBackgroundImg("https://i.ytimg.com/vi/1PzVgMFqy0A/maxresdefault.jpg");
            detailEventData.setEventName("Payung Teduh Goes to Binus");
            detailEventData.setHostedBy("Hosted By Payung Teduh");
            detailEventData.setDateResponse("Tomorrow at 14.35 AM");
            detailEventData.setLocation("Bina Nusantara, Kampus Alam Sutera");
            detailEventData.setCategory("Music");
            detailEventData.setHostImg("http://www.pramborsfm.com/wp-content/uploads/2017/09/payung-teduh-akad1.jpg");
            detailEventData.setDescription("Perjalanan karir band payung teduh, yang di selenggarakan di universitas Bina Nusantara");
            detailEventData.setTotalPeople(100);
        }
        if(eventId.equals("two")){
            detailEventData.setEventId("2");
            detailEventData.setBackgroundImg("http://event.binus.ac.id/files/2015/03/jobexpo-low1.jpg");
            detailEventData.setHostImg("https://udemy-images.udemy.com/user/200_H/23364412_337e.jpg");
            detailEventData.setEventName("Binus Job Expo");
            detailEventData.setHostedBy("Hosted By Bina Nusantara");
            detailEventData.setDateResponse("October, 17 at 10.45 AM");
            detailEventData.setLocation("Bina Nusantara, kampus Anggrek");
            detailEventData.setCategory("Job");
            detailEventData.setDescription("Event tahunan yang " +
                    "diselenggarakan oleh Bina Nusantara untuk mahasiswa untuk mencari pekerjaan yang cocok untuk mereka");
            detailEventData.setTotalPeople(80);
        }
        if(eventId.equals("three")){
            detailEventData.setEventId("3");
            detailEventData.setBackgroundImg("http://i.imgur.com/mo7fKqf.png");
            detailEventData.setHostImg("https://udemy-images.udemy.com/user/200_H/23364412_337e.jpg");
            detailEventData.setEventName("Helmy Yahya Goes To Campus");
            detailEventData.setHostedBy("Hosted By Bina Nusantara");
            detailEventData.setDateResponse("Today at 19.00");
            detailEventData.setLocation("Bina Nusantara, kampus Anggrek");
            detailEventData.setCategory("Enterpreneur");
            detailEventData.setDescription("Seminar tentang enterpreneur yang akan dibawakan oleh Helmy Yahya");
            detailEventData.setTotalPeople(80);
        }
        fillDetailEvent(detailEventData);
    }
    private void fetchDataThreadDummy(){
        threadEventData = new ArrayList<ThreadModel>();

        ThreadModel threadData1 = new ThreadModel();
        threadData1.setHostedBy("Hans Sagita");
        threadData1.setComments(2);
        threadData1.setEventId("one");
        threadData1.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
        threadData1.setLikes(5);
        threadData1.setMessage("Does anyone come from  Kelapa Gading ?");
        threadData1.setPostinganDate("One Hour Ago");
        threadData1.setThreadId("threadOne");
        threadEventData.add(threadData1);

        ThreadModel threadData2 = new ThreadModel();
        threadData2.setHostedBy("Yan Firdaus");
        threadData2.setComments(1);
        threadData2.setEventId("two");
        threadData2.setHostImg("https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAAmXAAAAJGExNzQ4NWUwLTc4MzUtNDU1ZC1hMjk0LWVlNDVmZTNjNWY5Mg.jpg");
        threadData2.setLikes(20);
        threadData2.setMessage("Harga tiket nya berapa ya kak ?");
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

    private void popUpShareDetail(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Please Check " + detailEventData.getEventName()
                + " Event in " + detailEventData.getBackgroundImg());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
