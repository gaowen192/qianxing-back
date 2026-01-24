package com.ride.entity;

import jakarta.persistence.*;

/**
 * 商品图片实体类
 * 存储商品的图片信息，支持绑定到商品或SKU
 */
@Entity
@Table(name = "tcm_product_images")
public class TcmProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "sku_id")
    private Long skuId;

    @Column(name = "image_path", nullable = false, length = 255)
    private String imagePath;

    @Column(name = "image_type") // 0-商品主图，1-商品详情图，2-SKU图
    private Integer imageType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "remark", length = 255)
    private String remark;

    // 无参构造函数
    public TcmProductImage() {
    }

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
        return "TcmProductImage{" +
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