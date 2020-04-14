package com.anjuke.mobile.sign;

public class House {
    private String id;
    private String tw_url;
    private String title;
    private String house_price;
    private String house_avg_price;
    private String house_area_num;
    private String house_layout;
    private String house_floor_level;
    private String house_orient;
    private String house_use_type;
    private String community_name;
    private String community_address;
    private String broker_name;
    private String broker_mobile;
    private Integer post_date;
    private String is_call;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTw_url() {
        return tw_url;
    }

    public void setTw_url(String tw_url) {
        this.tw_url = tw_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHouse_price() {
        return house_price;
    }

    public void setHouse_price(String house_price) {
        this.house_price = house_price;
    }

    public String getHouse_avg_price() {
        return house_avg_price;
    }

    public void setHouse_avg_price(String house_avg_price) {
        this.house_avg_price = house_avg_price;
    }

    public String getHouse_area_num() {
        return house_area_num;
    }

    public void setHouse_area_num(String house_area_num) {
        this.house_area_num = house_area_num;
    }

    public String getHouse_layout() {
        return house_layout;
    }

    public void setHouse_layout(String house_layout) {
        this.house_layout = house_layout;
    }

    public String getHouse_floor_level() {
        return house_floor_level;
    }

    public void setHouse_floor_level(String house_floor_level) {
        this.house_floor_level = house_floor_level;
    }

    public String getHouse_orient() {
        return house_orient;
    }

    public void setHouse_orient(String house_orient) {
        this.house_orient = house_orient;
    }

    public String getHouse_use_type() {
        return house_use_type;
    }

    public void setHouse_use_type(String house_use_type) {
        this.house_use_type = house_use_type;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getCommunity_address() {
        return community_address;
    }

    public void setCommunity_address(String community_address) {
        this.community_address = community_address;
    }

    public String getBroker_name() {
        return broker_name;
    }

    public void setBroker_name(String broker_name) {
        this.broker_name = broker_name;
    }

    public String getBroker_mobile() {
        return broker_mobile;
    }

    public void setBroker_mobile(String broker_mobile) {
        this.broker_mobile = broker_mobile;
    }

    public Integer getPost_date() {
        return post_date;
    }

    public void setPost_date(Integer post_date) {
        this.post_date = post_date;
    }

    public String getIs_call() {
        return is_call;
    }

    public void setIs_call(String is_call) {
        this.is_call = is_call;
    }

    @Override
    public String toString() {
        return "House{" +
                "id='" + id + '\'' +
                ", tw_url='" + tw_url + '\'' +
                ", title='" + title + '\'' +
                ", house_price='" + house_price + '\'' +
                ", house_avg_price='" + house_avg_price + '\'' +
                ", house_area_num='" + house_area_num + '\'' +
                ", house_layout='" + house_layout + '\'' +
                ", house_floor_level='" + house_floor_level + '\'' +
                ", house_orient='" + house_orient + '\'' +
                ", house_use_type='" + house_use_type + '\'' +
                ", community_name='" + community_name + '\'' +
                ", community_address='" + community_address + '\'' +
                ", broker_name='" + broker_name + '\'' +
                ", broker_mobile='" + broker_mobile + '\'' +
                ", post_date='" + post_date + '\'' +
                ", is_call='" + is_call + '\'' +
                '}';
    }
}
