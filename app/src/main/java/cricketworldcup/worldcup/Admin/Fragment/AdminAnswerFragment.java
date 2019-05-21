package cricketworldcup.worldcup.Admin.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.Util.ValidationProcess;


public class AdminAnswerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private Button materialButton;
    private TextInputEditText et_ans_one, et_ans_two;
    private DatabaseReference databaseReference, databaseReferenceLiveAnswer;
private RelativeLayout relativeLayout;
    private OnFragmentInteractionListener mListener;

    public AdminAnswerFragment() {
    }

    public static AdminAnswerFragment newInstance(String param1, String param2) {
        AdminAnswerFragment fragment = new AdminAnswerFragment();
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
        View view = inflater.inflate(R.layout.fragment_admin_answer, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Quiz_Answer_data");
        databaseReferenceLiveAnswer = FirebaseDatabase.getInstance().getReference().child("live_quiz_answer");
        materialButton = view.findViewById(R.id.bt_ans_submit);
        relativeLayout = view.findViewById(R.id.root);
        et_ans_one = view.findViewById(R.id.et_ans_one);
        et_ans_two = view.findViewById(R.id.et_ans_two);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitAnswer();
            }
        });
        return view;

    }

    private void SubmitAnswer() {

        String ansone = String.valueOf(et_ans_one.getText());
        String anstwo = String.valueOf(et_ans_two.getText());

        if (TextUtils.isEmpty(ansone)) {
            et_ans_one.setError("Enter Question One Answer");
            et_ans_one.requestFocus();

        }
        if (TextUtils.isEmpty(anstwo)) {

            et_ans_two.setError("Enter Question Two Answer");


        } else {


            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("qus_one_ans", ansone);
            hashMap.put("qus_two_ans", anstwo);

            databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    databaseReferenceLiveAnswer.child("qus_one").setValue(ansone);
                    databaseReferenceLiveAnswer.child("qus_two").setValue(anstwo);
                    ValidationProcess.NotifyUser(relativeLayout,"Answer Submited");
                     ValidationProcess.clearForm(relativeLayout);
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
        void onFragmentInteraction(Uri uri);
    }
}
