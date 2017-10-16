package cariteman.hans.cariteman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cariteman.hans.datamodel.DetailEventModel;
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
    private TextView textViewDetailEventDescription;
    private TextView detailEventTotalPeople;
    private TextView detailEventCategory;
    private TextView detailEventDate;
    private TextView detailEventLocation;
    private TextView detailEventHostedByText;
    private CircleImageView detailEventHostedByImg;
    private String eventId;
    private DetailEventModel detailEventData;
    private RecyclerView recViewThreadInDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_page);
        detailEventPosterImage = (ImageView)findViewById(R.id.detailEventPosterImage);
        textViewDetailEventTitle = (CaviarTextView)findViewById(R.id.textViewDetailEventTitle);
        textViewDetailEventDescription = (TextView)findViewById(R.id.textViewDetailEventDescription);
        detailEventTotalPeople = (TextView)findViewById(R.id.detailEventTotalPeople);
        detailEventCategory = (TextView)findViewById(R.id.detailEventCategory);
        detailEventDate = (TextView)findViewById(R.id.detailEventDate);
        detailEventHostedByText = (TextView)findViewById(R.id.detailEventHostedByText);
        detailEventHostedByImg = (CircleImageView) findViewById(R.id.detailEventHostedByImg);
        detailEventLocation = (TextView)findViewById(R.id.detailEventLocation);
        recViewThreadInDetail = (RecyclerView)findViewById(R.id.recViewThreadInDetail);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventId = null;
            } else {
                eventId= extras.getString("eventId");
//                fetchDetailEventData();
                fetchDataDummy();
            }
        } else {
            eventId = (String) savedInstanceState.getSerializable("eventId");
//            fetchDetailEventData();
            fetchDataDummy();
        }

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

    private void fetchDataDummy(){
        detailEventData = new DetailEventModel();
        if(eventId.equals("one")){
            detailEventData.setEventId("1");
            detailEventData.setBackgroundImg("http://popspoken.com/wp-content/uploads/2015/01/download.jpeg");
            detailEventData.setEventName("Taylor Swift World Tour");
            detailEventData.setHostedBy("Hosted By Taylor Swift");
            detailEventData.setDateResponse("Today at 19.45 PM");
            detailEventData.setLocation("Graha Niaga Thamrin");
            detailEventData.setCategory("Music");
            detailEventData.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            detailEventData.setDescription("lorem ipsun dolor si amet dolor dolor dolor baymax kucing");
            detailEventData.setTotalPeople(100);
        }
        if(eventId.equals("two")){
            detailEventData.setEventId("2");
            detailEventData.setBackgroundImg("https://www.airasia.com/cdn/aa-images/en-ID/blibli.jpg?sfvrsn=0");
            detailEventData.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
            detailEventData.setEventName("Blibli Starlight");
            detailEventData.setHostedBy("Hosted By  Maroon 5");
            detailEventData.setDateResponse("Tommorow at 10.45 AM");
            detailEventData.setLocation("Thamrin Residence");
            detailEventData.setCategory("Music");
            detailEventData.setDescription("lorem ipsun dolor si amet dolor dolor dolor baymax kucing");
            detailEventData.setTotalPeople(80);
        }
        fillDetailEvent(detailEventData);
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
