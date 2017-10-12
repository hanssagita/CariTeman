package cariteman.hans.cariteman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailEventPageActivity extends AppCompatActivity {

    private ImageView detailEventPosterImage;
    private TextView textViewDetailEventTitle;
    private TextView textViewDetailEventDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_page);
        detailEventPosterImage = (ImageView)findViewById(R.id.detailEventPosterImage);
        textViewDetailEventTitle = (TextView)findViewById(R.id.textViewDetailEventTitle);
        textViewDetailEventDescription = (TextView)findViewById(R.id.textViewDetailEventDescription);
        Glide.with(DetailEventPageActivity.this)
                .load("http://popspoken.com/wp-content/uploads/2015/01/download.jpeg")
                .into(detailEventPosterImage);
        textViewDetailEventTitle.setText("Taylor Swift World Tour 2017");
        textViewDetailEventDescription.setText("Lorem ipsum dolor sit amet," +
                " consectetur adipisicing elit, sed do eiusmod\n" +
                "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n" +
                "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n" +
                "consequat.");
    }
}
