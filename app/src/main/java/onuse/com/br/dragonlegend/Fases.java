package onuse.com.br.dragonlegend;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import onuse.com.br.dragonlegend.Helper.Auxiliadora;
import onuse.com.br.dragonlegend.StartActivityFragment.StartActivity;

public class Fases extends AppCompatActivity {
    String[] falasText;
    private TextView falas, textVida;
    private ImageView btnFaseProx, quadroImageFaseUm;
    private int numeroTexto = 0;
    private int Saude = 100;
    private int faseAtual, hpInimigo, chancesFugir;
    private int posicaoMusica = 0;
    private TypedArray img;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fase);
        falas = (TextView)findViewById(R.id.textoFalas);
        textVida = (TextView)findViewById(R.id.textVida);
        btnFaseProx = (ImageView)findViewById(R.id.btnFaseProx);
        quadroImageFaseUm = (ImageView)findViewById(R.id.quadroImageFaseUm);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        if(b != null) {
            Saude = b.getInt("Saude");
            faseAtual = b.getInt("BixoId");
            hpInimigo = b.getInt("hpInimigo");
            chancesFugir = b.getInt("Fugir");
        }else{
            faseAtual = 0;
            hpInimigo = 10;
            chancesFugir = 100;
        }

        //Seta a musica do cenário
        if (faseAtual == 8) {
            mediaPlayer = MediaPlayer.create(this, R.raw.music_ending);
        }else{
            mediaPlayer = MediaPlayer.create(this, R.raw.music_theme);
        }
        mediaPlayer.start();

        //Pega as strings do XML
        //falasText = getResources().getStringArray(R.array.fase_um);
        falasText = Auxiliadora.FaseFalasAtual(this, faseAtual);

        //Seta o primeiro texto na primeira posição das strings do xml
        falas.setText(falasText[numeroTexto]);

        //img = getResources().obtainTypedArray(R.array.fase_um_images);
        img = Auxiliadora.FaseAtual(this, faseAtual);

        quadroImageFaseUm.setBackgroundResource(img.getResourceId(numeroTexto, -1));
        btnFaseProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroTexto += 1;
                ProximoTexto(numeroTexto);
            }
        });

        textVida.setText(Saude+"");
    }

    private void ProximoTexto(int numeroTexto){
        if(numeroTexto < falasText.length){
            falas.setText(falasText[numeroTexto]);
            quadroImageFaseUm.setBackgroundResource(img.getResourceId(numeroTexto, -1));
        }else {

            if (faseAtual != 8) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Inimigo");
                alertDialog.setMessage("O inimigo aproximou-se de você, oque deseja fazer?");
                alertDialog.setPositiveButton("Lutar!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Fases.this, FightActivity.class);
                        intent.putExtra("Saude", Saude);
                        intent.putExtra("BixoId", faseAtual);
                        intent.putExtra("Fugir", 95);
                        intent.putExtra("Atk", 10);
                        intent.putExtra("Defesa", 90);
                        intent.putExtra("hpInimigo", hpInimigo);
                        intent.putExtra("Cenario", faseAtual);
                        startActivity(intent);
                        finish();
                    }
                });
                alertDialog.setNegativeButton("Fugir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(faseAtual == 7 || (Auxiliadora.Sortear0a100() > 50 && faseAtual == 3)){
                            GameOver();
                        }else{
                            ProximaFases();
                        }
                    }
                });

                AlertDialog alert = alertDialog.create();
                alert.show();
            } else {
                Intent intent = new Intent(Fases.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
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

    private void ProximaFases()
    {
        //startActivity(Auxiliadora.ProximaFases(this, bixoid, saudeEnviar));
        Intent intent = new Intent(Fases.this, Fases.class);
        intent.putExtra("Saude", Saude);//Salva a quantidade de vida
        intent.putExtra("BixoId", (faseAtual+1));//Salva a quantidade de vida
        intent.putExtra("hpInimigo", (hpInimigo+10));
        intent.putExtra("Fugir", (chancesFugir)                                                                                                                                                 );
        startActivity(intent);
        finish();
    }//Chama a classe static e defini pela id qual cena será chamada

    private void GameOver(){
        Intent intent = new Intent(Fases.this, StartActivity.class);
        intent.putExtra("gameover", "gameover");
        startActivity(intent);
        finish();
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
