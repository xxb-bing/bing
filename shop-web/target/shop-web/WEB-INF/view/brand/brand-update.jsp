<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/10/23
  Time: 17:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>



<form  class="form-horizontal" id="updateBrandForm">
    <input type="hidden" value="${brand.id}" name="id"/>
    <input type="hidden" id="photoUrl" name="logoUrl" value="${brand.logoUrl}"/>
    <div class="form-group">
        <label  class="col-sm-2 control-label">品牌名</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="brandName" value="${brand.brandName}" >
        </div>
    </div>

    <div class="form-group">
        <label  class="col-sm-2 control-label">联系电话:</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="telPhone" value="${brand.telPhone}" >
        </div>
    </div>

    <div class="form-group">
        <label  class="col-sm-2 control-label">品牌网址</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="website" value="${brand.website}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">
            品牌状态:
        </label>
        <label class="control-label">
            <input type="radio" name="status" value="1"  ${brand.status==1?"checked":""} >公开
            <input type="radio" name="status" value="2"  ${brand.status==2?"checked":""}>不公开
        </label>
    </div>



    <div class="form-group">
        <label  class="col-sm-2 control-label">品牌类型</label>
        <div class="col-sm-6">
            <input type="hidden" id="selectTypeId" name="typeIds"/>
            <input type="text"  id="selectTypeName" onclick="openType()" class="form-control" readonly  placeholder="点击选择类型" >
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">品牌logo:</label>
        <label class="control-label"><input type="file" id="logoPhoto" name="logoPhoto" ></label>
    </div>

</form>


<script type="text/javascript">

   $(function () {
       queryTypeNameByBrandId();
   })

   function  queryTypeNameByBrandId(){
       $.post({
           "url":"<%=request.getContextPath()%>/queryTypeNameByBrandId",
           dataType:"json",
           data:{"brandId":${brand.id}},
           success:function(result){
               var  typeNames = "";
               var  typeIds = "";
               for(var i=0;i<result.length;i++){
                   typeNames+=result[i].typeName+",";
                   typeIds+=result[i].id+",";
               }
               typeNames = typeNames.substring(0,typeNames.length-1);
               typeIds = typeIds.substring(0,typeIds.length-1);
               $("#selectTypeName").val(typeNames)
               $("#selectTypeId").val(typeIds)
           }
       })
   }

    function  openType(){
        var selectTypeSetting = {
            view: {
                selectedMulti: false
            },
            check: {
                enable: true,
                chkboxType: { "Y": "p", "N": "s" }
            }
        }
        $.post({
            "url":"<%=request.getContextPath()%>/queryTypeTreeByBrandId",
            dataType:"json",
            data:{"brandId":${brand.id}},
            success:function(result){
                $.fn.zTree.init($("#selectTypeZtree"), selectTypeSetting, result).expandAll(true);
            }
        })


        bootbox.confirm({
            message:"<div class='ztree' id='selectTypeZtree'></div>" ,
            title:"选择品牌类型",
            size:"small",
            buttons: {
                confirm: {
                    label: '确定',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if(result){
                    var  typeZtreeObj =  $.fn.zTree.getZTreeObj("selectTypeZtree");
                    var  typeCheckedNodes = typeZtreeObj.getCheckedNodes();
                    var  typeIds = "";
                    var  typeNames = "";
                    for(var i=0;i<typeCheckedNodes.length;i++){
                         typeIds+=typeCheckedNodes[i].id+",";
                         typeNames+=typeCheckedNodes[i].name+",";
                    }
                    typeIds = typeIds.substring(0,typeIds.length-1);
                    typeNames = typeNames.substring(0,typeNames.length-1);
                    $("#selectTypeId").val(typeIds)
                    $("#selectTypeName").val(typeNames)
                }
            }
        });
    }
</script>

<script>
    var  url = '${brand.logoUrl}';
    var   previewArr = []
    if(url != null && url != ""){
        previewArr.push(url);
    }
    $("#logoPhoto").fileinput({
        uploadUrl:"<%=request.getContextPath()%>/uploadFile",//上传的地址
        language : 'zh',//显示中文
        dropZoneEnabled : false,//是否显示拖拽区域，默认不写为true，但是会占用很大区域
        showCaption:false,//是否显示标题,就是那个文本框
        allowedFileTypes : [ 'image' ],//配置允许文件上传的类型
        /*  allowedFileExtensions: ['jpg','png','jpeg'],//自定义设置允许上传的文件后缀  和 allowedFileTypes二选一*/
        showUpload : false, //是否显示上传按钮,跟随文本框的那个
        showRemove : false, //显示移除按钮,跟随文本框的那个
        initialPreview:previewArr,
        initialPreviewAsData: true, // 特别重要
    }).on("fileuploaded",function(event, result, previewId, index) {
        var  url = result.response.data;
        $("#photoUrl").val(url);
    });
</script>
</body>
</html>
