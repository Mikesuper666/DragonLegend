package onuse.com.br.dragonlegend;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by maico on 02/08/17.
 */

public class HistoriaInicial extends AppCompatActivity {
    String[] historiaText;
    private TextView historia;
    private ImageView btnHistoriaProx, quadroHistoriaImagem;
    private int numeroTexto = 0;
    private TypedArray img;
    private int posicaoMusica = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_inicial);
        historia = (TextView)findViewById(R.id.textoHostoriaInicial);
        btnHistoriaProx = (ImageView)findViewById(R.id.btnHistoriaProx);
        quadroHistoriaImagem = (ImageView)findViewById(R.id.imageHistoria);

        //Seta a musica do menu inicial
        mediaPlayer = MediaPlayer.create(this, R.raw.music_theme);
        mediaPlayer.start();

        //Pega as strings do XML
        historiaText = getResources().getStringArray(R.array.historia);
        //Seta o primeiro texto na primeira posição das strings do xml
        historia.setText(historiaText[numeroTexto]);

        img = getResources().obtainTypedArray(R.array.historia_images);

        quadroHistoriaImagem.setBackgroundResource(img.getResourceId(numeroTexto, -1));
        btnHistoriaProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroTexto += 1;
                ProximoTexto(numeroTexto);
            }
        });
    }

    private void ProximoTexto(int numeroTexto){
        if(numeroTexto < historiaText.length){
            historia.setText(historiaText[numeroTexto]);
            quadroHistoriaImagem.setBackgroundResource(img.getResourceId(numeroTexto, -1));
        }else{
            Intent intent = new Intent(HistoriaInicial.this, Fases.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Sair do jogo");
        alertDialog.setMessage("Você tem certeza que deseja sair?");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
                return;
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    private void PararMusica(){
        mediaPlayer.stop();
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void IniciarMusica(){
        mediaPlayer.seekTo(posicaoMusica);
        mediaPlayer.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        PararMusica();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IniciarMusica();
    }

    @Override
    protected void onStop() {
        super.onStop();
        PararMusica();
    }
}