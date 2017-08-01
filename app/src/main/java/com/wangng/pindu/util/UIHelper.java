package com.wangng.pindu.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.wangng.pindu.R;
import com.wangng.pindu.ui.about.AboutActivity;
import com.wangng.pindu.ui.bigimage.BigImageActivity;
import com.wangng.pindu.ui.detail.GestosLifeDetailActivity;
import com.wangng.pindu.ui.detail.StoryDetailActivity;
import com.wangng.pindu.ui.easteregg.EasterEggActivity;
import com.wangng.pindu.ui.edittheme.AllThemeActivity;
import com.wangng.pindu.ui.openlicense.OpenLisenceActivity;
import com.wangng.pindu.ui.search.SearchActivity;
import com.wangng.pindu.ui.setting.SettingActivity;

/**
 * Created by wng on 2017/2/17.
 */

public class UIHelper {
    private UIHelper() {
    }

    public static void showDialog(Context context, int titleRes, int messageRes, DialogInterface.OnClickListener l) {
        showDialog(context,
                context.getString(titleRes),
                context.getString(messageRes),
                l);
    }

    public static void showDialog(Context context, String title, String message, DialogInterface.OnClickListener l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.sure, l);
        builder.create().show();
    }

    public static void showToast(Context context, int textRes) {
        showToast(context, context.getString(textRes));
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showSetting(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public static void showAbout(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    public static void showEasterEgg(Context context) {
        Intent intent = new Intent(context, EasterEggActivity.class);
        context.startActivity(intent);
    }

    public static void showStoryDetail(Context context, int id) {
        Intent intent = new Intent(context, StoryDetailActivity.class);
        intent.putExtra("story_id", id);
        context.startActivity(intent);
    }

    public static void showSearch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public static void showAllTheme(Activity context, int requestCode) {
        Intent intent = new Intent(context, AllThemeActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    public static void showOpenLicense(Context context) {
        Intent intent = new Intent(context, OpenLisenceActivity.class);
        context.startActivity(intent);
    }

    public static void showBigImage(Context context, String imgUrl) {
        Intent intent = new Intent(context, BigImageActivity.class);
        intent.putExtra("imgUrl", imgUrl);
        context.startActivity(intent);
    }

    public static void showGestosLifeDetail(Context context, String url) {
        Intent intent = new Intent(context, GestosLifeDetailActivity.class);
        intent.putExtra("detailUrl", url);
        context.startActivity(intent);
    }
}
