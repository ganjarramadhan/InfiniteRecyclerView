package com.iamtheib.infiniterecyclerview.demo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.iamtheib.infiniterecyclerview.demo.R;

/**
 * Created by Saurabh on 6/2/16.
 */
public class ErrorViewHolder extends RecyclerView.ViewHolder {

  public TextView tvError;

  public ErrorViewHolder(View itemView) {
    super(itemView);
    tvError = (TextView) itemView.findViewById(R.id.list_error_view_tv_error);
  }
}
