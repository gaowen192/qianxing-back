package com.ride.dto;

/**
 * 商品图片DTO类
 * 用于商品图片数据的传输
 */
public class TcmProductImageDTO {

    private Long id;
    private Long productId;
    private Long skuId;
    private String imagePath;
    private Integer imageType;
    private Integer sortOrder;
    private String remark;

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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getImageType() {
        return imageType;
    }

    public void setImageType(Integer imageType) {
        this.imageType = imageType;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TcmProductImageDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", skuId=" + skuId +
                ", imagePath='" + imagePath + '\'' +
                ", imageType=" + imageType +
                ", sortOrder=" + sortOrder +
                ", remark='" + remark + '\'' +
                '}';
    }
}