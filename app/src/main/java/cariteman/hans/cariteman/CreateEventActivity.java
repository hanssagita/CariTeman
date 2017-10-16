package cariteman.hans.cariteman;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cariteman.hans.datamodel.EventModel;
import fr.ganfra.materialspinner.MaterialSpinner;
import io.fabric.sdk.android.Fabric;

/**
 * Created by jevonaverill on 15/10/17.
 */

public class CreateEventActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        View.OnClickListener {

    private static final String TAG = CreateEventActivity.class.getSimpleName();

    MaterialSpinner spinner;
    List<String> categoryList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    private TextInputEditText mNameView;
    private TextInputEditText mDescriptionView;
    private TextView mDateView;
    private TextView mTimeView;
    private View mSelectDate;
    private View mSelectTime;
    private Button btnSave;
    private Button btnChoose;
    private Button btnUpload;

    private String eventName;
    private String eventDescription;
    private String eventCategory;
    private Date eventDate;

    //Selected due date, stored as a timestamp
    private long mDate = Long.MAX_VALUE;
    //Selected time, stored as a timestamp
    private long mTime = Long.MAX_VALUE;
    private String zone;

    private SimpleDateFormat dateFormat, timeFormat;

    private ImageView mImageView;
    //a Uri object to store file path
    private Uri filePath;

    private FirebaseStorage mStorage = FirebaseStorage.getInstance();
    private StorageReference mStorageRef = mStorage.getReferenceFromUrl("gs://cariteman-256b3.appspot.com");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_create_event);

        mImageView = (ImageView) findViewById(R.id.imageView);

        dateFormat = new SimpleDateFormat("EEEE, MMM d");
        timeFormat = new SimpleDateFormat("hh:mm a");

        mNameView = (TextInputEditText) findViewById(R.id.text_input_event_name);
        mDescriptionView = (TextInputEditText) findViewById(R.id.text_input_event_descirption);
        mDateView = (TextView) findViewById(R.id.text_date);
        mTimeView = (TextView) findViewById(R.id.text_time);
        mSelectDate = findViewById(R.id.select_date);
        mSelectTime = findViewById(R.id.select_time);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);

        setCategoryList();

        spinner = (MaterialSpinner) findViewById(R.id.categorySpinner);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != -1) {
                    eventCategory = spinner.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSelectDate.setOnClickListener(this);
        mSelectTime.setOnClickListener(this);
        updateDateDisplay();
        updateTimeDisplay();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventName = mNameView.getText().toString().trim();
                eventDescription = mDescriptionView.getText().toString().trim();
                if (eventCategory.isEmpty()) {
                    spinner.setError("Please choose category");
                }
                Date d = new Date(mDate);
                Date t = new Date(mTime);
                long dd = d.getTime() / 86400000l * 86400000l;
                long tt = t.getTime() - (t.getTime() / 86400000l * 86400000l);
                eventDate = new Date(dd+tt);

                // TODO: call api createEvent / save to Cloud Firestore
//                EventModel eventModel = new EventModel();
//                eventModel.setEventName(eventName);
//                eventModel.setCategory(eventCategory);
//                eventModel.setEventDescription(eventDescription);
//                eventModel.setEventDate(eventDate);
//                eventModel.setBackgroundImg();

            }
        });
    }

    //this method will upload the file
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            StorageReference eventsRef = storageReference.child("images/pic.jpg");
            eventsRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            Toast.makeText(getApplicationContext(), "No chosen file!", Toast.LENGTH_SHORT).show();
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 234);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 234 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setCategoryList() {
        categoryList.add(0, "Entertainment");
        categoryList.add(1, "Study");
        categoryList.add(2, "Sport");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("eventDate", mDate);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDate = savedInstanceState.getLong("eventDate", Long.MAX_VALUE);
        updateDateDisplay();
    }

    /* Manage the selected date value */
    public void setDateSelection(long selectedTimestamp) {
        mDate = selectedTimestamp;
        updateDateDisplay();
    }

    /* Manage the selected date value */
    public void setTimeSelection(long selectedTimestamp) {
        mTime = selectedTimestamp;
        updateTimeDisplay();
    }

    public long getDateSelection() {
        return mDate;
    }

    public long getTimeSelection() {
        return mTime;
    }

    /* Click events on Due Date */
    @Override
    public void onClick(View v) {
        if (v == mSelectDate) {
            DatePickerFragment dialogFragment = new DatePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "datePicker");
        } else {
            TimePickerFragment dialogFragment = new TimePickerFragment();
            dialogFragment.show(getSupportFragmentManager(), "timePicker");
        }

    }

    /* Date set events from dialog */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //Set to noon on the selected day
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);

        setDateSelection(c.getTimeInMillis());
    }

    /* Time set events from dialog */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        setTimeSelection(c.getTimeInMillis());
    }

    private String formatDate(long timeStamp) {
        Date date = new Date(timeStamp);
        String dateToShow = dateFormat.format(date);
        return dateToShow;
    }

    private void updateDateDisplay() {
        if (getDateSelection() == Long.MAX_VALUE) {
            mDateView.setText("Not Set");
        } else {
            String date = formatDate(mDate);
            mDateView.setText(date);
        }
    }

    private void updateTimeDisplay() {
        if (getTimeSelection() == Long.MAX_VALUE) {
            mTimeView.setText("Not Set");
        } else {
            mTimeView.setText(timeFormat.format(mTime));
        }
    }

}
