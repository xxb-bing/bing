<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/10/22
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<div class="panel-body">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    正在进行类型修改
</div>
<form  class="form-horizontal" id="updateShopTypeForm" >
    <input type="hidden" name="pid"  value="${shopTypePo.pid}"/>
    <input type="hidden" name="id"  value="${shopTypePo.id}"/>

    <div class="form-group">
        <label  class="col-sm-2 control-label">父类型名称:</label>
        <div class="col-sm-2">
            <input type="text"  class="form-control"  id="parentShopType" readonly >
        </div>
    </div>

    <div class="form-group">
        <label  class="col-sm-2 control-label">类型名称</label>
        <div class="col-sm-2">
            <input type="text" class="form-control" name="typeName" value="${shopTypePo.typeName}">
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">
            类型状态
        </label>
        <label class="control-label">
            <input type="radio" name="isValid" value="1"  ${shopTypePo.isValid==1?"checked":""}>有效
            <input type="radio" name="isValid" value="2"  ${shopTypePo.isValid==2?"checked":""}>无效
        </label>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">

        </label>
        <button type="button" onclick="updateShopType()" class="btn btn-primary" >立即提交</button>
    </div>
</form>



</body>
</html>
