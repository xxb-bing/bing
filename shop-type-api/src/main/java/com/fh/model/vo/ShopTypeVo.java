package com.fh.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShopTypeVo implements Serializable {

    private  Integer  id;

    private  String  typeName;

    private  Integer  isValid;

    private  Integer  pid;

    private  boolean checked;

}
