package com.fh.model.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("t_paylog")
public class Paylog implements Serializable {
    @TableField(value = "outTradeNo")
    private String  outTradeNo;//支付单号

    @TableField("orderId")
    private String  orderId;//支付单号

    @TableField("userId")
    private Integer  userId;//购买人id

    @TableField("transactionId")
    private Integer  transactionId;// 事务id(用于分布式事务)

    @TableField("createTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间

    @TableField("payTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  payTime;//支付时间

    @TableField("payMoney")
    private BigDecimal payMoney ;//支付金额

    @TableField("payType")
    private Integer  payType;//支付方式

    @TableField("payStatus")
    private Integer  payStatus;//支付状态


}
