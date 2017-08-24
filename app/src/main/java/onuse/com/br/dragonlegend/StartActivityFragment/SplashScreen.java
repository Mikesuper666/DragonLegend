package onuse.com.br.dragonlegend.StartActivityFragment;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;

import onuse.com.br.dragonlegend.R;

public class SplashScreen extends AppCompatActivity {

    Thread spalshsThread;
    AnimationDrawable animaLogo;

    public void onAttachedToWindow(){
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        StartAnimation();
    }

    private void StartAnimation(){
        //adicionar no manifest
        // android:largeHeap="true"
        ImageView imgIntro = (ImageView)findViewById(R.id.imgIntroOnuse);
        imgIntro.setBackgroundResource(R.drawable.on_use_anima);

        animaLogo = (AnimationDrawable)imgIntro.getBackground();
        animaLogo.setOneShot(true);
        animaLogo.start();

        spalshsThread = new Thread(){
            @Override
            public void run(){
                try{
                    int espere = 0;//define o tempo para zero
                    while(espere < 5500){
                        sleep(100);
                        espere += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this, StartActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    SplashScreen.this.finish();
                }catch (InterruptedException e){
                    //faz nada
                }finally {
                    SplashScreen.this.finish();
                }
            }
        };
        spalshsThread.start();
    }
}
