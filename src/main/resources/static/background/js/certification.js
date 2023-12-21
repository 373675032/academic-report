/**
 * 删除用户id的全部消息
 */
function delAllMessage(id) {
    $.ajax({
        type: "POST",
        url: "updateMessageIsRead",
        data: {id: id},
        dataType: "json",
        success: function (data) {
            if (data['code'] == 200) {
                alert("操作成功！");
                window.location.reload();
            } else {
                alert("服务器发生了一个错误");
            }
        }
    });
}

function submitForm() {
    var idCardReg = /^[1-9]\d{5}(18|19|20|(3\d))\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    var idCard = $("#idCard").val();

    var name = $("#name").val();
    if (name == "") {
        alert("机构名称不能为空!")
        return;
    }
    var leaderName = $("#leaderName").val();
    if (leaderName == "") {
        alert("领导者姓名不能为空!")
        return;
    }
    var idCard = $("#idCard").val();
    if (idCard == "") {
        alert("领导者身份证号不能为空!")
        return;
    }
    if (idCardReg.test(idCard) == false) {
        alert("身份证号不符合要求！");
        return;
    }
    // var license = $("#license").val();
    // if (license == "") {
    //     alert("单证证明/营业执照不能为空!")
    //     return;
    // }
    // var idCard1 = $("#idCard1").val();
    // if (idCard1 == "") {
    //     alert("领导者身份证件不能为空!")
    //     return;
    // }
    // var idCard2 = $("#idCard2").val();
    // if (idCard2 == "") {
    //     alert("领导者身份证件不能为空!")
    //     return;
    // }
    var type = $("#type").val();
    if (type == "") {
        alert("机构不能为空!")
        return;
    }
    var showName = $("#showName").val();
    if (showName == "") {
        alert("认证账号不能为空!")
        return;
    }

    $.ajax({
        type: "POST",
        url: "insertCertification",
        data: {
            name: name,
            leaderName: leaderName,
            idCard: idCard,
            // license: license,
            // idCard1: idCard1,
            // idCard2: idCard2,
            type: type,
            showName: showName,
        },
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                alert("申请认证成功！");
                window.location.href = "/love-charity/profile";
            } else {
                alert("服务器发生了一个错误");
            }
        }
    });

}
