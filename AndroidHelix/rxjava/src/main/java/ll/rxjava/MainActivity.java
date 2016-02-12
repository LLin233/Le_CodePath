package ll.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ll.rxjava.caches.MyImageLoader;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    private RxBus mRxBus = null;

    // This is better done with a DI Library like Dagger
    public RxBus getRxBusSingleton() {
        if (mRxBus == null) {
            mRxBus = new RxBus();
        }

        return mRxBus;
    }

    @Bind(R.id.listView)
    ListView mListView;

    ArrayList<String> contents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRxBus = this.getRxBusSingleton();

        Observable.just("Hello, world!", "Leonard")
                .map(s -> s.hashCode())
                .map(i -> i.toString())
                .subscribe(s -> System.out.println(s));

        mRxBus.toObserverable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (event instanceof TapEvent) {
                            showToast(((TapEvent) event).getMsg());
                        }
                    }
                });

        MyImageLoader.init(getApplicationContext());
        for (int i = 0; i < 1; i++) {
            contents.add("http://att.x2.hiapk.com/forum/month_1008/100804175235a96c931557db2c.png");
            contents.add("http://img5.imgtn.bdimg.com/it/u=1755111051,4257519768&fm=21&gp=0.jpg");
            contents.add("http://img2.imgtn.bdimg.com/it/u=3675742480,2498904140&fm=21&gp=0.jpg");
            contents.add("http://img4.imgtn.bdimg.com/it/u=3066194656,1157598834&fm=21&gp=0.jpg");
            contents.add("http://img4.imgtn.bdimg.com/it/u=1368479866,1863058464&fm=21&gp=0.jpg");
            contents.add("http://img0.imgtn.bdimg.com/it/u=4087866388,590061000&fm=21&gp=0.jpg");
            contents.add("http://img5.imgtn.bdimg.com/it/u=1561037318,1220192463&fm=21&gp=0.jpg");
            contents.add("http://img1.imgtn.bdimg.com/it/u=662082337,2002986732&fm=21&gp=0.jpg");
            contents.add("http://img0.imgtn.bdimg.com/it/u=140615978,892488256&fm=21&gp=0.jpg");
            contents.add("http://img1.imgtn.bdimg.com/it/u=1611159065,380062274&fm=21&gp=0.jpg");
            contents.add("http://img0.imgtn.bdimg.com/it/u=4122493773,2558548513&fm=21&gp=0.jpg");
            contents.add("http://img5.imgtn.bdimg.com/it/u=4130769033,1807453428&fm=21&gp=0.jpg");
            contents.add("http://img0.imgtn.bdimg.com/it/u=2820222087,1709896027&fm=21&gp=0.jpg");
            contents.add("http://img2.imgtn.bdimg.com/it/u=3305887887,1438165635&fm=21&gp=0.jpg");
            contents.add("http://img5.imgtn.bdimg.com/it/u=4223057213,412589332&fm=21&gp=0.jpg");
            contents.add("http://img1.imgtn.bdimg.com/it/u=942005080,847944849&fm=21&gp=0.jpg");
            contents.add("http://img1.imgtn.bdimg.com/it/u=1381276545,2126755198&fm=21&gp=0.jpg");
            contents.add("http://img2.imgtn.bdimg.com/it/u=1106185903,889365061&fm=21&gp=0.jpg");
            contents.add("http://img5.imgtn.bdimg.com/it/u=3742930320,3001723047&fm=21&gp=0.jpg");
        }
        mListView.setAdapter(new RxAdapter());


    }

    void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button)
    public void onTapButtonClicked() {
        mRxBus.send(new TapEvent("Rxjava!"));
    }


    private Subscription startSubscribe(ImageView img, String url) {
        return MyImageLoader.getLoaderObservable(img, url)
                .subscribe(data -> Log.i(this.getClass().getSimpleName(), "bitmap size:" + data.bitmap.getHeight() * data.bitmap.getWidth()));
    }

    class RxAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return contents.size();
        }

        @Override
        public String getItem(int i) {
            return contents.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = View.inflate(MainActivity.this, R.layout.list_item, null);
                holder.img = (ImageView) view.findViewById(R.id.img);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.img.setImageResource(R.mipmap.ic_launcher);
            startSubscribe(holder.img, getItem(i));
            return view;
        }
    }

    class ViewHolder {
        public ImageView img;
    }


}