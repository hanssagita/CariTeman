package cariteman.hans.cariteman;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cariteman.hans.datamodel.Member;

public class RegisterPageActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmailRegister;
    private EditText editTextPasswordRegister;
    private EditText editTextFullNameRegister;
    private EditText editTextAddressRegister;
    private EditText editTextPhoneNumberRegister;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mRootRef = FirebaseDatabase.getInstance();
    private DatabaseReference mMemberRef = mRootRef.getReference().child("users");
    private String email;
    private String password;
    private String fullName;
    private String address;
    private String phoneNumber;
    private AnimationDrawable animationDrawable;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayoutRegisterPage);
        animationDrawable =(AnimationDrawable)relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(3000);

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        editTextEmailRegister = (EditText)findViewById(R.id.editTextEmailRegister);
        editTextPasswordRegister = (EditText)findViewById(R.id.editTextPasswordRegister);
        editTextFullNameRegister = (EditText)findViewById(R.id.editTextFullNameRegister);
        editTextAddressRegister = (EditText)findViewById(R.id.editTextAddressRegister);
        editTextPhoneNumberRegister = (EditText)findViewById(R.id.editTextPhoneNumberRegister);
        mAuth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextEmailRegister.getText().toString().trim();
                password = editTextPasswordRegister.getText().toString().trim();
                password = editTextPasswordRegister.getText().toString().trim();
                fullName = editTextFullNameRegister.getText().toString().trim();
                address = editTextAddressRegister.getText().toString().trim();
                phoneNumber = editTextPhoneNumberRegister.getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty()) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterPageActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    //Todo Disini bakal di buat post request data user ke backend buat di save data di backend
                                    Member member = new Member(email,address,fullName,phoneNumber);
                                    mMemberRef.child(mMemberRef.push().getKey())
                                            .setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isComplete()){
                                                startActivity(new Intent(RegisterPageActivity.this,
                                                        LoginPageActivity.class));
                                                finish();
                                            }
                                        }
                                    });
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

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }
}

