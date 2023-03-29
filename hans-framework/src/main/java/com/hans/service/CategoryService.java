package com.hans.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hans.commen.ResponseResult;
import com.hans.entity.Category;

/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-25 13:25:41
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

