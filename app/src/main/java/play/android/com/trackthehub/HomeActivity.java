package play.android.com.trackthehub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import play.android.com.trackthehub.network.fetchService;
import play.android.com.trackthehub.util.Utils;

import static play.android.com.trackthehub.network.fetchService.ACTION_DATA_UPDATED;

public class HomeActivity extends AppCompatActivity {

    Toolbar mtoolbar;
    int[] tabIcons = {R.drawable.ic_repo,R.drawable.ic_issues};
    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;


    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);

        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyReposFragment(), getString(R.string.repositories));
        adapter.addFragment(new myissuefragment(), getString(R.string.issues));
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        mtoolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(mtoolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mtoolbar, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
    // creates call to onPrepareOptionsMenu()
            }
        };





        mDrawerToggle.setDrawerIndicatorEnabled(true);


        mDrawerToggle.syncState();
        Intent i=new Intent();
        i.setAction(ACTION_DATA_UPDATED);
        sendBroadcast(i);


viewPager.setOffscreenPageLimit(3);










    }

    @Override
    protected void onResume() {
        super.onResume();
        String username=Utils.getString("username","null",this);
        if(Utils.getString("user_fetched","false",this).equals("false"))
        {
            Intent i=new Intent(this,fetchService.class);
            i.putExtra("code",2);
            i.putExtra("user",username);
            i.putExtra("url","https://api.github.com/user?oauth_token="+Utils.getString(username+":token","null",this));
            startService(i);



        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();

        inflater.inflate(R.menu.main_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mLogout) {

            Utils.SetString("loggedin","false",this);
            Utils.SetString("user_fetched","false",this);
            startActivity(new Intent(this,LoginActivity.class));
            finish();
            return true;



        }

        return super.onOptionsItemSelected(item);
    }
}

