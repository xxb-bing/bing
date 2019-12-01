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



<form  class="form-horizontal" id="addBrandForm">
    <input type="hidden" id="photoUrl" name="logoUrl" value=""/>
    <div class="form-group">
        <label  class="col-sm-2 control-label">品牌名</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="brandName" >
        </div>
    </div>

    <div class="form-group">
        <label  class="col-sm-2 control-label">联系电话:</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="telPhone" >
        </div>
    </div>

    <div class="form-group">
        <label  class="col-sm-2 control-label">品牌网址</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="website">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">
            品牌状态:
        </label>
        <label class="control-label">
            <input type="radio" name="status" value="1" >公开
            <input type="radio" name="status" value="2" >不公开
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
            "url":"/queryTypeTree",
            dataType:"json",
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


</body>
</html>
