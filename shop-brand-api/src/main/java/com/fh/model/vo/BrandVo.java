package com.fh.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BrandVo implements Serializable {

    private   Integer   id;

    private   String    brandName;

    private   String    telPhone;

    private   String    website;

    private   Integer   status;

    private   String    logoUrl;

    private   String    typeName;

    private   Date      updateTime;


}
