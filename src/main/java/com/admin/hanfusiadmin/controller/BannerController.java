package com.admin.hanfusiadmin.controller;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.Banner;
import com.admin.hanfusiadmin.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    /**
     * 获取轮播图列表
     * @param pageIndex 当前页
     * @return 列表
     */
    @RequestMapping("getBannerList")
    public ResultVO getBannerList(Integer pageIndex){
        return bannerService.getBannerList(pageIndex);
    }

    /**
     * 添加轮播图
     * @param file 轮播图图片
     * @param banner 轮播图参数
     * @return 结果
     */
    @RequestMapping("addBanner")
    public ResultVO addBanner(@RequestParam("file") MultipartFile file, Banner banner){
        return bannerService.addBanner(file, banner);
    }

    /**
     * 删除轮播图
     * @param bannerId 轮播图id
     * @return 结果
     */
    @RequestMapping("delBanner")
    public ResultVO delBanner(Integer bannerId){
        return bannerService.delBanner(bannerId);
    }

    /**
     * 修改轮播图排序
     * @param bannerId 轮播图id
     * @param sort 排序
     * @return 结果
     */
    @RequestMapping("updateSort")
    public ResultVO updateSort(@RequestParam("bannerId") Integer bannerId, @RequestParam("sort") Integer sort){
        return bannerService.updateSort(bannerId, sort);
    }

    /**
     * 获取单个轮播图信息
     * @param bannerId 轮播图id
     * @return 信息
     */
    @RequestMapping("getBanner")
    public ResultVO getBanner(Integer bannerId){
        return bannerService.getBanner(bannerId);
    }
}
