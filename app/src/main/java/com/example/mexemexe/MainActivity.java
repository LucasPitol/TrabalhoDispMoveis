package com.example.mexemexe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private ImageView startBtn;
    private ImageView stopBtn;
    private ImageView cleanBtn;
    private ImageView gambiSpace;
    private TextView texto;

    private TextView xPosition;
    private TextView yPosition;
    private TextView zPosition;

    private Sensor sensor;
    private SensorManager gerenciadorMovimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();

        startSensor();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.VISIBLE);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBtn.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    public void startSensor() {
        gerenciadorMovimento = (SensorManager)getSystemService(SENSOR_SERVICE);

        sensor = gerenciadorMovimento.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        gerenciadorMovimento.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void setUI() {
        startBtn = findViewById(R.id.start_btn);
        stopBtn = findViewById(R.id.stop_btn);
        cleanBtn = findViewById(R.id.clean_icon);
        gambiSpace = findViewById(R.id.gambi);
        texto = findViewById(R.id.texto);

        xPosition = findViewById(R.id.xPosition);
        yPosition = findViewById(R.id.yPosition);
        zPosition = findViewById(R.id.zPosition);

        stopBtn.setVisibility(View.GONE);
        cleanBtn.setVisibility(View.GONE);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xPosition.setText("X: " + event.values[0]);
        yPosition.setText("Y: " + event.values[1]);
        zPosition.setText("Z: " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Não utilizar
    }
}
