package com.ride.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 * 对应淘宝商品的基本信息
 */
@Entity
@Table(name = "tcm_products")
public class TcmProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "brand", length = 100)
    private String brand;

    @Column(name = "main_image", length = 255)
    private String mainImage;

    @Column(name = "sub_image_1", length = 255)
    private String subImage1;

    @Column(name = "sub_image_2", length = 255)
    private String subImage2;

    @Column(name = "sub_image_3", length = 255)
    private String subImage3;

    @Column(name = "sub_image_4", length = 255)
    private String subImage4;

    @Column(name = "sub_image_5", length = 255)
    private String subImage5;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "sales")
    private Integer sales;

    @Column(name = "status", nullable = false) // 0-下架，1-上架
    private Integer status;

    @Column(name = "is_free_shipping") // 0-不包邮，1-包邮
    private Integer isFreeShipping;

    @Column(name = "shipping_fee", precision = 10, scale = 2)
    private BigDecimal shippingFee;

    @Column(name = "weight", precision = 10, scale = 2)
    private BigDecimal weight;

    @Column(name = "size", length = 100)
    private String size;

    @Column(name = "color", length = 100)
    private String color;

    @Column(name = "material", length = 100)
    private String material;

    @Column(name = "origin", length = 100)
    private String origin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 无参构造函数
    public TcmProduct() {
    }

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

    public String getSubImage1() {
        return subImage1;
    }

    public void setSubImage1(String subImage1) {
        this.subImage1 = subImage1;
    }

    public String getSubImage2() {
        return subImage2;
    }

    public void setSubImage2(String subImage2) {
        this.subImage2 = subImage2;
    }

    public String getSubImage3() {
        return subImage3;
    }

    public void setSubImage3(String subImage3) {
        this.subImage3 = subImage3;
    }

    public String getSubImage4() {
        return subImage4;
    }

    public void setSubImage4(String subImage4) {
        this.subImage4 = subImage4;
    }

    public String getSubImage5() {
        return subImage5;
    }

    public void setSubImage5(String subImage5) {
        this.subImage5 = subImage5;
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

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "TcmProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", userId=" + userId +
                ", brand='" + brand + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", subImage1='" + subImage1 + '\'' +
                ", subImage2='" + subImage2 + '\'' +
                ", subImage3='" + subImage3 + '\'' +
                ", subImage4='" + subImage4 + '\'' +
                ", subImage5='" + subImage5 + '\'' +
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
                ", deletedAt=" + deletedAt +
                '}';
    }
}