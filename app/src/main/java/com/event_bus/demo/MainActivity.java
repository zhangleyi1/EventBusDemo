package com.event_bus.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.openActivity)
    public Button mOpenActivity;

    @BindView(R.id.sendDataBtn)
    public Button mSendData;

    @BindView(R.id.receiveDataTv)
    public TextView mReceiveDataTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        ButterKnife.bind(this);
        //注册订阅者
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.sendDataBtn, R.id.receiveDataTv, R.id.openActivity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sendDataBtn:
                //发送事件
                CustomEvent event = new CustomEvent("小明");
                EventBus.getDefault().post(event);
                break;
            case R.id.receiveDataTv:
                break;
            case R.id.openActivity:
                {
                    event = new CustomEvent("小小明");
                    EventBus.getDefault().postSticky(event);
                    Intent intent = new Intent(this, TwoActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveData(CustomEvent event) {
        Log.d("zly", "zly --> MainActivity receiveData");
        mReceiveDataTv.setText(event.getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
