package com.ayyayo.g.UI;

import java.util.List;

import com.ayyayo.g.Database.DatabaseHandler;
import com.ayyayo.g.Database.ListViewSwipeGesture;
import com.ayyayo.g.Database.News;
import com.ayyayo.g.Push.FCMActivity;
import com.ayyayo.g.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends FCMActivity {


    private DisplayImageOptions options;

    DatabaseHandler db;

    List<News> contacts;
    SampleAdapter adapter;
    ListView list;
    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(config);
        db = new DatabaseHandler(this);


        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapter = new SampleAdapter(this);
        db = new DatabaseHandler(this);
        contacts = db.getAllContacts(DatabaseHandler.TABLE_NEWS);

        if (contacts.size() != 0) {
            ListViewSwipeGesture touchListener = new ListViewSwipeGesture(list,
                    swipeListener, this);
            touchListener.SwipeType = ListViewSwipeGesture.Dismiss;
            touchListener.HalfDrawable = getResources().getDrawable(R.drawable.ic_delete);
            list.setOnTouchListener(touchListener);
        } else {
            adapter.add(new News(
                    -1,
                    "No Notifications",
                    "-",
                    "http://dummyimage.com/qvga/CCC/000.png&text=No+Notifications",
                    "-1", "-1"));

        }
        View empty = findViewById(R.id.empty);
        list.setEmptyView(empty);

        list.setAdapter(adapter);


    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            contacts.clear();
            contacts = db.getAllContacts(DatabaseHandler.TABLE_NEWS);
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        try {
            registerReceiver(receiver, new IntentFilter(FCMActivity.NEW_NOTIFICATION));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ListViewSwipeGesture.TouchCallbacks swipeListener = new ListViewSwipeGesture.TouchCallbacks() {

        @Override
        public void FullSwipeListView(int position) {
            // Call back function for second action
        }

        @Override
        public void HalfSwipeListView(int position) {

        }

        @Override
        public void LoadDataForScroll(int count) {
            // call back function to load more data in listview (Continuous
            // scroll)

        }

        @Override
        public void onDismiss(ListView listView, int[] reverseSortedPositions) {

            try {
                for (int i : reverseSortedPositions) {
                    if (contacts.get(i).getID() != -1) {

                        db.deleteContact(contacts.get(i));
                        adapter.remove(contacts.get(i));
                        adapter.notifyDataSetChanged();
                        contacts.remove(i);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void OnClickListView(int position) {
            try {
                if (contacts.get(position).getID() != -1) {

                    Intent intent = new Intent(MainActivity.this,
                            CustomeWebView.class);
                    intent.putExtra("link", contacts.get(position).getLink());

                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(),
                        PreferenceActivity.class));
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public class SampleAdapter extends ArrayAdapter<News> {

        public SampleAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public int getCount() {
            return contacts.size();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflater.inflate(R.layout.cat_swipe_layout, parent,
                    false);
            if (convertView == null) {
                inflater.inflate(R.layout.cat_swipe_layout, parent, false);
            }
            TextView title = (TextView) convertView.findViewById(R.id.date); // title
            TextView branch = (TextView) convertView.findViewById(R.id.msg); // title
            ImageView v = (ImageView) convertView.findViewById(R.id.img);
            try {
                title.setText(Html.fromHtml((contacts.get(position).getName())));
                branch.setText(Html.fromHtml(contacts.get(position).getDate()));
                // Log.d("image", getItem(position).seen);

                if ((contacts.get(position).getImage().trim().length() <= 0)) {

                } else {
                    ImageLoader.getInstance().displayImage(
                            contacts.get(position).getImage(), v, options);
                }

                branch.setSelected(true);
                branch.requestFocus();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return convertView;

        }

    }

}
