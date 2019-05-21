package cricketworldcup.worldcup.Admin.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import cricketworldcup.worldcup.Admin.Model.team_player;
import cricketworldcup.worldcup.Admin.ViewHolder.team_player_view_holder;
import cricketworldcup.worldcup.R;


public class ShowTeamOneFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView teamoneRecyclerView;
    private String mParam1;
    private String mParam2;
    private FirebaseRecyclerOptions<team_player> options;
    private FirebaseRecyclerAdapter<team_player, team_player_view_holder> adapter;
    private DatabaseReference databaseReference, databaseReferenceLive;
    private OnFragmentInteractionListener mListener;
    String value;
    DatabaseReference databaseRef;

    public ShowTeamOneFragment() {
        // Required empty public constructor
    }


    public static ShowTeamOneFragment newInstance(String param1, String param2) {
        ShowTeamOneFragment fragment = new ShowTeamOneFragment();
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


        View view = inflater.inflate(R.layout.fragment_show_team_one, container, false);
        String teamone = this.getArguments().getString("teamone");
        String matchno = this.getArguments().getString("matchno");

        Toast.makeText(getActivity(), ""+matchno, Toast.LENGTH_SHORT).show();
        teamoneRecyclerView = view.findViewById(R.id.teamone_recyclerview);
        teamoneRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        databaseReference = FirebaseDatabase.getInstance().getReference().child("squad").child(Objects.requireNonNull(teamone));

        options = new FirebaseRecyclerOptions.Builder<team_player>().setQuery(databaseReference, team_player.class).build();
        adapter = new FirebaseRecyclerAdapter<team_player, team_player_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull team_player_view_holder holder, int position, @NonNull team_player model) {

                holder.playername.setText(model.getPlayer_name());
            }

            @NonNull
            @Override
            public team_player_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new team_player_view_holder(LayoutInflater.from(getActivity()).inflate(R.layout.team_poin_row, viewGroup, false));
            }
        };



        teamoneRecyclerView.setAdapter(adapter);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
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
