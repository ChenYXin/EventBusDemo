package dom.donkor.demo.eventbus;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author donkor
 * csdn blog: http://blog.csdn.net/donkor_（昵称：donkor_）
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Button mBtn1,mBtn2,mBtn3,mBtn4;
    private TextView mTextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        mBtn1= (Button) findViewById(R.id.btn1);
        mBtn2= (Button) findViewById(R.id.btn2);
        mBtn3= (Button) findViewById(R.id.btn3);
        mBtn4= (Button) findViewById(R.id.btn4);
        mTextview= (TextView) findViewById(R.id.tv);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);
        mBtn4.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                EventBus.getDefault().post(new MainMessage("Hello Donkor"));
                break;
            case R.id.btn2:
                EventBus.getDefault().post(new BackgroundMessage("Hello Donkor"));
                break;
            case R.id.btn3:
                EventBus.getDefault().post(new AsyncMessage("Hello Donkor"));
                break;
            case R.id.btn4:
                EventBus.getDefault().post(new PostingMessage("Hello Donkor"));
                break;
        }
    }

    //主线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEventBus(MainMessage msg) {
        mTextview.setText("MAIN主线程中发出："+msg.getMessage());
    }

    //后台线程
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackgroundEventBus(BackgroundMessage msg) {
        mTextview.setText("BACKGROUND后台线程中发出：" + msg.getMessage());
    }

    //异步线程
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onAsyncEventBus(AsyncMessage msg) {
        mTextview.setText("ASYNC异步线程中发出：" + msg.getMessage());
    }

    //默认情况，和发送事件在同一个线程
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onPostEventBus(PostingMessage msg) {
        mTextview.setText("POSTING发送事件在同一个线程中发出：" + msg.getMessage());
    }
}
