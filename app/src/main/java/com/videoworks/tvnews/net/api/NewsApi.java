package com.videoworks.tvnews.net.api;

import com.videoworks.tvnews.net.UrlMgr;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by mxh on 2017/8/8.
 * Describe：新闻类接口
 */

public interface NewsApi {

    @GET(UrlMgr.URL_CATEGORY_TAB)
    Observable<Response> getCategoryInfo();

}
