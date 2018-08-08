package com.event_bus.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/31.
 */
public class TwoActivity extends AppCompatActivity {

    @BindView(R.id.send)
    public Button mSend;

    @BindView(R.id.btn_receive)
    public Button mReceiveBtn;

    @BindView(R.id.receive)
    public TextView mReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        ButterKnife.bind(this);
        EventBus.getDefault().register(TwoActivity.this);
    }

    @OnClick({R.id.send, R.id.btn_receive})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.send:
                CustomEvent event = new CustomEvent("小红");
                EventBus.getDefault().post("小红");
                break;
            case R.id.btn_receive:
                new Thread() {
                    public void run() {
                        CustomEvent event = new CustomEvent("小小红");
                        EventBus.getDefault().post(event);
                    }
                }.start();
                break;
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void receiveData(String str) {
//        mReceive.setText(str);
////        Toast.makeText(this, event.getName(), Toast.LENGTH_SHORT).show();
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveData(Object obj) {
        if (obj instanceof String) {
            mReceive.setText((String)obj);
        } else if (obj instanceof CustomEvent) {
            mReceive.setText(((CustomEvent) obj).getName());
        }
//        Toast.makeText(this, event.getName(), Toast.LENGTH_SHORT).show();
    }

//    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
//    public void receiveData(CustomEvent event) {
//        Log.d("zly", "zly --> TwoActivity receiveData");
//        mReceive.setText(event.getName());
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
