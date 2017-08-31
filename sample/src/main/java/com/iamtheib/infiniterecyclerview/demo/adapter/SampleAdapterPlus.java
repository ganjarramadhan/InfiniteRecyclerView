package com.iamtheib.infiniterecyclerview.demo.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import com.iamtheib.infiniterecyclerview.InfiniteAdapterPlus;
import com.iamtheib.infiniterecyclerview.demo.R;
import com.iamtheib.infiniterecyclerview.demo.viewholder.DummyViewHolder;
import com.iamtheib.infiniterecyclerview.demo.viewholder.ErrorViewHolder;
import com.iamtheib.infiniterecyclerview.demo.viewholder.LoadingViewHolder;
import java.util.List;

/**
 * Created by Saurabh on 6/2/16.
 */
public class SampleAdapterPlus extends InfiniteAdapterPlus<RecyclerView.ViewHolder> {

  private List<String> sampleData;
  private Context mContext;

  public SampleAdapterPlus(Context context, List<String> dummyData) {
    mContext = context;
    sampleData = dummyData;
  }

  @Override public RecyclerView.ViewHolder getLoadingViewHolder(ViewGroup parent) {
    View loadingView =
        LayoutInflater.from(mContext).inflate(R.layout.list_loading_view, parent, false);
    return new LoadingViewHolder(loadingView);
  }

  @Override public RecyclerView.ViewHolder getErrorViewHolder(ViewGroup parent) {
    View errorView = LayoutInflater.from(mContext).inflate(R.layout.list_error_view, parent, false);
    return new ErrorViewHolder(errorView);
  }

  @Override public int getCount() {
    return sampleData.size();
  }

  @Override public int getViewType(int position) {
    return 2;
  }

  @Override public RecyclerView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
    View dummyView = LayoutInflater.from(mContext).inflate(R.layout.item_dummy, parent, false);
    return new DummyViewHolder(dummyView);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof LoadingViewHolder) {
      LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
      ObjectAnimator animator =
          ObjectAnimator.ofFloat(loadingViewHolder.loadingImage, "rotation", 0, 360);
      animator.setRepeatCount(ValueAnimator.INFINITE);
      animator.setInterpolator(new LinearInterpolator());
      animator.setDuration(1000);
      animator.start();
      return;
    } else if (holder instanceof ErrorViewHolder) {
      // do nothing
      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          if (getOnErrorListener() == null) return;
          getOnErrorListener().onRetryLoadMore();
        }
      });
      return;
    } else {
      ((DummyViewHolder) holder).tv.setText(sampleData.get(position));
    }

    super.onBindViewHolder(holder, position);
  }

  @Override public int getVisibleThreshold() {
    return 5;
  }
}
