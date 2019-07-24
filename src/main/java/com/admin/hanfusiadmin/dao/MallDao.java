package com.admin.hanfusiadmin.dao;

import com.admin.hanfusiadmin.VO.OrdersVO;
import com.admin.hanfusiadmin.entity.Goods;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Mapper
@Component
public interface MallDao {

    @Select("SELECT count(*) FROM goods")
    int getGoodsCount();

    @Select("SELECT * FROM goods ORDER BY createTime DESC LIMIT #{pageIndex}, 6")
    List<Goods> getGoodsList(Integer pageIndex);

    @Insert("INSERT INTO goods (goodsName, goodsCurrency, goodsDetail, longImageUrl, acrossImageUrl, smallImageUrl) " +
            "VALUES (#{goodsName}, #{goodsCurrency}, #{goodsDetail}, #{longImageUrl}, #{acrossImageUrl}, #{smallImageUrl})")
    int addGoods(Goods goods);

    @Select("SELECT * FROM goods WHERE goodsId = #{goodsId}")
    Goods getGoods(Integer goodsId);

    @Update("UPDATE goods SET state = #{state} WHERE goodsId = #{goodsId}")
    int updateGoods(@Param("goodsId") Integer goodsId, @Param("state") Integer state);

    @Delete("DELETE FROM goods WHERE goodsId = #{goodsId}")
    int delGoods(Integer goodsId);

    @Select("SELECT count(*) FROM orders")
    int getOrderCount();

    @Select("SELECT o.nickName,o.courierNumber,o.couriterName, u.avatarUrl, g.goodsName, g.goodsCurrency, o.ordersId, o.phone, o.address, o.state, " +
            "o.createTime FROM orders o JOIN userinfo u ON o.openId = u.openId JOIN goods g ON o.goodsId = g.goodsId " +
            "ORDER BY o.createTime DESC LIMIT #{pageIndex}, 6")
    List<OrdersVO> getOrderList(Integer pageIndex);

    @Select("SELECT * FROM orders WHERE ordersId = #{ordersId}")
    OrdersVO getOrder(long ordersId);

    @Update("UPDATE orders SET state = #{state},courierNumber = #{courierNumber}, couriterName = #{couriterName} WHERE ordersId = #{ordersId}")
    int updateOrderState(@Param("ordersId") long ordersId, @Param("state") Integer state, @Param("courierNumber") BigInteger courierNumber, @Param("couriterName") String couriterName);
}
