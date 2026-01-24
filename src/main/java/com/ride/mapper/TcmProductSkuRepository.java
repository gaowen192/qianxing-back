package com.ride.mapper;

import com.ride.entity.TcmProductSku;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品规格Repository接口
 * 用于商品规格相关的数据库操作
 */
public interface TcmProductSkuRepository extends JpaRepository<TcmProductSku, Long> {

    /**
     * 根据商品ID查询商品规格
     * @param productId 商品ID
     * @return 商品规格列表
     */
    List<TcmProductSku> findByProductId(Long productId);

    /**
     * 根据商品ID和状态查询商品规格
     * @param productId 商品ID
     * @param status 商品状态
     * @return 商品规格列表
     */
    List<TcmProductSku> findByProductIdAndStatus(Long productId, Integer status);

    /**
     * 根据商品ID删除商品规格
     * @param productId 商品ID
     */
    void deleteByProductId(Long productId);

    /**
     * 根据SKU编码查询商品规格
     * @param skuCode SKU编码
     * @return 商品规格
     */
    TcmProductSku findBySkuCode(String skuCode);
}