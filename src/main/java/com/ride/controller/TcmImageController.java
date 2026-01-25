package com.ride.controller;

import com.ride.dto.TcmProductImageDTO;
import com.ride.service.TcmImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 图片上传控制器
 * 处理图片上传相关的HTTP请求
 */
@RestController
@RequestMapping("tcm/images")
@Tag(name = "图片管理", description = "图片上传和管理相关的API接口")
public class TcmImageController {

    @Autowired
    private TcmImageService tcmImageService;

    /**
     * 上传图片
     * @param file 图片文件
     * @param remark 备注
     * @return 图片DTO
     */
    @PostMapping
    @Operation(summary = "上传图片", description = "上传图片并保存到数据库，返回图片信息")
    public ResponseEntity<TcmProductImageDTO> uploadImage(
            @Parameter(description = "图片文件", required = true) @RequestParam("file") MultipartFile file,
            @Parameter(description = "备注") @RequestParam(required = false) String remark) {
        TcmProductImageDTO imageDTO = tcmImageService.uploadImage(file, remark);
        return new ResponseEntity<>(imageDTO, HttpStatus.CREATED);
    }

    /**
     * 获取用户未绑定的图片列表
     * @param userId 用户ID
     * @return 图片DTO列表
     */
    @GetMapping("/unbound")
    @Operation(summary = "获取未绑定图片", description = "获取用户未绑定到商品的图片列表")
    public ResponseEntity<List<TcmProductImageDTO>> getUnboundImages(
            @Parameter(description = "用户ID", required = true) @RequestParam Long userId) {
        List<TcmProductImageDTO> images = tcmImageService.getUnboundImages(userId);
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    /**
     * 删除图片
     * @param id 图片ID
     * @return 响应
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除图片", description = "删除指定的图片")
    public ResponseEntity<Void> deleteImage(
            @Parameter(description = "图片ID", required = true) @PathVariable Long id) {
        tcmImageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
