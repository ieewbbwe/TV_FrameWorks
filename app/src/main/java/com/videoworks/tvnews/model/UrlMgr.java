package com.videoworks.tvnews.model;

/**
 * Created by mxh on 2017/8/8.
 * Describe：接口地址
 */

public interface UrlMgr {

    String HOST = " http://182.61.20.161/";
    String PORT = "7080";

    String Service = HOST + PORT;

    /*获取新闻tab*/
    String URL_CATEGORY_TAB = "/enews4tv/api/categorys";

}
