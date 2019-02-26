package com.zhangjie.zjcustomview.view.zj;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhangjie.zjcustomview.R;

/**
 * 京软选择栏
 */

public class SelectView extends Fragment {

    private View root;

    private RecyclerView rv_left, rv_right;

    private String[] leftList = {"2016", "2017", "2018", "2018", "2019", "2010", "2011"};
    private String[] rightList = {"1", "2", "3", "4", "5", "6", "7","1", "2", "3", "4", "5", "6", "7"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.select_view, container);
        rv_left = root.findViewById(R.id.rv_left);
        rv_right = root.findViewById(R.id.rv_right);

        initView();
        return root;
    }

    private LeftHomeAdapter leftHomeAdapter;
    private RightHomeAdapter rightHomeAdapter;
    private LinearLayoutManager leftLinearLayoutManager,rightLinearLayoutManager;

    private void initView() {
        leftLinearLayoutManager = new LinearLayoutManager(getContext());
        rightLinearLayoutManager=new LinearLayoutManager(getContext());
        rv_left = root.findViewById(R.id.rv_left);
        rv_right=root.findViewById(R.id.rv_right);
        leftHomeAdapter = new LeftHomeAdapter();
        rightHomeAdapter=new RightHomeAdapter();

        rv_left.setLayoutManager(leftLinearLayoutManager);
        rv_left.setAdapter(leftHomeAdapter);

        rv_right.setLayoutManager(rightLinearLayoutManager);
        rv_right.setAdapter(rightHomeAdapter);


    }

    class LeftHomeAdapter extends RecyclerView.Adapter<ViewHolder> {


        public LeftHomeAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            ViewHolder holder;


            holder = ViewHolder.createViewHolder(getContext(), parent, R.layout.select_view_item);


            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {


        }


        @Override
        public int getItemCount() {
            //返回集合的长度
            return leftList.length;
        }


    }

    class RightHomeAdapter extends RecyclerView.Adapter<ViewHolder> {


        public RightHomeAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            ViewHolder holder;


            holder = ViewHolder.createViewHolder(getContext(), parent, R.layout.select_view_item);


            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {


        }


        @Override
        public int getItemCount() {
            //返回集合的长度
            return rightList.length;
        }


    }
}
