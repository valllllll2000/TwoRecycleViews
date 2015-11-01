package com.vaxapp.tworecyclerviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.recycler_view2)
    RecyclerView recyclerView1;

    @Bind(R.id.recycler_view3)
    RecyclerView recyclerView2;


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        setUpRecyclerView(new String[]{"one", "two", "three", "four"}, recyclerView, R.layout.my_text_view);
        setUpRecyclerView(new String[]{"red", "green", "blue", "yellow"}, recyclerView1, R.layout.my_text_view2);
        setUpRecyclerView(new String[]{"", "", "", ""}, recyclerView2, R.layout.background);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                recyclerView1.smoothScrollToPosition(recyclerView.get);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int x = (int) ((double) dx / recyclerView.getWidth() * recyclerView1.getWidth());
                Log.d("onScrolled",  "x : "+x+", dx : "+dx);
                recyclerView1.smoothScrollBy(x, dy);
                recyclerView2.scrollBy(dx / 10, dy);
            }
        });

    }

    private void setUpRecyclerView(String[] myDataset, RecyclerView mRecyclerView, int resLayout) {
        RecyclerView.Adapter mAdapter = new MyAdapter(myDataset, resLayout);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final String[] mDataset;
        private final int layoutRes;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.text);
            }
        }

        public MyAdapter(String[] myDataset, int my_text_view) {
            mDataset = myDataset;
            layoutRes = my_text_view;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(layoutRes, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder.mTextView != null) {
                holder.mTextView.setText(mDataset[position]);
            }
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }
}
