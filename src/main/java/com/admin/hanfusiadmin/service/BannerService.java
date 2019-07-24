package com.admin.hanfusiadmin.service;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {

    ResultVO getBannerList(Integer pageIndex);

    ResultVO addBanner(MultipartFile file, Banner banner);

    ResultVO delBanner(Integer bannerId);

    ResultVO updateSort(Integer bannerId, Integer sort);

    ResultVO getBanner(Integer bannerId);
}
