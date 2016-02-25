package com.zhaoliang.littlejoke.domain;

import java.util.List;

/**
 * Created by pro on 16/2/23.
 */
public class IndexResponse {

    /**
     * status : ok
     * data : [{"title":"无题","content":"有位非常漂亮的女同事，有天起晚了没有时间化妆便急忙冲到公司。结果那天她被记旷工了\u2026\u2026","author_time":"2016-02-22 04:03:59"},{"title":"无题","content":"一同事在窗台边给领导打电话，刚接通，\u201c喂，领导\u2026\u2026\u201d就在那一瞬间另一同事开玩笑一个猴子偷桃，\u201c卧槽尼玛\u201d，","author_time":"2016-02-22 04:03:59"}]
     * time : 2016-02-22 04:03:59
     */

    private String status;
    private String time;
    /**
     * title : 无题
     * content : 有位非常漂亮的女同事，有天起晚了没有时间化妆便急忙冲到公司。结果那天她被记旷工了……
     * author_time : 2016-02-22 04:03:59
     */

    private List<DataEntity> data;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private String title;
        private String content;
        private String author_time;

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setAuthor_time(String author_time) {
            this.author_time = author_time;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getAuthor_time() {
            return author_time;
        }
    }
}
