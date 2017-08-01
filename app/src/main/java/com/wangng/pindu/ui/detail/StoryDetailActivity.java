package com.wangng.pindu.ui.detail;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wangng.pindu.R;
import com.wangng.pindu.base.BaseActivity;
import com.wangng.pindu.customtabs.CustomFallback;
import com.wangng.pindu.customtabs.CustomTabActivityHelper;
import com.wangng.pindu.data.DataManager;
import com.wangng.pindu.data.local.PreferenceUtil;
import com.wangng.pindu.util.ToastUtil;
import com.wangng.pindu.util.UIHelper;

import butterknife.BindView;

/**
 * Created by 小爱 on 2017/3/9.
 */

public class StoryDetailActivity extends BaseActivity<DetailPresenter, DetailModel> implements DetailContract.View{

    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    private int mStoryId;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void initView() {
        //设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        webView.setScrollbarFadingEnabled(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebSettings webViewSettings = webView.getSettings();
        //能够和js交互
        webViewSettings.setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        webViewSettings.setBuiltInZoomControls(false);
        //缓存
        webViewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        webViewSettings.setDomStorageEnabled(true);
        //开启application Cache功能
        webViewSettings.setAppCacheEnabled(false);
        webViewSettings.setBlockNetworkImage(DataManager.getInstance().getPictureMode());

        //对图片的点击事件用js调用java方法，详见 https://juejin.im/post/58d9aca80ce463005713dba6
        webView.addJavascriptInterface(new JavascriptInterface(mContext), "ImageClickListener");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                openUrl(view, url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //这段js函数的功能就是注册监听，遍历所有的img标签，并添加onClick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
                webView.loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName(\"img\"); "
                        + "for(var i=0;i<objs.length;i++)  " + "{"
                        + "    objs[i].onclick=function()  " + "    {  "
                        + "        window.ImageClickListener.onImageClick(this.src);  "
                        + "    }  " + "}" + "})()");
            }
        });
    }

    public void openUrl(WebView webView, String url) {
        if (PreferenceUtil.getBoolean(mContext, "in_app_browser", true)) {
            CustomTabsIntent.Builder customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(getResources().getColor(R.color.colorAccent))
                    .setShowTitle(true);
            CustomTabActivityHelper.openCustomTab(
                    (Activity) mContext,
                    customTabsIntent.build(),
                    Uri.parse(url),
                    new CustomFallback() {
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            super.openUri(activity, uri);
                        }
                    }
            );
        } else {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url)));
            } catch (ActivityNotFoundException ex) {
                Snackbar.make(imageView, R.string.no_browser_found, Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void initData() {
        mStoryId = getIntent().getIntExtra("story_id", 0);
        mPresenter.getStorydetail(mStoryId);
    }

    @Override
    public void startLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void stopLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void loadDataWithBaseURL(String data) {
        webView.loadDataWithBaseURL("x-data://base", data, "text/html", "utf-8", null);
    }

    @Override
    public void showLoadError() {
        ToastUtil.showSnackBar(coordinatorLayout, R.string.network_error, R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    @Override
    public void showCopySuccess() {
        ToastUtil.showSnackBar(coordinatorLayout, R.string.copy_success);
    }

    public void setCollapsingToolbarLayoutTitle(String title) {
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        toolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    @Override
    public void loadImage(String url) {
        Picasso.with(mContext).load(url).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_more:
                mPresenter.showbottomSheetDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showbottomSheetDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext);
        View view = View.inflate(mContext, R.layout.dialog_detial, null);

        final boolean bookmarded = mPresenter.ifBookmarked(mStoryId);
        if(bookmarded) {
            ((TextView) view.findViewById(R.id.textView)).setText(R.string.action_delete_from_bookmarks);
            ((ImageView) view.findViewById(R.id.imageView))
                    .setColorFilter(getResources().getColor(R.color.colorPrimary));
        }

        view.findViewById(R.id.layout_bookmark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookmarded) {
                    mPresenter.unbookmarkStory();
                } else {
                    mPresenter.bookmarkStory();
                }
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.layout_copy_link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.copyLink();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.layout_open_in_browser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openInBrowser();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.layout_copy_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.copyText();
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.layout_share_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.shareAsText();
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    @Override
    public void showBookmarkSuccess() {
        ToastUtil.showSnackBar(coordinatorLayout, R.string.bookmark_success);
    }

    @Override
    public void showBookmarkFail() {
        ToastUtil.showSnackBar(coordinatorLayout, R.string.bookmark_fail);
    }

    @Override
    public void showUnbookmarkSuccess() {
        ToastUtil.showSnackBar(coordinatorLayout, R.string.unbookmark_success);
    }

    @Override
    public void showUnbookmarkFail() {
        ToastUtil.showSnackBar(coordinatorLayout, R.string.unbookmark_fail);
    }

    @Override
    public void shareAsText(String title, String url) {
        Intent shareIntent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
        StringBuilder shareText = new StringBuilder();
        shareText.append(title)
                .append(" ")
                .append(url)
                .append(" ,分享自 知乎日报（非官方）");

        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString());
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
    }

    @Override
    public void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    class JavascriptInterface {
        private Context context;


        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void onImageClick(String img) {
            UIHelper.showBigImage(mContext, img);
        }
    }

}
