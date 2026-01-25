package com.ride.service.impl;

import com.ride.dto.*;
import com.ride.entity.*;
import com.ride.mapper.*;
import com.ride.service.TcmProductService;
import com.ride.service.TcmImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 商品Service实现类
 * 实现商品相关的业务逻辑操作
 */
@Service
public class TcmProductServiceImpl implements TcmProductService {

    @Autowired
    private TcmProductRepository tcmProductRepository;

    @Autowired
    private TcmProductAttributeRepository tcmProductAttributeRepository;

    @Autowired
    private TcmProductSkuRepository tcmProductSkuRepository;

    @Autowired
    private TcmProductImageRepository tcmProductImageRepository;

    @Autowired
    private TcmImageService tcmImageService;

    @Override
    @Transactional
    public TcmProductDTO createProduct(TcmProductDTO productDTO, MultipartFile[] files) {
        // 创建商品主表
        TcmProduct product = new TcmProduct();
        BeanUtils.copyProperties(productDTO, product);
        product.setSales(0);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        TcmProduct savedProduct = tcmProductRepository.save(product);

        // 处理图片上传
        if (files != null && files.length > 0) {
            List<TcmProductImage> images = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                if (!file.isEmpty()) {
                    // 上传图片并获取图片路径
                    TcmProductImageDTO imageDTO = tcmImageService.uploadImage(file, null);
                    String imagePath = imageDTO.getImagePath();
                    
                    // 创建商品图片记录
                    TcmProductImage image = new TcmProductImage();
                    image.setProductId(savedProduct.getId());
                    image.setImagePath(imagePath);
                    image.setImageType(1); // 1-主图，2-副图，3-详情图
                    image.setSortOrder(i);
                    images.add(image);
                }
            }
            if (!images.isEmpty()) {
                tcmProductImageRepository.saveAll(images);
                
                // 设置主图路径
                if (!images.isEmpty()) {
                    product.setMainImage(images.get(0).getImagePath());
                    // 设置副图路径
                    if (images.size() > 1) product.setSubImage1(images.get(1).getImagePath());
                    if (images.size() > 2) product.setSubImage2(images.get(2).getImagePath());
                    if (images.size() > 3) product.setSubImage3(images.get(3).getImagePath());
                    if (images.size() > 4) product.setSubImage4(images.get(4).getImagePath());
                    if (images.size() > 5) product.setSubImage5(images.get(5).getImagePath());
                    tcmProductRepository.save(product);
                }
            }
        }



        // 转换为DTO并返回
        return convertToDTO(savedProduct);
    }

    @Override
    @Transactional
    public TcmProductDTO updateProduct(Long id, TcmProductDTO productDTO) {
        // 查找商品
        Optional<TcmProduct> optionalProduct = tcmProductRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("商品不存在");
        }

        TcmProduct product = optionalProduct.get();
        // 更新商品主表
        BeanUtils.copyProperties(productDTO, product);
        product.setUpdatedAt(LocalDateTime.now());
        TcmProduct updatedProduct = tcmProductRepository.save(product);



        // 绑定已上传的图片
        // 注意：这里需要在 TcmProductDTO 中添加 imageIds 字段

        // 转换为DTO并返回
        return convertToDTO(updatedProduct);
    }

    @Override
    public TcmProductDTO getProductById(Long id) {
        Optional<TcmProduct> optionalProduct = tcmProductRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("商品不存在");
        }
        return convertToDTO(optionalProduct.get());
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        // 删除商品相关数据
        tcmProductAttributeRepository.deleteByProductId(id);
        tcmProductSkuRepository.deleteByProductId(id);
        tcmProductImageRepository.deleteByProductId(id);
        // 删除商品主表
        tcmProductRepository.deleteById(id);
    }

    @Override
    public Page<TcmProductDTO> getProducts(String name, Long categoryId, String brand, Integer status, Pageable pageable) {
        Page<TcmProduct> products;
        if (name != null && !name.isEmpty()) {
            products = tcmProductRepository.findByNameContainingAndStatus(name, status, pageable);
        } else if (categoryId != null) {
            products = tcmProductRepository.findByCategoryIdAndStatus(categoryId, status, pageable);
        } else if (brand != null && !brand.isEmpty()) {
            products = tcmProductRepository.findByBrandAndStatus(brand, status, pageable);
        } else {
            // 这里需要修改，因为JpaRepository没有直接的findByStatus方法
            // 暂时返回所有商品
            products = tcmProductRepository.findAll(pageable);
        }
        return products.map(this::convertToDTO);
    }

    @Override
    public List<TcmProductDTO> getHotProducts(int limit) {
        List<TcmProduct> products = tcmProductRepository.findTopBySales(1, limit);
        List<TcmProductDTO> productDTOs = new ArrayList<>();
        for (TcmProduct product : products) {
            productDTOs.add(convertToDTO(product));
        }
        return productDTOs;
    }

    @Override
    @Transactional
    public void updateProductStatus(Long id, Integer status) {
        Optional<TcmProduct> optionalProduct = tcmProductRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("商品不存在");
        }
        TcmProduct product = optionalProduct.get();
        product.setStatus(status);
        product.setUpdatedAt(LocalDateTime.now());
        tcmProductRepository.save(product);
    }

    @Override
    @Transactional
    public void updateProductStock(Long id, Integer stock) {
        Optional<TcmProduct> optionalProduct = tcmProductRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("商品不存在");
        }
        TcmProduct product = optionalProduct.get();
        product.setStock(stock);
        product.setUpdatedAt(LocalDateTime.now());
        tcmProductRepository.save(product);
    }

    @Override
    public TcmProductDTO getProductBySkuCode(String skuCode) {
        TcmProductSku sku = tcmProductSkuRepository.findBySkuCode(skuCode);
        if (sku == null) {
            throw new RuntimeException("商品规格不存在");
        }
        return getProductById(sku.getProductId());
    }

    // 转换为DTO
    private TcmProductDTO convertToDTO(TcmProduct product) {
        TcmProductDTO productDTO = new TcmProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }
}