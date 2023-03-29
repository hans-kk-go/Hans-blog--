package com.hans.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hans.commen.ResponseResult;
import com.hans.entity.Link;

/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-26 19:25:30
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

