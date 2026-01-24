package com.ride.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * 商品规格实体类
 * 存储商品的具体规格信息，如不同颜色、尺寸组合的价格和库存
 */
@Entity
@Table(name = "tcm_product_skus")
public class TcmProductSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "sku_code", nullable = false, length = 100, unique = true)
    private String skuCode;

    @Column(name = "sku_name", nullable = false, length = 255)
    private String skuName;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "original_price", precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "sales")
    private Integer sales;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "attributes", columnDefinition = "TEXT")
    private String attributes; // 存储规格属性，如颜色:红色;尺寸:M

    @Column(name = "status") // 0-下架，1-上架
    private Integer status;

    // 无参构造函数
    public TcmProductSku() {
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

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TcmProductSku{" +
                "id=" + id +
                ", productId=" + productId +
                ", skuCode='" + skuCode + '\'' +
                ", skuName='" + skuName + '\'' +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", stock=" + stock +
                ", sales=" + sales +
                ", imageUrl='" + imageUrl + '\'' +
                ", attributes='" + attributes + '\'' +
                ", status=" + status +
                '}';
    }
}