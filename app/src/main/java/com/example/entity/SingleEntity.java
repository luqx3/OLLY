package com.example.entity;

/**
 * Created by HP on 2016/12/16.
 */

public class SingleEntity {
    private String authorName;//作者名称
    private String title;//书名
    private String firstUrl;//链接属性
    private String imageUrl;//图片链接属性
    private String publisher;//出版商
    private String pubdate;
    private String summary;
    private String rating_average;
    private String borrow_time;
    private String borrow_ddl;
    private String location;
    private String leave_num;
    private String total_num;
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getFirstUrl() {
        return firstUrl;
    }
    public void setFirstUrl(String firstUrl) {
        this.firstUrl = firstUrl;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getBookName() {
        return title;
    }
    public void setBookName(String bookName) {
        this.title = bookName;
    }
    public String getPublisher(){
        return publisher;
    }
    public void setPublisher(String mpublisher){
        publisher=mpublisher;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRating_average() {
        return rating_average;
    }

    public void setRating_average(String rating_average) {
        this.rating_average = rating_average;
    }

    public String getBorrow_time() {
        return borrow_time;
    }

    public void setBorrow_time(String borrow_time) {
        this.borrow_time = borrow_time;
    }

    public String getBorrow_ddl() {
        return borrow_ddl;
    }

    public void setBorrow_ddl(String borrow_ddl) {
        this.borrow_ddl = borrow_ddl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLeave_num() {
        return leave_num;
    }

    public void setLeave_num(String leave_num) {
        this.leave_num = leave_num;
    }

    public String getTotal_num() {
        return total_num;
    }

    public void setTotal_num(String total_num) {
        this.total_num = total_num;
    }
}
