package cricketworldcup.worldcup.Admin;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import cricketworldcup.worldcup.Admin.Fragment.AdminQuizFragment;
import cricketworldcup.worldcup.Admin.Fragment.AdminTeamFragment;
import cricketworldcup.worldcup.R;

public class AdminActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private AdminQuizFragment adminQuizFragment;
    private AdminTeamFragment adminTeamFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin);
        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        adminQuizFragment = new AdminQuizFragment();
        adminTeamFragment = new AdminTeamFragment();
        SetFragment(adminQuizFragment);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.admin_quiz:
                        SetFragment(adminQuizFragment);
                        break;


                    case R.id.admin_team:
                        SetFragment(adminTeamFragment);
                        break;

                }


                return false;
            }
        });
    }

    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();


    }
}
