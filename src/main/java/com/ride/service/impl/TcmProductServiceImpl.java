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
    public TcmProductDTO createProduct(TcmProductRequest productRequest) {
        // 创建商品主表
        TcmProduct product = new TcmProduct();
        BeanUtils.copyProperties(productRequest, product);
        product.setSales(0);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        TcmProduct savedProduct = tcmProductRepository.save(product);

        // 创建商品属性
        if (productRequest.getAttributes() != null && !productRequest.getAttributes().isEmpty()) {
            List<TcmProductAttribute> attributes = new ArrayList<>();
            for (TcmProductAttributeRequest attrRequest : productRequest.getAttributes()) {
                TcmProductAttribute attribute = new TcmProductAttribute();
                attribute.setProductId(savedProduct.getId());
                attribute.setAttributeName(attrRequest.getAttributeName());
                attribute.setAttributeValue(attrRequest.getAttributeValue());
                attribute.setSortOrder(attrRequest.getSortOrder());
                attributes.add(attribute);
            }
            tcmProductAttributeRepository.saveAll(attributes);
        }

        // 创建商品规格
        if (productRequest.getSkus() != null && !productRequest.getSkus().isEmpty()) {
            List<TcmProductSku> skus = new ArrayList<>();
            for (TcmProductSkuRequest skuRequest : productRequest.getSkus()) {
                TcmProductSku sku = new TcmProductSku();
                sku.setProductId(savedProduct.getId());
                sku.setSkuCode(skuRequest.getSkuCode());
                sku.setSkuName(skuRequest.getSkuName());
                sku.setPrice(skuRequest.getPrice());
                sku.setOriginalPrice(skuRequest.getOriginalPrice());
                sku.setStock(skuRequest.getStock());
                sku.setSales(0);
                sku.setImageUrl(skuRequest.getImageUrl());
                sku.setAttributes(skuRequest.getAttributes());
                sku.setStatus(skuRequest.getStatus());
                skus.add(sku);
            }
            tcmProductSkuRepository.saveAll(skus);
        }

        // 创建商品图片
        if (productRequest.getImages() != null && !productRequest.getImages().isEmpty()) {
            List<TcmProductImage> images = new ArrayList<>();
            for (TcmProductImageRequest imageRequest : productRequest.getImages()) {
                TcmProductImage image = new TcmProductImage();
                image.setProductId(savedProduct.getId());
                image.setSkuId(imageRequest.getSkuId());
                image.setImagePath(imageRequest.getImagePath());
                image.setImageType(imageRequest.getImageType());
                image.setSortOrder(imageRequest.getSortOrder());
                image.setRemark(imageRequest.getRemark());
                images.add(image);
            }
            tcmProductImageRepository.saveAll(images);
        }

        // 绑定已上传的图片
        if (productRequest.getImageIds() != null && !productRequest.getImageIds().isEmpty()) {
            tcmImageService.bindImagesToProduct(productRequest.getImageIds(), savedProduct.getId());
        }

        // 转换为DTO并返回
        return convertToDTO(savedProduct);
    }

    @Override
    @Transactional
    public TcmProductDTO updateProduct(Long id, TcmProductRequest productRequest) {
        // 查找商品
        Optional<TcmProduct> optionalProduct = tcmProductRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new RuntimeException("商品不存在");
        }

        TcmProduct product = optionalProduct.get();
        // 更新商品主表
        BeanUtils.copyProperties(productRequest, product);
        product.setUpdatedAt(LocalDateTime.now());
        TcmProduct updatedProduct = tcmProductRepository.save(product);

        // 更新商品属性
        if (productRequest.getAttributes() != null) {
            // 删除旧属性
            tcmProductAttributeRepository.deleteByProductId(id);
            // 添加新属性
            List<TcmProductAttribute> attributes = new ArrayList<>();
            for (TcmProductAttributeRequest attrRequest : productRequest.getAttributes()) {
                TcmProductAttribute attribute = new TcmProductAttribute();
                attribute.setProductId(id);
                attribute.setAttributeName(attrRequest.getAttributeName());
                attribute.setAttributeValue(attrRequest.getAttributeValue());
                attribute.setSortOrder(attrRequest.getSortOrder());
                attributes.add(attribute);
            }
            tcmProductAttributeRepository.saveAll(attributes);
        }

        // 更新商品规格
        if (productRequest.getSkus() != null) {
            // 删除旧规格
            tcmProductSkuRepository.deleteByProductId(id);
            // 添加新规格
            List<TcmProductSku> skus = new ArrayList<>();
            for (TcmProductSkuRequest skuRequest : productRequest.getSkus()) {
                TcmProductSku sku = new TcmProductSku();
                sku.setProductId(id);
                sku.setSkuCode(skuRequest.getSkuCode());
                sku.setSkuName(skuRequest.getSkuName());
                sku.setPrice(skuRequest.getPrice());
                sku.setOriginalPrice(skuRequest.getOriginalPrice());
                sku.setStock(skuRequest.getStock());
                sku.setSales(0); // 重置销量
                sku.setImageUrl(skuRequest.getImageUrl());
                sku.setAttributes(skuRequest.getAttributes());
                sku.setStatus(skuRequest.getStatus());
                skus.add(sku);
            }
            tcmProductSkuRepository.saveAll(skus);
        }

        // 更新商品图片
        if (productRequest.getImages() != null) {
            // 删除旧图片
            tcmProductImageRepository.deleteByProductId(id);
            // 添加新图片
            List<TcmProductImage> images = new ArrayList<>();
            for (TcmProductImageRequest imageRequest : productRequest.getImages()) {
                TcmProductImage image = new TcmProductImage();
                image.setProductId(id);
                image.setSkuId(imageRequest.getSkuId());
                image.setImagePath(imageRequest.getImagePath());
                image.setImageType(imageRequest.getImageType());
                image.setSortOrder(imageRequest.getSortOrder());
                image.setRemark(imageRequest.getRemark());
                images.add(image);
            }
            tcmProductImageRepository.saveAll(images);
        }

        // 绑定已上传的图片
        if (productRequest.getImageIds() != null && !productRequest.getImageIds().isEmpty()) {
            // 先删除旧图片关联
            tcmProductImageRepository.deleteByProductId(id);
            // 绑定新图片
            tcmImageService.bindImagesToProduct(productRequest.getImageIds(), id);
        }

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

        // 添加商品属性
        List<TcmProductAttribute> attributes = tcmProductAttributeRepository.findByProductId(product.getId());
        List<TcmProductAttributeDTO> attributeDTOs = new ArrayList<>();
        for (TcmProductAttribute attribute : attributes) {
            TcmProductAttributeDTO attributeDTO = new TcmProductAttributeDTO();
            BeanUtils.copyProperties(attribute, attributeDTO);
            attributeDTOs.add(attributeDTO);
        }
        productDTO.setAttributes(attributeDTOs);

        // 添加商品规格
        List<TcmProductSku> skus = tcmProductSkuRepository.findByProductId(product.getId());
        List<TcmProductSkuDTO> skuDTOs = new ArrayList<>();
        for (TcmProductSku sku : skus) {
            TcmProductSkuDTO skuDTO = new TcmProductSkuDTO();
            BeanUtils.copyProperties(sku, skuDTO);
            skuDTOs.add(skuDTO);
        }
        productDTO.setSkus(skuDTOs);

        // 添加商品图片
        List<TcmProductImage> images = tcmProductImageRepository.findByProductId(product.getId());
        List<TcmProductImageDTO> imageDTOs = new ArrayList<>();
        for (TcmProductImage image : images) {
            TcmProductImageDTO imageDTO = new TcmProductImageDTO();
            BeanUtils.copyProperties(image, imageDTO);
            imageDTOs.add(imageDTO);
        }
        productDTO.setImages(imageDTOs);

        return productDTO;
    }
}