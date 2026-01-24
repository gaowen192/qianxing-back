package com.ride.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品DTO类
 * 用于商品数据的传输
 */
public class TcmProductDTO {

    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private Long userId;
    private String brand;
    private String mainImage;
    private BigDecimal price;
    private Integer stock;
    private Integer sales;
    private Integer status;
    private Integer isFreeShipping;
    private BigDecimal shippingFee;
    private BigDecimal weight;
    private String size;
    private String color;
    private String material;
    private String origin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 商品属性列表
    private List<TcmProductAttributeDTO> attributes;
    // 商品规格列表
    private List<TcmProductSkuDTO> skus;
    // 商品图片列表
    private List<TcmProductImageDTO> images;

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(Integer isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TcmProductAttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<TcmProductAttributeDTO> attributes) {
        this.attributes = attributes;
    }

    public List<TcmProductSkuDTO> getSkus() {
        return skus;
    }

    public void setSkus(List<TcmProductSkuDTO> skus) {
        this.skus = skus;
    }

    public List<TcmProductImageDTO> getImages() {
        return images;
    }

    public void setImages(List<TcmProductImageDTO> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "TcmProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", userId=" + userId +
                ", brand='" + brand + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", sales=" + sales +
                ", status=" + status +
                ", isFreeShipping=" + isFreeShipping +
                ", shippingFee=" + shippingFee +
                ", weight=" + weight +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", material='" + material + '\'' +
                ", origin='" + origin + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", attributes=" + attributes +
                ", skus=" + skus +
                ", images=" + images +
                '}';
    }
}