package com.fh.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "t_address")
public class AddressPo implements Serializable {

    @TableId(value = "id",type = IdType.AUTO )//指定自增策略
    private  Integer id;

    @TableField(value = "cnee")
    private  String  cnee;

    @TableField(value = "address")
    private String address;

    @TableField(value = "telphone")
    private String telphone;

    @TableField(value = "email")
    private String  email;

    @TableField(value = "userId")
    private  Integer userId;

    @TableField(value = "addTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

}
