package com.wangng.pindu.ui.gestoslife;

import com.wangng.pindu.data.network.ApiConfig;
import com.wangng.pindu.httpclient.RetrofitRequestClient;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by yu on 2017/1/23.
 */

public class GestosLifeService {
    public static IGestosLifeService Instance;

    static {
        Instance = RetrofitRequestClient.getInstance().createService(ApiConfig.BASE_URL, IGestosLifeService.class);
    }

    public interface IGestosLifeService {
        @GET()
        Observable<ResponseBody> getGestosLifeData(@Url String url);

        @GET()
        Observable<ResponseBody> getGestosLifeDetailData(@Url String url);
    }
}
