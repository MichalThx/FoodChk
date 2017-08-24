package developer.mstudio.foodchk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import fragments.Dos;
import fragments.Tres;
import fragments.Uno;


public class MainActivity extends AppCompatActivity {

    public static String wuszukiwanie ="";
    public static int wyszuk=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

    }
    public void setCurrentItem(int item, boolean smoothScroll) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setCurrentItem(item, smoothScroll);

    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {
                case 0: return Uno.newInstance("");
                case 1: return Dos.newInstance("");
                case 2: return Tres.newInstance("");
                default: return Uno.newInstance("Default");
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
//        @Override
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }

    }



}
