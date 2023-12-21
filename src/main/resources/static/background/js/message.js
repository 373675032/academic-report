

/**
 * 删除用户id的全部消息
 */
function delAllMessage(id){
    $.ajax({
        type: "POST",
        url: "updateMessageIsRead",
        data: {id:id},
        dataType: "json",
        success: function(data){
            if (data['code']== 200){
                alert("操作成功！");
                window.location.reload();
            }else {
                alert("服务器发生了一个错误");
            }
        }
    });
}
