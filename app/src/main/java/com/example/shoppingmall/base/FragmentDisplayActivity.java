package com.example.shoppingmall.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.shoppingmall.R;

/**
 * <pre>
 * Description: 展示activity用的宿主;
 * 调用{@link #start(Context, Class)}方法，传入需要展示的Fragment的class，
 * singleInstance启动模式，支持启动多次展示多个fragment支持返回
 *
 * </pre>
 */
public class FragmentDisplayActivity extends AppCompatActivity {
    private static final String TAG = "FragmentDisplayActivity";

    private static final String KEY_FRAGMENT_NAME = "fragment_name";
    /**
     * 参数
     */
    private static final String KEY_ARGUMENT = "argument";
    /**
     * 沉浸式状态栏
     */
    private static final String KEY_STATUS_BAR_IMMERSIVE = "status_bar_immersive";

    private FragmentManager fragmentManager;

    private String mFragmentPath;

    private FragmentTransaction mFragmentTransaction;

    private boolean fromNewIntent = false;

    private Bundle argument;

    private BaseFragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFragmentPath = getIntent().getStringExtra(KEY_FRAGMENT_NAME);
            argument = getIntent().getBundleExtra(KEY_ARGUMENT);

            fromNewIntent = false;

            super.onCreate(savedInstanceState);
        }
        Log.i(TAG, "onCreate: ");
        setContentView(R.layout.activity_display_fragment);

        initData();
    }

    protected void initData() {
        Log.i(TAG, "initData: ");
        if (TextUtils.isEmpty(mFragmentPath)) {
            Log.w(TAG, "initData: fragmentPath is empty");
            finish();
            return;
        }

        Log.i(TAG, "initData: show fragment:" + mFragmentPath);

        try {
            Class<?> clazz = Class.forName(mFragmentPath);
            fragment = (BaseFragment) clazz.newInstance();
            fragment.setArguments(argument);
            fragmentManager = getSupportFragmentManager();
            mFragmentTransaction = fragmentManager.beginTransaction();
            if (fromNewIntent) {
                mFragmentTransaction.setCustomAnimations(
                        R.anim.slide_enter,
                        R.anim.slide_exit,
                        R.anim.pop_left_enter,
                        R.anim.pop_left_exit
                );
            }
            mFragmentTransaction.replace(R.id.frame_layout, fragment);
            mFragmentTransaction.addToBackStack(mFragmentPath);
            mFragmentTransaction.commit();
        } catch (ClassNotFoundException e) {
            Log.w(TAG, "initData: ClassNotFoundException:" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.w(TAG, "initData: IllegalAccessException:" + e.getMessage());
            e.printStackTrace();
        } catch (InstantiationException e) {
            Log.w(TAG, "initData: InstantiationException:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i(TAG, "onNewIntent: " + intent);

        fromNewIntent = true;
        if (intent != null) {
            mFragmentPath = intent.getStringExtra(KEY_FRAGMENT_NAME);
            argument = intent.getBundleExtra(KEY_ARGUMENT);
            initData();
        } else {
            Log.i(TAG, "onNewIntent: intent is null");
        }
    }

    @Override
    public void onBackPressed() {
        Log.w(TAG, "onBackPressed: " + fragmentManager.getBackStackEntryCount());
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        mFragmentTransaction = fragmentManager.beginTransaction();
        mFragmentTransaction.setCustomAnimations(0, 0);
        mFragmentTransaction.commit();

        super.finish();

        overridePendingTransition(R.anim.pop_left_enter, R.anim.pop_left_exit);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_enter, R.anim.activity_start_outgoing);
    }

    public static void start(Context context, Class<? extends Fragment> fragmentCls) {
        start(context, fragmentCls, null, false);
    }

    public static void start(Context context, String fragClassName, Bundle argument,
                             boolean immersiveStatusBar) {
        Class<? extends Fragment> fragClass;
        try {
            Class<?> originClass = Class.forName(fragClassName);
            fragClass = (Class<? extends Fragment>) originClass;
            start(context, fragClass, argument, immersiveStatusBar);
        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    /**
     * 展示fragment
     *
     * @param context     上下文
     * @param fragmentCls fragment
     * @param argument    argument
     */
    public static void start(Context context, Class<? extends Fragment> fragmentCls, Bundle argument,
                             boolean immersiveStatusBar) {
        if (fragmentCls != null) {
            Log.i(TAG, "start: " + fragmentCls.getName());
            Intent intent = new Intent(context, FragmentDisplayActivity.class);
            intent.putExtra(KEY_FRAGMENT_NAME, fragmentCls.getName());
            intent.putExtra(KEY_STATUS_BAR_IMMERSIVE, immersiveStatusBar);
            if (argument != null) {
                intent.putExtra(KEY_ARGUMENT, argument);
            }
            context.startActivity(intent);

            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(R.anim.slide_enter, R.anim.activity_start_outgoing);
            } else {
                Log.w(TAG, "start: context is not instance of Activity, " + context);
            }
        } else {
            Log.w(TAG, "start: fragment is null");
        }
    }
}
