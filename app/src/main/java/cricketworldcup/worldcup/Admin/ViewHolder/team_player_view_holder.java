package cricketworldcup.worldcup.Admin.ViewHolder;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cricketworldcup.worldcup.R;

public class team_player_view_holder extends RecyclerView.ViewHolder{
    public TextView playername;
    public TextInputEditText Prun,Pwicket;
    public Button save;
    public team_player_view_holder(@NonNull View itemView) {
        super(itemView);
        playername = itemView.findViewById(R.id.tv_point_name);
        Prun = itemView.findViewById(R.id.ti_run);
        Pwicket = itemView.findViewById(R.id.ti_wi);
        save = itemView.findViewById(R.id.bt_Save);
    }
}
