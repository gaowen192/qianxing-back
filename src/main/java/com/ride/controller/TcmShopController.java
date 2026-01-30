package com.ride.controller;

import com.ride.common.Result;
import com.ride.dto.TcmShopDTO;
import com.ride.dto.TcmShopCategoryDTO;
import com.ride.service.TcmShopService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 店铺Controller类
 * 处理店铺相关的HTTP请求
 */
@RestController
@RequestMapping("tcm/shops")
@Tag(name = "店铺管理", description = "店铺相关的API接口")
public class TcmShopController {

    private static final Logger logger = LoggerFactory.getLogger(TcmShopController.class);

    @Autowired
    private TcmShopService tcmShopService;

    @Operation(summary = "创建店铺", description = "创建新的店铺信息，包括基本信息、logo和横幅")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺创建成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效或文件格式不支持"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<TcmShopDTO> createShop(
            @Parameter(description = "店铺名称", required = true)
            @RequestParam("name") String name,
            @Parameter(description = "店铺描述")
            @RequestParam(value = "description", required = false) String description,
            @Parameter(description = "店主ID")
            @RequestParam(value = "ownerId", required = false) Long ownerId,
            @Parameter(description = "店主名称")
            @RequestParam(value = "ownerName", required = false) String ownerName,
            @Parameter(description = "联系电话")
            @RequestParam(value = "contactPhone", required = false) String contactPhone,
            @Parameter(description = "联系邮箱")
            @RequestParam(value = "contactEmail", required = false) String contactEmail,
            @Parameter(description = "店铺地址")
            @RequestParam(value = "address", required = false) String address,
            @Parameter(description = "店铺logo", required = false)
            @RequestPart(value = "logoFile", required = false) MultipartFile logoFile,
            @Parameter(description = "店铺横幅", required = false)
            @RequestPart(value = "bannerFile", required = false) MultipartFile bannerFile) {
        logger.info("===============Received request to create shop: {}", name);
        try {
            TcmShopDTO shopDTO = new TcmShopDTO();
            shopDTO.setName(name);
            shopDTO.setDescription(description);
            shopDTO.setOwnerId(ownerId);
            shopDTO.setOwnerName(ownerName);
            shopDTO.setContactPhone(contactPhone);
            shopDTO.setContactEmail(contactEmail);
            shopDTO.setAddress(address);

            TcmShopDTO createdShopDTO = tcmShopService.createShop(shopDTO, logoFile, bannerFile);
            return Result.success("店铺创建成功", createdShopDTO);
        } catch (Exception e) {
            logger.error("===============Error creating shop", e);
            return Result.error("店铺创建失败：" + e.getMessage());
        }
    }

    @Operation(summary = "更新店铺", description = "根据ID更新店铺信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺更新成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "404", description = "店铺不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<TcmShopDTO> updateShop(
            @Parameter(description = "店铺ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "店铺名称")
            @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "店铺描述")
            @RequestParam(value = "description", required = false) String description,
            @Parameter(description = "店主ID")
            @RequestParam(value = "ownerId", required = false) Long ownerId,
            @Parameter(description = "店主名称")
            @RequestParam(value = "ownerName", required = false) String ownerName,
            @Parameter(description = "联系电话")
            @RequestParam(value = "contactPhone", required = false) String contactPhone,
            @Parameter(description = "联系邮箱")
            @RequestParam(value = "contactEmail", required = false) String contactEmail,
            @Parameter(description = "店铺地址")
            @RequestParam(value = "address", required = false) String address,
            @Parameter(description = "店铺状态，1-正常营业，0-关闭")
            @RequestParam(value = "status", required = false) Integer status,
            @Parameter(description = "店铺logo", required = false)
            @RequestPart(value = "logoFile", required = false) MultipartFile logoFile,
            @Parameter(description = "店铺横幅", required = false)
            @RequestPart(value = "bannerFile", required = false) MultipartFile bannerFile) {
        logger.info("===============Received request to update shop: {}", id);
        try {
            TcmShopDTO shopDTO = new TcmShopDTO();
            shopDTO.setName(name);
            shopDTO.setDescription(description);
            shopDTO.setOwnerId(ownerId);
            shopDTO.setOwnerName(ownerName);
            shopDTO.setContactPhone(contactPhone);
            shopDTO.setContactEmail(contactEmail);
            shopDTO.setAddress(address);
            shopDTO.setStatus(status);

            TcmShopDTO updatedShopDTO = tcmShopService.updateShop(id, shopDTO, logoFile, bannerFile);
            return Result.success("店铺更新成功", updatedShopDTO);
        } catch (Exception e) {
            logger.error("===============Error updating shop: {}", id, e);
            return Result.error("店铺更新失败：" + e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询店铺", description = "根据店铺ID查询店铺详情")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺查询成功"),
        @ApiResponse(responseCode = "404", description = "店铺不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/{id}")
    public Result<TcmShopDTO> getShopById(
            @Parameter(description = "店铺ID", required = true, example = "1")
            @PathVariable Long id) {
        logger.info("===============Received request to get shop by id: {}", id);
        try {
            TcmShopDTO shopDTO = tcmShopService.getShopById(id);
            return Result.success("店铺查询成功", shopDTO);
        } catch (Exception e) {
            logger.error("===============Error getting shop by id: {}", id, e);
            return Result.error("店铺查询失败：" + e.getMessage());
        }
    }

    @Operation(summary = "删除店铺", description = "根据店铺ID删除店铺")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺删除成功"),
        @ApiResponse(responseCode = "404", description = "店铺不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @DeleteMapping("/{id}")
    public Result<Void> deleteShop(
            @Parameter(description = "店铺ID", required = true, example = "1")
            @PathVariable Long id) {
        logger.info("===============Received request to delete shop: {}", id);
        try {
            tcmShopService.deleteShop(id);
            return Result.success("店铺删除成功");
        } catch (Exception e) {
            logger.error("===============Error deleting shop: {}", id, e);
            return Result.error("店铺删除失败：" + e.getMessage());
        }
    }

    @Operation(summary = "分页查询店铺", description = "根据条件分页查询店铺列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺查询成功"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping
    public Result<Page<TcmShopDTO>> getShops(
            @Parameter(description = "店铺名称")
            @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "店主ID")
            @RequestParam(value = "ownerId", required = false) Long ownerId,
            @Parameter(description = "店铺状态，1-正常营业，0-关闭")
            @RequestParam(value = "status", required = false, defaultValue = "1") Integer status,
            @Parameter(description = "页码", example = "1")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @Parameter(description = "每页大小", example = "10")
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        logger.info("===============Received request to list shops, name: {}, ownerId: {}, status: {}, page: {}, size: {}", 
                name, ownerId, status, page, size);
        try {
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<TcmShopDTO> shops = tcmShopService.getShops(name, ownerId, status, pageable);
            return Result.success("店铺查询成功", shops);
        } catch (Exception e) {
            logger.error("===============Error listing shops", e);
            return Result.error("店铺查询失败：" + e.getMessage());
        }
    }

    @Operation(summary = "更改店铺状态", description = "更新店铺的营业状态")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺状态更新成功"),
        @ApiResponse(responseCode = "404", description = "店铺不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/{id}/status")
    public Result<Void> updateShopStatus(
            @Parameter(description = "店铺ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "店铺状态，1-正常营业，0-关闭", required = true, example = "1")
            @RequestParam(value = "status") Integer status) {
        logger.info("===============Received request to update shop status: {}, status: {}", id, status);
        try {
            tcmShopService.updateShopStatus(id, status);
            return Result.success("店铺状态更新成功");
        } catch (Exception e) {
            logger.error("===============Error updating shop status: {}, status: {}", id, status, e);
            return Result.error("店铺状态更新失败：" + e.getMessage());
        }
    }

    @Operation(summary = "验证店铺", description = "更新店铺的认证状态")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺验证成功"),
        @ApiResponse(responseCode = "404", description = "店铺不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/{id}/verify")
    public Result<Void> verifyShop(
            @Parameter(description = "店铺ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "是否认证，1-已认证，0-未认证", required = true, example = "1")
            @RequestParam(value = "isVerified") Integer isVerified) {
        logger.info("===============Received request to verify shop: {}, isVerified: {}", id, isVerified);
        try {
            tcmShopService.verifyShop(id, isVerified);
            return Result.success("店铺验证成功");
        } catch (Exception e) {
            logger.error("===============Error verifying shop: {}, isVerified: {}", id, isVerified, e);
            return Result.error("店铺验证失败：" + e.getMessage());
        }
    }

    @Operation(summary = "添加店铺分类", description = "为指定店铺添加新的分类")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺分类添加成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "404", description = "店铺不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/{shopId}/categories")
    public Result<TcmShopCategoryDTO> addShopCategory(
            @Parameter(description = "店铺ID", required = true, example = "1")
            @PathVariable Long shopId,
            @Parameter(description = "分类名称", required = true)
            @RequestParam("categoryName") String categoryName,
            @Parameter(description = "排序顺序")
            @RequestParam(value = "sortOrder", required = false, defaultValue = "0") Integer sortOrder) {
        logger.info("===============Received request to add category for shop: {}, categoryName: {}", shopId, categoryName);
        try {
            TcmShopCategoryDTO categoryDTO = new TcmShopCategoryDTO();
            categoryDTO.setCategoryName(categoryName);
            categoryDTO.setSortOrder(sortOrder);

            TcmShopCategoryDTO createdCategoryDTO = tcmShopService.addShopCategory(shopId, categoryDTO);
            return Result.success("店铺分类添加成功", createdCategoryDTO);
        } catch (Exception e) {
            logger.error("===============Error adding category for shop: {}, categoryName: {}", shopId, categoryName, e);
            return Result.error("店铺分类添加失败：" + e.getMessage());
        }
    }

    @Operation(summary = "更新店铺分类", description = "更新指定的店铺分类信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺分类更新成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "404", description = "店铺分类不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/categories/{id}")
    public Result<TcmShopCategoryDTO> updateShopCategory(
            @Parameter(description = "分类ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "分类名称", required = true)
            @RequestParam("categoryName") String categoryName,
            @Parameter(description = "排序顺序")
            @RequestParam(value = "sortOrder", required = false, defaultValue = "0") Integer sortOrder) {
        logger.info("===============Received request to update category: {}, categoryName: {}", id, categoryName);
        try {
            TcmShopCategoryDTO categoryDTO = new TcmShopCategoryDTO();
            categoryDTO.setCategoryName(categoryName);
            categoryDTO.setSortOrder(sortOrder);

            TcmShopCategoryDTO updatedCategoryDTO = tcmShopService.updateShopCategory(id, categoryDTO);
            return Result.success("店铺分类更新成功", updatedCategoryDTO);
        } catch (Exception e) {
            logger.error("===============Error updating category: {}, categoryName: {}", id, categoryName, e);
            return Result.error("店铺分类更新失败：" + e.getMessage());
        }
    }

    @Operation(summary = "删除店铺分类", description = "删除指定的店铺分类")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺分类删除成功"),
        @ApiResponse(responseCode = "404", description = "店铺分类不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @DeleteMapping("/categories/{id}")
    public Result<Void> deleteShopCategory(
            @Parameter(description = "分类ID", required = true, example = "1")
            @PathVariable Long id) {
        logger.info("===============Received request to delete category: {}", id);
        try {
            tcmShopService.deleteShopCategory(id);
            return Result.success("店铺分类删除成功");
        } catch (Exception e) {
            logger.error("===============Error deleting category: {}", id, e);
            return Result.error("店铺分类删除失败：" + e.getMessage());
        }
    }

    @Operation(summary = "查询店铺分类", description = "查询指定店铺的所有分类")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "店铺分类查询成功"),
        @ApiResponse(responseCode = "404", description = "店铺不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/{shopId}/categories")
    public Result<List<TcmShopCategoryDTO>> getShopCategories(
            @Parameter(description = "店铺ID", required = true, example = "1")
            @PathVariable Long shopId) {
        logger.info("===============Received request to get categories for shop: {}", shopId);
        try {
            List<TcmShopCategoryDTO> categories = tcmShopService.getShopCategories(shopId);
            return Result.success("店铺分类查询成功", categories);
        } catch (Exception e) {
            logger.error("===============Error getting categories for shop: {}", shopId, e);
            return Result.error("店铺分类查询失败：" + e.getMessage());
        }
    }
}
