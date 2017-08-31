package com.iamtheib.infiniterecyclerview.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.iamtheib.infiniterecyclerview.InfiniteAdapterPlus;
import com.iamtheib.infiniterecyclerview.demo.adapter.SampleAdapterPlus;
import java.util.ArrayList;

public class MainActivityPlus extends AppCompatActivity {

  RecyclerView dummyRV;
  SampleAdapterPlus mSampleAdapter;
  ArrayList<String> mDummyData;

  private int page = 1;
  private int maxPage = 3;
  private InfiniteAdapterPlus.OnLoadMoreListener mLoadMoreListener =
      new InfiniteAdapterPlus.OnLoadMoreListener() {
        @Override public void onLoadMore() {
          Log.v("Main", "Load more fired");

          final int currSize = mDummyData.size();
          new Handler().postDelayed(new Runnable() {
            @Override public void run() {
              page++;
              addMoreData(page);
              mSampleAdapter.moreDataLoaded(currSize, mDummyData.size() - currSize);
              if (page == maxPage) {
                mSampleAdapter.showError(true);
              }
            }
          }, 3000);
        }
      };

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mDummyData = new ArrayList<>();
    setupDummyData();

    mSampleAdapter = new SampleAdapterPlus(this, mDummyData);
    dummyRV = (RecyclerView) findViewById(R.id.dummy_rv);
    dummyRV.setHasFixedSize(true);
    dummyRV.setAdapter(mSampleAdapter);
    dummyRV.setLayoutManager(new LinearLayoutManager(this));
    mSampleAdapter.setOnLoadMoreListener(mLoadMoreListener);

    mSampleAdapter.setOnErrorListener(new InfiniteAdapterPlus.OnErrorListener() {
      @Override public void onRetryLoadMore() {
        // tap to retry, can scroll 3 page more
        maxPage = maxPage + 3;
        mSampleAdapter.showError(false);
      }
    });
  }

  private void setupDummyData() {
    for (int i = 1; i <= 20; i++) {
      mDummyData.add("Text " + i);
    }
  }

  private void addMoreData(int page) {
    for (int i = 1; i <= 20; i++) {
      mDummyData.add("Text " + ((page * 20) + i));
    }
  }
}
