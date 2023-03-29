package com.hans.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hans.commen.ResponseResult;
import com.hans.Constants.SystemConstants;
import com.hans.dao.LinkDao;
import com.hans.entity.Link;
import com.hans.service.LinkService;
import com.hans.vo.LInkVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-26 19:25:30
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkDao, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.eq(Link::getStatus, SystemConstants.link_pass);
        List<Link> linkList = list(linkLambdaQueryWrapper);

        List<LInkVo> lInkVos = BeanUtil.copyToList(linkList, LInkVo.class);

        return ResponseResult.ok("操作成功",lInkVos);
    }
}
