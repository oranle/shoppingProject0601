package com.example.shoppingmall.user.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.utils.CacheUtils;

import static android.content.ContentValues.TAG;

/**
 * 作者：尚硅谷-杨光福 on 2016/11/14 11:07
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：用户中心的Fragment
 */
public class UpdateInfoFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View initView() {
        Log.e(TAG, "用户中心的Fragment的UI被初始化了");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_personal_center, null, false);


        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"用户中心的Fragment的数据被初始化了");
        textView.setText("用户中心内容");
    }
}
