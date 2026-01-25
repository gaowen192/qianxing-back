package com.ride.service;

import com.ride.dto.TcmProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品Service接口
 * 用于商品相关的业务逻辑操作
 */
public interface TcmProductService {

    /**
     * 创建商品
     * @param productDTO 商品数据
     * @param files 商品图片文件
     * @return 商品DTO
     */
    TcmProductDTO createProduct(TcmProductDTO productDTO, MultipartFile[] files);

    /**
     * 更新商品
     * @param id 商品ID
     * @param productDTO 商品数据
     * @return 商品DTO
     */
    TcmProductDTO updateProduct(Long id, TcmProductDTO productDTO);

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品DTO
     */
    TcmProductDTO getProductById(Long id);

    /**
     * 删除商品
     * @param id 商品ID
     */
    void deleteProduct(Long id);

    /**
     * 分页查询商品
     * @param name 商品名称
     * @param categoryId 分类ID
     * @param brand 品牌
     * @param status 状态
     * @param pageable 分页参数
     * @return 商品分页列表
     */
    Page<TcmProductDTO> getProducts(String name, Long categoryId, String brand, Integer status, Pageable pageable);

    /**
     * 查询热销商品
     * @param limit 限制数量
     * @return 商品列表
     */
    List<TcmProductDTO> getHotProducts(int limit);

    /**
     * 上下架商品
     * @param id 商品ID
     * @param status 状态
     */
    void updateProductStatus(Long id, Integer status);

    /**
     * 更新商品库存
     * @param id 商品ID
     * @param stock 库存数量
     */
    void updateProductStock(Long id, Integer stock);

    /**
     * 根据SKU编码查询商品规格
     * @param skuCode SKU编码
     * @return 商品DTO
     */
    TcmProductDTO getProductBySkuCode(String skuCode);
}