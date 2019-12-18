package com.lucas.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cria a media
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.musica);
        inicializarSeekBar();

    }

    private void inicializarSeekBar(){

        seekVolume = findViewById(R.id.seekVolume);

        //configura o audio manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Recupera o volume maximo e atual;
        int volumeMAX = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int volumeAtual = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //configurar volume maximo da seekbar
        seekVolume.setMax(volumeMAX);

        //configura o volume atual
        seekVolume.setProgress(volumeAtual);

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i,
                        0);
                //Flag são as configurações adicionais quando se meche no volume
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void executaSom(View view){
        //verifica se existe midia
        if (mediaPlayer != null){
            //inicia a midia
            mediaPlayer.start();
        }
    }

    public void pausaSom(View view){
        if (mediaPlayer.isPlaying()){
            //pausa a musica
            mediaPlayer.pause();
        }
    }

    public void paraSom(View view){
        if (mediaPlayer.isPlaying()){
            //encerra e execução
            mediaPlayer.stop();
            //cria a media novamente por que
            //o .stop() destroi a musica tornando null
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.musica);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //verifica se existe ou esta executando
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop(); //para a musica
            mediaPlayer.release(); //libera recursos de memoria
            mediaPlayer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //quando sair do app pausa a musica
        if (mediaPlayer.isPlaying()){
            //pausa a musica
            mediaPlayer.pause();
        }
    }
}
