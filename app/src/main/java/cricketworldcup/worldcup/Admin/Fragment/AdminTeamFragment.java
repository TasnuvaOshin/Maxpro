package cricketworldcup.worldcup.Admin.Fragment;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import cricketworldcup.worldcup.Admin.Model.team_player;
import cricketworldcup.worldcup.Admin.ViewHolder.team_player_view_holder;
import cricketworldcup.worldcup.R;


public class AdminTeamFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private ScrollableNumberPicker numberPicker;
    private Button button;
    private Spinner spinner_team_one_select, spinner_team_two_select;
    private TextView tv_teamone, tv_teamtwo;
    private OnFragmentInteractionListener mListener;
    private Bundle bundle;
    private ShowTeamOneFragment showTeamOneFragment;
    private Button materialButton;


    public AdminTeamFragment() {

    }

    public static AdminTeamFragment newInstance(String param1, String param2) {
        AdminTeamFragment fragment = new AdminTeamFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_team, container, false);
        numberPicker = view.findViewById(R.id.numberPicker);
        button = view.findViewById(R.id.bt_numberPicker);
        spinner_team_one_select = view.findViewById(R.id.my_team_spinner_one);
        spinner_team_two_select = view.findViewById(R.id.my_team_spinner_two);
        tv_teamone = view.findViewById(R.id.tv_Team_one_name);
        tv_teamtwo = view.findViewById(R.id.tv_Team_two_name);
        showTeamOneFragment = new ShowTeamOneFragment();
        materialButton = view.findViewById(R.id.teamonebtn);

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SetFragment(showTeamOneFragment);
            }
        });

        bundle = new Bundle();
        ShowteamOne();
        ShowteamTwo();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "" + numberPicker.getValue(), Toast.LENGTH_SHORT).show();

                bundle.putString("matchno", String.valueOf(numberPicker.getValue()));
            }
        });

        return view;
    }

    private void ShowteamTwo() {

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.team_list, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_team_two_select.setAdapter(arrayAdapter);
        spinner_team_two_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                tv_teamtwo.setText(adapterView.getItemAtPosition(i).toString());
                //    questionInfo.put("question_four", adapterView.getItemAtPosition(i).toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void ShowteamOne() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.team_list, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_team_one_select.setAdapter(arrayAdapter);
        spinner_team_one_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                tv_teamone.setText(adapterView.getItemAtPosition(i).toString());
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Admin_Live");
                db.child("teamone").setValue(adapterView.getItemAtPosition(i).toString());
                bundle.putString("teamone",adapterView.getItemAtPosition(i).toString());
                showTeamOneFragment.setArguments(bundle);
              /*
                bundle.putString("teamone",adapterView.getItemAtPosition(i).toString());
                showTeamOneFragment.setArguments(bundle);
                SetFragment(showTeamOneFragment);
                */
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }





    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();


    }
}
