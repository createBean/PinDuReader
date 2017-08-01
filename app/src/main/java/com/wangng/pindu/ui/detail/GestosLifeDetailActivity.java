package com.wangng.pindu.ui.detail;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wangng.pindu.R;
import com.wangng.pindu.base.BaseActivity;
import com.wangng.pindu.ui.gestoslife.GestosLifeService;
import com.wangng.pindu.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class GestosLifeDetailActivity extends BaseActivity {
    @BindView(R.id.head_bar_title_view)
    TextView tvTitleView;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.frame_layout)
    FrameLayout mFrameLayout;
    private String detailUrl;

    @OnClick(R.id.back_view)
    void backView() {
        finish();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_gestos_life_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        detailUrl = intent.getStringExtra("detailUrl");

    }

    @Override
    protected void initData() {
        getDataList();
    }

    public void getDataList(){
        GestosLifeService.Instance.getGestosLifeDetailData(detailUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressBar.setVisibility(View.GONE);
                        showLoadError();
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            String doc = new String(body.bytes(), "GBK");
                            Document document = Jsoup.parse(doc);
                            String titleString = document.select("h1").get(0).text();
                            tvTitleView.setText(TextUtils.isEmpty(titleString) ? "详情": titleString);
                            Elements es = document.select("p");
                            List<Node> nodeList = es.get(0).childNodes();
                            for (int i = 0; i < nodeList.size(); i++) {
                                String contentString = nodeList.get(i).toString();
                                if (contentString.equals("<br>")) {
                                    continue;
                                }
                                TextView textView = new TextView(GestosLifeDetailActivity.this);
                                if (TextUtils.isEmpty(contentString)) {
                                    textView.setText("\n");
                                } else {
                                    textView.setText(contentString);
                                }
                                textView.setTextSize(20);
                                textView.setTextColor(Color.parseColor("#414141"));
                                contentLayout.addView(textView);
                            }
                            TextView nullTextView = new TextView(GestosLifeDetailActivity.this);
                            nullTextView.setText("\n"+"\n"+"\n"+"\n"+"\n");
                            contentLayout.addView(nullTextView);
                            mProgressBar.setVisibility(View.GONE);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void showLoadError() {
        ToastUtil.showSnackBar(mFrameLayout, R.string.network_error, R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataList();
            }
        });
    }

    @OnClick(R.id.fab)
    void fabClick(){

    }
}
