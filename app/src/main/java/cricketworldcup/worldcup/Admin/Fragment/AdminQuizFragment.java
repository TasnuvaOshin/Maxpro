package cricketworldcup.worldcup.Admin.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cricketworldcup.worldcup.Admin.Model.Quiz_Data;
import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.Util.ValidationProcess;


public class AdminQuizFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextInputEditText et_ques_one, et_ques_two, et_ques_one_op_one, et_ques_one_op_two, et_ques_two_op_one, et_ques_two_op_two,et_match_no;
    private Button materialButton,answer_btn;
    private String qus_one, qus_two, qus_one_op_one, qus_one_op_two, qus_two_op_one, qus_two_op_two,matchno;
    private String mParam1;
    private String mParam2;
    private DatabaseReference databaseReference, databaseReferenceLive;

    private OnFragmentInteractionListener mListener;

    public AdminQuizFragment() {

    }


    public static AdminQuizFragment newInstance(String param1, String param2) {
        AdminQuizFragment fragment = new AdminQuizFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_quiz, container, false);
        et_ques_one = view.findViewById(R.id.et_qus_one);
        et_ques_two = view.findViewById(R.id.et_qus_two);
        et_ques_one_op_one = view.findViewById(R.id.et_qus_one_op_one);
        et_ques_one_op_two = view.findViewById(R.id.et_qus_one_op_two);
        et_ques_two_op_one = view.findViewById(R.id.et_qus_two_op_one);
        et_ques_two_op_two = view.findViewById(R.id.et_qus_two_op_two);
        et_match_no = view.findViewById(R.id.et_match_no);
        answer_btn = view.findViewById(R.id.bt_submit_ans);
        ValidationProcess.disableEditText(et_ques_one);
        materialButton = view.findViewById(R.id.bt_submit_qus);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Quiz_data");
        databaseReferenceLive = FirebaseDatabase.getInstance().getReference().child("live_quiz");
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitQuestion();
            }
        });

        answer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminAnswerFragment adminAnswerFragment = new AdminAnswerFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,adminAnswerFragment);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void SubmitQuestion() {

        qus_one = (String) et_ques_one.getHint();
        Toast.makeText(getActivity(), "" + qus_one, Toast.LENGTH_LONG).show();
       if(TextUtils.isEmpty(et_match_no.getText())){
           et_match_no.requestFocus();
           et_match_no.setError("Enter Match No");

       }

        if (TextUtils.isEmpty(et_ques_one_op_one.getText())) {

            et_ques_one_op_one.requestFocus();
            et_ques_one_op_one.setError("enter Option One");
        }

        if (TextUtils.isEmpty(et_ques_one_op_two.getText())) {

            et_ques_one_op_two.requestFocus();
            et_ques_one_op_two.setError("enter Option Two");
        }


        if (TextUtils.isEmpty(et_ques_two.getText())) {

            et_ques_two.requestFocus();
            et_ques_two.setError("enter Question Two");
        }


        if (TextUtils.isEmpty(et_ques_two_op_one.getText())) {

            et_ques_two_op_one.requestFocus();
            et_ques_two_op_one.setError("enter Option One");
        }
        if (TextUtils.isEmpty(et_ques_two_op_two.getText())) {

            et_ques_two_op_two.requestFocus();
            et_ques_two_op_two.setError("enter Option Two");
        } else {
            //store
            matchno = String.valueOf(et_match_no.getText());
            qus_one = (String) et_ques_one.getHint();
            qus_two = String.valueOf(et_ques_two.getText());
            qus_one_op_one = String.valueOf(et_ques_one_op_one.getText());
            qus_one_op_two = String.valueOf(et_ques_one_op_two.getText());
            qus_two_op_one = String.valueOf(et_ques_two_op_one.getText());
            qus_two_op_two = String.valueOf(et_ques_two_op_two.getText());
            Quiz_Data quiz_data = new Quiz_Data(qus_one, qus_two, qus_one_op_one, qus_one_op_two, qus_two_op_one, qus_two_op_two,matchno);
            databaseReference.push().setValue(quiz_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    databaseReferenceLive.child("qus_one").child("qus").setValue(qus_one);
                    databaseReferenceLive.child("qus_one").child("opt_one").setValue(qus_one_op_one);
                    databaseReferenceLive.child("qus_one").child("opt_two").setValue(qus_one_op_two);
                    databaseReferenceLive.child("qus_two").child("qus").setValue(qus_two);
                    databaseReferenceLive.child("qus_two").child("opt_one").setValue(qus_two_op_one);
                    databaseReferenceLive.child("qus_two").child("opt_two").setValue(qus_two_op_two);
                    databaseReferenceLive.child("matchno").setValue(matchno);


                }
            });
        }

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
