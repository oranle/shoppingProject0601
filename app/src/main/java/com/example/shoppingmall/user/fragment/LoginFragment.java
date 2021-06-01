package com.example.shoppingmall.user.fragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.data.bean.User;
import com.example.shoppingmall.data.dao.UserDao;
import com.example.shoppingmall.data.db.AppDataBase;
import com.example.shoppingmall.utils.CacheUtils;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 作者：尚硅谷-杨光福 on 2016/11/14 11:07
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：用户中心的Fragment
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "LoginFragment";
    public static final String KEY_LOGIN_USER_NAME = "login_user_name";
    private TextView inputName;
    private TextView password;
    private TextView login_btn;

    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login, null, false);

        inputName = view.findViewById(R.id.account);
        password = view.findViewById(R.id.password);
        login_btn = view.findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "用户中心的Fragment的数据被初始化了");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_btn) {
            String name = inputName.getText().toString();
            String psw = password.getText().toString();

            if (TextUtils.isEmpty(name)) {
                Toast.makeText(mContext, "用户名为空", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(psw)) {
                Toast.makeText(mContext, "密码为空", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread() {

                @Override
                public void run() {
                    super.run();
                    UserDao userDao = AppDataBase.get().getUserDao();
                    List<User> user = userDao.getUser(name, psw);
                    if (user.size() > 0) {
                        Message msg = mHandler.obtainMessage(CODE_MSG);
                        msg.obj = "登录成功";
                        msg.sendToTarget();

                        Message msgSucc = mHandler.obtainMessage(CODE_SUCCESS);
                        msgSucc.sendToTarget();

                        CacheUtils.saveBoolean(getContext(), UserFragment.USER_LOGIN, true);
                        CacheUtils.saveString(getContext(), KEY_LOGIN_USER_NAME, name);
                    } else {
                        Message msg = mHandler.obtainMessage(CODE_MSG);
                        msg.obj = "用户名或密码错误";
                        msg.sendToTarget();
                    }
                }
            }.start();
        }
    }

    private static final int CODE_MSG = 332;
    private static final int CODE_SUCCESS = 973;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == CODE_MSG) {
                Toast.makeText(mContext, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            } else {
                getActivity().onBackPressed();
            }

        }
    };
}
