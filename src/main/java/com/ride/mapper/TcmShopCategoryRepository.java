package com.ride.mapper;

import com.ride.entity.TcmShopCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 店铺分类Mapper接口
 * 用于与数据库进行交互
 */
@Repository
public interface TcmShopCategoryRepository extends JpaRepository<TcmShopCategory, Long> {

    /**
     * 根据店铺ID查询店铺分类列表
     * @param shopId 店铺ID
     * @return 店铺分类列表
     */
    List<TcmShopCategory> findByShopId(Long shopId);

    /**
     * 根据店铺ID和分类名称查询店铺分类
     * @param shopId 店铺ID
     * @param categoryName 分类名称
     * @return 店铺分类
     */
    TcmShopCategory findByShopIdAndCategoryName(Long shopId, String categoryName);

    /**
     * 根据店铺ID删除店铺分类
     * @param shopId 店铺ID
     */
    void deleteByShopId(Long shopId);
}
