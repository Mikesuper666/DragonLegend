package onuse.com.br.dragonlegend;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import onuse.com.br.dragonlegend.Helper.Auxiliadora;
import onuse.com.br.dragonlegend.StartActivityFragment.StartActivity;


public class FightActivity extends AppCompatActivity {

    private ImageView btnAtk, btnDef, btnFugir, espadaAldebaran, quadroImageLutaCenario, quadroImageInimigo;
    private TextView textVidaLuta, textVidaLutaInimigoVida, textoDescricaoLuta, textVidaLutaInimigoNome;
    private int chancesFugir, forcaAtk, saude, defesa, hpInimigo, hpInimigoInterno, cenario, bixoid;
    Animation animEspada;
    AnimationDrawable animacaoEfeitoEspada;
    private int posicaoMusica = 0;
    MediaPlayer mediaPlayer, somEspada, somCorrida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fight);

        btnAtk = (ImageView)findViewById(R.id.btnAtk);
        btnDef = (ImageView)findViewById(R.id.btnDef);
        btnFugir = (ImageView)findViewById(R.id.btnFugir);
        espadaAldebaran = (ImageView)findViewById(R.id.espadaAldebaran);
        quadroImageLutaCenario = (ImageView)findViewById(R.id.quadroImageLutaCenario);
        quadroImageInimigo = (ImageView)findViewById(R.id.quadroImageInimigo);

        textVidaLuta = (TextView)findViewById(R.id.textVidaLuta);
        textVidaLutaInimigoVida = (TextView)findViewById(R.id.textVidaLutaInimigoVida);
        textoDescricaoLuta = (TextView)findViewById(R.id.textoDescricaoLuta);
        textVidaLutaInimigoNome = (TextView)findViewById(R.id.textVidaLutaInimigoNome);

        animEspada = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.aldebaran_espada_mover);
        espadaAldebaran.startAnimation(animEspada);

        //Busca todas referencias da fase anterior via bundle
        Bundle b = new Bundle();
        b = getIntent().getExtras();
        if(b != null) {

            saude = b.getInt("Saude");
            bixoid = b.getInt("BixoId");
            chancesFugir = b.getInt("Fugir");
            forcaAtk = b.getInt("Atk");
            defesa = b.getInt("Defesa");
            hpInimigo = b.getInt("hpInimigo");
            cenario = b.getInt("Cenario");
        }
        hpInimigoInterno = hpInimigo;

        //Seta a música da fase
        somEspada = MediaPlayer.create(this, R.raw.music_sword);
        somCorrida = MediaPlayer.create(this, R.raw.music_running);
        if(bixoid == 7){
            mediaPlayer = MediaPlayer.create(this, R.raw.music_battle_boss);
        }else{
            mediaPlayer = MediaPlayer.create(this, R.raw.music_battle);
        }
        mediaPlayer.start();

        //seta a vida do inimigo atual
        textVidaLutaInimigoVida.setText(": "+hpInimigoInterno);
        //seta o nome do inimigo na label
        textVidaLutaInimigoNome.setText(Auxiliadora.NomeInimigo(bixoid));

        //Busca  o cenario do inimigo no array de cenarios do xml
        final TypedArray imgCenario = getResources().obtainTypedArray(R.array.cenario_batalha_images);
        quadroImageLutaCenario.setBackgroundResource(imgCenario.getResourceId(cenario, -1));

        TypedArray imgInimigo = getResources().obtainTypedArray(R.array.inimigos_batalha_images);
        quadroImageInimigo.setBackgroundResource(imgInimigo.getResourceId(bixoid, -1));


        textoDescricaoLuta.setText("O inimigo "+Auxiliadora.NomeInimigo(bixoid)+" está a sua frente!");
        textVidaLuta.setText("" + saude);

        btnAtk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                btnAtk.setEnabled(false);
                textoDescricaoLuta.setText("Você atacou o "+Auxiliadora.NomeInimigo(bixoid)+ "! Seu dano foi "+ forcaAtk);
                hpInimigoInterno -= forcaAtk;
                //seta a vida do inimigo atual
                textVidaLutaInimigoVida.setText(": "+hpInimigoInterno);

                final ImageView espadaAldebaranAnimacao = (ImageView)findViewById(R.id.espadaAldebaranAnimacao);
                espadaAldebaranAnimacao.setBackgroundResource(R.drawable.aldebaran_attack);
                espadaAldebaranAnimacao.setVisibility(View.VISIBLE);
                animacaoEfeitoEspada = (AnimationDrawable)espadaAldebaranAnimacao.getBackground();
                animacaoEfeitoEspada.setOneShot(true);
                animacaoEfeitoEspada.start();

                //Animação da rotação da espada
                espadaAldebaran.animate().rotation(90).setDuration(400);

                //Som espada
                int resetarSom = 0;
                somEspada.seekTo(resetarSom);
                somEspada.start();

                //faz mover-se no eixo Y e na duração designada
                quadroImageInimigo.animate().translationY(100).setDuration(500);
                //espera ate a animação acabar
                espadaAldebaranAnimacao.postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       espadaAldebaranAnimacao.setVisibility(View.GONE);
                       animacaoEfeitoEspada.stop();
                       animacaoEfeitoEspada.selectDrawable(0);
                       btnAtk.setEnabled(true);
                       textoDescricaoLuta.setText(Auxiliadora.NomeInimigo(bixoid)+" atacou você!");

                       //Retorno da rotação da espada
                       espadaAldebaran.animate().rotation(0).setDuration(400);
                       //Dano do inimigo
                       saude -= 3;
                       //Animação de dano do inimigo
                       quadroImageLutaCenario.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                       quadroImageLutaCenario.postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               quadroImageLutaCenario.setBackgroundResource(imgCenario.getResourceId(cenario, -1));
                           }
                       },50);

                       quadroImageInimigo.animate().translationY(0).setDuration(500);
                       ConfimarVitoria();
                   }
               }, 800);
            }
        });

        btnDef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDef.setEnabled(false);
                quadroImageInimigo.animate().translationY(200).setDuration(400);

                if(defesa > Auxiliadora.Sortear0a100()){
                    textoDescricaoLuta.setText("Você defendeu-se do "+Auxiliadora.NomeInimigo(bixoid));
                    saude -= 1;
                }else {
                    textoDescricaoLuta.setText("Sua defesa não foi eficiente ");
                    saude -= 3;
                }

                //espera ate a animação acabar
                quadroImageInimigo.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnDef.setEnabled(true);
                        textoDescricaoLuta.setText(Auxiliadora.NomeInimigo(bixoid)+" atacou você!");
                        quadroImageInimigo.animate().translationY(0).setDuration(400);

                        //Animação de dano do inimigo
                        quadroImageLutaCenario.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        quadroImageLutaCenario.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                quadroImageLutaCenario.setBackgroundResource(imgCenario.getResourceId(cenario, -1));
                            }
                        },50);
                    }
                }, 1000);
                ConfimarVitoria();
            }
        });

        btnFugir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Auxiliadora.Sortear0a100() < chancesFugir && bixoid != 7){
                    //Som espada
                    int resetarSom = 0;
                    somCorrida.seekTo(resetarSom);
                    somCorrida.start();

                    //faz mover-se no eixo Y e na duração designada
                    quadroImageInimigo.animate().translationY(-200).setDuration(2000);
                    textoDescricaoLuta.setText("Você fugiu do "+Auxiliadora.NomeInimigo(bixoid));
                    //espera ate a animação acabar
                    quadroImageInimigo.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ProximaFases();
                        }
                    }, 2000);
                }else {
                    GameOver();
                }
            }
        });
    }

    private void ConfimarVitoria(){
        textVidaLuta.setText("" + saude);
        if(saude <= 0){
            GameOver();
        }
        if(hpInimigoInterno <= 0){
            ProximaFases();
        }
    }//Confima se alguém morreu apos  o atk

    private void ProximaFases()
    {
        //startActivity(Auxiliadora.ProximaFases(this, bixoid, saudeEnviar));
        Intent intent = new Intent(FightActivity.this, Fases.class);
        intent.putExtra("Saude", saude);//Salva a quantidade de vida
        intent.putExtra("BixoId", (bixoid+1));//Salva a quantidade de vida
        intent.putExtra("hpInimigo", (hpInimigo+10));
        intent.putExtra("Fugir", (chancesFugir-5));
        startActivity(intent);
        finish();
    }//Chama a classe static e defini pela id qual cena será chamada

    private void GameOver(){
        Intent intent = new Intent(FightActivity.this, StartActivity.class);
        intent.putExtra("gameover", "gameover");
        startActivity(intent);
        finish();
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
