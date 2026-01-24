-- 商品表
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL COMMENT '商品名称',
    description TEXT COMMENT '商品描述',
    category_id BIGINT COMMENT '分类ID',
    brand VARCHAR(100) COMMENT '品牌',
    main_image VARCHAR(255) COMMENT '主图片',
    price DECIMAL(10,2) COMMENT '价格',
    stock INT COMMENT '库存',
    sales INT DEFAULT 0 COMMENT '销量',
    status INT NOT NULL DEFAULT 0 COMMENT '状态：0-下架，1-上架',
    is_free_shipping INT DEFAULT 0 COMMENT '是否包邮：0-不包邮，1-包邮',
    shipping_fee DECIMAL(10,2) COMMENT '运费',
    weight DECIMAL(10,2) COMMENT '重量',
    size VARCHAR(100) COMMENT '尺寸',
    color VARCHAR(100) COMMENT '颜色',
    material VARCHAR(100) COMMENT '材质',
    origin VARCHAR(100) COMMENT '产地',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted_at DATETIME COMMENT '删除时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 商品属性表
CREATE TABLE IF NOT EXISTS product_attributes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL COMMENT '商品ID',
    attribute_name VARCHAR(100) NOT NULL COMMENT '属性名称',
    attribute_value VARCHAR(255) NOT NULL COMMENT '属性值',
    sort_order INT DEFAULT 0 COMMENT '排序',
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性表';

-- 商品规格表
CREATE TABLE IF NOT EXISTS product_skus (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL COMMENT '商品ID',
    sku_code VARCHAR(100) NOT NULL UNIQUE COMMENT 'SKU编码',
    sku_name VARCHAR(255) NOT NULL COMMENT 'SKU名称',
    price DECIMAL(10,2) NOT NULL COMMENT '价格',
    original_price DECIMAL(10,2) COMMENT '原价',
    stock INT NOT NULL COMMENT '库存',
    sales INT DEFAULT 0 COMMENT '销量',
    image_url VARCHAR(255) COMMENT '图片',
    attributes TEXT COMMENT '规格属性，如颜色:红色;尺寸:M',
    status INT DEFAULT 1 COMMENT '状态：0-下架，1-上架',
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品规格表';

-- 商品图片表
CREATE TABLE IF NOT EXISTS product_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL COMMENT '商品ID',
    image_url VARCHAR(255) NOT NULL COMMENT '图片URL',
    image_type INT DEFAULT 0 COMMENT '图片类型：0-主图，1-详情图',
    sort_order INT DEFAULT 0 COMMENT '排序',
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品图片表';

-- 商品分类表（如果不存在）
CREATE TABLE IF NOT EXISTS product_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类ID',
    level INT DEFAULT 1 COMMENT '分类级别',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status INT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

-- 创建索引
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_brand ON products(brand);
CREATE INDEX idx_products_status ON products(status);
CREATE INDEX idx_products_created_at ON products(created_at);
CREATE INDEX idx_product_attributes_product_id ON product_attributes(product_id);
CREATE INDEX idx_product_skus_product_id ON product_skus(product_id);
CREATE INDEX idx_product_skus_sku_code ON product_skus(sku_code);
CREATE INDEX idx_product_images_product_id ON product_images(product_id);
CREATE INDEX idx_product_categories_parent_id ON product_categories(parent_id);
CREATE INDEX idx_product_categories_status ON product_categories(status);

-- 插入示例数据
INSERT INTO product_categories (name, parent_id, level, sort_order, status) VALUES
('电子产品', 0, 1, 1, 1),
('手机', 1, 2, 1, 1),
('电脑', 1, 2, 2, 1),
('服装', 0, 1, 2, 1),
('男装', 4, 2, 1, 1),
('女装', 4, 2, 2, 1),
('食品', 0, 1, 3, 1),
('零食', 7, 2, 1, 1),
('饮料', 7, 2, 2, 1);

-- 插入示例商品
INSERT INTO products (name, description, category_id, brand, main_image, price, stock, sales, status, is_free_shipping, shipping_fee, weight, size, color, material, origin) VALUES
('iPhone 15 Pro', '苹果最新款手机，搭载A17 Pro芯片', 2, 'Apple', 'https://example.com/iphone15pro.jpg', 9999.00, 100, 0, 1, 1, 0.00, 0.2, '6.1英寸', '深空黑色', '玻璃+金属', '美国'),
('MacBook Pro 16', '苹果高端笔记本电脑，搭载M3 Pro芯片', 3, 'Apple', 'https://example.com/macbookpro16.jpg', 19999.00, 50, 0, 1, 1, 0.00, 2.2, '16英寸', '深空灰色', '金属', '美国'),
('纯棉T恤', '舒适透气的纯棉T恤，适合日常穿着', 5, 'Uniqlo', 'https://example.com/tshirt.jpg', 99.00, 500, 0, 1, 0, 10.00, 0.1, 'M', '白色', '纯棉', '中国'),
('牛仔裤', '经典款式牛仔裤，百搭时尚', 6, 'Levi\'s', 'https://example.com/jeans.jpg', 399.00, 200, 0, 1, 0, 15.00, 0.5, 'M', '蓝色', '牛仔布', '中国'),
('薯片', '香脆可口的薯片，多种口味可选', 8, 'Lay\'s', 'https://example.com/chips.jpg', 19.90, 1000, 0, 1, 0, 5.00, 0.1, '标准', '多种口味', '土豆', '中国'),
('可乐', '经典碳酸饮料，冰镇更爽', 9, 'Coca-Cola', 'https://example.com/cola.jpg', 3.90, 2000, 0, 1, 0, 20.00, 1.0, '500ml', '黑色', '碳酸水+糖', '中国');

-- 插入示例商品属性
INSERT INTO product_attributes (product_id, attribute_name, attribute_value, sort_order) VALUES
(1, '屏幕尺寸', '6.1英寸', 1),
(1, '处理器', 'A17 Pro', 2),
(1, '存储容量', '256GB', 3),
(1, '摄像头', '4800万像素', 4),
(2, '屏幕尺寸', '16英寸', 1),
(2, '处理器', 'M3 Pro', 2),
(2, '内存', '16GB', 3),
(2, '存储容量', '512GB', 4),
(3, '材质', '100%纯棉', 1),
(3, '版型', '宽松', 2),
(3, '适用季节', '四季', 3),
(4, '材质', '牛仔布', 1),
(4, '版型', '修身', 2),
(4, '适用季节', '春秋', 3),
(5, '口味', '原味', 1),
(5, '净含量', '100g', 2),
(5, '保质期', '12个月', 3),
(6, '口味', '原味', 1),
(6, '净含量', '500ml', 2),
(6, '保质期', '9个月', 3);

-- 插入示例商品规格
INSERT INTO product_skus (product_id, sku_code, sku_name, price, original_price, stock, sales, image_url, attributes, status) VALUES
(1, 'IP15P-256GB-BLACK', 'iPhone 15 Pro 256GB 深空黑色', 9999.00, 10999.00, 50, 0, 'https://example.com/iphone15pro-black.jpg', '颜色:深空黑色;存储:256GB', 1),
(1, 'IP15P-256GB-WHITE', 'iPhone 15 Pro 256GB 白色', 9999.00, 10999.00, 50, 0, 'https://example.com/iphone15pro-white.jpg', '颜色:白色;存储:256GB', 1),
(2, 'MBP16-M3PRO-16GB-512GB', 'MacBook Pro 16 M3 Pro 16GB 512GB', 19999.00, 21999.00, 25, 0, 'https://example.com/macbookpro16.jpg', '内存:16GB;存储:512GB', 1),
(2, 'MBP16-M3PRO-32GB-1TB', 'MacBook Pro 16 M3 Pro 32GB 1TB', 24999.00, 26999.00, 25, 0, 'https://example.com/macbookpro16.jpg', '内存:32GB;存储:1TB', 1),
(3, 'TSHIRT-M-WHITE', '纯棉T恤 M码 白色', 99.00, 129.00, 200, 0, 'https://example.com/tshirt-white.jpg', '尺码:M;颜色:白色', 1),
(3, 'TSHIRT-L-BLACK', '纯棉T恤 L码 黑色', 99.00, 129.00, 200, 0, 'https://example.com/tshirt-black.jpg', '尺码:L;颜色:黑色', 1),
(3, 'TSHIRT-XL-GRAY', '纯棉T恤 XL码 灰色', 99.00, 129.00, 100, 0, 'https://example.com/tshirt-gray.jpg', '尺码:XL;颜色:灰色', 1),
(4, 'JEANS-M-BLUE', '牛仔裤 M码 蓝色', 399.00, 499.00, 100, 0, 'https://example.com/jeans-blue.jpg', '尺码:M;颜色:蓝色', 1),
(4, 'JEANS-L-BLACK', '牛仔裤 L码 黑色', 399.00, 499.00, 100, 0, 'https://example.com/jeans-black.jpg', '尺码:L;颜色:黑色', 1),
(5, 'CHIPS-ORIGINAL-100G', '薯片 原味 100g', 19.90, 29.90, 500, 0, 'https://example.com/chips-original.jpg', '口味:原味;净含量:100g', 1),
(5, 'CHIPS-BARBQ-100G', '薯片 烧烤味 100g', 19.90, 29.90, 500, 0, 'https://example.com/chips-bbq.jpg', '口味:烧烤味;净含量:100g', 1),
(6, 'COLA-500ML', '可乐 500ml', 3.90, 4.90, 1000, 0, 'https://example.com/cola.jpg', '净含量:500ml', 1),
(6, 'COLA-2L', '可乐 2L', 9.90, 12.90, 1000, 0, 'https://example.com/cola-2l.jpg', '净含量:2L', 1);

-- 插入示例商品图片
INSERT INTO product_images (product_id, image_url, image_type, sort_order) VALUES
(1, 'https://example.com/iphone15pro-1.jpg', 0, 1),
(1, 'https://example.com/iphone15pro-2.jpg', 0, 2),
(1, 'https://example.com/iphone15pro-3.jpg', 0, 3),
(1, 'https://example.com/iphone15pro-4.jpg', 1, 1),
(1, 'https://example.com/iphone15pro-5.jpg', 1, 2),
(2, 'https://example.com/macbookpro16-1.jpg', 0, 1),
(2, 'https://example.com/macbookpro16-2.jpg', 0, 2),
(2, 'https://example.com/macbookpro16-3.jpg', 0, 3),
(2, 'https://example.com/macbookpro16-4.jpg', 1, 1),
(2, 'https://example.com/macbookpro16-5.jpg', 1, 2),
(3, 'https://example.com/tshirt-1.jpg', 0, 1),
(3, 'https://example.com/tshirt-2.jpg', 0, 2),
(3, 'https://example.com/tshirt-3.jpg', 1, 1),
(4, 'https://example.com/jeans-1.jpg', 0, 1),
(4, 'https://example.com/jeans-2.jpg', 0, 2),
(4, 'https://example.com/jeans-3.jpg', 1, 1),
(5, 'https://example.com/chips-1.jpg', 0, 1),
(5, 'https://example.com/chips-2.jpg', 1, 1),
(6, 'https://example.com/cola-1.jpg', 0, 1),
(6, 'https://example.com/cola-2.jpg', 1, 1);
