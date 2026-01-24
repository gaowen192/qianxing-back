package com.ride.mapper;

import com.ride.entity.TcmProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品属性Repository接口
 * 用于商品属性相关的数据库操作
 */
public interface TcmProductAttributeRepository extends JpaRepository<TcmProductAttribute, Long> {

    /**
     * 根据商品ID查询商品属性
     * @param productId 商品ID
     * @return 商品属性列表
     */
    List<TcmProductAttribute> findByProductId(Long productId);

    /**
     * 根据商品ID删除商品属性
     * @param productId 商品ID
     */
    void deleteByProductId(Long productId);
}