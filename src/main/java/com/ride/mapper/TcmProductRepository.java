package com.ride.mapper;

import com.ride.entity.TcmProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 商品Repository接口
 * 用于商品相关的数据库操作
 */
public interface TcmProductRepository extends JpaRepository<TcmProduct, Long> {

    /**
     * 根据商品名称和状态查询商品
     * @param name 商品名称
     * @param status 商品状态
     * @param pageable 分页参数
     * @return 商品列表
     */
    Page<TcmProduct> findByNameContainingAndStatus(String name, Integer status, Pageable pageable);

    /**
     * 根据商品分类查询商品
     * @param categoryId 分类ID
     * @param status 商品状态
     * @param pageable 分页参数
     * @return 商品列表
     */
    Page<TcmProduct> findByCategoryIdAndStatus(Long categoryId, Integer status, Pageable pageable);

    /**
     * 根据商品品牌查询商品
     * @param brand 商品品牌
     * @param status 商品状态
     * @param pageable 分页参数
     * @return 商品列表
     */
    Page<TcmProduct> findByBrandAndStatus(String brand, Integer status, Pageable pageable);

    /**
     * 查询销量前N的商品
     * @param status 商品状态
     * @param limit 限制数量
     * @return 商品列表
     */
    @Query("SELECT p FROM TcmProduct p WHERE p.status = :status ORDER BY p.sales DESC LIMIT :limit")
    List<TcmProduct> findTopBySales(@Param("status") Integer status, @Param("limit") int limit);

    /**
     * 根据商品ID列表查询商品
     * @param ids 商品ID列表
     * @param status 商品状态
     * @return 商品列表
     */
    List<TcmProduct> findByIdInAndStatus(List<Long> ids, Integer status);
}