package com.videoworks.tvnews.net.response;

import com.android_mobile.net.response.BaseResponse;
import com.videoworks.tvnews.model.CategoryBean;

import java.util.List;

/**
 * Created by mxh on 2017/8/8.
 * Describe：新闻栏目响应信息
 */

public class CategoryResponse extends BaseResponse {

    /**
     * data : {"categories":[{"description":"描述","id":"5847670d3a50643c66d54b4f","inserted_at":"1480645296578","name":"手动添加","parent":"parent","path":"path","status":true,"updated_at":"1480645296578"}]}
     * statusCode : 200
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CategoryBean> categories;

        public List<CategoryBean> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoryBean> categories) {
            this.categories = categories;
        }

    }
}
