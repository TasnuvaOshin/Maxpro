package cricketworldcup.worldcup.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cricketworldcup.worldcup.Model.player_model;
import cricketworldcup.worldcup.R;
import cricketworldcup.worldcup.Util.ValidationProcess;
import cricketworldcup.worldcup.Viewholder.myteam_player_view_holder;


public class MyTeamFragment extends Fragment {


    private TextView teamonename, teamtwoname;
    private RecyclerView teamoneRecyclerview, teamtwoRecyclerview, rvshowmyteam;
    private String mParam1;
    private String mParam2, matchno, userid;
    private DatabaseReference databaseReferencePlayer, databaseReferenceplayerteamtwo, databaseReference;
    private FirebaseRecyclerOptions<player_model> options, options2;
    private FirebaseRecyclerAdapter<player_model, myteam_player_view_holder> adapter, adapter2;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReferenceTeamData, databaseReferenceShow;
    private RelativeLayout relativeLayout, myteamSelect, myTeamShow;
    private Button materialButton;
    private PreviewMyTeamFragment previewMyTeamFragment;
    int i = 0;
    List<String> players;
    private Button button,check;
    private DatabaseReference rootRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_team, container, false);
        String teamone = this.getArguments().getString("teamone");

        button = view.findViewById(R.id.btn_submit_my_team);
        firebaseAuth = FirebaseAuth.getInstance();
        players = new ArrayList<>();
        userid = firebaseAuth.getCurrentUser().getUid();
        checkCurrentStatus();

        relativeLayout = view.findViewById(R.id.root);
        check = view.findViewById(R.id.check_team);
        myteamSelect = view.findViewById(R.id.myteamselect); //main
        //myTeamShow = view.findViewById(R.id.myteamshow);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (players.size() > 5) {

                    if (i < 7) {
                        rootRef = FirebaseDatabase.getInstance().getReference().child("team_selection").child(firebaseAuth.getCurrentUser().getUid());
                        for(String friend : players) {
                            rootRef.push().child("player_name").setValue(friend);
                            }
                        button.setVisibility(View.GONE);
                        myteamSelect.setVisibility(View.GONE);
                        check.setVisibility(View.VISIBLE);
                        ValidationProcess.NotifyUser(relativeLayout,"Submitted");


                    } else {

                        ValidationProcess.NotifyUser(relativeLayout, "You canchose only six players");


                    }
                } else {

                    ValidationProcess.NotifyUser(relativeLayout, "You canchose only six players");

                }

            }


        });



        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to preview team

                previewMyTeamFragment = new PreviewMyTeamFragment();
                SetFragment(previewMyTeamFragment);
            }
        });


        /*
        databaseReferenceShow = FirebaseDatabase.getInstance().getReference().child("team_data").child("1").child(userid);


*/
        databaseReference = FirebaseDatabase.getInstance().getReference().child("live_quiz");
        databaseReference.child("matchno").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                matchno = String.valueOf(dataSnapshot.getValue());
                //  Toast.makeText(getActivity(), ""+matchno, Toast.LENGTH_LONG).show();
                databaseReferenceTeamData = FirebaseDatabase.getInstance().getReference().child("team_data").child(matchno);
                //CheckStatus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        teamonename = view.findViewById(R.id.team_one_name);
        teamtwoname = view.findViewById(R.id.team_two_name);

        String teamtwo = this.getArguments().getString("teamtwo");
        //  Toast.makeText(getActivity(), ""+teamone+" "+teamtwo, Toast.LENGTH_LONG).show();
        teamonename.setText(teamone);
        teamtwoname.setText(teamtwo);

        teamoneRecyclerview = view.findViewById(R.id.team_one_player);
        teamtwoRecyclerview = view.findViewById(R.id.team_two_player);
        teamtwoRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        teamoneRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseReferencePlayer = FirebaseDatabase.getInstance().getReference().child("squad").child(teamone);
        databaseReferenceplayerteamtwo = FirebaseDatabase.getInstance().getReference().child("squad").child(teamtwo);


        options2 = new FirebaseRecyclerOptions.Builder<player_model>().setQuery(databaseReferenceplayerteamtwo, player_model.class).build();

        options = new FirebaseRecyclerOptions.Builder<player_model>().setQuery(databaseReferencePlayer, player_model.class).build();

        adapter = new FirebaseRecyclerAdapter<player_model, myteam_player_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull myteam_player_view_holder holder, int position, @NonNull player_model model) {

                holder.checkBox.setText(model.getPlayer_name());

                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            i++;
                            if (players.size() < 6) {
                                players.add(model.getPlayer_name());
                                ValidationProcess.NotifyUser(relativeLayout,"You Have Selected : "+model.getPlayer_name());

                                // Toast.makeText(getActivity(), "you have selected" + model.getPlayer_name(), Toast.LENGTH_SHORT).show();

                            } else {


                               // Toast.makeText(getActivity(), "you cant select more than 6 players", Toast.LENGTH_SHORT).show();
                                //submit button show

                            }
                        } else {
                            i--;
                            players.remove(model.getPlayer_name());
                            //  Toast.makeText(getActivity(), ""+players.size(), Toast.LENGTH_SHORT).show();
                            //remove this from arry
                        }
                    }
                });


            }

            @NonNull
            @Override
            public myteam_player_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new myteam_player_view_holder(LayoutInflater.from(getActivity()).inflate(R.layout.my_team_player_row, viewGroup, false));
            }
        };


        adapter2 = new FirebaseRecyclerAdapter<player_model, myteam_player_view_holder>(options2) {
            @Override
            protected void onBindViewHolder(@NonNull myteam_player_view_holder holder, int position, @NonNull player_model model) {


                holder.checkBox.setText(model.getPlayer_name());

                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (b) {
                            i++;
                            if (players.size() < 6) {
                                players.add(model.getPlayer_name());
                                ValidationProcess.NotifyUser(relativeLayout,"You Have Selected : "+model.getPlayer_name());

                                //  Toast.makeText(getActivity(), "you have selected" + model.getPlayer_name(), Toast.LENGTH_SHORT).show();

                            } else {


                               // Toast.makeText(getActivity(), "you cant select more than 6 players", Toast.LENGTH_SHORT).show();
                                //submit button show

                            }
                        } else {
                            i--;
                            players.remove(model.getPlayer_name());
                            //    Toast.makeText(getActivity(), ""+players.size(), Toast.LENGTH_SHORT).show();
                            //remove this from arry
                        }
                    }
                });


            }

            @NonNull
            @Override
            public myteam_player_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new myteam_player_view_holder(LayoutInflater.from(getActivity()).inflate(R.layout.my_team_player_row, viewGroup, false));
            }
        };

        teamtwoRecyclerview.setAdapter(adapter2);
        teamoneRecyclerview.setAdapter(adapter);


        return view;

    }

    private void checkCurrentStatus() {


     DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("team_selection").child(firebaseAuth.getCurrentUser().getUid());


     db.addValueEventListener(new ValueEventListener() {
         @Override

         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             if(dataSnapshot.exists()){

                 button.setVisibility(View.GONE);
                 myteamSelect.setVisibility(View.GONE);
                 check.setVisibility(View.VISIBLE);






             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
    }


    /*
    private void CheckStatus() {


        databaseReferenceTeamData.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    if (dataSnapshot.getChildrenCount() < 6) {
                        myteamSelect.setVisibility(View.VISIBLE);

                    } else {
                        myteamSelect.setVisibility(View.GONE);
                        myTeamShow.setVisibility(View.VISIBLE);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    */

/*
    private void HandleThisPlayer(String player_name) {

        databaseReferenceTeamData.child(userid).orderByChild("player_name").equalTo(player_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //already selected
                    Toast.makeText(getActivity(), "Already Selected", Toast.LENGTH_SHORT).show();

                } else {


                    databaseReferenceTeamData.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int i;
                            i = (int) dataSnapshot.getChildrenCount();
                            if (i < 6) {

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("player_name", player_name);
                                databaseReferenceTeamData.child(userid).push().setValue(hashMap);

                                // Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();

                            } else {

                                //Toast.makeText(getActivity(), "You Have Already Selected 6 PLayers", Toast.LENGTH_SHORT).show();
                                ValidationProcess.NotifyUser(relativeLayout, "6 Player Already Selected");
                                myteamSelect.setVisibility(View.GONE);
                                myTeamShow.setVisibility(View.VISIBLE);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
*/

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        adapter2.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
        adapter2.startListening();


    }

    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();


    }
}
