package cariteman.hans.cariteman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import cariteman.hans.adapter.EventAdapter;
import cariteman.hans.datamodel.EventModel;
import cariteman.hans.response.EventResponse;
import cariteman.hans.rest.ApiClient;
import cariteman.hans.rest.ApiInterface;
import io.fabric.sdk.android.Fabric;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private WaveSwipeRefreshLayout pullRefreshAllEvent;
    private List<EventModel> eventData = new ArrayList<EventModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        FirebaseMessaging.getInstance().subscribeToTopic("user");

        recyclerView = (RecyclerView)findViewById(R.id.recViewAllEvent);
        pullRefreshAllEvent = (WaveSwipeRefreshLayout)findViewById(R.id.pullRefreshAllEvent);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        fetchDataDummy();
        eventAdapter = new EventAdapter(eventData,getBaseContext());
        recyclerView.setAdapter(eventAdapter);

//        fetchAllEventData();

        //Validasi check user isLogin Or Not
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Toast.makeText(MainActivity.this, "Welcome Back User : "+ user.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    // User is signed out (will be punch out into login activity)
                    startActivity(new Intent(MainActivity.this,
                            LoginPageActivity.class));
                    finish();
                }
            }
        };

        pullRefreshAllEvent.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                fetchDataDummy();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logOut) {
            mAuth.signOut();
        } else if (id == R.id.nav_create_event) {
            startActivity(new Intent(MainActivity.this,
                    CreateEventActivity.class));
//            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //application start will be automatic check user auth session
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    //remove user auth session
    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void fetchAllEventData(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<EventResponse> call = apiService.getAllEvent();
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                eventData = response.body().getResults();
                eventAdapter = new EventAdapter(eventData,getBaseContext());
                recyclerView.setAdapter(eventAdapter);
                pullRefreshAllEvent.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.e("Retrovit Error",t.toString());
            }
        });
    }

    private void fetchDataDummy(){
        EventModel eventModel1 = new EventModel();
        eventModel1.setEventId("one");
        eventModel1.setBackgroundImg("http://popspoken.com/wp-content/uploads/2015/01/download.jpeg");
        eventModel1.setEventName("Taylor Swift World Tour");
        eventModel1.setHostedBy("Hosted By Taylor Swift");
        eventModel1.setDateResponse("Today at 19.45 PM");
        eventModel1.setLocation("Graha Niaga Thamrin");
        eventModel1.setCategory("Music");
        eventModel1.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
        eventData.add(eventModel1);

        EventModel eventModel2 = new EventModel();
        eventModel2.setEventId("two");
        eventModel2.setBackgroundImg("https://www.airasia.com/cdn/aa-images/en-ID/blibli.jpg?sfvrsn=0");
        eventModel2.setHostImg("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg");
        eventModel2.setEventName("Blibli Starlight");
        eventModel2.setHostedBy("Hosted By  Maroon 5");
        eventModel2.setDateResponse("Tommorow at 10.45 AM");
        eventModel2.setLocation("Thamrin Residence");
        eventModel2.setCategory("Music");
        eventData.add(eventModel2);

        pullRefreshAllEvent.setRefreshing(false);
    }
}
