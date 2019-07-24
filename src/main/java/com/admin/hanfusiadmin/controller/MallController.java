package com.admin.hanfusiadmin.controller;

import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.entity.Goods;
import com.admin.hanfusiadmin.service.MallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.math.BigInteger;

@RestController
@RequestMapping("mall")
public class MallController {

    @Autowired
    private MallService mallService;

    /**
     * 获取商品列表
     * @param pageIndex 当前页
     * @return 商品列表
     */
    @RequestMapping("getGoodsList")
    public ResultVO getGoodsList(Integer pageIndex){
        return mallService.getGoodsList(pageIndex);
    }

    /**
     * 添加商品
     * @param request 请求（用于获取多个文件）
     * @param goods 商品参数
     * @return 结果
     */
    @RequestMapping("addGoods")
    public ResultVO addGoods(MultipartHttpServletRequest request, Goods goods){
        return mallService.addGoods(request, goods);
    }

    /**
     * 获取商品信息
     * @param goodsId 商品id
     * @return 商品信息
     */
    @RequestMapping("getGoods")
    public ResultVO getGoods(Integer goodsId){
        return mallService.getGoods(goodsId);
    }

    /**
     * 修改商品状态
     * @param goodsId 商品id
     * @return 结果
     */
    @RequestMapping("updateGoods")
    public ResultVO updateGoods(Integer goodsId){
        return mallService.updateGoods(goodsId);
    }

    /**
     * 删除商品
     * @param goodsId 商品id
     * @return 结果
     */
    @RequestMapping("delGoods")
    public ResultVO delGoods(Integer goodsId){
        return mallService.delGoods(goodsId);
    }

    /**
     * 获取订单列表
     * @param pageIndex 当前页
     * @return 列表
     */
    @RequestMapping("getOrderList")
    public ResultVO getOrderList(Integer pageIndex){
        return mallService.getOrderList(pageIndex);
    }

    /**
     * 修改订单状态
     * @param ordersId 订单id
     * @return 结果
     */
    @RequestMapping("updateOrderState")
    public ResultVO updateOrderState(@RequestParam("ordersId") long ordersId, @RequestParam("courierNumber") BigInteger courierNumber, @RequestParam("courierName") String courierName){
        return mallService.updateOrderState(ordersId,courierNumber,courierName);
    }
}
