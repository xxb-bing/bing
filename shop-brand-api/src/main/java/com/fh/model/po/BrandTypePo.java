package com.fh.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "t_brand_type")
public class BrandTypePo implements Serializable {
    @TableId(value = "id",type = IdType.AUTO )//指定自增策略
    private  Integer  id;

    @TableField(value = "brandId")
    private   Integer  brandId;

    @TableField(value = "typeId")
    private   Integer   typeId;

}
