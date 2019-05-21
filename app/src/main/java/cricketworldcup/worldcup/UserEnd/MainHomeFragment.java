package cricketworldcup.worldcup.UserEnd;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cricketworldcup.worldcup.Fragments.MatchQuizFragment;
import cricketworldcup.worldcup.Fragments.MyTeamFragment;
import cricketworldcup.worldcup.R;


public class MainHomeFragment extends Fragment {

    private TextView teamone, teamtwo;
    private AppCompatButton quiz, myteam;
    private MatchQuizFragment matchQuizFragment;
    private Bundle bundle;
    private ImageView banner;
   private  MyTeamFragment myTeamFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
      myTeamFragment = new MyTeamFragment();
        matchQuizFragment = new MatchQuizFragment();
        teamone = view.findViewById(R.id.teamonename);
        teamtwo = view.findViewById(R.id.teamtwoname);
        quiz = view.findViewById(R.id.btn_matchQUiz);
        myteam = view.findViewById(R.id.btn_myteam);
        bundle = new Bundle();
        banner= view.findViewById(R.id.iv_banner_ads);
        ShowAds();



        myteam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putString("teamone", String.valueOf(teamone.getText()));
                bundle.putString("teamtwo", String.valueOf(teamtwo.getText()));
                myTeamFragment.setArguments(bundle);
                SetFragment(myTeamFragment);


            }
        });





        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                SetFragment(matchQuizFragment);


            }
        });

        return view;
    }

    private void ShowAds() {


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            private boolean useDiceOne;

            @Override
            public void run() {

                if (!useDiceOne) {
                    banner.setImageResource(R.drawable.maxpro);
                } else {
                    banner.setImageResource(R.drawable.cal);
                }
                useDiceOne = !useDiceOne;
                handler.postDelayed(this, 5000);
            }
        }, 5000);

    }

    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();


    }
}
