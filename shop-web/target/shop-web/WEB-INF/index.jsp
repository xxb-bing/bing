<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2019/10/28
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link type="text/css" href="/commons/a/css/style.css" rel="stylesheet" />
    <script type="text/javascript" src="/commons/a/js/jquery.min.js"></script>
    <!-- bootstrap -->
    <script type="text/javascript" src="/commons/bootstrap/js/bootstrap.min.js"></script>
    <!-- datatable -->
    <script type="text/javascript" src="/commons/datatable/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="/commons/datatable/js/dataTables.bootstrap.min.js"></script>
    <!-- bootstrap -->
    <link href="/commons/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- datatable -->
    <link href="/commons/datatable/css/dataTables.bootstrap.min.css" rel="stylesheet"/>


</head>
<body>

<!--左侧菜单-->
<div class="sec-mainL left">
    <div class="mainL-hd-box">
        <h2 class="mainL-hd"><a href="#">商品</a></h2>
    </div>
    <ul class="sec-mainNav" id="menuTree">

    </ul>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-lg-10" >
            <div id="brandAndTypeShow">

            </div>
            <div id="table"></div>
        </div>
    </div>
 </div>

<script>
    $(function(){
        oneNodeMenu();
    })
    var menuHtml = "";
    function  oneNodeMenu(){
        $.ajax({
            url:"http://localhost:8091/type",
            dataType:"json",
            type:"get",
            async:false,
            success:function (result) {
                 for(var i=0;i<result.length;i++){
                     if(result[i].pid == 0){
                         menuHtml += '<li><h3>'+result[i].name+'</h3>'
                     }
                     var  twoNodes =  result[i].children;
                     menuHtml += '<div class="menu-tab">'
                     for(var j=0;j<twoNodes.length;j++){
                         if(j == 3){
                             break;
                         }/*'+twoNodes[j].id+','+result[i].id+'*/
                         menuHtml += ' <a href="javascript:;" onclick="shopAndBrandAndTypeShow('+twoNodes[j].id+','+result[i].id+')">'+ twoNodes[j].name + '</a>'
                     }
                     menuHtml += '<div class="fix"></div></div>'

                     menuHtml += '<div class="menu-panel"><div class="menu-panel-hd"><h4>热门分类</h4> <div class="sub-group">'
                     for(var b=0;b<twoNodes.length;b++){
                       menuHtml += '<a href="javascript:;" onclick="shopAndBrandAndTypeShow('+twoNodes[b].id+','+result[i].id+')">' + twoNodes[b].name + '</a>'
                     }
                     menuHtml += '</div></div>'

                     $.ajax({
                         url:"http://localhost:8092/brand/"+result[i].id,
                         dataType:"json",
                         type:"get",
                         async:false,
                         success:function (logoResult) {
                             menuHtml += '<div class="menu-panel-bd"><ul>'
                             for(var c=0;c<logoResult.length;c++){
                                 if(logoResult[c] != null){
                                     menuHtml += '<li><a href="javascript:;" onclick="shopShowByBrnadId('+logoResult[c].id+','+result[i].id+')"><img src="'+logoResult[c].logoUrl+'" /></a></li>'
                                 }
                             }
                         }
                     })
                     menuHtml += '</ul></div></li>'
                 }
            }
        })
        $("#menuTree").html(menuHtml)
    }
</script>
<script type="text/javascript">
    $(function() {
        var $top = $('.sec-mainNav').offset().top + $('.sec-mainNav').height()
        //左侧导航动画
        $('.sec-mainNav li').on('mouseenter', function() {
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
</script>

<script>
    function shopAndBrandAndTypeShow(typeId,parentTypeId) {
        var showHtml="";
        $.ajax({
            type:"get",
            url:"http://localhost:8091/type/"+parentTypeId,
            dataType:"json",
            async:false,
            success:function (result) {
                 //拼接类别菜单
                showHtml+='<div class="menu-panel-hd">';
                showHtml+='<h4>热门分类</h4>';
                showHtml+='<div class="sub-group">';
                for(var i=0;i<result.length;i++){
                    showHtml+='<a   href="javascript:void(0)" >'+result[i].typeName+'</a>';
                 }
                showHtml+='</div>';
                showHtml+='</div>';
            }
        })
        $.ajax({
            url:"http://localhost:8092/brand/"+parentTypeId,
            dataType:"json",
            type:"get",
            async:false,
            success:function (logoResult) {
                showHtml+='<div class="menu-panel-bd">';
                showHtml+=' <ul>';
                for(var c=0;c<logoResult.length;c++){
                    if(logoResult[c] != null){
                        showHtml+='<li><a  href="javascript:void(0)" ><img src="'+logoResult[c].logoUrl+'" /></a></li>';
                    }
                }
                showHtml+='</ul>';
                showHtml+='</div>';
            }
        })
        $("#brandAndTypeShow").html(showHtml);
      $.ajax({
            "url":"<%=request.getContextPath()%>/toShopShow",
            dataType:"html",
            data:{"typeId":typeId},
            success:function(result){
                $("#table").html(result)
            }
        })
    }

    function shopShowByBrnadId(brandId,parentTypeId){
        var showHtml="";
        $.ajax({
            type:"get",
            url:"http://localhost:8091/type/"+parentTypeId,
            dataType:"json",
            async:false,
            success:function (result) {
                //拼接类别菜单
                showHtml+='<div class="menu-panel-hd">';
                showHtml+='<h4>热门分类</h4>';
                showHtml+='<div class="sub-group">';
                for(var i=0;i<result.length;i++){
                    showHtml+='<a   href="javascript:void(0)" >'+result[i].name+'</a>';
                }
                showHtml+='</div>';
                showHtml+='</div>';
            }
        })
        $.ajax({
            url:"http://localhost:8092/brand/"+parentTypeId,
            dataType:"json",
            type:"get",
            async:false,
            success:function (logoResult) {
                showHtml+='<div class="menu-panel-bd">';
                showHtml+=' <ul>';
                for(var c=0;c<logoResult.length;c++){
                    if(logoResult[c] != null){
                        showHtml+='<li><a  href="javascript:void(0)" ><img src="'+logoResult[c].logoUrl+'" /></a></li>';
                    }
                }
                showHtml+='</ul>';
                showHtml+='</div>';
            }
        })
        $("#brandAndTypeShow").html(showHtml);
        $.ajax({
            "url":"<%=request.getContextPath()%>/toShopShowByBrand",
            dataType:"html",
            data:{"brandId":brandId},
            success:function(result){
                $("#table").html(result)
            }
        })
    }
</script>


</body>
</html>
