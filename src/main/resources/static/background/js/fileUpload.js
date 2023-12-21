/*
 * author: pjavac
 * version: 1.0
 * name: fileUpload
 * describe: 文件上传
 */


function updatePhoto(flag) {
    var formdata=new FormData();
    console.log(flag);
    if(flag === 0){
        formdata.append("fileName",$("#fileName0").get(0).files[0]);
        // 更新背景
    }else if(flag === 1){
        formdata.append("fileName",$("#fileName1").get(0).files[0]);
    }
    console.log(formdata);
    $.ajax({
        async: false,
        type: "POST",
        url: "profilePage/imageUpload/" + flag,
        dataType: "json",
        data: formdata,
        contentType:false,//ajax上传图片需要添加
        processData:false,//ajax上传图片需要添加
        success: function (data) {
            window.location.reload();
        },
        error: function (e) {
            alert("修改失败")
        }
    });
}
