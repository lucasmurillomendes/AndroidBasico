package com.example.progressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ProgressBar progressBarCarregando;
    private int progresso = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBarCarregando = findViewById(R.id.progressBarCarregando);

        progressBarCarregando.setVisibility(View.GONE);
    }

    public void carregar(View view){

        progressBarCarregando.setVisibility(View.VISIBLE);
        this.progresso = this.progresso + 10;
        progressBar.setProgress(this.progresso);

        //criando uma thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0; i<=100; i++){
                    final int progresso = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progresso);
                            if (progresso == 100){
                                progressBarCarregando.setVisibility(View.GONE);
                            }
                        }
                    });

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
