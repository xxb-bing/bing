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

@TableName(value = "t_shop_product")
@Data
public class ShopPo implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private  Integer   id;

    @TableField(value = "brand_id")
    private  Integer   brandId;//   品牌ID

    @TableField(value = "name")
    private   String    name;//  商品名称

    @TableField(value = "subtitle")
    private   String    subtitle;//宣传标题

    @TableField(value = "main_img")
    private   String    mainImg;//主图片

    @TableField(value = "sub_imgs")
    private   String    subImgs;// 副图片

    @TableField(value = "detail")
    private   String     detail;//商品描述

    @TableField(value = "price")
    private   BigDecimal    price;//价格  java代码使用 BigDecimal 数据库使用decimal

    @TableField(value = "price")
    private   Integer    stock;//库存

    @TableField(value = "status")
    private   Integer    status;// 状态

    @TableField(value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private    Date      createTime;//创建时间

    @TableField(value = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private    Date     updateTime;// 修改时间

}
