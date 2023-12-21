/*
 * author: pjavac
 * version: 1.0
 * name: profile
 * describe: 用户资料修改中各种验证 最后的表单提交
 */

var old_pwd;
var new1;

function clickUpload() {
    $("#file").click();
}


function uploadUserImg() {
    var formdata = new FormData();
    formdata.append("file", $("#file").get(0).files[0]);
    $.ajax({
        async: false,
        type: "POST",
        url: "image/user",
        dataType: "json",
        data: formdata,
        contentType: false,//ajax上传图片需要添加
        processData: false,//ajax上传图片需要添加
        success: function (data) {
            if (data.code == 200) {
                alert("头像上传成功!")
                window.location.reload();
            } else if (data.code == 500) {
                alert("头像上传失败!")
            }
        },
        error: function (e) {
            alert("头像上传失败！");
        }
    });
}

function checkUserPhone() {
    var phoneReg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
    var phone = $("#phone").val().trim();
    if (phone != null && phone != "") {
        if (!phoneReg.test(phone)) {
            alert("手机号格式错误!");
            return false;
        }
        return true;
    }
}

function checkUserEmail() {
    var userEmail = $("#oldEmail").val().trim();
    var newUserEmail = $("#newEmail").val().trim();
    var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    $("#emailCode").attr("disabled", false);
    var userEmailNotSame;
    if (newUserEmail == "") {
        alert("邮箱不能为空!");
        $("#emailCode").attr("disabled", true);
        return false;
    } else if (newUserEmail === userEmail) {
        $("#emailCode").attr("disabled", true);
    } else if (!reg.test(newUserEmail)) {
        alert("邮箱格式错误!");
        return false;
    } else {
        $.ajax({
            url: "getUserByEmailOrName",
            data: {param:newUserEmail},
            type: "post",
            dataType: "json",
            success: function (str) {
                if (str.code == 200) {
                    return true;
                } else {
                    alert("邮箱已经存在！");
                    $("#emailCode").attr("disabled", true);
                    return false;
                }
            }
        });
    }
}


/**
 信息页面修改密码
 */
function updatePwd1() {
    var old_pwd = $('#old_pwd').val().trim();
    var new1 = $('#new_pwd').val().trim();
    var new2 = $('#new_pwd1').val().trim();
    if (old_pwd == '') {
        alert("请输入原始密码");
        return;
    }
    if (new1 == '') {
        alert("请输入新密码");
        return;
    }
    if (new2 == '') {
        alert("请输入确认密码");
        return;
    }
    if (new1 === '' || new2 === '' || new1 != new2) {
        alert("两次密码输入的不一致！");
        // xtip.msg("两次密码输入的不一致！",{times:2});
        return;
    } else {
        $.ajax({
            type: "POST",
            url: "updateUserPwd",
            data: {
                oldPwd: old_pwd,
                newPwd: new1
            },
            dataType: "json",
            success: function (data) {
                if (data['code'] == 200) {
                    alert("密码修改成功！");
                    window.location.reload();
                } else {
                    alert("原始密码错误！");
                }
            }
        });
    }
}

/**
 * 个人资料用户信息修改
 */
function submitUserInfo() {

    //获取普通信息
    var name = $("#name").val();
    if (name == "") {
        alert("用户名不能为空!")
        return;
    }
    var phone = $("#phone").val();
    if (checkUserPhone() == false){
        return;
    };
    var newEmail = $("#newEmail").val();
    var code = $("#code").val();
    var career = $("#career").val();
    var sex = $("#sex").val();
    var birthday = $("#data").val();
    //获取地址
    var province = $("#input_province").find("option:selected").text();
    var city = $("#input_city").find("option:selected").text();
    var area = $("#input_area").find("option:selected").text();
    var content = "";
    if (null != province && province != "") {
        content = province;
        if (null != city && city != "") {
            content += "-" + city;
            if (null != area && area != "") {
                content += "-" + area;
            }
        }
    }
    var strs= new Array(); //定义一数组
    strs=content.split("-"); //字符分割
    if (strs.length < 3) {
        content = "";
    }

    $.ajax({
        type: "POST",
        url: "updateUserInfo",
        data: {
            name: name,
            phone: phone,
            email: newEmail,
            code: code,
            career: career,
            sex: sex,
            bornDate :birthday,
            address:content
        },
        dataType: "json",
        success: function (data) {
            if (data['code'] == 200) {
                alert("信息修改成功！");
                window.location.reload();
            } else {
                alert("信息修改失败！");
            }
        }
    });
}