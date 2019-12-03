package com.fh.controller;

import com.fh.login.LoginAnnotation;
import com.fh.model.po.AddressPo;
import com.fh.model.vo.AddressVo;
import com.fh.service.AddressService;
import com.fh.utils.ResponseServer;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("address")//exposedHeaders允许返回的头部信息
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080",exposedHeaders="NOLOGIN")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping
    @LoginAnnotation
    public ResponseServer  queryAddress(HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        List<AddressVo>  list = addressService.queryAddress(phone);
        return ResponseServer.success(list);
    }

    @PutMapping
    @LoginAnnotation
    public  ResponseServer addOrUpdateAddress(AddressPo addressPo, HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        addressService.addOrUpdateAddress(addressPo,phone);
        return ResponseServer.success();
    }

    @GetMapping("{id}")
    @LoginAnnotation
    public ResponseServer getAddressById(@PathVariable  Integer id,HttpServletRequest request){
        String phone = (String) request.getAttribute("phone");
        AddressPo addressPo = addressService.getAddressById(id,phone);
        return ResponseServer.success(addressPo);
    }

    @DeleteMapping("{id}")
    @LoginAnnotation
    public  ResponseServer deleteAddress(@PathVariable  Integer id){
        addressService.deleteAddress(id);
        return ResponseServer.success();
    }

}
