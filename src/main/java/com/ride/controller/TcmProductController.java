package com.ride.controller;

import com.ride.common.Result;
import com.ride.dto.TcmProductDTO;
import com.ride.service.TcmProductService;
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

import java.math.BigDecimal;

import java.util.List;

/**
 * 商品Controller
 * 处理商品相关的HTTP请求
 */
@RestController
@RequestMapping("tcm/products")
@Tag(name = "商品管理", description = "商品相关的API接口")
public class TcmProductController {

    private static final Logger logger = LoggerFactory.getLogger(TcmProductController.class);

    @Autowired
    private TcmProductService tcmProductService;

    @Operation(summary = "创建商品", description = "创建新的商品信息，包括基本信息和图片")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "商品创建成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效或文件格式不支持"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result<TcmProductDTO> createProduct(
            @Parameter(description = "商品名称", required = true)
            @RequestParam("name") String name,
            @Parameter(description = "商品描述")
            @RequestParam(value = "description", required = false) String description,
            @Parameter(description = "分类ID")
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @Parameter(description = "用户ID")
            @RequestParam(value = "userId", required = false) Long userId,
            @Parameter(description = "店铺ID")
            @RequestParam(value = "shopId", required = false) Long shopId,
            @Parameter(description = "店铺名称")
            @RequestParam(value = "shopName", required = false) String shopName,
            @Parameter(description = "品牌")
            @RequestParam(value = "brand", required = false) String brand,
            @Parameter(description = "价格")
            @RequestParam(value = "price", required = false) BigDecimal price,
            @Parameter(description = "库存")
            @RequestParam(value = "stock", required = false) Integer stock,
            @Parameter(description = "状态，1-上架，0-下架")
            @RequestParam(value = "status", required = false) Integer status,
            @Parameter(description = "是否包邮，1-是，0-否")
            @RequestParam(value = "isFreeShipping", required = false) Integer isFreeShipping,
            @Parameter(description = "运费")
            @RequestParam(value = "shippingFee", required = false) BigDecimal shippingFee,
            @Parameter(description = "重量")
            @RequestParam(value = "weight", required = false) BigDecimal weight,
            @Parameter(description = "尺寸")
            @RequestParam(value = "size", required = false) String size,
            @Parameter(description = "颜色")
            @RequestParam(value = "color", required = false) String color,
            @Parameter(description = "材质")
            @RequestParam(value = "material", required = false) String material,
            @Parameter(description = "产地")
            @RequestParam(value = "origin", required = false) String origin,
            @Parameter(description = "商品图片", required = false)
            @RequestPart(value = "files", required = false) MultipartFile[] files) {
        logger.info("===============Received request to create product: {}", name);
        try {
            TcmProductDTO productDTO = new TcmProductDTO();
            productDTO.setName(name);
            productDTO.setDescription(description);
            productDTO.setCategoryId(categoryId);
            productDTO.setUserId(userId);
            productDTO.setShopId(shopId);
            productDTO.setShopName(shopName);
            productDTO.setBrand(brand);
            productDTO.setPrice(price);
            productDTO.setStock(stock);
            productDTO.setStatus(status);
            productDTO.setIsFreeShipping(isFreeShipping);
            productDTO.setShippingFee(shippingFee);
            productDTO.setWeight(weight);
            productDTO.setSize(size);
            productDTO.setColor(color);
            productDTO.setMaterial(material);
            productDTO.setOrigin(origin);
            
            TcmProductDTO createdProductDTO = tcmProductService.createProduct(productDTO, files);
            return Result.success("商品创建成功", createdProductDTO);
        } catch (Exception e) {
            logger.error("===============Error creating product", e);
            return Result.error("商品创建失败：" + e.getMessage());
        }
    }

    @Operation(summary = "更新商品", description = "根据ID更新商品信息")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "商品更新成功"),
        @ApiResponse(responseCode = "400", description = "请求参数无效"),
        @ApiResponse(responseCode = "404", description = "商品不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/{id}")
    public Result<TcmProductDTO> updateProduct(
            @Parameter(description = "商品ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "商品数据", required = true)
            @RequestBody TcmProductDTO productDTO) {
        logger.info("===============Received request to update product: {}", id);
        try {
            TcmProductDTO updatedProductDTO = tcmProductService.updateProduct(id, productDTO);
            return Result.success("商品更新成功", updatedProductDTO);
        } catch (Exception e) {
            logger.error("===============Error updating product: {}", id, e);
            return Result.error("商品更新失败：" + e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询商品", description = "根据商品ID查询商品详情")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "商品查询成功"),
        @ApiResponse(responseCode = "404", description = "商品不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/{id}")
    public Result<TcmProductDTO> getProductById(
            @Parameter(description = "商品ID", required = true, example = "1")
            @PathVariable Long id) {
        logger.info("===============Received request to get product by id: {}", id);
        try {
            TcmProductDTO productDTO = tcmProductService.getProductById(id);
            return Result.success("商品查询成功", productDTO);
        } catch (Exception e) {
            logger.error("===============Error getting product by id: {}", id, e);
            return Result.error("商品查询失败：" + e.getMessage());
        }
    }

    @Operation(summary = "删除商品", description = "根据商品ID删除商品")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "商品删除成功"),
        @ApiResponse(responseCode = "404", description = "商品不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @DeleteMapping("/{id}")
    public Result<Void> deleteProduct(
            @Parameter(description = "商品ID", required = true, example = "1")
            @PathVariable Long id) {
        logger.info("===============Received request to delete product: {}", id);
        try {
            tcmProductService.deleteProduct(id);
            return Result.success("商品删除成功");
        } catch (Exception e) {
            logger.error("===============Error deleting product: {}", id, e);
            return Result.error("商品删除失败：" + e.getMessage());
        }
    }

    @Operation(summary = "分页查询商品", description = "根据条件分页查询商品列表，支持按名称、分类、品牌和状态筛选")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "商品查询成功"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping
    public Result<Page<TcmProductDTO>> getProducts(
            @Parameter(description = "商品名称")
            @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "分类ID")
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @Parameter(description = "品牌")
            @RequestParam(value = "brand", required = false) String brand,
            @Parameter(description = "店铺ID")
            @RequestParam(value = "shopId", required = false) Long shopId,
            @Parameter(description = "店铺名称")
            @RequestParam(value = "shopName", required = false) String shopName,
            @Parameter(description = "状态，1-上架，0-下架")
            @RequestParam(value = "status", required = false, defaultValue = "1") Integer status,
            @Parameter(description = "页码", example = "1")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @Parameter(description = "每页大小", example = "10")
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @Parameter(description = "排序字段", example = "createdAt")
            @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort,
            @Parameter(description = "排序方式，asc-升序，desc-降序", example = "desc")
            @RequestParam(value = "order", required = false, defaultValue = "desc") String order) {
        logger.info("===============Received request to list products, name: {}, categoryId: {}, brand: {}, shopId: {}, shopName: {}, status: {}, page: {}, size: {}, sort: {}, order: {}", 
                name, categoryId, brand, shopId, shopName, status, page, size, sort, order);
        try {
            Sort.Direction direction = order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sort));

            Page<TcmProductDTO> products = tcmProductService.getProducts(name, categoryId, brand, shopId, shopName, status, pageable);
            return Result.success("商品查询成功", products);
        } catch (Exception e) {
            logger.error("===============Error listing products", e);
            return Result.error("商品查询失败：" + e.getMessage());
        }
    }

    @Operation(summary = "查询热销商品", description = "查询销量前N的商品列表")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "热销商品查询成功"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/hot")
    public Result<List<TcmProductDTO>> getHotProducts(
            @Parameter(description = "限制数量", example = "10")
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        logger.info("===============Received request to get hot products, limit: {}", limit);
        try {
            List<TcmProductDTO> products = tcmProductService.getHotProducts(limit);
            return Result.success("热销商品查询成功", products);
        } catch (Exception e) {
            logger.error("===============Error getting hot products", e);
            return Result.error("热销商品查询失败：" + e.getMessage());
        }
    }

    @Operation(summary = "上下架商品", description = "更新商品的上架/下架状态")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "商品状态更新成功"),
        @ApiResponse(responseCode = "404", description = "商品不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/{id}/status")
    public Result<Void> updateProductStatus(
            @Parameter(description = "商品ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "状态，1-上架，0-下架", required = true, example = "1")
            @RequestParam(value = "status") Integer status) {
        logger.info("===============Received request to update product status: {}, status: {}", id, status);
        try {
            tcmProductService.updateProductStatus(id, status);
            return Result.success("商品状态更新成功");
        } catch (Exception e) {
            logger.error("===============Error updating product status: {}, status: {}", id, status, e);
            return Result.error("商品状态更新失败：" + e.getMessage());
        }
    }

    @Operation(summary = "更新商品库存", description = "更新商品的库存数量")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "商品库存更新成功"),
        @ApiResponse(responseCode = "404", description = "商品不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/{id}/stock")
    public Result<Void> updateProductStock(
            @Parameter(description = "商品ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "库存数量", required = true, example = "100")
            @RequestParam(value = "stock") Integer stock) {
        logger.info("===============Received request to update product stock: {}, stock: {}", id, stock);
        try {
            tcmProductService.updateProductStock(id, stock);
            return Result.success("商品库存更新成功");
        } catch (Exception e) {
            logger.error("===============Error updating product stock: {}, stock: {}", id, stock, e);
            return Result.error("商品库存更新失败：" + e.getMessage());
        }
    }

    @Operation(summary = "根据SKU编码查询商品", description = "根据商品的SKU编码查询商品详情")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "商品查询成功"),
        @ApiResponse(responseCode = "404", description = "商品不存在"),
        @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/sku/{skuCode}")
    public Result<TcmProductDTO> getProductBySkuCode(
            @Parameter(description = "SKU编码", required = true, example = "PROD001")
            @PathVariable String skuCode) {
        logger.info("===============Received request to get product by SKU code: {}", skuCode);
        try {
            TcmProductDTO productDTO = tcmProductService.getProductBySkuCode(skuCode);
            return Result.success("商品查询成功", productDTO);
        } catch (Exception e) {
            logger.error("===============Error getting product by SKU code: {}", skuCode, e);
            return Result.error("商品查询失败：" + e.getMessage());
        }
    }
}