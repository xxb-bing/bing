<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/11/21
  Time: 14:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="row">
    <div class="panel panel-default">
        <div class="panel-body">
          <%--  此品牌下的所有商品--%>
         </div>
    </div>
</div>
<div class="row">
    <div class="panel panel-default">
        <div class="panel-footer">
            <table id="shopTable1"  class="table table-hover" ></table>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function(){
        var  brandId = "${brandId}"
        var  shopTable1 = $("#shopTable1").DataTable({
            "lengthMenu": [ 2, 5, 10 ],
            "ordering": false,//是否允许排序",
            "paging": true,//是否允许开启本地分页
            "serverSide": true,//true是服务器模式，false是客户端模式

            "ajax":{
                url:"http://localhost:8090/shop/"+brandId,
                type:"get",
                "dataSrc":function (result) {
                    return result.data;
                }
            },
            //数据如何展示到表格中
            "columns":[
                {"data":"name","title":"商品名"},
                {"data":"brandName","title":"品牌名"},
                {"data":"typeName","title":"类型名"},
                {"data":"subtitle","title":"宣传标题"},
                {"data":"mainImg","title":"主图片",render:function (data,type,row,meta) {
                        return "";

                    }},
                {"data":"subImgs","title":"副图片",render:function (data,type,row,meta) {
                        return "";
                    }},
                {"data":"detail","title":"商品描述"},
                {"data":"price","title":"价格"},
                {"data":"stock","title":"库存"},
                {"data":"createTime","title":"创建时间",render:function (data, type, row, meta) {
                        if(data!=null){
                            return new Date(data).Format("yyyy-MM-dd hh:mm:ss");
                        }
                        return "";
                    }},
                {"data":"updateTime","title":"修改时间",render:function (data, type, row, meta) {
                        if(data!=null){
                            return new Date(data).Format("yyyy-MM-dd hh:mm:ss");
                        }
                        return "";
                    }},
                {"data":"status","title":"商品状态",render:function (data, type, row, meta) {
                        return data==1?"上架":"下架";
                    }},
                {"data":"id","title":"操作",render:function (data, type, row, meta) {
                        return "";
                    }}
            ],
            "language":{
                "url":"<%=request.getContextPath()%>/commons/datatable/Chinese.json"
            }
        })
    })
</script>
</body>
</html>
