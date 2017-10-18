package cariteman.hans.cariteman;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cariteman.hans.datamodel.User;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = EditProfileActivity.class.getSimpleName();

    private Button btnEdit;
    private EditText editTextEmail;
    private EditText editTextFullName;
    private EditText editTextAddress;
    private EditText editTextPhoneNumber;
    private EditText editTextStudentId;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mRootRef = FirebaseDatabase.getInstance();
    private DatabaseReference mUserRef = mRootRef.getReference().child("users");
    private String email;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String studentId;
    private ImageView ivPhoto;
    private AnimationDrawable animationDrawable;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutEditProfilePage);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(3000);

        btnEdit = (Button) findViewById(R.id.btnEdit);
        editTextEmail = (EditText) findViewById(R.id.editTextEmailRegister);
        editTextFullName = (EditText) findViewById(R.id.editTextFullNameRegister);
        editTextAddress = (EditText) findViewById(R.id.editTextAddressRegister);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumberRegister);
        editTextStudentId = (EditText) findViewById(R.id.editTextStudentId);
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);

        Glide.with(EditProfileActivity.this)
                .load("https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg")
                .into(ivPhoto);

        mAuth = FirebaseAuth.getInstance();

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (mAuth.getCurrentUser().getUid().equals(child.getKey())) {
                            User user = child.getValue(User.class);
                            editTextEmail.setText(user.getEmail());
                            editTextFullName.setText(user.getDisplayName());
                            editTextAddress.setText(user.getAddress());
                            editTextPhoneNumber.setText(user.getPhoneNumber());
                            editTextPhoneNumber.setText(user.getStudentId());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting User failed, log a message
                Log.w(TAG, "loadUser:onCancelled", databaseError.toException());
                // ...
            }
        };
        mUserRef.addValueEventListener(userListener);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString().trim();
                fullName = editTextFullName.getText().toString().trim();
                address = editTextAddress.getText().toString().trim();
                phoneNumber = editTextPhoneNumber.getText().toString().trim();
                studentId = editTextStudentId.getText().toString().trim();
                User user = new User(email, address, fullName, phoneNumber,
                        "https://pbs.twimg.com/profile_images/477132899041296385/M-7XVG3B_400x400.jpeg", studentId);
                mUserRef.child(mAuth.getCurrentUser().getUid()).setValue(user);
                Toast.makeText(getApplicationContext(), "Edit Success", Toast.LENGTH_SHORT).show();
            }
        });
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

