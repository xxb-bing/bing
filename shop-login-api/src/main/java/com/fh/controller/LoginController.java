package com.fh.controller;

import com.fh.jwt.JwtUtils;
import com.fh.model.po.UserPo;
import com.fh.service.UserService;
import com.fh.utils.ResponseServer;
import com.fh.utils.ServerEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("login")
@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
public class LoginController {

    @Resource
    private UserService userService;

    @Resource
    private  RedisTemplate redisTemplate;

    @GetMapping("/{phone}")
    public ResponseServer sendMessage(@PathVariable  String phone) throws IOException {
        //先判断一下
        if (StringUtils.isBlank(phone)) {
            return ResponseServer.error(ServerEnum.PHONE_ISNULL);
        }

        //获取发送验证码的状态
       /* JSONObject jsonObject = SendMessage.sendMessage(phone);
        String code = jsonObject.getString("code");
        if (code.equals("200")) {
            String checkCode = jsonObject.getString("obj");
            redisTemplate.opsForValue().set("code_" + phone, checkCode, 300, TimeUnit.SECONDS);
        }*/
        redisTemplate.opsForValue().set("code_" + phone, "123456", 300, TimeUnit.SECONDS);
        return ResponseServer.success();
    }

     @PostMapping()
     public ResponseServer login(String checkedCode,String phone){
         //先判断一下
         if (StringUtils.isBlank(checkedCode)) {
             return ResponseServer.error(ServerEnum.CHECKEDCODE_ISNULL);
         }
         String code = (String) redisTemplate.opsForValue().get("code_" + phone);
         if(!checkedCode.equals(code)){
             return ResponseServer.error(ServerEnum.CHECKEDCODE_ERROR);
         }

         redisTemplate.delete("code_"+phone);

         UserPo user = userService.isRegisterPhone(phone);
         redisTemplate.opsForValue().set("user_" + phone, user);
         redisTemplate.opsForValue().set("cartid_" + phone, user.getCartId());
         redisTemplate.opsForValue().set("userId_" + phone, user.getId());


         Map<String,Object> map = new HashMap<String,Object>();
         map.put("phone",phone);
         String token = JwtUtils.createToken(map);

         return ResponseServer.success(token);
     }


    /*@CrossOrigin(maxAge = 3600,origins = "http://localhost:8080")
    @GetMapping()
    public Map<String,Object> login( String loginName,  String password, HttpServletRequest request){
        Map<String,Object>  resultMap = new HashMap<String,Object>();

        if(StringUtils.isBlank(loginName) || StringUtils.isBlank(password)){
            resultMap.put("success",false);
            resultMap.put("message","用户Id或密码为空");
        }
        UserPo loginUser = null;
        try {
             loginUser = userService.queryUserByName(loginName);
        }catch (Exception e){
            resultMap.put("success",false);
            resultMap.put("message","账户异常,请联系管理员");
        }

        if(loginUser == null){
            resultMap.put("success",false);
            resultMap.put("message","用户名错误，请重新登录");
            return resultMap;
        }
        if(!loginUser.getPassword().equals(password)){
            resultMap.put("success",false);
            resultMap.put("message","密码错误，请重新登录");
            return resultMap;
        }
        resultMap.put("success",true);
        request.getSession().setAttribute("loginUser",loginUser);
        return  resultMap;
    }
*/





}
