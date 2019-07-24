package com.admin.hanfusiadmin.service;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.Goods;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.math.BigInteger;

public interface MallService {

    ResultVO getGoodsList(Integer pageIndex);

    ResultVO addGoods(MultipartHttpServletRequest request, Goods goods);

    ResultVO getGoods(Integer goodsId);

    ResultVO updateGoods(Integer goodsId);

    ResultVO delGoods(Integer goodsId);

    ResultVO getOrderList(Integer pageIndex);

    ResultVO updateOrderState(long ordersId, BigInteger courierNumber, String courierName);
}
