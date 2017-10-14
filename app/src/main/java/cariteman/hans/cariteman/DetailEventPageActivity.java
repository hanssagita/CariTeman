package cariteman.hans.cariteman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cariteman.hans.datamodel.DetailEventModel;
import cariteman.hans.response.DetailEventResponse;
import cariteman.hans.rest.ApiClient;
import cariteman.hans.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEventPageActivity extends AppCompatActivity {

    private ImageView detailEventPosterImage;
    private TextView textViewDetailEventTitle;
    private TextView textViewDetailEventDescription;
    private TextView detailEventTotalPeople;
    private TextView detailEventCategory;
    private String eventId;
    private DetailEventModel detailEventData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_page);
        detailEventPosterImage = (ImageView)findViewById(R.id.detailEventPosterImage);
        textViewDetailEventTitle = (TextView)findViewById(R.id.textViewDetailEventTitle);
        textViewDetailEventDescription = (TextView)findViewById(R.id.textViewDetailEventDescription);
        detailEventTotalPeople = (TextView)findViewById(R.id.detailEventTotalPeople);
        detailEventCategory = (TextView)findViewById(R.id.detailEventCategory);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                eventId= null;
            } else {
                eventId= extras.getString("eventId");
                fetchDetailEventData();
            }
        } else {
            eventId = (String) savedInstanceState.getSerializable("eventId");
            fetchDetailEventData();
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
                fillDetailEvent(detailEventData);
            }

            @Override
            public void onFailure(Call<DetailEventResponse> call, Throwable t) {
                Log.e("Retrovit Error",t.toString());
                System.out.println(t.toString());
            }
        });
    }
    private void fillDetailEvent(DetailEventModel detailEventData){
        Glide.with(DetailEventPageActivity.this)
                .load(detailEventData.getBackgroundImg())
                .into(detailEventPosterImage);
        textViewDetailEventTitle.setText(detailEventData.getEventName());
        textViewDetailEventDescription.setText(detailEventData.getDescription());
        detailEventTotalPeople.setText(detailEventData.getTotalPeople()+ " Going ");
        detailEventCategory.setText(detailEventData.getCategory());
    }
}
