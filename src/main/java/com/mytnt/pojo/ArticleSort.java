package com.mytnt.pojo;

/**
 * Created by meiyan on 2020/3/2.
 */
public class ArticleSort {
    private Integer sortId;
    private String sortName;
    public Integer getSortId() {

        return sortId;
    }
    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
    public String getSortName() {
        return sortName;
    }
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    @Override
    public String toString() {
        return "ArticleSort{" +
                "sortId=" + sortId +
                ", sortName='" + sortName + '\'' +
                '}';
    }
}
