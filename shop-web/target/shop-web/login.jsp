<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
   <%-- <script type="text/javascript" src="/commons/jquery-3.2.1.js"></script>--%>
    <link rel="apple-touch-icon" sizes="76x76" href="commons/assets/img/apple-icon.png">
    <link rel="icon" type="image/png" href="commons/assets/img/favicon.png">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>Login Page - Now UI Kit by Creative Tim</title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
    <link href="commons/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link href="commons/assets/css/now-ui-kit.css?v=1.1.0" rel="stylesheet" />
    <!-- CSS Just for demo purpose, don't include it in your project -->
    <link href="commons/assets/css/demo.css" rel="stylesheet" />
    <!-- Canonical SEO -->
    <link rel="canonical" href="" />
    <!--  Social tags      -->
    <meta name="keywords" content="">
    <meta name="description" content="">

</head>
<script type="text/javascript" src="/commons/js/jquery-3.3.1.min.js"></script>
<script>

    function login(){
        var  loginName = $("#loginName").val();
        var  password = $("#password").val();
        if(loginName == null || loginName.trim().length == 0){
            alert("用户id不能为空");
            return false;
        }
        if(password ==null || password.trim().length == 0  ){
            alert("密码不能为空");
            return false;
        }
        $.ajax({
            url:"http://localhost:8093/login",
            type:"get",
            dataType:"json",
            data:{
                "loginName":loginName,
                "password": password
            },
            success:function(result){
                if(result.success){
                    /*window.location.href = "<%=request.getContextPath()%>/jumpJsp?url=index2"*/
                    window.location.href="/login/index.html";
                }else{
                   alert(result.message)
                }
            },
            error:function(){
               alert("登录异常");
            }
        });
    }

    function keyLogin(){
        if (event.keyCode==13)  //回车键的键值为13
         /*   document.getElementById("login").click();*/ //调用登录按钮的登录事件
        $("#login").click();
    }

</script>

<body class="login-page sidebar-collapse" onkeydown="keyLogin();">

<div class="page-header" >
    <div class="page-header-image" style="background-image:url(commons/assets/img/bg7.jpg)"></div>
    <div class="container">
        <div class="col-md-4 content-center">
            <div class="card card-login card-plain">
                <form class="form">
                    <div class="content">
                        <div class="input-group form-group-no-border input-lg">
                                <span class="input-group-addon">
                                    <i class="now-ui-icons users_circle-08"></i>
                                </span>
                            <input type="text" class="form-control"  name="loginName"  id="loginName"  placeholder="登录名">
                        </div>
                        <div class="input-group form-group-no-border input-lg">
                                <span class="input-group-addon">
                                    <i class="now-ui-icons text_caps-small"></i>
                                </span>
                            <input type="password"   id="password"  placeholder="密码" class="form-control" />
                        </div>
                    </div>
                    <div class="footer text-center">
                        <a href="javascript:;"  type="submit" id="login"  onclick="login()" class="btn  btn-round btn-lg btn-block">登录</a>
                    </div>
                </form>
            </div>
        </div>
    </div>


</div>
</body>


</html>


