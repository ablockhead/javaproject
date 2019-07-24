package com.admin.hanfusiadmin.service.impl;

import com.admin.hanfusiadmin.VO.OrdersVO;
import com.admin.hanfusiadmin.VO.ResultVO;
import com.admin.hanfusiadmin.dao.MallDao;
import com.admin.hanfusiadmin.entity.Goods;
import com.admin.hanfusiadmin.enums.HanfusiEnums;
import com.admin.hanfusiadmin.exception.HanfusiException;
import com.admin.hanfusiadmin.service.MallService;
import com.admin.hanfusiadmin.utils.ResultVOUtil;
import com.admin.hanfusiadmin.utils.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MallServiceImpl implements MallService {

    @Autowired
    private MallDao mallDao;

    /**
     * 获取商品列表
     * @param pageIndex 当前页
     * @return 商品列表
     */
    @Override
    public ResultVO getGoodsList(Integer pageIndex) {
        int totalCount = mallDao.getGoodsCount();
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }

        pageIndex = (pageIndex - 1) * 6;
        List<Goods> goodsList = mallDao.getGoodsList(pageIndex);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("goodsList", goodsList);
        return ResultVOUtil.success(map);
    }

    /**
     * 添加商品
     * @param request 请求（用于获取多个文件）
     * @param goods 商品参数
     * @return 结果
     */
    @Override
    public ResultVO addGoods(MultipartHttpServletRequest request, Goods goods) {
        Map<String, MultipartFile> map = request.getFileMap();
        if (map.size() > 0){
            for(String mapName: map.keySet()){
                if ("longImageFile".equals(mapName)){
                    MultipartFile file = map.get(mapName);
                    String url = UploadFileUtil.uploadFile(file, "goods");
                    goods.setLongImageUrl(url);
                } else if ("acrossImageFile".equals(mapName)) {
                    MultipartFile file = map.get(mapName);
                    String url = UploadFileUtil.uploadFile(file, "goods");
                    goods.setAcrossImageUrl(url);
                } else if ("smallImageFile".equals(mapName)) {
                    MultipartFile file = map.get(mapName);
                    String url = UploadFileUtil.uploadFile(file, "goods");
                    goods.setSmallImageUrl(url);
                }
            }
        }

        int row = mallDao.addGoods(goods);
        if (row <= 0){
            throw new HanfusiException(HanfusiEnums.ADD_ERROR);
        }

        return ResultVOUtil.success();
    }

    /**
     * 获取商品信息
     * @param goodsId 商品id
     * @return 商品信息
     */
    @Override
    public ResultVO getGoods(Integer goodsId) {
        return ResultVOUtil.success(mallDao.getGoods(goodsId));
    }

    /**
     * 修改商品状态
     * @param goodsId 商品id
     * @return 结果
     */
    @Override
    public ResultVO updateGoods(Integer goodsId) {
        Goods goods = mallDao.getGoods(goodsId);
        if (goods.getState() == 0){
            int row = mallDao.updateGoods(goodsId, 1);
            if (row <= 0){
                throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
            }
        } else {
            int row = mallDao.updateGoods(goodsId, 0);
            if (row <= 0){
                throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
            }
        }

        return ResultVOUtil.success();
    }

    /**
     * 删除商品状态
     * @param goodsId 商品id
     * @return 结果
     */
    @Override
    public ResultVO delGoods(Integer goodsId) {
        int row = mallDao.delGoods(goodsId);
        if (row <= 0){
            throw new HanfusiException(HanfusiEnums.DEL_ERROR);
        }

        return ResultVOUtil.success();
    }

    /**
     * 获取订单列表
     * @param pageIndex 当前页
     * @return 列表
     */
    @Override
    public ResultVO getOrderList(Integer pageIndex) {
        int totalCount = mallDao.getOrderCount();
        if (totalCount > 0){
            totalCount = totalCount % 6 == 0 ? totalCount / 6 : totalCount / 6 + 1;
        } else {
            totalCount = 1;
        }
        if (pageIndex < 1 || pageIndex > totalCount){
            throw new HanfusiException(HanfusiEnums.NOT_MORE_ERROR);
        }

        pageIndex = (pageIndex - 1) * 6;
        List<OrdersVO> ordersVOList = mallDao.getOrderList(pageIndex);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("orderList", ordersVOList);
        return ResultVOUtil.success(map);
    }

    /**
     * 修改订单状态
     * @param ordersId 订单id
     * @return 结果
     */
    @Override
    public ResultVO updateOrderState(long ordersId, BigInteger courierNumber, String courierName) {
        OrdersVO ordersVO = mallDao.getOrder(ordersId);
            if (ordersVO.getState() == 0){
            int row = mallDao.updateOrderState(ordersId, 1,courierNumber,courierName);
            if (row <= 0){
                throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
            }
        } else {
            int row = mallDao.updateOrderState(ordersId, 0,courierNumber,courierName);
            if (row <= 0){
                throw new HanfusiException(HanfusiEnums.UPDATE_ERROR);
            }
        }

        return ResultVOUtil.success();
    }
}
