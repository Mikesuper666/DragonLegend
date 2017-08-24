package onuse.com.br.dragonlegend.StartActivityFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import onuse.com.br.dragonlegend.HistoriaInicial;
import onuse.com.br.dragonlegend.R;

/**
 * Created by maico on 01/08/17.
 */

public class StartActivitySecondFragment extends Fragment {
    private ImageButton btnPlay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infate o layout nessa view do fragment
        View view = inflater.inflate(R.layout.fragment_start_second, container, false);
        btnPlay = (ImageButton)view.findViewById(R.id.imagemTituloButton);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menuPrincipal = new Intent(getActivity(), HistoriaInicial.class);
                startActivity(menuPrincipal);
            }
        });

        return view;
    }
}
