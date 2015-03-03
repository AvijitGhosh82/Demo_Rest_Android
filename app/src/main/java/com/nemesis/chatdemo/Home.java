package com.nemesis.chatdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import de.hdodenhof.circleimageview.CircleImageView;


public class Home extends ActionBarActivity {

    private Toolbar toolbar;
    private PagerAdapter mPagerAdapter;
    private ListView listView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView drawerlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle= new ActionBarDrawerToggle(this, mDrawerLayout,toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        drawerlistView = (ListView) findViewById(R.id.list_view_drawer);

        String[] items = { "Dashboard", "Mail", "Project", "Share", "Update sheet" ,"E attendance"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);


        drawerlistView.setAdapter(adapter1);
        
        SpannableString s = new SpannableString("Wavit");

        if(toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(s);
        }

        this.initialisePaging();
    }

    private void initialisePaging() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, Fragment1.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragment2.class.getName()));
        fragments.add(Fragment.instantiate(this, Fragment3.class.getName()));
        this.mPagerAdapter  = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
        pager.setAdapter(this.mPagerAdapter);

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(Gravity.START|Gravity.LEFT)){
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    public static class Fragment1 extends ListFragment {

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            MyAdapter adapter = new MyAdapter(getActivity());
            setListAdapter(adapter);

            getListView().setDivider(null);
            getListView().setDividerHeight(0);

            getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) {
                    //Get your item here with the position
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.contact_card, null);
                    dialogBuilder.setView(dialogView);


                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();
                    return true;
                }
            });


        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            // do something with the data
            final Intent intent = new Intent(getActivity(), MessageActivity.class);

            TextView tv1=(TextView) v.findViewById(R.id.name);
            String name = tv1.getText().toString();
            intent.putExtra("name", name);
            startActivity(intent);
        }


    }

    public static class Fragment2 extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.frag2, container, false);
        }
    }

    public static class Fragment3 extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.frag3, container, false);
        }
    }
}


class MyAdapter extends BaseAdapter {

    private Context context;
    String[] txt,name;
    int[] images={R.drawable.im0,R.drawable.im1,R.drawable.im2,R.drawable.im3,R.drawable.im4,R.drawable.im5,R.drawable.im6,R.drawable.im7,R.drawable.im8,R.drawable.im9,R.drawable.im10,R.drawable.im11,R.drawable.im12};


    MyAdapter(Context context)
    {
        this.context=context;
        name=context.getResources().getStringArray(R.array.sample_names);
        txt=context.getResources().getStringArray(R.array.sample_text);
        shuffleArray(images);


        Random rng = new Random();
        List<String> arr = Arrays.asList(txt);
        Collections.shuffle(arr, rng);
        arr.toArray(txt);


        rng = new Random();
        arr = Arrays.asList(name);
        Collections.shuffle(arr, rng);
        arr.toArray(name);
    }



    static void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return name.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return name[position];
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row=null;


        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.list_single, parent, false);
        }
        else
        {
            row=convertView;
        }
        TextView tv1=(TextView) row.findViewById(R.id.name);
        TextView tv2=(TextView) row.findViewById(R.id.txt);
        TextView tv3=(TextView) row.findViewById(R.id.time);
        CircleImageView iv1=(CircleImageView) row.findViewById(R.id.img);


            tv1.setText(name[position]);
            tv2.setText(txt[position]);
            iv1.setImageResource(images[position]);

            Random r = new Random();
            int Low = 0;
            int High = 23;
            int R1 = r.nextInt(High-Low) + Low;
            String f1 = String.format("%02d", R1);
            Low = 00;
            High=59;
            int R2 = r.nextInt(High-Low) + Low;
            String f2 = String.format("%02d", R2);
            tv3.setText(f1+":"+f2);

        return row;
    }

}


