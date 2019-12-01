package com.fh.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ShopVo implements Serializable {

    private  Integer   id;

    private  String   brandName;//   品牌名称

    private   String    name;//  商品名称

    private   String    subtitle;//宣传标题

    private   String    mainImg;//主图片

    private   String    subImgs;// 副图片

    private   String     detail;//商品描述

    private   BigDecimal price;//价格

    private   Integer    stock;//库存

    private   Integer    status;// 状态

    private    Date createTime;//创建时间

    private    Date     updateTime;// 修改时间

    private   String   typeName;

}
