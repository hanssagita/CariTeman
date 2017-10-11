package cariteman.hans.cariteman;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPageActivity extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonRegisterInLoginPage;
    private EditText editTextEmailLogin;
    private EditText editTextPasswordLogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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
        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmailLogin.getText().toString().trim();
                String password = editTextPasswordLogin.getText().toString().trim();
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
        });

    }
}
