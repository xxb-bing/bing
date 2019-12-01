package com.fh.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "t_user")
public class UserPo  implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private  Integer  id;

    @TableField("username")
    private  String  username;

    @TableField("password")
    private   String  password;

    @TableField("phone")
    private  String  phone;

    @TableField("loginDate")
    private Date loginDate;

    @TableField("cartId")
    private  String cartId;

}
