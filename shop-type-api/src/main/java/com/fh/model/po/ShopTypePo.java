package com.fh.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "t_shop_type")
public class ShopTypePo implements Serializable {

    @TableId(value = "id",type = IdType.AUTO )//指定自增策略
    private  Integer  id;

    @TableField(value = "typeName")
    private  String  typeName;

    @TableField(value = "isValid")
    private  Integer  isValid;

    @TableField(value = "pid")
    private  Integer  pid;

}
