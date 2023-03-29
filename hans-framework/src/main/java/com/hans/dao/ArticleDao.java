package com.hans.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hans.entity.Article;
import org.apache.ibatis.annotations.Mapper;


/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-25 00:48:09
 */
@Mapper
public interface ArticleDao extends BaseMapper<Article> {

}

