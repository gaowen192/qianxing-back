package com.ride.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品请求类
 * 用于创建和更新商品的请求数据
 */
public class TcmProductRequest {

    private Long id;
    private String name;
    private String description;
    private Long categoryId;
    private Long userId;
    private String brand;
    private String mainImage;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private Integer isFreeShipping;
    private BigDecimal shippingFee;
    private BigDecimal weight;
    private String size;
    private String color;
    private String material;
    private String origin;

    // 商品属性列表
    private List<TcmProductAttributeRequest> attributes;
    // 商品规格列表
    private List<TcmProductSkuRequest> skus;
    // 商品图片列表（用于直接上传）
    private List<TcmProductImageRequest> images;
    // 已上传图片ID列表（用于选择已上传的图片）
    private List<Long> imageIds;

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

    public List<TcmProductAttributeRequest> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<TcmProductAttributeRequest> attributes) {
        this.attributes = attributes;
    }

    public List<TcmProductSkuRequest> getSkus() {
        return skus;
    }

    public void setSkus(List<TcmProductSkuRequest> skus) {
        this.skus = skus;
    }

    public List<TcmProductImageRequest> getImages() {
        return images;
    }

    public void setImages(List<TcmProductImageRequest> images) {
        this.images = images;
    }

    public List<Long> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<Long> imageIds) {
        this.imageIds = imageIds;
    }

    @Override
    public String toString() {
        return "TcmProductRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", userId=" + userId +
                ", brand='" + brand + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                ", isFreeShipping=" + isFreeShipping +
                ", shippingFee=" + shippingFee +
                ", weight=" + weight +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", material='" + material + '\'' +
                ", origin='" + origin + '\'' +
                ", attributes=" + attributes +
                ", skus=" + skus +
                ", images=" + images +
                ", imageIds=" + imageIds +
                '}';
    }
}