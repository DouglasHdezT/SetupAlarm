package com.debugprojects.setupalarm;

import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    //Definicion de variables

    private EditText editTextHours;
    private EditText editTextMinutes;
    private EditText editTextComment;
    private RadioButton radioButtonVibrator;
    private Button buttonProgramar;

    private int minutes_var = 0;
    private int hours_var = 0;
    private String comment_var="";
    private boolean vabrator_var= false;

    //Metodo onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllViews();

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            radioButtonVibrator.setChecked(false);
            radioButtonVibrator.setEnabled(false);
        }

        buttonProgramar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editTextComment.getText().toString().matches("")){
                    comment_var = editTextComment.getText().toString();
                }
                if(!editTextHours.getText().toString().matches("")){
                    hours_var = Integer.parseInt(editTextHours.getText().toString());
                }

                if(!editTextMinutes.getText().toString().matches("")){
                    minutes_var = Integer.parseInt(editTextMinutes.getText().toString());
                }

                vabrator_var = radioButtonVibrator.isChecked();

                setupAlarm(hours_var, minutes_var, comment_var, vabrator_var);
            }
        });

    }

    //Metodo de levantamiento de views
    private void getAllViews(){
        editTextHours = findViewById(R.id.editHours);
        editTextMinutes = findViewById(R.id.editMinutes);
        editTextComment = findViewById(R.id.editComment);
        radioButtonVibrator = findViewById(R.id.radioVibrate);
        buttonProgramar = findViewById(R.id.buttonProgramar);
    }

    //Intent para programar la alarma
    private void setupAlarm(int hours, int minutes, String comment, boolean vibrator){

        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);

        alarmIntent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, hours);
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, comment);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmIntent.putExtra(AlarmClock.EXTRA_VIBRATE, vibrator);
        }

        if(alarmIntent.resolveActivity(getPackageManager()) != null){
            startActivity(alarmIntent);
            editTextHours.setText("");
            editTextComment.setText("");
            editTextMinutes.setText("");
            radioButtonVibrator.setChecked(false);
        }


    }
}
