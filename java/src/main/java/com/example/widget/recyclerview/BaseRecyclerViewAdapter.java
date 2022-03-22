package com.example.widget.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private static final String TAG = "BaseRecyclerViewAdapter";
    @NonNull
    private Context mContext;
    @LayoutRes
    private int mLayoutId;
    @NonNull
    private ArrayList<T> mDataList;

    private RecyclerView.ViewHolder mHolder;
    private BaseRecyclerViewAdapterBackCall mBaseRecyclerViewAdapterBackCall;


    public void initRecyclerView(@NonNull Context context, @LayoutRes int layoutId, @NonNull ArrayList<T> dataList) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        this.mDataList = dataList;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(this.mLayoutId, parent, false);
        return new BaseRecyclerViewHolder(layout);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        mHolder = holder;
        mBaseRecyclerViewAdapterBackCall.onBindViewHolder(holder, position);
    }

    public View getRootView() {
        return mHolder.itemView;
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "mDataList size: " + mDataList.size());
        return Math.max(mDataList.size(), 1);
    }


    public void setBaseRecyclerViewAdapterBackCall(BaseRecyclerViewAdapterBackCall baseRecyclerViewAdapterBackCall) {
        this.mBaseRecyclerViewAdapterBackCall = baseRecyclerViewAdapterBackCall;
    }

    public interface BaseRecyclerViewAdapterBackCall {
        void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position);
    }


//    public static class Build<T> {
//        @NonNull
//        private Context mContext;
//        @LayoutRes
//        private int mLayoutId;
//        @NonNull
//        private ArrayList<T> mDataList;
//
//
//        public Build setContext(@NonNull Context mContext) {
//            this.mContext = mContext;
//            return this;
//        }
//
//        public Build setLayoutId(int mLayoutId) {
//            this.mLayoutId = mLayoutId;
//            return this;
//        }
//
//        public Build setDataList(@NonNull ArrayList<T> mDataList) {
//            this.mDataList = mDataList;
//            return this;
//        }
//
//
//
//
//        public BaseRecyclerViewAdapter build() {
//            return new BaseRecyclerViewAdapter<>();
//        }
//
//    }
}