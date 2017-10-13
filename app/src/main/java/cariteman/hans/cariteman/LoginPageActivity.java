package cariteman.hans.cariteman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cariteman.hans.datamodel.Member;

public class LoginPageActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private Button buttonLogin;
    private Button buttonRegisterInLoginPage;
    private EditText editTextEmailLogin;
    private EditText editTextPasswordLogin;
    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton buttonSignInWithGoogleAccount;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseDatabase mRootRef = FirebaseDatabase.getInstance();
    private DatabaseReference mMemberRef = mRootRef.getReference().child("members");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        editTextEmailLogin = (EditText)findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = (EditText)findViewById(R.id.editTextPasswordLogin);
        buttonRegisterInLoginPage = (Button)findViewById(R.id.buttonRegisterInLoginPage);
        buttonRegisterInLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPageActivity.this,
                        RegisterPageActivity.class));
            }
        });

        buttonSignInWithGoogleAccount = (SignInButton) findViewById(R.id.buttonSignInWithGoogleAccount);
        buttonSignInWithGoogleAccount.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, (GoogleApiClient.OnConnectionFailedListener) this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();


        buttonSignInWithGoogleAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmailLogin.getText().toString().trim();
                String password = editTextPasswordLogin.getText().toString().trim();
                signInWithEmailAndPasswordBasic(email, password);
            }
        });
    }

    private void signInWithEmailAndPasswordBasic(String email,String password) {
        if(!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginPageActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(LoginPageActivity.this,"Login is successfull",
                                    Toast.LENGTH_SHORT);

                            //TODO maybe will be call to backend to get fullname and username and save to session

                            startActivity(new Intent(LoginPageActivity.this,
                                    MainActivity.class));
                            finish();

                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginPageActivity.this,
                                        "Failed To Login", Toast.LENGTH_SHORT);
                            }
                        }
                    });
        } else{
            Toast.makeText(LoginPageActivity.this,"Please fill all first", Toast.LENGTH_SHORT);
        }
    }

    private void signInWithGoogle(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount acct = result.getSignInAccount();
                firebaseAuthWithGoogle(acct);
            }else{
                Toast.makeText(LoginPageActivity.this, "Failed Login With Google", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //TODO maybe will be call to backend to get fullname and username and save to session
                            FirebaseUser user = mAuth.getCurrentUser();
                            Member member = new Member(user.getEmail(),"Unknown",user.getDisplayName(),user.getPhoneNumber());
                            mMemberRef.child(mMemberRef.push().getKey()).setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isComplete()){
                                        startActivity(new Intent(LoginPageActivity.this,MainActivity.class));
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginPageActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginPageActivity.this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
}
