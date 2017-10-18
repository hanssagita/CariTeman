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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private List<EventModel> eventData;
    private FirebaseDatabase mRootRef = FirebaseDatabase.getInstance();
    private DatabaseReference mUserRef = mRootRef.getReference().child("users");
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        FirebaseMessaging.getInstance().unsubscribeFromTopic("user");
        FirebaseMessaging.getInstance().subscribeToTopic("event");

        recyclerView = (RecyclerView) findViewById(R.id.recViewAllEvent);
        pullRefreshAllEvent = (WaveSwipeRefreshLayout) findViewById(R.id.pullRefreshAllEvent);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        fetchAllDataDummy();
//        fetchAllEventData();

        //Validasi check user isLogin Or Not
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull final FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    if (!user.getDisplayName().equals("")) {
                        Toast.makeText(MainActivity.this, "Welcome, " + user.getDisplayName(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Welcome, " + user.getEmail(),
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // User is signed out (will be punch out into login activity)
                    startActivity(new Intent(MainActivity.this,
                            LoginPageActivity.class));
                    finish();
                }
            }
        };

        pullRefreshAllEvent.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchAllDataDummy();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        if (id == R.id.myEvent) {
            fetchDataMyEventDummy();
            this.setTitle("My Event");
        } else if (id == R.id.allEvent) {
            fetchAllDataDummy();
            this.setTitle("EventKu");
        } else if (id == R.id.nav_edit_profile) {
            startActivity(new Intent(MainActivity.this,
                    EditProfileActivity.class));
            // Handle the camera action
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this,
                    ProfileActivity.class));
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {

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

    private void fetchAllEventData() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<EventResponse> call = apiService.getAllEvent();
        call.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                eventData = response.body().getResults();
                eventAdapter = new EventAdapter(eventData, getBaseContext());
                recyclerView.setAdapter(eventAdapter);
                pullRefreshAllEvent.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<EventResponse> call, Throwable t) {
                Log.e("Retrovit Error", t.toString());
            }
        });
    }

    private void fetchAllDataDummy() {
        eventData = new ArrayList<>();
        EventModel eventModel1 = new EventModel();
        eventModel1.setEventId("one");
        eventModel1.setBackgroundImg("https://i.ytimg.com/vi/1PzVgMFqy0A/maxresdefault.jpg");
        eventModel1.setEventName("Payung Teduh Goes to Binus");
        eventModel1.setHostedBy("Hosted By Payung Teduh");
        eventModel1.setDateResponse("Tomorrow at 14.35 AM");
        eventModel1.setLocation("Bina Nusantara, Kampus Alam Sutera");
        eventModel1.setCategory("Music");
        eventModel1.setHostImg("http://www.pramborsfm.com/wp-content/uploads/2017/09/payung-teduh-akad1.jpg");
        eventData.add(eventModel1);

        EventModel eventModel2 = new EventModel();
        eventModel2.setEventId("two");
        eventModel2.setBackgroundImg("http://event.binus.ac.id/files/2015/03/jobexpo-low1.jpg");
        eventModel2.setHostImg("https://udemy-images.udemy.com/user/200_H/23364412_337e.jpg");
        eventModel2.setEventName("Binus Job Expo");
        eventModel2.setHostedBy("Hosted By Bina Nusantara");
        eventModel2.setDateResponse("October, 17 at 10.45 AM");
        eventModel2.setLocation("Bina Nusantara, kampus Anggrek");
        eventModel2.setCategory("Job");
        eventData.add(eventModel2);

        EventModel eventModel3 = new EventModel();
        eventModel3.setEventId("three");
        eventModel3.setBackgroundImg("http://i.imgur.com/mo7fKqf.png");
        eventModel3.setHostImg("https://udemy-images.udemy.com/user/200_H/23364412_337e.jpg");
        eventModel3.setEventName("Helmy Yahya Goes To Campus");
        eventModel3.setHostedBy("Hosted By Bina Nusantara");
        eventModel3.setDateResponse("Today at 19.00");
        eventModel3.setLocation("Bina Nusantara, kampus Anggrek");
        eventModel3.setCategory("Enterpreneur");
        eventData.add(eventModel3);
        eventAdapter = new EventAdapter(eventData, getBaseContext());
        recyclerView.setAdapter(eventAdapter);
        pullRefreshAllEvent.setRefreshing(false);
    }

    private void fetchDataMyEventDummy() {
        eventData = new ArrayList<>();
        EventModel eventModel1 = new EventModel();
        eventModel1.setEventId("one");
        eventModel1.setBackgroundImg("https://i.ytimg.com/vi/1PzVgMFqy0A/maxresdefault.jpg");
        eventModel1.setEventName("Payung Teduh Goes to Binus");
        eventModel1.setHostedBy("Hosted By Payung Teduh");
        eventModel1.setDateResponse("Tomorrow at 14.35 AM");
        eventModel1.setLocation("Bina Nusantara, Kampus Alam Sutera");
        eventModel1.setCategory("Music");
        eventModel1.setHostImg("http://www.pramborsfm.com/wp-content/uploads/2017/09/payung-teduh-akad1.jpg");
        eventData.add(eventModel1);
        eventAdapter = new EventAdapter(eventData, getBaseContext());
        recyclerView.setAdapter(eventAdapter);
        pullRefreshAllEvent.setRefreshing(false);
    }
}
