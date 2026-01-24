package com.ride.service.impl;

import com.ride.dto.TcmProductImageDTO;
import com.ride.entity.TcmProductImage;
import com.ride.mapper.TcmProductImageRepository;
import com.ride.service.TcmImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 图片服务实现类
 * 实现图片相关的业务逻辑
 */
@Service
@Transactional
public class TcmImageServiceImpl implements TcmImageService {

    @Autowired
    private TcmProductImageRepository tcmProductImageRepository;

    @Override
    public TcmProductImageDTO uploadImage(MultipartFile file, Long productId, Long skuId, Integer imageType, Integer sortOrder, String remark) {
        try {
            // 确保文件不为空
            if (file.isEmpty()) {
                throw new IllegalArgumentException("图片文件不能为空");
            }
            
            // 生成唯一文件名
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            
            // 定义上传目录
            String uploadDir = "uploads/images";
            Path uploadPath = Paths.get(uploadDir);
            
            // 确保上传目录存在
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            
            // 保存文件到服务器
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath);
            
            // 创建图片实体
            TcmProductImage image = new TcmProductImage();
            image.setProductId(productId);
            image.setSkuId(skuId);
            image.setImagePath(filePath.toString()); // 保存文件路径
            image.setImageType(imageType != null ? imageType : 0);
            image.setSortOrder(sortOrder != null ? sortOrder : 0);
            image.setRemark(remark);
            
            // 保存图片
            TcmProductImage savedImage = tcmProductImageRepository.save(image);
            
            // 转换为DTO并返回
            return convertToDTO(savedImage);
        } catch (IOException e) {
            throw new RuntimeException("图片上传失败: " + e.getMessage(), e);
        }
    }

    @Override
    public List<TcmProductImageDTO> getUnboundImages(Long userId) {
        // 查询未绑定到商品的图片
        List<TcmProductImage> images = tcmProductImageRepository.findByProductIdIsNull();
        
        // 转换为DTO并返回
        return images.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteImage(Long id) {
        // 删除图片
        tcmProductImageRepository.deleteById(id);
    }

    @Override
    public void bindImagesToProduct(List<Long> imageIds, Long productId) {
        // 查询图片
        List<TcmProductImage> images = tcmProductImageRepository.findAllById(imageIds);
        
        // 绑定到商品
        for (TcmProductImage image : images) {
            image.setProductId(productId);
        }
        
        // 保存修改
        tcmProductImageRepository.saveAll(images);
    }

    /**
     * 转换为DTO
     * @param image 图片实体
     * @return 图片DTO
     */
    private TcmProductImageDTO convertToDTO(TcmProductImage image) {
        TcmProductImageDTO dto = new TcmProductImageDTO();
        dto.setId(image.getId());
        dto.setProductId(image.getProductId());
        dto.setSkuId(image.getSkuId());
        dto.setImagePath(image.getImagePath());
        dto.setImageType(image.getImageType());
        dto.setSortOrder(image.getSortOrder());
        dto.setRemark(image.getRemark());
        return dto;
    }
}
