package com.example.shoppingmall.user.fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.base.FragmentDisplayActivity;
import com.example.shoppingmall.utils.CacheUtils;

import static android.content.ContentValues.TAG;

/**
 * 作者：尚硅谷-杨光福 on 2016/11/14 11:07
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：用户中心的Fragment
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "UserFragment";

    public static final String USER_LOGIN = "user_login";

    private TextView textView;

    View unLoginLayout;
    View loginLayout;
    TextView name;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.w(TAG, "onHiddenChanged: " + hidden);
        if (hidden) {

        } else {
            initView();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.w(TAG, "onPause: ");
    }

    @Override
    public View initView() {
        Log.e(TAG, "用户中心的Fragment的UI被初始化了");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_personal_center, null, false);

        unLoginLayout = view.findViewById(R.id.un_login_layout);
        loginLayout = view.findViewById(R.id.login_layout);
        TextView login = view.findViewById(R.id.login);
        TextView register = view.findViewById(R.id.register);
        TextView logout = view.findViewById(R.id.logout);
        TextView name = view.findViewById(R.id.name);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        logout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        boolean hasLogin = CacheUtils.getBoolean(getContext(), USER_LOGIN);
        name.setText(CacheUtils.getString(mContext, LoginFragment.KEY_LOGIN_USER_NAME));

        showLogin(hasLogin);
    }

    private void showLogin(boolean hasLogin) {
        if (hasLogin) {
            unLoginLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);
        } else {
            unLoginLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            FragmentDisplayActivity.start(getContext(), LoginFragment.class);
        } else if (view.getId() == R.id.register) {
            FragmentDisplayActivity.start(getContext(), RegisterFragment.class);
        } else if (view.getId() == R.id.logout) {
            CacheUtils.saveBoolean(getContext(), UserFragment.USER_LOGIN, false);
            showLogin(false);
        }
    }
}
