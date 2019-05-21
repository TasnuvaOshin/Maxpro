package cricketworldcup.worldcup.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import cricketworldcup.worldcup.R;


public class QuizWinnerFragment extends Fragment {
    private Spinner spinner;
    private TextView textView;
    private Button materialButton;
    private MyTeamWinnerFragment myTeamWinnerFragment;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_winner, container, false);

        spinner = view.findViewById(R.id.my_team_spinner);
        myTeamWinnerFragment = new MyTeamWinnerFragment();
        textView = view.findViewById(R.id.winner_textview);
        WinnerShowSpinner();
        materialButton = view.findViewById(R.id.bt_team_winner);
        // Inflate the layout for this fragment
            materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout,myTeamWinnerFragment);
                    fragmentTransaction.commit();
                }
            });
         return view;
    }

    private void WinnerShowSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.match_winner, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                textView.setText("1.Rahat " + "\n" +
                        "2.Jamil" + "\n" + "3.kawsar" + "\n" + "4.Rahim" + "\n" + "5.Karim");
                //    questionInfo.put("question_four", adapterView.getItemAtPosition(i).toString());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }


}
