package com.hans.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hans.commen.ResponseResult;
import com.hans.dao.ArticleDao;
import com.hans.dao.CategoryDao;
import com.hans.entity.Article;
import com.hans.service.ArticleService;
import com.hans.vo.ArticleDetailVo;
import com.hans.vo.ArticleListVo;
import com.hans.vo.HotArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.hans.Constants.SystemConstants.ARTICLE_STATUS_NORMAL;

/**
 * 文章表(Article)表服务实现类
 *
 * @author makejava
 * @since 2023-03-25 01:04:58
 */
@Service("articleService")
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        articleLambdaQueryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        articleLambdaQueryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询10条(分页查询)
        Page<Article> Page = new Page<>(1,3);
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Article> page = page(Page, articleLambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        List<HotArticleVo> hotArticleVos = BeanUtil.copyToList(articles, HotArticleVo.class);
        return ResponseResult.ok(hotArticleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        if (categoryId == null){

            LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            articleLambdaQueryWrapper.eq(Article::getStatus,"0");

            // 对isTop进行降序
            articleLambdaQueryWrapper.orderByDesc(Article::getIsTop);
            Page<Article> articlePage = new Page<>(pageNum, pageSize);
            Page<Article> page = page(articlePage, articleLambdaQueryWrapper);
            List<Article> articles = page.getRecords();

            //转型
            List<ArticleListVo> articleListVos = BeanUtil.copyToList(articles, ArticleListVo.class);


            List<ArticleListVo> articleListVos1 = articleListVos.stream()
                    .peek(one -> one.setCategoryName(categoryDao.selectById(one.getCategoryId()).getName()))
                    .collect(Collectors.toList());


            return ResponseResult.ok(articleListVos1);
        }

        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.eq(Article::getCategoryId,categoryId);
        articleLambdaQueryWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL);
        // 对isTop进行降序
        articleLambdaQueryWrapper.orderByDesc(Article::getIsTop);
        Page<Article> articlePage = new Page<>(pageNum, pageSize);
        Page<Article> page = page(articlePage, articleLambdaQueryWrapper);
        List<Article> articles = page.getRecords();
        List<ArticleListVo> articleListVos = BeanUtil.copyToList(articles, ArticleListVo.class);

        List<ArticleListVo> collect = articleListVos.stream()
                .peek(one -> one.setCategoryName(categoryDao.selectById(one.getCategoryId()).getName()))
                .collect(Collectors.toList());
        return ResponseResult.ok(collect);

    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        ArticleDetailVo articleDetailVo = BeanUtil.toBean(article, ArticleDetailVo.class);

        articleDetailVo.setCategoryName(categoryDao.selectById(articleDetailVo.getId()).getName());

        return ResponseResult.ok("操作成功",articleDetailVo);

    }

}
