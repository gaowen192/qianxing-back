package com.ride.mapper;

import com.ride.entity.TcmShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 店铺Mapper接口
 * 用于与数据库进行交互
 */
@Repository
public interface TcmShopRepository extends JpaRepository<TcmShop, Long> {

    /**
     * 根据店铺名称模糊查询店铺列表
     * @param name 店铺名称
     * @return 店铺列表
     */
    List<TcmShop> findByNameContaining(String name);

    /**
     * 根据店铺名称模糊查询店铺列表（分页）
     * @param name 店铺名称
     * @param pageable 分页参数
     * @return 店铺分页列表
     */
    org.springframework.data.domain.Page<TcmShop> findByNameContaining(String name, org.springframework.data.domain.Pageable pageable);

    /**
     * 根据店主ID查询店铺列表
     * @param ownerId 店主ID
     * @return 店铺列表
     */
    List<TcmShop> findByOwnerId(Long ownerId);

    /**
     * 根据店主ID查询店铺列表（分页）
     * @param ownerId 店主ID
     * @param pageable 分页参数
     * @return 店铺分页列表
     */
    org.springframework.data.domain.Page<TcmShop> findByOwnerId(Long ownerId, org.springframework.data.domain.Pageable pageable);

    /**
     * 根据店铺状态查询店铺列表
     * @param status 店铺状态
     * @return 店铺列表
     */
    List<TcmShop> findByStatus(Integer status);

    /**
     * 根据店铺状态查询店铺列表（分页）
     * @param status 店铺状态
     * @param pageable 分页参数
     * @return 店铺分页列表
     */
    org.springframework.data.domain.Page<TcmShop> findByStatus(Integer status, org.springframework.data.domain.Pageable pageable);

    /**
     * 根据店铺名称和状态查询店铺列表
     * @param name 店铺名称
     * @param status 店铺状态
     * @return 店铺列表
     */
    List<TcmShop> findByNameContainingAndStatus(String name, Integer status);

    /**
     * 根据店铺名称和状态查询店铺列表（分页）
     * @param name 店铺名称
     * @param status 店铺状态
     * @param pageable 分页参数
     * @return 店铺分页列表
     */
    org.springframework.data.domain.Page<TcmShop> findByNameContainingAndStatus(String name, Integer status, org.springframework.data.domain.Pageable pageable);

    /**
     * 根据店主ID和状态查询店铺列表
     * @param ownerId 店主ID
     * @param status 店铺状态
     * @return 店铺列表
     */
    List<TcmShop> findByOwnerIdAndStatus(Long ownerId, Integer status);

    /**
     * 根据店主ID和状态查询店铺列表（分页）
     * @param ownerId 店主ID
     * @param status 店铺状态
     * @param pageable 分页参数
     * @return 店铺分页列表
     */
    org.springframework.data.domain.Page<TcmShop> findByOwnerIdAndStatus(Long ownerId, Integer status, org.springframework.data.domain.Pageable pageable);
}
