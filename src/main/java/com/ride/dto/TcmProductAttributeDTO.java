package com.ride.dto;

/**
 * 商品属性DTO类
 * 用于商品属性数据的传输
 */
public class TcmProductAttributeDTO {

    private Long id;
    private Long productId;
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        return "TcmProductAttributeDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", attributeName='" + attributeName + '\'' +
                ", attributeValue='" + attributeValue + '\'' +
                ", sortOrder=" + sortOrder +
                '}';
    }
}