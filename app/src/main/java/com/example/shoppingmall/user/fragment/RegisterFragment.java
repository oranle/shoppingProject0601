package com.example.shoppingmall.user.fragment;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.shoppingmall.R;
import com.example.shoppingmall.base.BaseFragment;
import com.example.shoppingmall.data.bean.User;
import com.example.shoppingmall.data.dao.UserDao;
import com.example.shoppingmall.data.db.AppDataBase;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 作者：尚硅谷-杨光福 on 2016/11/14 11:07
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：用户中心的Fragment
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    private TextView textView;

    private EditText inputName;
    private EditText psw_input;
    private TextView submit;

    @Override
    public View initView() {
        Log.e(TAG, "用户中心的Fragment的UI被初始化了");
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_register, null, false);

        inputName = view.findViewById(R.id.input_name);
        psw_input = view.findViewById(R.id.psw_input);
        submit = view.findViewById(R.id.submit);
        submit.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        Log.e(TAG, "用户中心的Fragment的数据被初始化了");
        textView.setText("用户中心内容");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {

            String name = inputName.getText().toString();
            String psw = psw_input.getText().toString();
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
                    User user = new User();
                    user.setName(name);
                    user.setPassword(psw);
                    UserDao userDao = AppDataBase.get().getUserDao();
                    List<User> userByName = userDao.getUserByName(name);
                    if (userByName.size() > 0) {

                    } else {

                    }

                    userDao.insert(user);
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
