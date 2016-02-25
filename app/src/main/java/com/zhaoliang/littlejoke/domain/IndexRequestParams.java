package com.zhaoliang.littlejoke.domain;

/**
 * Created by pro on 16/2/23.
 */
public class IndexRequestParams {


    /**
     * action : get_article
     * time_m : 94226
     * sort : old
     * row : 5
     */

    private String action;
    private String time_m;
    private String sort;
    private int row;

    public IndexRequestParams(String action, String id, String sort, int row) {
        this.action = action;
        this.time_m = id;
        this.sort = sort;
        this.row = row;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTime_m(String time_m) {
        this.time_m = time_m;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getAction() {
        return action;
    }

    public String getTime_m() {
        return time_m;
    }

    public String getSort() {
        return sort;
    }

    public int getRow() {
        return row;
    }
}
