package com.ride.service;

import com.ride.dto.TcmProductImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 图片服务接口
 * 用于图片相关的业务逻辑操作
 */
public interface TcmImageService {

    /**
     * 上传图片
     * @param file 图片文件
     * @param productId 商品ID
     * @param skuId SKU ID
     * @param imageType 图片类型
     * @param sortOrder 排序
     * @param remark 备注
     * @return 图片DTO
     */
    TcmProductImageDTO uploadImage(MultipartFile file, Long productId, Long skuId, Integer imageType, Integer sortOrder, String remark);

    /**
     * 获取用户未绑定的图片列表
     * @param userId 用户ID
     * @return 图片DTO列表
     */
    List<TcmProductImageDTO> getUnboundImages(Long userId);

    /**
     * 删除图片
     * @param id 图片ID
     */
    void deleteImage(Long id);

    /**
     * 绑定图片到商品
     * @param imageIds 图片ID列表
     * @param productId 商品ID
     */
    void bindImagesToProduct(List<Long> imageIds, Long productId);
}
