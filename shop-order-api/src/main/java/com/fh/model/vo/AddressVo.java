package com.fh.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AddressVo implements Serializable {

    private  Integer id;

    private  String  cnee;

    private String address;

    private String telphone;

    private String  email;

    private  Integer userId;

    private Date addTime;

}
