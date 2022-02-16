package com.portal.full.appmozoou;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class Start extends AppCompatActivity implements View.OnClickListener {

    //Defining views
    private EditText editTextName;
    private EditText etxtSerial;
    private EditText editTextSal;

    private Button btnStart;
    private Button buttonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);


        //Initializing views
        editTextName = (EditText) findViewById(R.id.editText);
        etxtSerial = (EditText) findViewById(R.id.etxtSerial);
        // editTextSal = (EditText) findViewById(R.id.editTextSalary);

        btnStart = (Button) findViewById(R.id.btnStart);
        //   buttonView = (Button) findViewById(R.id.buttonView);

        //Setting listeners to button
        btnStart.setOnClickListener(this);
        // buttonView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnStart) {
            TelephonyManager tManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            String phone_serial_number = (String) tManager.getDeviceId(); // permission --> READ_PHONE_STATE
            if (phone_serial_number.equals("9774d56d682e549c")) {
                // produce random_number_of_phone
                int min = 1;
                int max = 2100000000;
                Random r = new Random();
                int random_number_of_phone = r.nextInt(max - min + 1) + min;
                phone_serial_number = "random_" + String.valueOf(random_number_of_phone);
                etxtSerial.setText(phone_serial_number);
            }


        }
    }

}