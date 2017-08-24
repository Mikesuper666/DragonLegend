package onuse.com.br.dragonlegend.WinOver;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import onuse.com.br.dragonlegend.R;
import onuse.com.br.dragonlegend.StartActivityFragment.StartActivity;

public class GameOverFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infate o layout nessa view do fragment
        View view = inflater.inflate(R.layout.fragment_game_over, container, false);

        ImageView imagemGameOver = (ImageView)view.findViewById(R.id.imagemGameOver);

        imagemGameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StartActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
