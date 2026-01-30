package com.ride.service.impl;

import com.ride.dto.TcmShopDTO;
import com.ride.dto.TcmShopCategoryDTO;
import com.ride.entity.TcmShop;
import com.ride.entity.TcmShopCategory;
import com.ride.entity.TcmUser;
import com.ride.mapper.TcmShopRepository;
import com.ride.mapper.TcmShopCategoryRepository;
import com.ride.mapper.TcmUserRepository;
import com.ride.service.TcmShopService;
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
 * 店铺Service实现类
 * 实现店铺相关的业务逻辑操作
 */
@Service
public class TcmShopServiceImpl implements TcmShopService {

    @Autowired
    private TcmShopRepository tcmShopRepository;

    @Autowired
    private TcmShopCategoryRepository tcmShopCategoryRepository;

    @Autowired
    private TcmImageService tcmImageService;

    @Autowired
    private TcmUserRepository tcmUserRepository;

    @Override
    @Transactional
    public TcmShopDTO createShop(TcmShopDTO shopDTO, MultipartFile logoFile, MultipartFile bannerFile) {
        // 创建店铺实体
        TcmShop shop = new TcmShop();
        BeanUtils.copyProperties(shopDTO, shop);
        shop.setStatus(1); // 默认店铺状态为正常营业
        shop.setIsVerified(0); // 默认店铺未认证
        shop.setCreatedAt(LocalDateTime.now());
        shop.setUpdatedAt(LocalDateTime.now());

        // 处理logo文件上传
        if (logoFile != null && !logoFile.isEmpty()) {
            try {
                // 上传logo图片并获取图片路径
                // 注意：这里需要根据实际的图片上传服务进行修改
                // 假设tcmImageService.uploadImage方法返回图片路径
                String logoPath = tcmImageService.uploadImage(logoFile, "店铺logo").getImagePath();
                shop.setLogo(logoPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 处理banner文件上传
        if (bannerFile != null && !bannerFile.isEmpty()) {
            try {
                // 上传banner图片并获取图片路径
                // 注意：这里需要根据实际的图片上传服务进行修改
                // 假设tcmImageService.uploadImage方法返回图片路径
                String bannerPath = tcmImageService.uploadImage(bannerFile, "店铺横幅").getImagePath();
                shop.setBanner(bannerPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 保存店铺信息
        TcmShop savedShop = tcmShopRepository.save(shop);

        // 更新用户的是否开店标识为1
        if (savedShop.getOwnerId() != null) {
            Optional<TcmUser> optionalUser = tcmUserRepository.findById(savedShop.getOwnerId());
            if (optionalUser.isPresent()) {
                TcmUser user = optionalUser.get();
                user.setHasShop(1);
                tcmUserRepository.save(user);
            }
        }

        // 转换为DTO并返回
        return convertToDTO(savedShop);
    }

    @Override
    @Transactional
    public TcmShopDTO updateShop(Long id, TcmShopDTO shopDTO, MultipartFile logoFile, MultipartFile bannerFile) {
        // 查找店铺
        Optional<TcmShop> optionalShop = tcmShopRepository.findById(id);
        if (!optionalShop.isPresent()) {
            throw new RuntimeException("店铺不存在");
        }

        // 更新店铺信息
        TcmShop shop = optionalShop.get();
        BeanUtils.copyProperties(shopDTO, shop);
        shop.setUpdatedAt(LocalDateTime.now());

        // 处理logo文件上传
        if (logoFile != null && !logoFile.isEmpty()) {
            try {
                // 上传logo图片并获取图片路径
                // 注意：这里需要根据实际的图片上传服务进行修改
                // 假设tcmImageService.uploadImage方法返回图片路径
                String logoPath = tcmImageService.uploadImage(logoFile, "店铺logo").getImagePath();
                shop.setLogo(logoPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 处理banner文件上传
        if (bannerFile != null && !bannerFile.isEmpty()) {
            try {
                // 上传banner图片并获取图片路径
                // 注意：这里需要根据实际的图片上传服务进行修改
                // 假设tcmImageService.uploadImage方法返回图片路径
                String bannerPath = tcmImageService.uploadImage(bannerFile, "店铺横幅").getImagePath();
                shop.setBanner(bannerPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 保存店铺信息
        TcmShop updatedShop = tcmShopRepository.save(shop);

        // 转换为DTO并返回
        return convertToDTO(updatedShop);
    }

    @Override
    public TcmShopDTO getShopById(Long id) {
        // 查找店铺
        Optional<TcmShop> optionalShop = tcmShopRepository.findById(id);
        if (!optionalShop.isPresent()) {
            throw new RuntimeException("店铺不存在");
        }

        // 转换为DTO并返回
        return convertToDTO(optionalShop.get());
    }

    @Override
    @Transactional
    public void deleteShop(Long id) {
        // 删除店铺相关的分类
        tcmShopCategoryRepository.deleteByShopId(id);
        // 删除店铺
        tcmShopRepository.deleteById(id);
    }

    @Override
    public Page<TcmShopDTO> getShops(String name, Long ownerId, Integer status, Pageable pageable) {
        Page<TcmShop> shops;
        if (name != null && !name.isEmpty()) {
            shops = tcmShopRepository.findByNameContainingAndStatus(name, status, pageable);
        } else if (ownerId != null) {
            shops = tcmShopRepository.findByOwnerIdAndStatus(ownerId, status, pageable);
        } else if (status != null) {
            shops = tcmShopRepository.findByStatus(status, pageable);
        } else {
            shops = tcmShopRepository.findAll(pageable);
        }
        return shops.map(this::convertToDTO);
    }

    @Override
    @Transactional
    public void updateShopStatus(Long id, Integer status) {
        // 查找店铺
        Optional<TcmShop> optionalShop = tcmShopRepository.findById(id);
        if (!optionalShop.isPresent()) {
            throw new RuntimeException("店铺不存在");
        }

        // 更新店铺状态
        TcmShop shop = optionalShop.get();
        shop.setStatus(status);
        shop.setUpdatedAt(LocalDateTime.now());
        tcmShopRepository.save(shop);
    }

    @Override
    @Transactional
    public void verifyShop(Long id, Integer isVerified) {
        // 查找店铺
        Optional<TcmShop> optionalShop = tcmShopRepository.findById(id);
        if (!optionalShop.isPresent()) {
            throw new RuntimeException("店铺不存在");
        }

        // 更新店铺认证状态
        TcmShop shop = optionalShop.get();
        shop.setIsVerified(isVerified);
        shop.setUpdatedAt(LocalDateTime.now());
        tcmShopRepository.save(shop);
    }

    @Override
    @Transactional
    public TcmShopCategoryDTO addShopCategory(Long shopId, TcmShopCategoryDTO categoryDTO) {
        // 检查店铺是否存在
        Optional<TcmShop> optionalShop = tcmShopRepository.findById(shopId);
        if (!optionalShop.isPresent()) {
            throw new RuntimeException("店铺不存在");
        }

        // 创建店铺分类实体
        TcmShopCategory category = new TcmShopCategory();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setShopId(shopId);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        // 保存店铺分类
        TcmShopCategory savedCategory = tcmShopCategoryRepository.save(category);

        // 转换为DTO并返回
        return convertToCategoryDTO(savedCategory);
    }

    @Override
    @Transactional
    public TcmShopCategoryDTO updateShopCategory(Long id, TcmShopCategoryDTO categoryDTO) {
        // 查找店铺分类
        Optional<TcmShopCategory> optionalCategory = tcmShopCategoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new RuntimeException("店铺分类不存在");
        }

        // 更新店铺分类
        TcmShopCategory category = optionalCategory.get();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUpdatedAt(LocalDateTime.now());

        // 保存店铺分类
        TcmShopCategory updatedCategory = tcmShopCategoryRepository.save(category);

        // 转换为DTO并返回
        return convertToCategoryDTO(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteShopCategory(Long id) {
        // 删除店铺分类
        tcmShopCategoryRepository.deleteById(id);
    }

    @Override
    public List<TcmShopCategoryDTO> getShopCategories(Long shopId) {
        // 查询店铺分类
        List<TcmShopCategory> categories = tcmShopCategoryRepository.findByShopId(shopId);
        List<TcmShopCategoryDTO> categoryDTOs = new ArrayList<>();

        // 转换为DTO
        for (TcmShopCategory category : categories) {
            categoryDTOs.add(convertToCategoryDTO(category));
        }

        return categoryDTOs;
    }

    // 转换店铺实体为DTO
    private TcmShopDTO convertToDTO(TcmShop shop) {
        TcmShopDTO shopDTO = new TcmShopDTO();
        BeanUtils.copyProperties(shop, shopDTO);
        return shopDTO;
    }

    // 转换店铺分类实体为DTO
    private TcmShopCategoryDTO convertToCategoryDTO(TcmShopCategory category) {
        TcmShopCategoryDTO categoryDTO = new TcmShopCategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        return categoryDTO;
    }
}
