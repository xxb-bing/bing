<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>jQuery常用电商导航</title>

    <script type="text/javascript" src="/commons/js/jquery-3.3.1.min.js"></script>
    <link type="text/css" href="/commons/a/css/style.css" rel="stylesheet" />
    <!-- bootstrap -->
    <script type="text/javascript" src="/commons/bootstrap/js/bootstrap.min.js"></script>
    <!-- bootstrap -->
    <link href="/commons/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- datatable -->
    <script type="text/javascript" src="/commons/datatable/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/commons/datatable/js/dataTables.bootstrap.min.js"></script>
    <!-- datatable -->
    <link href="/commons/datatable/css/dataTables.bootstrap.min.css" rel="stylesheet"/>



</head>

<body>
<!--左侧菜单-->
<div class="sec-mainL left">
    <div class="mainL-hd-box">
        <h2 class="mainL-hd"><a href="#">购物返彩贝商家</a></h2>
    </div>
    <!--菜单列表-->
    <ul class="sec-mainNav" id="menuTree">

    </ul>
</div>

<div  style="margin-left:20px" class="left">
    <div class="row" id="typeRightShow"></div>
    <div class="row" id="brandRightShow"></div>
    <div class="row">
        <table id="shopShowTable" class="table table-bordered table-striped"></table>
        <button class="btn btn-primary" type="button" onclick="lookCart()">
            查看购物车 <span class="badge" id="cartNum">0</span>
        </button>
    </div>
</div>


<script>
    var  shopShowTable = $("#shopShowTable").DataTable({
        "lengthMenu": [ 2, 5, 10 ],
        "ordering": false,//是否允许排序",
        "paging": true,//是否允许开启本地分页
        "serverSide": true,//true是服务器模式，false是客户端模式
        "ajax":{
            url:"http://localhost:8090/shop/",
            type:"post",
            "dataSrc":function (result) {
                return result.data;
            }
        },
        //数据如何展示到表格中
        "columns":[
            {"data":"name","title":"商品名称"},
            {"data":"subtitle","title":"宣传标题"},
            {"data":"detail","title":"商品描述"},
            {"data":"price","title":"价格"},
            {"data":"mainImg","title":"主图片",render:function (data,type,row,meta) {
                    return/* "<img src='"+data+"' class='img-thumbnail' width='100px' />";*/ "";
             }},
            {"data":"id","title":"操作",render:function (data, type, row, meta) {
                    return '<button class="btn btn-primary" type="button" onclick="addCarts('+data+')">加入购物车</button>';
             }}

        ],
        "language":{
            "url":"/commons/datatable/Chinese.json"
        }
    })

</script>

<script>
  function  addCarts(shopId){
      $.ajax({
          url:"http://localhost:8094/cart",
          dataType:"json",
          data:{"shopId":shopId},
          type:"post",
          success:function (result) {
              if(result.code == 200){
                  $("#cartNum").html(result.data);
                  alert("添加购物车成功");
              }
          }
      })
  }

  function lookCart(){
      $.ajax({
          url:"http://localhost:8094/cart",
          dataType:"json",
          type:"get",
          success:function (result) {
              if(result.code == 200){
                  window.open("cart/carts.html","_blank")
              }
          }
      })

  }

</script>


<script type="text/javascript">

     var alltypeList = [];
    $(function() {
        var token="";
        if(sessionStorage.getItem("token")){
            token=sessionStorage.getItem("token");
        }
        $(function () {
            $.ajaxSetup({
                //发送请求前触发
                contentType:"application/x-www-form-urlencoded;charset=utf-8",
                complete: function (XMLHttpRequest,textStatus) {
                    //请求完成时运行的函数
                    var nologin=XMLHttpRequest.getResponseHeader("NOLOGIN");
                    if (nologin=="6001" ) {
                        window.location.href="/login/index.html";
                    }
                },
                beforeSend: function (xhr) {
                    //发送请求前运行的函数。
                    //可以设置自定义标头
                    // alert("获取的token值:"+token);
                    xhr.setRequestHeader('token', token);
                }
            })
        })
        initTypeMenu();
        var $top = $('.sec-mainNav').offset().top + $('.sec-mainNav').height()
        //左侧导航动画
        $('.sec-mainNav li').on('mouseenter', function() {
            var typeAndBrandHtml='<div class="menu-panel">';
            var pid=$(this).attr("pid");
            //查询该大类别下的所有小类别
            var typeHtml=queryTypeByPid(pid);
             typeAndBrandHtml+=typeHtml;
            //查询该类别下的品牌
            var brandLogoHtml = queryBrandLogoByTypeId(pid)
            typeAndBrandHtml+=brandLogoHtml;
            typeAndBrandHtml+='</div>';
            $(this).append(typeAndBrandHtml)
            
            var $height = $(this).offset().top + $(this).find('.menu-panel').outerHeight();
            $(this).find('.menu-panel').show();
            if($height - $top >= 0) {
                $(this).find('.menu-panel').css({
                    top: -($height - $top) + 'px'
                })
            }
        });
        $('.sec-mainNav li').on('mouseleave', function() {
            $(this).find('.menu-panel').hide();
        });
    });

    function initTypeMenu(){
            $.ajax({
                url:"http://localhost:8091/type",
                dataType:"json",
                type:"get",
                async:false,
                success:function (result) {
                  if(result.code == 200){
                     var data =  result.data;
                     alltypeList=data;
                    var typeHtml = "";
                    for (var i = 0; i < data.length; i++) {
                        typeHtml += '<li pid="'+data[i].id+'">';
                        typeHtml += '<h3>' + data[i].name + '</h3>'
                        typeHtml += '<div class="menu-tab">'
                        var children = data[i].children;
                        for (var j = 0; j < children.length; j++) {
                            if(j==3){
                                break;
                            }
                            typeHtml += ' <a href="javascript:void(0)" >' + children[j].name + '</a>';
                        }
                        typeHtml += '<div class="fix"></div>';
                        typeHtml += '</div>';
                        typeHtml += '<span class="menu-more">更多</span>';
                        typeHtml += '</li>';
                    }
                    $("#menuTree").html(typeHtml)
                }
                }
            })
    }

    function queryTypeByPid(pid){
        var typeHtml=' <div class="menu-panel-hd">';
        typeHtml+=' <h4>热门分类</h4>';
        typeHtml+='  <div class="sub-group">';
        for(var i=0;i<alltypeList.length;i++){
            if(pid == alltypeList[i].id){
                var children=alltypeList[i].children;
                for(var j=0;j<children.length;j++){
                    typeHtml+='<a href="javascript:void(0)" pTypeId = "'+pid+'" typeId = "'+children[j].id+'" onclick="typeAndBrandRightShow(this)" >'+children[j].name+'</a>';
                }
                break;
            }
        }
        typeHtml+='</div>';
        typeHtml+='</div>';
        return typeHtml;
    }

    function  queryBrandLogoByTypeId(pid){
        var  brandHtml = '';
        $.ajax({
            url:"http://localhost:8092/brand/"+pid,
            dataType:"json",
            type:"get",
            async:false,
            success:function (logoResult) {
                  brandHtml += '<div class="menu-panel-bd"><ul>'
                for(var c=0;c<logoResult.length;c++){
                    if(logoResult[c] != null){
                        brandHtml += '<li><a href="javascript:void(0)" pTypeId = "'+pid+'" brandId = "'+logoResult[c].id+'" onclick="typeAndBrandRightShow(this)" ><img src="'+logoResult[c].logoUrl+'" /></a></li>'
                    }
                }
                brandHtml += '</ul></div>'
            }
        })
        return brandHtml;
    }
</script>

<script>
    //拼接  右边的品牌 类型展示
    function typeAndBrandRightShow(obj){
        var pid =  $(obj).attr("pTypeId");
        var typeId = $(obj).attr("typeId")
        var  brandId = $(obj).attr("brandId")
        if(typeof(typeId) == "undefined"){
            typeId = null;
        }
        if(typeof(brandId) == "undefined"){
            brandId = null;
        }
        queryTypeRightShow(typeId,pid);
        queryBrandRightShow(brandId,pid);
        searchShopShow();
    }


    //拼接右边类型
    function queryTypeRightShow(typeId,pid) {
        var typeRightHtml=' <div class="menu-panel-hd">';
        typeRightHtml+=' <h4>热门分类</h4>';
        typeRightHtml+='  <div class="sub-group">';
        for(var i=0;i<alltypeList.length;i++){
            if(pid == alltypeList[i].id){
                var children=alltypeList[i].children;
                for(var j=0;j<children.length;j++){
                    if(typeId == children[j].id){
                        typeRightHtml+='<a class="aclass" onclick="clickTypeStyle(this)" href="javascript:void(0)" pTypeId = "'+pid+'" typeId = "'+children[j].id+'" >'+children[j].name+'</a>';
                    }else{
                        typeRightHtml+='<a href="javascript:void(0)" onclick="clickTypeStyle(this)" pTypeId = "'+pid+'" typeId = "'+children[j].id+'" >'+children[j].name+'</a>';
                    }
                }
                break;
            }
        }
        typeRightHtml+='</div>';
        typeRightHtml+='</div>';
       $("#typeRightShow").html(typeRightHtml)
    }

    function  queryBrandRightShow(brandId,pid){
        var  brandRightHtml = '';
        $.ajax({
            url:"http://localhost:8092/brand/"+pid,
            dataType:"json",
            type:"get",
            async:false,
            success:function (logoResult) {
                brandRightHtml += '<div class="menu-panel-bd"><ul>'
                for(var c=0;c<logoResult.length;c++){
                    if(logoResult[c] != null){
                        if(brandId == logoResult[c].id){
                            brandRightHtml += '<li><a class="aclass" onclick="clickBrandStyle(this)"  href="javascript:void(0)" pTypeId = "'+pid+'" brandId = "'+logoResult[c].id+'" ><img src="'+logoResult[c].logoUrl+'" /></a></li>'
                        }else{
                            brandRightHtml += '<li><a href="javascript:void(0)" onclick="clickBrandStyle(this)" pTypeId = "'+pid+'" brandId = "'+logoResult[c].id+'" ><img src="'+logoResult[c].logoUrl+'" /></a></li>'
                        }
                    }
                }
                brandRightHtml += '</ul></div>'
            }
        })
        $("#brandRightShow").html(brandRightHtml)
    }

    function clickBrandStyle(obj){
       var allLi = $(obj).parent(0).siblings();
       allLi.each(function(){
           $(this).find("a").removeClass("aclass")
       })
        $(obj).addClass("aclass")
        searchShopShow();
    }

    function clickTypeStyle(obj){
        var allLi =$(obj).siblings();
        allLi.each(function(){
            $(this).removeClass("aclass")
        })
        $(obj).addClass("aclass")
        searchShopShow();
    }

    function  searchShopShow(){
         var pid = "";
         var typeId = "";
         var brandId = "";
         $(".aclass").each(function(){

           var  pTypeId =  $(this).attr("pTypeId");
           if(typeof (pTypeId) != "undefined"){
               pid = pTypeId;
           }

           var sunTypeId = $(this).attr("typeId");
           if(typeof (sunTypeId) != "undefined"){
               typeId = sunTypeId;
           }

           var brandid = $(this).attr("brandId");
           if(typeof (brandid) != "undefined"){
              brandId = brandid;
          }
         })
        var findTypeId = "";
        if(typeId == "" ){
            findTypeId = pid
        }else{
            findTypeId=typeId;
        }

        var parameter={};
        parameter.findTypeId=findTypeId;
        parameter.brandId=brandId;
        shopShowTable.settings()[0].ajax.data=parameter;
        shopShowTable.ajax.reload();

    }
</script>


</body>
</html>