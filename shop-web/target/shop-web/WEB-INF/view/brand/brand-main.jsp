<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/10/23
  Time: 16:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<jsp:include page="../../../plugin.jsp"></jsp:include>
<body>

    <div class="row">
        <div class="panel panel-default">
            <div class="panel-body">
                <button  onclick="addBrand()" type="button"  class="btn"><span class="glyphicon glyphicon-plus"/>新增品牌</button>
            </div>
            <div class="panel-footer">
                <table id="brandTable"  class="table table-hover" ></table>
            </div>
        </div>
    </div>




<script type="text/javascript">


     var  brandTable = $("#brandTable").DataTable({
        "lengthMenu": [ 3, 8, 12 ],
        "ordering": false,//是否允许排序",
        "paging": true,//是否允许开启本地分页
        "serverSide": true,//true是服务器模式，false是客户端模式
        "ajax":{
            url:"http://localhost:8092/brand",
            type:"get",
            dataType:"json",
            "dataSrc":function (result) {
                return result.data;
            }
        },
        //数据如何展示到表格中
        "columns":[
            {"data":"id","title":"<input type='checkbox' onclick='fanxuan()'/>",render:function(data, type, row, meta) {
                    return "<input type='checkbox' name='checkbox' value = '"+data+"' >";
                }},
            {"data":"brandName","title":"品牌名"},
            {"data":"telPhone","title":"联系电话"},
            {"data":"website","title":"品牌网址"},
            {"data":"status","title":"品牌状态",render:function (data, type, row, meta) {
                    return data==1?"公开":"不公开";
            }},
            {"data":"logoUrl","title":"品牌logo",render:function (data,type,row,meta) {
                    return "<img src='"+data+"' class='img-thumbnail' width='100px' />";
                }},
            {"data":"updateTime","title":"修改时间",render:function (data, type, row, meta) {
                    if(data!=null){
                        return new Date(data).Format("yyyy-MM-dd hh:mm:ss");
                    }
                    return "";
            }},

            {"data":"typeName","title":"品牌类型"},
            {"data":"id","title":"操作",render:function (data, type, row, meta) {
                    return  '<button type="button" class="btn" onclick="updateBrand('+data+')"><span class="glyphicon glyphicon-pencil"/>修改</button>'+
                            ' <button  onclick="deleteBrand('+data+')" type="button"  class="btn"><span class="glyphicon glyphicon-remove"/>删除</button>'
            }}
         ],
        "language":{
            "url":"../../commons/datatable/Chinese.json"
        }
    })

     function  fanxuan(){
         $("[name=checkbox]").each(function(i,e){
             e.checked=!e.checked;
         });
     }

     /*刷新页面*/
     function reload(){
         brandTable.ajax.reload();
     }

    /* function  addBrand(){
         var  addBrandHtml = null;
         $.post({
             url:"jumpJsp?url=brand/brand-add",
             dataType:"html",
             async:false,
             success:function(result){
                 addBrandHtml = result;
             },
             error:function(){
                 bootbox.alert("获取新增品牌页面失败")
             }
         });

         bootbox.confirm({
             message:addBrandHtml ,
             title:"新增品牌",
             size:"large",
             buttons: {
                 confirm: {
                     label: '保存',
                     className: 'btn-success'
                 },
                 cancel: {
                     label: '取消',
                     className: 'btn-danger'
                 }
             },
             callback: function (result) {
                 if(result){
                     $.post({
                         url:"addBrand",
                         dataType:"text",
                         data:$("#addBrandForm").serialize(),
                         success:function(result){
                             if(result == "ok"){
                                 reload();
                             }
                         },
                         error:function(){
                             bootbox.alert("新增品牌失败")
                         }
                     });
                 }
             }
         });
         uploadFile();
}

    function  updateBrand(id){
        var  updateBrandHtml = null;
        $.post({
            url:"toUpdateBrand",
            dataType:"html",
            data:{"id":id},
            async:false,
            success:function(result){
                updateBrandHtml = result;
            },
            error:function(){
                bootbox.alert("获取修改品牌页面失败")
            }
        });
        bootbox.confirm(
            {
            message:updateBrandHtml ,
            title:"修改品牌",
            size:"large",
            buttons: {
                confirm: {
                    label: '保存',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if(result){
                    $.post({
                        url:"updateBrand",
                        dataType:"text",
                        data:$("#updateBrandForm").serialize(),
                        success:function(result){
                            if(result == "ok"){
                                bootbox.alert("修改成功");
                                reload();
                            }
                        },
                        error:function(){
                            bootbox.alert("修改品牌失败")
                        }
                    });
                }
            }
        }
        );
    }

     function  deleteBrand(id){
         bootbox.confirm({
             message:"你确认要删除吗?(将无法恢复)",
             title:"删除用户",
             size:"large",
             buttons: {
                 confirm: {
                     label: '保存',
                     className: 'btn-success'
                 },
                 cancel: {
                     label: '取消',
                     className: 'btn-danger'
                 }
             },
             callback: function (result) {
                 if(result){
                     $.post({
                         url:"deleteBrand",
                         dataType:"text",
                         data:{"brandId":id},
                         success:function(result){
                             if(result == "ok"){
                                 bootbox.alert("删除成功")
                                 reload();
                             }
                         },
                         error:function(){
                             bootbox.alert("删除品牌失败")
                         }
                     });
                 }
             }
         });
     }*/

</script>
<script>
        // =============上传文件 ==============
      /*  function  uploadFile(){
            $("#logoPhoto").fileinput({
                uploadUrl:"uploadFile",//上传的地址
                language : 'zh',//显示中文
                dropZoneEnabled : false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
                showCaption:false,//是否显示标题,就是那个文本框
                allowedFileTypes : [ 'image' ],//配置允许文件上传的类型
                /!*  allowedFileExtensions: ['jpg','png','jpeg'],//自定义设置允许上传的文件后缀  和 allowedFileTypes二选一*!/
                showUpload : false, //是否显示上传按钮,跟随文本框的那个
                showRemove : false, //显示移除按钮,跟随文本框的那个
            }).on("fileuploaded",function(event, result, previewId, index) {
                var  url = result.response.data;
                $("#photoUrl").val(url);
            });
        }*/
</script>
</body>
</html>
