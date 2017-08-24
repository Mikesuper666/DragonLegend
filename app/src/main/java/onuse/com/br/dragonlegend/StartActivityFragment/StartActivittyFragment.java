package onuse.com.br.dragonlegend.StartActivityFragment;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import onuse.com.br.dragonlegend.R;

public class StartActivittyFragment extends Fragment implements SurfaceHolder.Callback{
    private MediaPlayer mp = null;
    SurfaceView mSurfaceView=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infate o layout nessa view do fragment
        View view = inflater.inflate(R.layout.fragment_start_activitty, container, false);


        mSurfaceView = (SurfaceView) view.findViewById(R.id.videoIncial);
        mSurfaceView.getHolder().addCallback(this);
        mSurfaceView.setZOrderOnTop(false);
        return view;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mp = new MediaPlayer();
        AssetFileDescriptor arquivoVideo = getResources().openRawResourceFd(R.raw.fire);
        try {
            mp.setDataSource(arquivoVideo.getFileDescriptor(), arquivoVideo.getStartOffset(), arquivoVideo.getDeclaredLength());
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Pega as dimensões da tela
        int LarguraDaTela = mSurfaceView.getWidth(); //100;// getWindowManager().getDefaultDisplay().getWidth();
        int AlturaDaTela = mSurfaceView.getHeight();//100; //getWindowManager().getDefaultDisplay().getHeight();

        //Pega os parametros de layout do surfaceView
        android.view.ViewGroup.LayoutParams lp = mSurfaceView.getLayoutParams();

        //Seta a altura e largura da tela para o surfaceview
        lp.width = LarguraDaTela;
        lp.height = AlturaDaTela;

        //Comit os parametros do layout para surface
        mSurfaceView.setLayoutParams(lp);

        //Inicia o video
        mp.setDisplay(holder);
        mp.setLooping(true);
        mp.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mp = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mp != null) {
            mp.release();
            mp = null;
        }
    }
}
