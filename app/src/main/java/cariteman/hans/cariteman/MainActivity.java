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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import cariteman.hans.Adapter.EventAdapter;
import cariteman.hans.datamodel.EventModel;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private WaveSwipeRefreshLayout pullRefreshAllEvent;
    private ArrayList<EventModel> eventData = new ArrayList<EventModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        //DUMMY
        EventModel eventModel1 = new EventModel();
        eventModel1.setEventDate("2017");
        eventModel1.setPhotoUrl("http://popspoken.com/wp-content/uploads/2015/01/download.jpeg");
        eventModel1.setTitle("Taylor Swift World Tour");
        eventModel1.setHostedBy("Hosted By Taylor Swift");
        eventModel1.setEventDate("Today at 19.45 PM");
        eventModel1.setLocation("Graha Niaga Thamrin");
        eventModel1.setCategory("Music");
        eventData.add(eventModel1);

        EventModel eventModel2 = new EventModel();
        eventModel2.setEventDate("2017");
        eventModel2.setPhotoUrl("https://c4ikmd.corednacdn.com/web_images/1129/merchant_1129.jpeg");
        eventModel2.setTitle("Blibli Starlight");
        eventModel2.setHostedBy("Hosted By  Maroon 5");
        eventModel2.setEventDate("Tommorow at 10.45 AM");
        eventModel2.setLocation("Thamrin Residence");
        eventModel2.setCategory("Music");
        eventData.add(eventModel2);

        EventModel eventModel3 = new EventModel();
        eventModel3.setEventDate("2012");
        eventModel3.setPhotoUrl("https://www.airasia.com/cdn/aa-images/en-ID/blibli.jpg?sfvrsn=0");
        eventModel3.setTitle("Blibli Dot Com");
        eventModel3.setHostedBy("Hosted By Leher Diko Ilang");
        eventModel3.setEventDate("Tommorow at 10.45 AM");
        eventModel3.setLocation("Thamrin Residence");
        eventModel3.setCategory("Eat");
        eventData.add(eventModel3);

        //Dummy


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)findViewById(R.id.recViewAllEvent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        eventAdapter = new EventAdapter(eventData,getBaseContext());
        recyclerView.setAdapter(eventAdapter);


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

        pullRefreshAllEvent = (WaveSwipeRefreshLayout)findViewById(R.id.pullRefreshAllEvent);
        pullRefreshAllEvent.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                pullRefreshAllEvent.setRefreshing(false);
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
}
