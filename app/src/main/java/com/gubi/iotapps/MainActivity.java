package com.gubi.iotapps;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference dbTemp;
    private DatabaseReference dbHum;
    private DatabaseReference dbDist;

    private TextView tv_optTemperature;
    private TextView tv_optHumidity;
    private TextView tv_optDistance;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        dbTemp = FirebaseDatabase.getInstance("https://petimonitoring-default-rtdb.firebaseio.com/").getReference("Data/TemperatureValue");
        dbHum = FirebaseDatabase.getInstance("https://petimonitoring-default-rtdb.firebaseio.com/").getReference("Data/HumidityValue");
        dbDist = FirebaseDatabase.getInstance("https://petimonitoring-default-rtdb.firebaseio.com/").getReference("Data/DistanceValue");

        tv_optTemperature = findViewById(R.id.card1_tv_optTemperature);
        tv_optHumidity = findViewById(R.id.card2_tv_optHumidity);
        tv_optDistance = findViewById(R.id.card3_tv_optDistance);

        getTempData();
        getHumData();
        getDistData();
    }

    private void getTempData() {
        dbTemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                tv_optTemperature.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Temperature data Firebase Error");
            }
        });
    }

    private void getHumData() {
        dbHum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                tv_optHumidity.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Humidity data Firebase Error");
            }
        });
    }

    private void getDistData() {
        dbDist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                tv_optDistance.setText(value.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Distance data Firebase Error");
            }
        });
    }
}