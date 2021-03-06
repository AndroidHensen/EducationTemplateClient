package com.hensen.shixiongsdk.BaseTemplate;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hensen.shixiongsdk.R;


public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    private View convertView;
    private SparseArray<View> mViews;
    private Intent intent;

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initListener();

    public abstract void initData();

    public abstract void processClick(View v);

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sdk_finish) {
            getActivity().finish();
        }
        processClick(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViews = new SparseArray<>();
        convertView = inflater.inflate(getLayoutId(), container, false);
        initViews();
        //Load data
        initListener();
        initData();
        return convertView;
    }

    public <E extends View> E findView(int viewId) {
        if (convertView != null) {
            E view = (E) mViews.get(viewId);
            if (view == null) {
                view = (E) convertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return view;
        }
        return null;
    }

    public <E extends View> void setOnClick(E view) {
        view.setOnClickListener(this);
    }

    public void startActivity(Class clazz) {
        intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    public void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    public void requestPermissions(String permissions) {
        if (ContextCompat.checkSelfPermission(getActivity(), permissions) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions)) {

            }
            ActivityCompat.requestPermissions(getActivity(), new String[]{permissions}, 0);
        }
    }

    public void setTitle(String title) {
        TextView tv_title = findView(R.id.sdk_title);
        tv_title.setText(title);
    }

    public void setTitleCanBack() {
        ImageView iv_finish = findView(R.id.sdk_finish);
        iv_finish.setVisibility(View.VISIBLE);
        setOnClick(iv_finish);
    }
}
