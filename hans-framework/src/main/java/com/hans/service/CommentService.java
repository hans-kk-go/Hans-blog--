package com.hans.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hans.commen.ResponseResult;
import com.hans.entity.Comment;

/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-03-28 22:16:04
 */
public interface CommentService extends IService<Comment> {

    ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}

