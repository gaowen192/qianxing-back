package com.ride.mapper;

import com.ride.entity.TcmProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品图片Repository接口
 * 用于商品图片相关的数据库操作
 */
public interface TcmProductImageRepository extends JpaRepository<TcmProductImage, Long> {

    /**
     * 根据商品ID查询商品图片
     * @param productId 商品ID
     * @return 商品图片列表
     */
    List<TcmProductImage> findByProductId(Long productId);

    /**
     * 根据商品ID和图片类型查询商品图片
     * @param productId 商品ID
     * @param imageType 图片类型
     * @return 商品图片列表
     */
    List<TcmProductImage> findByProductIdAndImageType(Long productId, Integer imageType);

    /**
     * 根据SKU ID查询商品图片
     * @param skuId SKU ID
     * @return 商品图片列表
     */
    List<TcmProductImage> findBySkuId(Long skuId);

    /**
     * 查询未绑定到商品的图片列表
     * @return 图片列表
     */
    List<TcmProductImage> findByProductIdIsNull();

    /**
     * 根据商品ID删除商品图片
     * @param productId 商品ID
     */
    void deleteByProductId(Long productId);

    /**
     * 根据SKU ID删除商品图片
     * @param skuId SKU ID
     */
    void deleteBySkuId(Long skuId);
}