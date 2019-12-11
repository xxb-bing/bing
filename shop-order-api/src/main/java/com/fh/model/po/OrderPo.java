package com.fh.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "t_shop_order")
public class OrderPo implements Serializable {
    @TableId(value = "id",type = IdType.INPUT)
    private String id;//订单id

    @TableField("userId")
    private Integer userId;//购买人id

    @TableField("status")
    private Integer status;//	订单状态

    @TableField("totalPrice")
    private BigDecimal totalPrice;//订单总金额

    @TableField("totalCount")
    private Integer totalCount;//商品数量

    @TableField("payType")
    private Integer payType;//支付方式

    @TableField("addressId")
    private Integer addressId;//地址id

    @TableField("payTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime	;//支付时间

    @TableField("createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间


}
