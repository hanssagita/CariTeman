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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cariteman.hans.datamodel.Member;

public class RegisterPageActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmailRegister;
    private EditText editTextPasswordRegister;
    private FirebaseAuth mAuth;
    FirebaseDatabase mRootRef = FirebaseDatabase.getInstance();
    DatabaseReference mMemberRef = mRootRef.getReference().child("members");
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        editTextEmailRegister = (EditText)findViewById(R.id.editTextEmailRegister);
        editTextPasswordRegister = (EditText)findViewById(R.id.editTextPasswordRegister);
        mAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmailRegister.getText().toString().trim();
                password = editTextPasswordRegister.getText().toString().trim();
                if(!email.isEmpty() && !password.isEmpty()) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterPageActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    //Todo Disini bakal di buat post request data user ke backend buat di save data di backend

                                    startActivity(new Intent(RegisterPageActivity.this,
                                            LoginPageActivity.class));

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(RegisterPageActivity.this,
                                                "Register is unsuccessfull", Toast.LENGTH_SHORT);
                                    }
                                }
                            });
                } else{
                    Toast.makeText(RegisterPageActivity.this,"Please fill all first", Toast.LENGTH_SHORT);
                }

            }
        });
    }
    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

