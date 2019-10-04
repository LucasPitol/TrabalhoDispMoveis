package com.example.mexemexe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private ImageView startBtn;
    private ImageView stopBtn;
    private ImageView cleanBtn;
    private ImageView gambiSpace;
    private ListView listView;

//    private TextView xPosition;
//    private TextView yPosition;
//    private TextView zPosition;

    private Sensor sensor;
    private SensorManager gerenciadorMovimento;

    private String posicaoAtual = "";
    private String posicaoAnterior = "";

    private boolean funfando = false;

    private ArrayList<Mapa> listaMovimentos;
    ArrayAdapter adapter;

    private GerenteBanco gerenteBanco;

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
                funfando = true;
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopBtn.setVisibility(View.GONE);
                startBtn.setVisibility(View.VISIBLE);
                funfando = false;
            }
        });

        cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaMovimentos.clear();
                gerenteBanco.limparDados();
                adapter.notifyDataSetChanged();
                trocaBotaoLimpar();
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

//        xPosition = findViewById(R.id.xPosition);
//        yPosition = findViewById(R.id.yPosition);
//        zPosition = findViewById(R.id.zPosition);

        listView = findViewById(R.id.list_view);

        listaMovimentos = new ArrayList<Mapa>();
        adapter = new ArrayAdapter<Mapa>(this,
                android.R.layout.simple_list_item_1,
                listaMovimentos);

        listView.setAdapter(adapter);

        gerenteBanco = new GerenteBanco(this);

        stopBtn.setVisibility(View.GONE);

        gerenteBanco.listarMapas(listaMovimentos);
        trocaBotaoLimpar();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (funfando) {

            float x =  event.values[0];
            float y =  event.values[1];
            //float z =  event.values[2];

//            xPosition.setText("X: " + x);
//            yPosition.setText("Y: " + y);
//            zPosition.setText("Z: " + z);

            boolean posicaoEnchada = ((x > -3.00f) && (x < 3.00f));

            boolean posicaoVolante = ((y > 6.00f) && (y < 10.00f));

            if (posicaoEnchada && !posicaoVolante) {

                if ((y > 0.00f) && (y < 3.00f)) {
                    posicaoAtual = "Deitou";

                } else if ((y > -10.0f) && (y < 0.00f)) {
                    posicaoAtual = "Cabeça p baixo";

                }

            }

            if (posicaoVolante && !posicaoEnchada) {
                if ((x > 3.00f) && (x < 10.0f)) {
                    posicaoAtual = "Esquerdou";
                } else if ((x > -10.0f) && (x < -3.00f)) {
                    posicaoAtual = "Direitou";
                }
            }

            if (posicaoEnchada && posicaoVolante) {
                posicaoAtual = "Em pé";
            }

            if (!posicaoAtual.equals(posicaoAnterior)) {

                Mapa mapa = new Mapa(posicaoAtual);

                //listaMovimentos.add(mapa);
                gerenteBanco.insereMapa(mapa);
                this.posicaoAnterior = posicaoAtual;
                gerenteBanco.listarMapas(listaMovimentos);

                trocaBotaoLimpar();

                adapter.notifyDataSetChanged();
            }
        }

    }

    public void trocaBotaoLimpar() {

        if (!listaMovimentos.isEmpty()) {
            this.cleanBtn.setVisibility(View.VISIBLE);
            this.gambiSpace.setVisibility(View.GONE);
        } else {
            this.cleanBtn.setVisibility(View.GONE);
            this.gambiSpace.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Não utilizar
    }
}
