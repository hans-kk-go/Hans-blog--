package com.hans.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hans.commen.ResponseResult;
import com.hans.dao.CommentDao;
import com.hans.entity.Article;
import com.hans.entity.Comment;
import com.hans.service.CommentService;
import com.hans.service.UserService;
import com.hans.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-03-28 22:16:04
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize) {
//        //查询对应文章的根评论
        LambdaQueryWrapper<Comment> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        //根评论 rootId为-1
        articleLambdaQueryWrapper.eq(Comment::getArticleId,articleId);
        articleLambdaQueryWrapper.eq(Comment::getRootId,"-1");
//        //分页查询
        Page<Comment> commentPage = new Page<>(pageNum,pageSize);
        List<Comment> commentList = page(commentPage, articleLambdaQueryWrapper).getRecords();
        List<CommentVo> commentVos = BeanUtil.copyToList(commentList, CommentVo.class);
        List<CommentVo> commentRoot = commentVos.stream()
                .peek(one -> one.setUsername(userService.getById(one.getCreateBy()).getUserName()))
                .collect(Collectors.toList());

        //子评论
        List<Comment> list = list();
        List<CommentVo> commentVos1 = BeanUtil.copyToList(list, CommentVo.class);
        List<CommentVo> collectSon = commentVos1.stream()
                .peek(one -> one.setUsername(userService.getById(one.getCreateBy()).getUserName()))
                .collect(Collectors.toList());
        List<CommentVo> collect = commentRoot.stream()
                .peek(one -> one.setChildren(collectSon.stream().filter(one1 -> one1.getToCommentUserId().equals(one.getId())).collect(Collectors.toList())))
                .collect(Collectors.toList());
        List<CommentVo> collect1 = collect.stream()
                .flatMap(one -> one.getChildren().stream())
                .peek(one -> one.setToCommentUserName(userService.getById(one.getToCommentUserId()).getUserName()))
                .collect(Collectors.toList());

        System.out.println(collect1);
        return ResponseResult.ok(collect1);

    }

    @Override
    public ResponseResult addComment(Comment comment) {
        if (StrUtil.isBlankIfStr(comment)){
            return ResponseResult.fail(401,"添加内容为空");
        }
        save(comment);
        return ResponseResult.ok("回复成功");
    }

}
