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
@TableName(value = "t_brand")
public class BrandPo implements Serializable {
   @TableId(value = "id",type = IdType.AUTO )//指定自增策略
   private   Integer   id;

   @TableField(value = "brandName")
   private   String    brandName;

   @TableField(value = "telPhone")
   private   String    telPhone;

   @TableField(value = "website")
   private   String    website;

   @TableField(value = "status")
   private   Integer   status;

   @TableField(value = "logoUrl")
   private   String    logoUrl;

   @TableField(value = "updateTime")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private   Date      updateTime;


}
