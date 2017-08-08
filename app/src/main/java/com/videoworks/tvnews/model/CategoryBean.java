package com.videoworks.tvnews.model;

/**
 * Created by mxh on 2017/8/8.
 * Describe：新闻栏目tab
 */

public class CategoryBean extends BaseBean {
    /**
     * description : 描述
     * id : 5847670d3a50643c66d54b4f
     * inserted_at : 1480645296578
     * name : 手动添加
     * parent : parent
     * path : path
     * status : true
     * updated_at : 1480645296578
     */

    private String description;
    private String id;
    private String inserted_at;
    private String name;
    private String parent;
    private String path;
    private boolean status;
    private String updated_at;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInserted_at() {
        return inserted_at;
    }

    public void setInserted_at(String inserted_at) {
        this.inserted_at = inserted_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
