package com.yozuru.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Yozuru
 */
@Data
@NoArgsConstructor
public class AdminArticleDetailVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //文章摘要
    private String summary;
    //所属分类id
    private Long categoryId;
    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //是否允许评论 1是，0否
    private String isComment;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;

    private List<Long> tags;
}
