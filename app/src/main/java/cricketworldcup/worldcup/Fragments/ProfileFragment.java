package cricketworldcup.worldcup.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import cricketworldcup.worldcup.Authentication.LoginActivity;
import cricketworldcup.worldcup.R;


public class ProfileFragment extends Fragment {
    private ImageView banner;

    private FirebaseAuth firebaseAuth;
    private android.support.design.widget.FloatingActionButton fblogout, fbedit;
    private EditProfileFragment editProfileFragment;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editProfileFragment = new EditProfileFragment();
        fbedit = view.findViewById(R.id.fb_edit);
        firebaseAuth = FirebaseAuth.getInstance();
        banner= view.findViewById(R.id.iv_banner_ads);
        ShowAds();

        fbedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,editProfileFragment);
                fragmentTransaction.commit();



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

}
