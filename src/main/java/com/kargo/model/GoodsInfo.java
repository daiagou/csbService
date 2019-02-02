package com.kargo.model;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsInfo {
    private Integer id;

    private String goodsName;

    private BigDecimal price;

    private String picUrl;

    private String goodsType;

    private Date createTime;

    private Date updateTime;

    private String detailPicUrl1;

    private String detailPicUrl2;

    private String detailPicUrl3;

    private String detailPicUrl4;

    private String detailPicUrl5;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType == null ? null : goodsType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDetailPicUrl1() {
        return detailPicUrl1;
    }

    public void setDetailPicUrl1(String detailPicUrl1) {
        this.detailPicUrl1 = detailPicUrl1 == null ? null : detailPicUrl1.trim();
    }

    public String getDetailPicUrl2() {
        return detailPicUrl2;
    }

    public void setDetailPicUrl2(String detailPicUrl2) {
        this.detailPicUrl2 = detailPicUrl2 == null ? null : detailPicUrl2.trim();
    }

    public String getDetailPicUrl3() {
        return detailPicUrl3;
    }

    public void setDetailPicUrl3(String detailPicUrl3) {
        this.detailPicUrl3 = detailPicUrl3 == null ? null : detailPicUrl3.trim();
    }

    public String getDetailPicUrl4() {
        return detailPicUrl4;
    }

    public void setDetailPicUrl4(String detailPicUrl4) {
        this.detailPicUrl4 = detailPicUrl4 == null ? null : detailPicUrl4.trim();
    }

    public String getDetailPicUrl5() {
        return detailPicUrl5;
    }

    public void setDetailPicUrl5(String detailPicUrl5) {
        this.detailPicUrl5 = detailPicUrl5 == null ? null : detailPicUrl5.trim();
    }
}