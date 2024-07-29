package com.example.semproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.semproject.R;
import com.example.semproject.db.DatabaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rm.com.clocks.ClockImageView;

public class AlarmActivity extends AppCompatActivity {

    TextView textViewMedicineName, textViewTime;
    Vibrator mVibrator;
    Button buttonDismiss, buttonSnooze, buttonTake;
    List<String> timingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent intent = getIntent();

        ClockImageView clockImageView = findViewById(R.id.clock_alarm);
        Calendar mCurrentTime = Calendar.getInstance();
        clockImageView.animateToTime(mCurrentTime.get(Calendar.HOUR_OF_DAY), mCurrentTime.get(Calendar.MINUTE));

        String medicineName = intent.getStringExtra("medicineName");
        textViewMedicineName = findViewById(R.id.text_view_medicine_name_alarm);
        textViewMedicineName.setText(medicineName);

        textViewTime = findViewById(R.id.text_view_time_alarm);
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        textViewTime.setText(format.format(mCurrentTime.getTime()));


        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {0, 1000, 1000};
        mVibrator.vibrate(pattern, 0);


        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();

        /*DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<String> timingList = databaseHelper.getTimings(medicineName);
        for (int i = 0; i < timingList.size(); i++) {
            Log.i(AlarmActivity.class.getName(), timingList.get(i));
        }

        Calendar nextAlarmTime = Calendar.getInstance();
        nextAlarmTime.set(Calendar.SECOND, 0);
        nextAlarmTime.set(Calendar.MILLISECOND, 0);*/

        fetchTimingsFromFirebase(medicineName, mCurrentTime);

        buttonDismiss = findViewById(R.id.button_dismiss);
        buttonDismiss.setOnClickListener(v -> {
            setNextAlarm(medicineName, mCurrentTime);
            mVibrator.cancel();
            ringtone.stop();
            finish();
        });

        buttonSnooze = findViewById(R.id.button_snooze);
        buttonSnooze.setOnClickListener(v -> {
            Calendar nextAlarmTime = Calendar.getInstance();
            nextAlarmTime.setTimeInMillis(mCurrentTime.getTimeInMillis());
            nextAlarmTime.add(Calendar.MINUTE, 5);
            mVibrator.cancel();
            ringtone.stop();
            setAlarm(nextAlarmTime, medicineName);
            finish();
        });
    }

    private void fetchTimingsFromFirebase(String medicineName, Calendar mCurrentTime) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Medicines").child(uid);
            reference.orderByChild("name").equalTo(medicineName).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String timingListJson = snapshot.child("timingList").getValue(String.class);
                        try {
                            JSONObject json = new JSONObject(timingListJson);
                            JSONArray timingsArray = json.getJSONArray("timingArrays");
                            for (int i = 0; i < timingsArray.length(); i++) {
                                timingList.add(timingsArray.getString(i));
                            }
                            Log.i(AlarmActivity.class.getName(), timingList.toString());
                            setNextAlarm(medicineName, mCurrentTime);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("AlarmActivity", "Database error: " + databaseError.getMessage());
                }
            });
        }
    }

    private void setNextAlarm(String medicineName, Calendar mCurrentTime) {
        if (timingList.isEmpty()) {
            return;
        }

        Calendar nextAlarmTime = Calendar.getInstance();
        nextAlarmTime.set(Calendar.SECOND, 0);
        nextAlarmTime.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < timingList.size(); i++) {
            String[] time = timingList.get(i).split(":");
            nextAlarmTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
            nextAlarmTime.set(Calendar.MINUTE, Integer.parseInt(time[1]));
            if (mCurrentTime.before(nextAlarmTime)) {
                break;
            } else if (mCurrentTime.after(nextAlarmTime) && i == (timingList.size() - 1)) {
                nextAlarmTime.add(Calendar.DAY_OF_MONTH, 1);
                time = timingList.get(0).split(":");
                nextAlarmTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
                nextAlarmTime.set(Calendar.MINUTE, Integer.parseInt(time[1]));
            }
        }

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Log.i("daysLeft", String.valueOf(databaseHelper.noOfDaysLeft(medicineName, nextAlarmTime)));
        if (databaseHelper.noOfDaysLeft(medicineName, nextAlarmTime) > 0) {
            setAlarm(nextAlarmTime, medicineName);
        }
    }

    public void setAlarm(Calendar mAlarmTime, String medicineName) {
        Intent intent = new Intent(this, AlarmActivity.class);
        intent.putExtra("medicineName", medicineName);

        PendingIntent operation = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /** Getting a reference to the System Service ALARM_SERVICE */
        AlarmManager alarmManagerNew = (AlarmManager) getSystemService(ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManagerNew.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(), operation);
        } else
            alarmManagerNew.setExact(AlarmManager.RTC_WAKEUP, mAlarmTime.getTimeInMillis(), operation);

    }
}
