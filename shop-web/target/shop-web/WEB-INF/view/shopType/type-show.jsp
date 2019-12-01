<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/10/22
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<jsp:include page="../../../plugin.jsp"></jsp:include>
<body>

<div class="container-fluid">

    <div class="row">
        <div class="col-lg-2" >
            <div class="panel-body">
                类型树展示
                <button  onclick="toAddShopType()" type="button" class="btn"><span class="glyphicon glyphicon-plus"/>新增类别</button>
                 </div>
                <div class="panel-footer">
                    <%--ztree--%>
                    <div class="ztree" id="typeZtree"></div>
                </div>
        </div>
        <div class="col-lg-10" >
            <div id="addOrUpdateTypeHtml"></div>
        </div>
    </div>
</div>

<script type="text/javascript">

    $(function(){
        initTypeTree();
    })

    var typeSetting = {
        view: {
            selectedMulti: false
        }
      /* , callback: {
            onClick: typeClick
        }*/
    }

   /* function  typeClick(event, treeId, treeNode){
        toUpdateShopType(treeNode.id)
    }*/

    function  initTypeTree(){
        $.ajax({
            "url":"http://localhost:8091/type",
            dataType:"json",
            type:"get",
            success:function(result){
                $.fn.zTree.init($("#typeZtree"), typeSetting, result).expandAll(true);
            }
        })
    }

  /* function  toAddShopType(){
        var  typeZtreeObj = $.fn.zTree.getZTreeObj("typeZtree");
        //获取选中的节点数组
        var  selectNodes =  typeZtreeObj.getSelectedNodes();
        //判断节点数组是否被选中
        if(selectNodes.length == 0){
            bootbox.alert("请先选中要添加的父节点")
            return false;
        }
        $.post({
            url:"http://localhost:8091/JumpController",
            data:{url:"/view/shopType/type-add"},
            type:"get",
            dataType:"html",
            async:false,
            success:function(result){
                $("#addOrUpdateTypeHtml").html(result);
            },
            error:function(){
                bootbox.alert("获取新增商品类型页面失败")
            }
        });
        $("#pid").val(selectNodes[0].id);
        $("#parentShopType").val(selectNodes[0].name);
    }

    function  addShopType(){
        var  typeZtreeObj = $.fn.zTree.getZTreeObj("typeZtree");
        //获取选中的节点数组
        var  selectNodes =  typeZtreeObj.getSelectedNodes();
        $.post({
            url:"http://localhost:8091/type",
            dataType:"json",
            type:"put",
            data:$("#addShopTypeForm").serialize(),
            success:function(result){
                bootbox.alert("添加成功")
                var  newNode = {
                    id:result.id,
                    name:result.typeName,
                    children:[],
                }
                typeZtreeObj.addNodes(selectNodes[0],newNode)
                $("#addOrUpdateTypeHtml").html("");
            },
            error:function(){
                bootbox.alert("新增商品类型失败")
            }
        });
    }

*/
  /*  function  toUpdateShopType(id){
        var  typeZtreeObj = $.fn.zTree.getZTreeObj("typeZtree");
        //获取选中的节点数组
        var  selectNodes =  typeZtreeObj.getSelectedNodes();
        $.post({
            url:"/toUpdateShopType",
            dataType:"html",
            async:false,
            data:{"id":id},
            success:function(result){
                $("#addOrUpdateTypeHtml").html(result);
                var typeParentNodes = selectNodes[0].getParentNode();
                if(typeParentNodes == null){
                    $("#parentShopType").val("这是一个根节点");
                }else{
                    $("#parentShopType").val(typeParentNodes.name);
                }

            },
            error:function(){
                bootbox.alert("获取修改商品类型页面失败")
            }
        });
    }

    function  updateShopType(){
        var  typeZtreeObj = $.fn.zTree.getZTreeObj("typeZtree");
        //获取选中的节点数组
        var  selectNodes =  typeZtreeObj.getSelectedNodes();
        $.post({
            url:"/updateShopType",
            dataType:"json",
            async:false,
            data:$("#updateShopTypeForm").serialize(),
            success:function(result){
                bootbox.alert("添加成功")
                selectNodes[0].name = result.typeName;
                typeZtreeObj.updateNode(selectNodes[0]);

            },
            error:function(){
                bootbox.alert("修改商品类型失败")
            }
        });
    }
*/

</script>

</body>
</html>
