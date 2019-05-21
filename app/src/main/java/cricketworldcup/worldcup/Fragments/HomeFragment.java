package cricketworldcup.worldcup.Fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cricketworldcup.worldcup.R;

public class HomeFragment extends Fragment {


    private Button match_quiz, my_team;
    private Bundle bundle;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView teamone,teamtwo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        my_team = view.findViewById(R.id.home_my_team);
        teamone = view.findViewById(R.id.between_team_one);
        teamtwo = view.findViewById(R.id.between_team_two);
        match_quiz = view.findViewById(R.id.home_match_quiz);
        bundle = new Bundle();
        my_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyTeamFragment myTeamFragment = new MyTeamFragment();
                bundle.putString("teamone", String.valueOf(teamone.getText()));
                bundle.putString("teamtwo", String.valueOf(teamtwo.getText()));
                myTeamFragment.setArguments(bundle);
                SetFragment(myTeamFragment);
            }
        });


        match_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MatchQuizFragment matchQuizFragment = new MatchQuizFragment();
                SetFragment(matchQuizFragment);
            }
        });
        return view;


    }


    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();


    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
