package com.fh.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("t_shop_order_detail")
public class OrderDetail implements Serializable {
    @TableField(value = "orderId")
    private String   orderId;//订单id

    @TableField("userId")
    private Integer  userId;//购买人id

    @TableField("productId")
    private Integer  productId;//  商品id

    @TableField("productName")
    private String   productName;//商品名称

    @TableField("price")
    private BigDecimal price;// 商品价格

    @TableField("subTotalPrice")
    private BigDecimal subTotalPrice;//  商品总计金额

    @TableField("image")
    private String   image;//图片

    @TableField("count")
    private Integer  count;// 购买数量

}
