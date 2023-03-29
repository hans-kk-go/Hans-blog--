package com.hans.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hans.commen.ResponseResult;
import com.hans.Constants.SystemConstants;
import com.hans.dao.CategoryDao;
import com.hans.entity.Article;
import com.hans.entity.Category;
import com.hans.service.ArticleService;
import com.hans.service.CategoryService;
import com.hans.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-03-25 13:25:41
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表  状态为已发布的文章
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleLambdaQueryWrapper);
        //获取文章的分类id，并且去重
        List<Long> idList = articleList.stream()
                .map(value -> value.getCategoryId())
                .distinct()
                .collect(Collectors.toList());

        //查询分类表
        List<Category> categories = listByIds(idList);
        List<Category> lastCategories = categories.stream()
                .filter(s -> SystemConstants.ARTICLE_TYPE_STATUS_NORMAL.equals(s.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanUtil.copyToList(lastCategories, CategoryVo.class);
        return ResponseResult.ok(categoryVos);

    }
}
