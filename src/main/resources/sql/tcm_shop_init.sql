-- 店铺表
CREATE TABLE IF NOT EXISTS tcm_shops (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT '店铺名称',
    description TEXT COMMENT '店铺描述',
    logo VARCHAR(255) COMMENT '店铺logo',
    banner VARCHAR(255) COMMENT '店铺横幅',
    owner_id BIGINT COMMENT '店主ID',
    owner_name VARCHAR(255) COMMENT '店主名称',
    contact_phone VARCHAR(20) COMMENT '联系电话',
    contact_email VARCHAR(255) COMMENT '联系邮箱',
    address VARCHAR(500) COMMENT '店铺地址',
    status INT DEFAULT 1 COMMENT '店铺状态：0-关闭，1-正常营业',
    is_verified INT DEFAULT 0 COMMENT '是否认证：0-未认证，1-已认证',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted_at DATETIME COMMENT '删除时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺表';

-- 店铺分类表
CREATE TABLE IF NOT EXISTS tcm_shop_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shop_id BIGINT COMMENT '店铺ID',
    category_name VARCHAR(100) COMMENT '分类名称',
    sort_order INT DEFAULT 0 COMMENT '排序',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺分类表';
