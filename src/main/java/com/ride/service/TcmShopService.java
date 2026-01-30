package com.ride.service;

import com.ride.dto.TcmShopDTO;
import com.ride.dto.TcmShopCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 店铺Service接口
 * 定义店铺相关的业务逻辑操作
 */
public interface TcmShopService {

    /**
     * 创建店铺
     * @param shopDTO 店铺数据
     * @param logoFile 店铺logo文件
     * @param bannerFile 店铺横幅文件
     * @return 店铺DTO
     */
    TcmShopDTO createShop(TcmShopDTO shopDTO, MultipartFile logoFile, MultipartFile bannerFile);

    /**
     * 更新店铺
     * @param id 店铺ID
     * @param shopDTO 店铺数据
     * @param logoFile 店铺logo文件
     * @param bannerFile 店铺横幅文件
     * @return 店铺DTO
     */
    TcmShopDTO updateShop(Long id, TcmShopDTO shopDTO, MultipartFile logoFile, MultipartFile bannerFile);

    /**
     * 根据ID查询店铺
     * @param id 店铺ID
     * @return 店铺DTO
     */
    TcmShopDTO getShopById(Long id);

    /**
     * 删除店铺
     * @param id 店铺ID
     */
    void deleteShop(Long id);

    /**
     * 分页查询店铺
     * @param name 店铺名称
     * @param ownerId 店主ID
     * @param status 店铺状态
     * @param pageable 分页参数
     * @return 店铺分页列表
     */
    Page<TcmShopDTO> getShops(String name, Long ownerId, Integer status, Pageable pageable);

    /**
     * 更改店铺状态
     * @param id 店铺ID
     * @param status 店铺状态
     */
    void updateShopStatus(Long id, Integer status);

    /**
     * 验证店铺
     * @param id 店铺ID
     * @param isVerified 是否验证
     */
    void verifyShop(Long id, Integer isVerified);

    /**
     * 添加店铺分类
     * @param shopId 店铺ID
     * @param categoryDTO 店铺分类数据
     * @return 店铺分类DTO
     */
    TcmShopCategoryDTO addShopCategory(Long shopId, TcmShopCategoryDTO categoryDTO);

    /**
     * 更新店铺分类
     * @param id 店铺分类ID
     * @param categoryDTO 店铺分类数据
     * @return 店铺分类DTO
     */
    TcmShopCategoryDTO updateShopCategory(Long id, TcmShopCategoryDTO categoryDTO);

    /**
     * 删除店铺分类
     * @param id 店铺分类ID
     */
    void deleteShopCategory(Long id);

    /**
     * 根据店铺ID查询店铺分类列表
     * @param shopId 店铺ID
     * @return 店铺分类列表
     */
    List<TcmShopCategoryDTO> getShopCategories(Long shopId);
}
