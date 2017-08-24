package onuse.com.br.dragonlegend.StartActivityFragment;

import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import java.io.IOException;

import onuse.com.br.dragonlegend.R;
import onuse.com.br.dragonlegend.StartActivityFragment.StartActivittyFragment;
import onuse.com.br.dragonlegend.StartActivityFragment.StartActivitySecondFragment;
import onuse.com.br.dragonlegend.WinOver.GameOverFragment;

public class StartActivity extends AppCompatActivity{
    private int posicaoMusica = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        if(b != null){
            String name = b.getString("gameover");
            GameOver();
        }else{
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.conteinerTitulo, new StartActivittyFragment(), "StartActivittyFragment");

            //transaction.add(R.id.conteinerTitulo, new GameOverFragment(), "GameOverFragment");
            transaction.add(R.id.conteinerTitulo, new StartActivitySecondFragment(), "StartActivitySecondFragment");
            transaction.commit();

            //Seta a musica do menu inicial
            mediaPlayer = MediaPlayer.create(this, R.raw.music_main_menu);
            mediaPlayer.start();
        }
    }


    public void GameOver(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.conteinerTitulo, new StartActivittyFragment(), "StartActivittyFragment");
        transaction.add(R.id.conteinerTitulo, new GameOverFragment(), "GameOverFragment");
        transaction.commit();

        //Seta a musica do gameover
        mediaPlayer = MediaPlayer.create(this, R.raw.music_gameover);
        mediaPlayer.start();
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
