package com.example.cuetintra_examquestion.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.cuetintra_examquestion.BuildConfig;
import com.example.cuetintra_examquestion.Fragment.BookmarksFrag;
import com.example.cuetintra_examquestion.Fragment.HomeFrag;
import com.example.cuetintra_examquestion.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ActionBarDrawerToggle Toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = (NavigationView) findViewById(R.id.navigationView);


        Toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        Toggle.setDrawerIndicatorEnabled(true);
        Toggle.syncState();
        drawerLayout.addDrawerListener(Toggle);

        //For Drawer Icon
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        navigationView.setCheckedItem(R.id.Home);
        //Default Fragment As
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new HomeFrag()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.Home:
                        if(!navigationView.getMenu().findItem(R.id.Home).isChecked()){
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new HomeFrag()).commit();
                            toolbar.setTitle("Home");
                        }
                        navigationView.setCheckedItem(R.id.Home);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Bookmarks:
                        if(!navigationView.getMenu().findItem(R.id.Bookmarks).isChecked()){
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, new BookmarksFrag()).commit();
                            toolbar.setTitle("Bookmarks");
                        }
                        navigationView.setCheckedItem(R.id.Bookmarks);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Email:
                        EmailMe();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.Follow:
                        FollowMe();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.RateApp:
                        RateApp();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.ShareApp:
                        ShareApp();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
                return true;
            }
        });

    }
    private void FollowMe() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Muntasir89")));
    }

    public void EmailMe(){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","muntasirhossen1704089@email.com", null));
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, "message");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }
    public void RateApp(){
        try{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
        }
        catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
        }
    }
    public void ShareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CUET Intra-Exam Question");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Choose an app"));
        } catch(Exception e) {
            //e.toString();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}