package com.master.mipasapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.master.mipasapp.R;

public class MenuActivity extends AppCompatActivity {
    private Button buttonWebServiceAPI;
    private Button buttonSensorData;
    private Button buttonConfiguration;
    private Button buttonLogOut;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser myCurrentuser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonWebServiceAPI = findViewById(R.id.buttonWebServiceAPI);
        buttonSensorData = findViewById(R.id.buttonSensorData);
        buttonConfiguration = findViewById(R.id.buttonConfiguration);
        buttonLogOut = findViewById(R.id.buttonLogOut);

        Intent i=getIntent();
        myCurrentuser = (FirebaseUser) i.getSerializableExtra("CurrentFireBaseUser");
        signals();
    }

    private void signals() {
        buttonWebServiceAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(MenuActivity.this, WebApiActivity.class);
                startActivity(i);
            }
        });

        buttonSensorData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(MenuActivity.this, SensorDataActivity.class);
                i.putExtra("CurrentFireBaseUser", myCurrentuser);
                startActivity(i);
            }
        });

        buttonConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(MenuActivity.this, ConfigurationActivity.class);
                startActivity(i);
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                /**
                 * COnfigurar el LogOut para salir directamente
                 * */
                firebaseAuth.getInstance().signOut();
                Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}