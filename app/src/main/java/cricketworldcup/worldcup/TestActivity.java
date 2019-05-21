package cricketworldcup.worldcup;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cricketworldcup.worldcup.Model.player_model;
import cricketworldcup.worldcup.Viewholder.player_view_holder;

public class TestActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMyTeam;
    private FirebaseRecyclerOptions<player_model> options;
    private FirebaseRecyclerAdapter<player_model,player_view_holder> adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("team_data").child("1").child("6QcqwVi4KdbiZXD8rB2o5U1ky402");
        recyclerViewMyTeam = findViewById(R.id.recyclerView_MyTeamShow);

        recyclerViewMyTeam.setLayoutManager(new LinearLayoutManager(this));

        options = new FirebaseRecyclerOptions.Builder<player_model>().setQuery(databaseReference,player_model.class).build();

        adapter = new FirebaseRecyclerAdapter<player_model, player_view_holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull player_view_holder holder, int position, @NonNull player_model model) {
                holder.playername.setText(model.getPlayer_name());
            }

            @NonNull
            @Override
            public player_view_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new player_view_holder(LayoutInflater.from(TestActivity.this).inflate(R.layout.player,viewGroup,false));
            }
        };




recyclerViewMyTeam.setAdapter(adapter);

    }
}
