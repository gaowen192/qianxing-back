package com.ride.controller;

import com.ride.dto.TcmProductDTO;
import com.ride.dto.TcmProductRequest;
import com.ride.service.TcmProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品Controller
 * 处理商品相关的HTTP请求
 */
@RestController
@RequestMapping("tcm/products")
@Tag(name = "商品管理", description = "商品相关的API接口")
public class TcmProductController {

    @Autowired
    private TcmProductService tcmProductService;

    /**
     * 创建商品
     * @param productRequest 商品请求数据
     * @return 商品DTO
     */
    @PostMapping
    @Operation(summary = "创建商品", description = "创建新的商品信息，包括基本信息、属性、规格和图片")
    public ResponseEntity<TcmProductDTO> createProduct(
            @Parameter(description = "商品请求数据", required = true) @RequestBody TcmProductRequest productRequest) {
        TcmProductDTO productDTO = tcmProductService.createProduct(productRequest);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    /**
     * 更新商品
     * @param id 商品ID
     * @param productRequest 商品请求数据
     * @return 商品DTO
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新商品", description = "根据商品ID更新商品信息，包括基本信息、属性、规格和图片")
    public ResponseEntity<TcmProductDTO> updateProduct(
            @Parameter(description = "商品ID", required = true) @PathVariable Long id,
            @Parameter(description = "商品请求数据", required = true) @RequestBody TcmProductRequest productRequest) {
        TcmProductDTO productDTO = tcmProductService.updateProduct(id, productRequest);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    /**
     * 根据ID查询商品
     * @param id 商品ID
     * @return 商品DTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询商品", description = "根据商品ID查询商品的详细信息，包括基本信息、属性、规格和图片")
    public ResponseEntity<TcmProductDTO> getProductById(
            @Parameter(description = "商品ID", required = true) @PathVariable Long id) {
        TcmProductDTO productDTO = tcmProductService.getProductById(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    /**
     * 删除商品
     * @param id 商品ID
     * @return 响应
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除商品", description = "根据商品ID删除商品信息")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "商品ID", required = true) @PathVariable Long id) {
        tcmProductService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 分页查询商品
     * @param name 商品名称
     * @param categoryId 分类ID
     * @param brand 品牌
     * @param status 状态
     * @param page 页码
     * @param size 每页大小
     * @param sort 排序字段
     * @param order 排序方式
     * @return 商品分页列表
     */
    @GetMapping
    @Operation(summary = "分页查询商品", description = "根据条件分页查询商品列表，支持按名称、分类、品牌和状态筛选")
    public ResponseEntity<Page<TcmProductDTO>> getProducts(
            @Parameter(description = "商品名称") @RequestParam(value = "name", required = false) String name,
            @Parameter(description = "分类ID") @RequestParam(value = "categoryId", required = false) Long categoryId,
            @Parameter(description = "品牌") @RequestParam(value = "brand", required = false) String brand,
            @Parameter(description = "状态，1-上架，0-下架") @RequestParam(value = "status", required = false, defaultValue = "1") Integer status,
            @Parameter(description = "页码") @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @Parameter(description = "每页大小") @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            @Parameter(description = "排序字段") @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort,
            @Parameter(description = "排序方式，asc-升序，desc-降序") @RequestParam(value = "order", required = false, defaultValue = "desc") String order) {

        Sort.Direction direction = order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort));

        Page<TcmProductDTO> products = tcmProductService.getProducts(name, categoryId, brand, status, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * 查询热销商品
     * @param limit 限制数量
     * @return 商品列表
     */
    @GetMapping("/hot")
    @Operation(summary = "查询热销商品", description = "查询销量前N的商品列表")
    public ResponseEntity<List<TcmProductDTO>> getHotProducts(
            @Parameter(description = "限制数量") @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {

        List<TcmProductDTO> products = tcmProductService.getHotProducts(limit);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * 上下架商品
     * @param id 商品ID
     * @param status 状态
     * @return 响应
     */
    @PutMapping("/{id}/status")
    @Operation(summary = "上下架商品", description = "更新商品的上架/下架状态")
    public ResponseEntity<Void> updateProductStatus(
            @Parameter(description = "商品ID", required = true) @PathVariable Long id,
            @Parameter(description = "状态，1-上架，0-下架", required = true) @RequestParam(value = "status") Integer status) {

        tcmProductService.updateProductStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 更新商品库存
     * @param id 商品ID
     * @param stock 库存数量
     * @return 响应
     */
    @PutMapping("/{id}/stock")
    @Operation(summary = "更新商品库存", description = "更新商品的库存数量")
    public ResponseEntity<Void> updateProductStock(
            @Parameter(description = "商品ID", required = true) @PathVariable Long id,
            @Parameter(description = "库存数量", required = true) @RequestParam(value = "stock") Integer stock) {

        tcmProductService.updateProductStock(id, stock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 根据SKU编码查询商品
     * @param skuCode SKU编码
     * @return 商品DTO
     */
    @GetMapping("/sku/{skuCode}")
    @Operation(summary = "根据SKU编码查询商品", description = "根据商品的SKU编码查询商品信息")
    public ResponseEntity<TcmProductDTO> getProductBySkuCode(
            @Parameter(description = "SKU编码", required = true) @PathVariable String skuCode) {
        TcmProductDTO productDTO = tcmProductService.getProductBySkuCode(skuCode);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
}