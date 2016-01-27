package me.boger.pjsua2.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.boger.pjsua2.R;

public class ContentActivity extends FragmentActivity implements ContentView, View.OnClickListener {

    private ContentPresenter mPresenter;
    private FragmentManager fragmentManager;

    @Bind(R.id.tv_conf)
    TextView tvConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        this.mPresenter = new ContentPresenterImpl(this);
        fragmentManager = getSupportFragmentManager();
        if (null == savedInstanceState) {
            mPresenter.init();
        }
        init();

    }

    private void init() {
        tvConf.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void replaceContent(Fragment fragment, String tag) {
        if (null == fragment) {
            throw new IllegalArgumentException("WTF,Fragment is empty!!");
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        enableAnimation(ft);
        ft.replace(R.id.fl_content, fragment, tag).addToBackStack(null).commit();
    }

    @Override
    public void addContent(Fragment fragment, String tag) {
        if (null == fragment) {
            throw new IllegalArgumentException("WTF,Fragment is empty!!");
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        enableAnimation(ft);
        ft.add(R.id.fl_content, fragment, tag).commit();
    }

    @Override
    public void removeContent(Fragment fragment, String tag) {
        throw new IllegalArgumentException("以后再写");
    }

    @Override
    public void hideContent(Fragment fragment, String tag) {
        throw new IllegalArgumentException("以后再写");
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void enableAnimation(FragmentTransaction transaction) {
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Toast.makeText(this, "跑到了Activity了...", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_conf) {
            mPresenter.openConfDialog();
        }
    }
}
