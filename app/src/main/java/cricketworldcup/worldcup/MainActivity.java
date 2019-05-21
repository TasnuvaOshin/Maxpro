package cricketworldcup.worldcup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Set;

import cricketworldcup.worldcup.API_CALL.Api_Client;
import cricketworldcup.worldcup.API_CALL.Api_Connection;
import cricketworldcup.worldcup.Authentication.LoginActivity;
import cricketworldcup.worldcup.Fragments.EditProfileFragment;
import cricketworldcup.worldcup.Fragments.FixtureFragment;
import cricketworldcup.worldcup.Fragments.HomeFragment;
import cricketworldcup.worldcup.Fragments.LiveTelecastFragment;
import cricketworldcup.worldcup.Fragments.MatchQuizFragment;
import cricketworldcup.worldcup.Fragments.MyTeamWinnerFragment;
import cricketworldcup.worldcup.Fragments.PreviewMyTeamFragment;
import cricketworldcup.worldcup.Fragments.ProfileFragment;
import cricketworldcup.worldcup.Fragments.QuizWinnerFragment;
import cricketworldcup.worldcup.Fragments.TeamFragment;
import cricketworldcup.worldcup.Fragments.WinnerFragment;
import cricketworldcup.worldcup.UserEnd.HIstory.HistoryFragment;
import cricketworldcup.worldcup.UserEnd.Live.LiveFragment;
import cricketworldcup.worldcup.UserEnd.MainHomeFragment;
import cricketworldcup.worldcup.UserEnd.Standings.StandingFragment;
import cricketworldcup.worldcup.UserEnd.myQUizScore.MyQuizScoreFragment;
import cricketworldcup.worldcup.UserEnd.myteamScore.MyTeamScoreFragment;
import cricketworldcup.worldcup.UserEnd.prizes.PrizeFragment;

public class MainActivity extends AppCompatActivity {
    private static final String Tag = "World Cup Cricket";
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private FixtureFragment fixtureFragment;
    private MainHomeFragment mainHomeFragment;
    private TeamFragment teamFragment;
    private WinnerFragment winnerFragment;
    private ProfileFragment profileFragment;
    private LiveTelecastFragment liveTelecastFragment;
    private ProgressDialog progressDialog;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LiveFragment liveFragment;
    private MatchQuizFragment matchQuizFragment;
    private FirebaseAuth firebaseAuth;
    private HistoryFragment historyFragment;
    private StandingFragment standingFragment;
    private PrizeFragment prizeFragment;
    private PreviewMyTeamFragment previewMyTeamFragment;
    private QuizWinnerFragment quizWinnerFragment;
    private MyTeamWinnerFragment myTeamWinnerFragment;
    private MyTeamScoreFragment myTeamScoreFragment;
    private MyQuizScoreFragment myQuizScoreFragment;
    private EditProfileFragment editProfileFragment;
    // private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frame_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        liveFragment = new LiveFragment();
        prizeFragment = new PrizeFragment();
        myTeamScoreFragment = new MyTeamScoreFragment();
        firebaseAuth = FirebaseAuth.getInstance();
        matchQuizFragment = new MatchQuizFragment();
        historyFragment = new HistoryFragment();
        editProfileFragment = new EditProfileFragment();
        quizWinnerFragment = new QuizWinnerFragment();
        previewMyTeamFragment = new PreviewMyTeamFragment();
        myQuizScoreFragment = new MyQuizScoreFragment();
        SetUpSideDrawer();
        /*
        imageView = findViewById(R.id.imageView);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            private boolean useDiceOne;

            @Override
            public void run() {

                if (!useDiceOne) {
                    imageView.setImageResource(R.drawable.a1);
                } else {
                    imageView.setImageResource(R.drawable.a2);
                }
                useDiceOne = !useDiceOne;
                handler.postDelayed(this, 3000);
            }
        }, 3000);

*/
        mainHomeFragment = new MainHomeFragment();
        standingFragment = new StandingFragment();
        myTeamWinnerFragment = new MyTeamWinnerFragment();
        SetFragment(mainHomeFragment);
        homeFragment = new HomeFragment();
        fixtureFragment = new FixtureFragment();
        teamFragment = new TeamFragment();
        winnerFragment = new WinnerFragment();
        profileFragment = new ProfileFragment();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("No Internet Connection !");
        progressDialog.show();
        SetUpSideBar();
        progressDialog.setCanceledOnTouchOutside(true);
        liveTelecastFragment = new LiveTelecastFragment();
        if (isNetworkAvailable()) {
            progressDialog.dismiss();
            SetFragment(mainHomeFragment);
            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {

                        case R.id.home:
                            startActivity(new Intent(MainActivity.this, MainActivity.class));
                            MainActivity.this.overridePendingTransition(0, 0);
                            return true;

                        case R.id.fixture:
                            SetFragment(fixtureFragment);
                            return true;

                        case R.id.team_profile:
                            SetFragment(teamFragment);
                            return true;

                        case R.id.user_profile:
                            SetFragment(editProfileFragment);
                            return true;

                        case R.id.live:
                            SetFragment(liveFragment);
                            return true;


                    }
                    return false;
                }
            });
        } else {


            progressDialog.show();


        }
        Api_Client myclient = Api_Connection.GetInstance(Api_Client.class);
   /*
        Call<List<My_data_Set>> datalist = myclient.getTestData();
        datalist.enqueue(new Callback<List<My_data_Set>>() {
            @Override
            public void onResponse(Call<List<My_data_Set>> call, Response<List<My_data_Set>> response) {
                Log.d(Tag,"We are getting response");
                Log.d(Tag, String.valueOf(response.body()));

            }

            @Override
            public void onFailure(Call<List<My_data_Set>> call, Throwable t) {
          Log.d(Tag,"it fails");
            }
        });

        */
    }

    private void SetUpSideBar() {


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {


                    case R.id.home_home:
                        SetFragment(mainHomeFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_live:
                        SetFragment(liveFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_fixture:
                        SetFragment(fixtureFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_team:
                        SetFragment(teamFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_profile:
                        SetFragment(editProfileFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_quiz:
                        SetFragment(matchQuizFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_history:
                        SetFragment(historyFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_logout:
                        logMeout();
                        break;

                    case R.id.home_standing:
                        SetFragment(standingFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_prize:
                        SetFragment(prizeFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_my_team:
                        SetFragment(previewMyTeamFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_quiz_winner:
                        SetFragment(quizWinnerFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_my_team_winner:
                        SetFragment(myTeamWinnerFragment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.home_my_team_score:
                        SetFragment(myTeamScoreFragment);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.home_quiz_score:
                        SetFragment(myQuizScoreFragment);
                        drawerLayout.closeDrawers();
                        break;


                }


                return false;
            }
        });
    }

    private void logMeout() {


        firebaseAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        MainActivity.this.overridePendingTransition(0, 0);
        finish();

    }

    private void SetUpSideDrawer() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    private void SetFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack("my_fragment").commit();


    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
