package com.admin.hanfusiadmin.service.impl;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.dao.BannerDao;
import com.admin.hanfusiadmin.entity.Banner;
import com.admin.hanfusiadmin.enums.HanfusiEnums;
import com.admin.hanfusiadmin.exception.HanfusiException;
import com.admin.hanfusiadmin.service.BannerService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import com.admin.hanfusiadmin.utils.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    /**
     * 获取轮播图列表
     * @param pageIndex 当前页
     * @return 列表
     */
    @Override
    public ResultVO getBannerList(Integer pageIndex) {
        int totalCount = bannerDao.getBannerCount();
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }

        pageIndex = (pageIndex - 1) * 6;
        List<Banner> bannerList = bannerDao.getBannerList(pageIndex);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("bannerList", bannerList);

        return ResultVOUtil.success(map);
    }

    /**
     * 添加轮播图
     * @param file 轮播图图片
     * @param banner 轮播图参数
     * @return 结果
     */
    @Override
    @CacheEvict(cacheNames = "banner", allEntries = true)
    public ResultVO addBanner(MultipartFile file, Banner banner) {
        String fileName = UploadFileUtil.uploadFile(file, "banner");
        banner.setUrl(fileName);
        int row = bannerDao.addBanner(banner);
        if (row <= 0){
            throw new HanfusiException(HanfusiEnums.ADD_ERROR);
        }

        return ResultVOUtil.success();
    }

    /**
     * 删除轮播图
     * @param bannerId 轮播图id
     * @return 结果
     */
    @Override
    @CacheEvict(cacheNames = "banner", allEntries = true)
    public ResultVO delBanner(Integer bannerId) {
        int row = bannerDao.delBanner(bannerId);
        if (row <= 0){
            throw new HanfusiException(HanfusiEnums.DEL_ERROR);
        }

        return ResultVOUtil.success();
    }

    /**
     * 修改轮播图排序
     * @param bannerId 轮播图id
     * @param sort 排序
     * @return 结果
     */
    @Override
    @CacheEvict(cacheNames = "banner", allEntries = true)
    public ResultVO updateSort(Integer bannerId, Integer sort) {
        int row = bannerDao.updateSort(bannerId, sort);
        if (row <= 0){
            throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
        }

        return ResultVOUtil.success();
    }

    /**
     * 获取单个轮播图信息
     * @param bannerId 轮播图id
     * @return 信息
     */
    @Override
    public ResultVO getBanner(Integer bannerId) {
        return ResultVOUtil.success(bannerDao.getBanner(bannerId));
    }
}
