package com.fh.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CartBean implements Serializable {

    private  String  shopName;

    private  Integer  shopId;

    private  String  mainImg;

    private  BigDecimal  price;

    private  Boolean  isChecked;

    private  BigDecimal subtotal;

    private  Integer  count;

    private  Integer  stock;

    private  Boolean  isStock;

}
