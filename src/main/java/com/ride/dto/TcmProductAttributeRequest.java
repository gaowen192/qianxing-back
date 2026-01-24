package com.ride.dto;

/**
 * 商品属性请求类
 * 用于创建和更新商品属性的请求数据
 */
public class TcmProductAttributeRequest {

    private Long id;
    private String attributeName;
    private String attributeValue;
    private Integer sortOrder;

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "TcmProductAttributeRequest{" +
                "id=" + id +
                ", attributeName='" + attributeName + '\'' +
                ", attributeValue='" + attributeValue + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }
}