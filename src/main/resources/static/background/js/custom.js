/**
 * 登录
 */
function login() {
    let no = $("#no").val().trim();
    let password = $("#password").val().trim();
    let role = $('#role').find('option:selected').val();

    if (no == '') {
        xtip.msg("编号不能为空");
        return false;
    }

    if (password == '') {
        xtip.msg("密码不能为空");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "login",
        data: {no: no, password: password, role: role},
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reloadTo('/profile')", 1000);
            }
        }
    });
    return false;
}

/**
 * AI 润色标题
 */
function generateTitle() {
    let title = $("#title").val().trim();
    if (title === '') {
        xtip.msg("标题不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "generateTitle",
        data: {title: title},
        dataType: "json",
        success: function (data) {
            xtip.msg("AI 润色成功！");
            $("#title").val(data['message']);
        }
    });
}

/**
 * AI 润色报告简介
 */
function generateReport() {
    let info = $("#info").val().trim();
    let title = $("#title").val().trim();
    if (info === '' && title === '') {
        xtip.msg("标题和简介不能同时为空");
        return;
    }
    if (info === '' && title !== '') {
        info = title;
    }
    $.ajax({
        type: "POST",
        url: "generateReport",
        data: {info: info},
        dataType: "json",
        success: function (data) {
            xtip.msg("AI 润色成功！");
            $("#info").val(data['message']);
        }
    });
}

/**
 * AI 润色报告简介
 */
function generateReporter() {
    let reporterInfo = $("#reporterInfo").val().trim();
    if (reporterInfo === '') {
        xtip.msg("报告人简介不能为空");
        return;
    }
    $.ajax({
        type: "POST",
        url: "generateReporter",
        data: {reporterInfo: reporterInfo},
        dataType: "json",
        success: function (data) {
            xtip.msg("AI 润色成功！");
            $("#reporterInfo").val(data['message']);
        }
    });
}

/**
 * 修改密码
 */
function reset() {
    let old = $("#old-input").val().trim();
    let password = $("#password").val().trim();
    let confirm = $("#password-confirm").val().trim();

    if (old == '') {
        xtip.msg("请输入原密码");
        return;
    }
    if (password == '') {
        xtip.msg("请输入新密码");
        return;
    }
    if (confirm == '') {
        xtip.msg("请确认新密码");
        return;
    }
    if (password != confirm) {
        xtip.msg("两次输入的密码不一致");
        return;
    }
    $.ajax({
        type: "POST",
        url: "reset",
        data: {oldPassword: old, newPassword: password},
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reloadTo('/login.html')", 1000);
            }
        }
    });
}

/**
 * 修改邮箱
 */
function updateEmail() {
    let email = $("#email").val().trim();
    if (email == '') {
        xtip.msg("邮箱不可为空");
        return;
    }
    if (!isPhone(email)) {
        xtip.msg("请输入正确的邮箱地址");
        return;
    }
    $.ajax({
        type: "POST",
        url: "updateEmail",
        data: {email: email},
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reload()", 1000);
            }
        }
    });
}

/**
 * 修改手机号
 */
function updatePhone() {
    let phone = $("#phone").val().trim();
    if (phone == '') {
        xtip.msg("手机号不可为空");
        return;
    }
    if (!isPhone(phone)) {
        xtip.msg("请输入正确的手机号");
        return;
    }
    $.ajax({
        type: "POST",
        url: "updatePhone",
        data: {phone: phone},
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reload()", 1000);
            }
        }
    });
}

/**
 * 检查手机号格式
 * @param phone
 * @returns {boolean}
 */
function isPhone(phone) {
    var reg = /^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
    if (!reg.test(phone)) {
        return false;
    } else {
        return true;
    }
}

/**
 * 检查邮箱格式
 * @param email
 * @returns {boolean}
 */
function isEmail(email) {
    var reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    if (!reg.test(email)) {
        return false;
    } else {
        return true;
    }
}

/**
 * 忘记密码
 */
function forget() {
    xtip.confirm('邮箱：373675032@qq.com <br>' +
        '电话：17879540430（微信同号）', '', {title: '登陆账号请联系作者：XUEW'});
}

/**
 * 刷新页面
 */
function reload() {
    window.location.reload();
}

/**
 * 跳转到指定页面
 * url 例如 /hello
 */
function reloadTo(url) {
    url = url.trim();
    let href = window.location.href;
    href = href.split("/report")[0];
    if (url != null && url != '') {
        window.location.href = href + "/report" + url;
    }
}

/**
 * 导入学生列表
 */
function importStudents() {
    var formData = new FormData();
    let file = $('#importStudents')[0].files[0];
    formData.append("file", file);
    $.ajax({
        url: 'importStudents',
        dataType: 'json',
        type: 'POST',
        async: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data['code'] == 200) {
                xtip.msg(data['message']);
                setTimeout("reload()", 2000);
            } else if (data['code'] == 303) {
                xtip.win({
                    type: 'confirm', //alert 或 confirm
                    btn: ['确定'],
                    tip: data['data'],
                    icon: 'w',
                    title: data['message'],
                    min: true,
                    width: '500px',
                    shade: false,
                    shadeClose: false,
                    lock: false,
                    btn1: function () {
                        reload();
                    },
                    zindex: 99999,
                });
            } else {
                xtip.msg(data['message']);
            }
        },
        error: function (response) {
            console.log(response);
        }
    });
}

/**
 * 导入教师列表
 */
function importTeachers() {
    var formData = new FormData();
    let file = $('#importTeachers')[0].files[0];
    formData.append("file", file);
    $.ajax({
        url: 'importTeachers',
        dataType: 'json',
        type: 'POST',
        async: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data['code'] == 200) {
                xtip.msg(data['message']);
                setTimeout("reload()", 2000);
            } else if (data['code'] == 303) {
                xtip.win({
                    type: 'confirm', //alert 或 confirm
                    btn: ['确定'],
                    tip: data['data'],
                    icon: 'w',
                    title: data['message'],
                    min: true,
                    width: '500px',
                    shade: false,
                    shadeClose: false,
                    lock: false,
                    btn1: function () {
                        reload();
                    },
                    zindex: 99999,
                });
            } else {
                xtip.msg(data['message']);
            }
        },
        error: function (response) {
            console.log(response);
        }
    });
}

/**
 * 发表学术报告
 */
function publish() {
    var formData = new FormData();
    let title = $("#title").val().trim();
    let reporterInfo = $("#reporterInfo").val().trim();
    let info = $("#info").val().trim();
    let file = $('#file')[0].files[0];
    let reportFile = $('#reportFile')[0].files[0];
    if (title == '') {
        xtip.msg("标题不能为空");
        return;
    }
    if (info == '') {
        xtip.msg("报告简介不能为空");
        return;
    }
    if (reporterInfo == '') {
        xtip.msg("报告人简介不能为空");
        return;
    }
    if (reportFile == null) {
        xtip.msg("未上传学术报告文件");
        return;
    } else {
        if (file != null) {
            formData.append("file", file);
        }
        formData.append("reportFile", reportFile);
        formData.append("title", title);
        formData.append("info", info);
        formData.append("reporterInfo", reporterInfo);

        $.ajax({
            url: 'publishReport',
            dataType: 'json',
            type: 'POST',
            async: false,
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                xtip.msg(data['message']);
                if (data['code'] == 200) {
                    setTimeout("reloadTo('/my-report')", 2000);
                }
            },
            error: function (response) {
                console.log(response);
            }
        });
    }
}

/**
 * 编辑学术报告
 */
function edit() {
    var formData = new FormData();
    let id = $("#id").val().trim();
    let title = $("#title").val().trim();
    let reporterInfo = $("#reporterInfo").val().trim();
    let info = $("#info").val().trim();
    let file = $('#file')[0].files[0];
    let reportFile = $('#reportFile')[0].files[0];

    if (title == '') {
        xtip.msg("标题不能为空");
        return;
    }
    if (info == '') {
        xtip.msg("报告简介不能为空");
        return;
    }
    if (reporterInfo == '') {
        xtip.msg("报告人简介不能为空");
        return;
    }
    formData.append("id", id);
    formData.append("title", title);
    formData.append("info", info);
    formData.append("reporterInfo", reporterInfo);
    if (file != null) {
        formData.append("file", file);
    }
    if (reportFile != null) {
        formData.append("reportFile", reportFile);
    }
    $.ajax({
        url: 'editReport',
        dataType: 'json',
        type: 'POST',
        async: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                setTimeout("reloadTo('/my-report')", 2000);
            }
        },
        error: function (response) {
            console.log(response);
        }
    });
}

/**
 * 移入回收站
 */
function intoTrash(id) {
    xtip.confirm('这样将会复原此报告的审核进度 ! <br> 确认将此报告移入回收站吗 ? ', function () {
        $.ajax({
            type: "POST",
            url: "intoTrash",
            data: {id: id},
            dataType: "json",
            success: function (data) {
                xtip.msg(data['message']);
                if (data['code'] == 200) {
                    // 等待1秒钟
                    setTimeout("reload()", 1000);
                }
            }
        });
    }, {"title": "移入回收站"});
}

/**
 * 移出回收站
 */
function restore(id) {
    $.ajax({
        type: "POST",
        url: "restore",
        data: {id: id},
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reload()", 1000);
            }
        }
    });
}

/**
 * 永久删除
 */
function deleteReport(id) {
    xtip.confirm('此操作不可逆 ! <br> 确认将此报告永久删除吗 ? ', function () {
        $.ajax({
            type: "POST",
            url: "deleteReport",
            data: {id: id},
            dataType: "json",
            success: function (data) {
                xtip.msg(data['message']);
                if (data['code'] == 200) {
                    // 等待1秒钟
                    setTimeout("reload()", 1000);
                }
            }
        });
    }, {"title": "永久删除"});
}

/**
 * 永久删除全部
 */
function deleteAll() {
    xtip.confirm('此操作不可逆 ! <br> 确认将全部报告永久删除吗 ? ', function () {
        $.ajax({
            type: "POST",
            url: "deleteAll",
            data: {},
            dataType: "json",
            success: function (data) {
                xtip.msg(data['message']);
                if (data['code'] == 200) {
                    // 等待1秒钟
                    setTimeout("reload()", 1000);
                }
            }
        });
    }, {"title": "清空回收站"});
}

/**
 * 全部移出回收站
 */
function restoreAll() {
    $.ajax({
        type: "POST",
        url: "restoreAll",
        data: {},
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reload()", 1000);
            }
        }
    });
}

/**
 * 初步审核报告
 */
function showReport(title, info, reporterInfo, id) {
    xtip.win({
        width: '800px',
        tip: '<div class="card" style="margin: 0px;padding: 0px">\n' +
            '    <div class="card-body">\n' +
            '      <div id="accordion">\n' +
            '        <div class="accordion">\n' +
            '          <div class="accordion-header" aria-expanded="true">\n' +
            '            <h4>报告简介</h4>\n' +
            '          </div>\n' +
            '          <div class="accordion-body collapse show" data-parent="#accordion" style="text-indent: 2em">\n' +
            info +
            '          </div>\n' +
            '        </div>\n' +
            '        <div class="accordion">\n' +
            '          <div class="accordion-header" aria-expanded="true">\n' +
            '            <h4>报告人简介</h4>\n' +
            '          </div>\n' +
            '          <div class="accordion-body collapse show" data-parent="#accordion" style="text-indent: 2em">\n' +
            reporterInfo +
            '          </div>\n' +
            '        </div>\n' +
            '      </div>\n' +
            '    </div>\n' +
            '</div>',
        title: '《' + title + '》',
        btn: ['去处理'],
        btn1: function () {
            xtip.win({
                width: '600px',
                tip: '<div class="card" style="margin: 0px;padding: 0px">\n' +
                    '    <div class="card-body" style="padding: 0px">\n' +
                    '       <div class="form-group">\n' +
                    '           <select class="form-control" id="checkType">\n' +
                    '               <option value="1">通过初审 → 移交教务部门审核</option>\n' +
                    '               <option value="-1">驳回报告</option>\n' +
                    '           </select><br>\n' +
                    '           <textarea id="checkInfo" style="height:200px" class="form-control" placeholder="请在此处填写处理意见 ..."></textarea>' +
                    '       </div>' +
                    '    </div>\n' +
                    '</div>',
                title: '处理《' + title + '》',
                btn: ['提交'],
                btn1: function () {
                    let checkInfo = $("#checkInfo").val().trim();
                    let check = $('#checkType').find('option:selected').val();
                    if (checkInfo == '') {
                        checkInfo = "通过审核！";
                    }
                    $.ajax({
                        type: "POST",
                        url: "dealReport",
                        data: {id: id, status: check, checkInfo: checkInfo},
                        dataType: "json",
                        success: function (data) {
                            xtip.msg(data['message']);
                            if (data['code'] == 200) {
                                // 等待1秒钟
                                setTimeout("reload()", 1000);
                            }
                        }
                    });
                },
            });
        },
    });
}

/**
 * 最终审核报告
 */
function auditReport(title, info, reporterInfo, id, checkInfo1) {
    xtip.win({
        width: '800px',
        tip: '<div class="card" style="margin: 0px;padding: 0px">\n' +
            '    <div class="card-body">\n' +
            '      <div id="accordion">\n' +
            '        <div class="accordion">\n' +
            '          <div class="accordion-header" aria-expanded="true">\n' +
            '            <h4>报告简介</h4>\n' +
            '          </div>\n' +
            '          <div class="accordion-body collapse show" data-parent="#accordion" style="text-indent: 2em">\n' +
            info +
            '          </div>\n' +
            '        </div>\n' +
            '        <div class="accordion">\n' +
            '          <div class="accordion-header" aria-expanded="true">\n' +
            '            <h4>报告人简介</h4>\n' +
            '          </div>\n' +
            '          <div class="accordion-body collapse show" data-parent="#accordion" style="text-indent: 2em">\n' +
            reporterInfo +
            '          </div>\n' +
            '        <div class="accordion">\n' +
            '          <div class="accordion-header" aria-expanded="true">\n' +
            '            <h4>初审评语</h4>\n' +
            '          </div>\n' +
            '          <div class="accordion-body collapse show" data-parent="#accordion" style="text-indent: 2em">\n' +
            checkInfo1 +
            '          </div>\n' +
            '        </div>\n' +
            '      </div>\n' +
            '    </div>\n' +
            '</div>',
        title: '《' + title + '》',
        btn: ['最终审核'],
        btn1: function () {
            xtip.win({
                width: '600px',
                tip: '<div class="card" style="margin: 0px;padding: 0px">\n' +
                    '    <div class="card-body" style="padding: 0px">\n' +
                    '       <div class="form-group">\n' +
                    '           <select class="form-control" id="checkType">\n' +
                    '               <option value="2">通过审核</option>\n' +
                    '               <option value="-2">驳回报告</option>\n' +
                    '           </select><br>\n' +
                    '           <textarea id="checkInfo" style="height:200px" class="form-control" placeholder="请在此处填写处理意见 ..."></textarea>' +
                    '       </div>' +
                    '    </div>\n' +
                    '</div>',
                title: '处理《' + title + '》',
                btn: ['提交'],
                btn1: function () {
                    let checkInfo = $("#checkInfo").val().trim();
                    let check = $('#checkType').find('option:selected').val();
                    if (checkInfo == '') {
                        checkInfo = "通过审核！";
                    }
                    $.ajax({
                        type: "POST",
                        url: "dealReport",
                        data: {id: id, status: check, checkInfo: checkInfo},
                        dataType: "json",
                        success: function (data) {
                            xtip.msg(data['message']);
                            if (data['code'] == 200) {
                                // 等待1秒钟
                                setTimeout("reload()", 1000);
                            }
                        }
                    });
                },
            });
        },
    });
}


/**
 * 预览报告
 */
function previewReport(title, info, reporterInfo, id, checkInfo1, checkInfo2, teacher, phone) {
    xtip.win({
        width: '800px',
        tip: '<div class="card" style="margin: 0px;padding: 0px">\n' +
            '    <div class="card-body">\n' +
            '      <div id="accordion">\n' +
            '        <div class="accordion">\n' +
            '          <div class="accordion-header" aria-expanded="true">\n' +
            '            <h4>报告简介</h4>\n' +
            '          </div>\n' +
            '          <div class="accordion-body collapse show" data-parent="#accordion" style="text-indent: 2em">\n' +
            info +
            '          </div>\n' +
            '        </div>\n' +
            '        <div class="accordion">\n' +
            '          <div class="accordion-header" aria-expanded="true">\n' +
            '            <h4>报告人简介</h4>\n' +
            '          </div>\n' +
            '          <div class="accordion-body collapse show" data-parent="#accordion" style="text-indent: 2em">\n' +
            '【' + teacher + "：" + phone + '】' + reporterInfo +
            '          </div>\n' +
            '        <div class="accordion">\n' +
            '          <div class="accordion-header" aria-expanded="true">\n' +
            '            <h4>初审评语</h4>\n' +
            '          </div>\n' +
            '          <div class="accordion-body collapse show" data-parent="#accordion" style="text-indent: 2em">\n' +
            checkInfo1 +
            '          </div>\n' +
            '        </div>\n' +
            '        <div class="accordion">\n' +
            '          <div class="accordion-header" aria-expanded="true">\n' +
            '            <h4>终审评语</h4>\n' +
            '          </div>\n' +
            '          <div class="accordion-body collapse show" data-parent="#accordion" style="text-indent: 2em">\n' +
            checkInfo2 +
            '          </div>\n' +
            '        </div>\n' +
            '      </div>\n' +
            '    </div>\n' +
            '</div>',
        title: '《' + title + '》',
    });
}

/**
 * 安排会议
 */
function arrangeMeeting(id, title, name, phone) {
    xtip.win({
        width: '800px',
        tip: '<div class="card" style="margin: 0px;padding: 0px">\n' +
            '    <div class="card-body">\n' +
            '       <div class="row">' +
            '           <div class="col-6 form-group">' +
            '               <label for="reporter">报告人</label>' +
            '               <input id="reporter" disabled type="text" value="' + name + ' : ' + phone + '" class="form-control">' +
            '           </div>' +
            '           <div class="col-6 form-group">' +
            '               <label for="host_no">主持人职工号</label>' +
            '               <input id="host_no" type="text" class="form-control">' +
            '           </div>' +
            '       </div>' +
            '       <div class="row">' +
            '           <div class="col-6 form-group">' +
            '               <label for="report_time">报告时间</label>' +
            '               <input id="report_time" type="text" placeholder="格式：2020-02-08 12:30" class="form-control">' +
            '           </div>' +
            '           <div class="col-6 form-group">' +
            '               <label for="report_address">报告地点</label>' +
            '               <input id="report_address" type="text" class="form-control">' +
            '           </div>' +
            '       </div>' +
            '       <div class="row">' +
            '           <div class="col-6 form-group">' +
            '               <label for="appoint_end">预约截止时间</label>' +
            '               <input id="appoint_end" type="text" placeholder="格式：2020-02-08 08:00" class="form-control">' +
            '           </div>' +
            '           <div class="col-6 form-group">' +
            '               <label for="capacity">容纳人数</label>' +
            '               <input id="capacity" type="number" class="form-control">' +
            '           </div>' +
            '       </div>' +
            '    </div>\n' +
            '</div>',
        title: '安排报告会议 - ' + title,
        btn: ['提交会议安排'],
        btn1: function () {
            let hostNo = $("#host_no").val().trim();
            let reportTime = $("#report_time").val().trim();
            let reportAddress = $("#report_address").val().trim();
            let appointEnd = $("#appoint_end").val().trim();
            let capacity = $("#capacity").val().trim();
            if (hostNo == '' || reportTime == '' || reportAddress == '' || appointEnd == '' || capacity == '') {
                xtip.msg("请将会议信息填写完整！");
                return;
            }
            $.ajax({
                type: "POST",
                url: "arrangeMeeting",
                data: {
                    id: id,
                    hostNo: hostNo,
                    reportTime: reportTime,
                    reportAddress: reportAddress,
                    appointEnd: appointEnd,
                    capacity: capacity
                },
                dataType: "json",
                success: function (data) {
                    xtip.msg(data['message']);
                    if (data['code'] == 200) {
                        // 等待1秒钟
                        setTimeout("reload()", 1000);
                    }
                }
            });
        },
    });
}

/**
 * 修改学术报告会议
 */
function updateMeeting() {
    let meetingId = $("#meeting_id").val().trim();
    let hostNo = $("#host_no").val().trim();
    let reportTime = $("#report_time").val().trim();
    let reportAddress = $("#report_address").val().trim();
    let appointEnd = $("#appoint_end").val().trim();
    let capacity = $("#capacity").val().trim();
    if (hostNo == '' || reportTime == '' || reportAddress == '' || appointEnd == '' || capacity == '') {
        xtip.msg("请将会议信息填写完整！");
        return;
    }
    $.ajax({
        type: "POST",
        url: "updateMeeting",
        data: {
            meetingId: meetingId,
            hostNo: hostNo,
            reportTime: reportTime,
            reportAddress: reportAddress,
            appointEnd: appointEnd,
            capacity: capacity
        },
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reload()", 1000);
            }
        }
    });
}

/**
 * 更新签到状态
 */
function updateStatus(id) {
    let statusId = '#select' + id;
    let status = $(statusId).find('option:selected').val();
    $.ajax({
        type: "POST",
        url: "updateStatus",
        data: {id: id, status: status},
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reload()", 1000);
            }
        }
    });
}

/**
 * 导入签到情况
 */
function importAppointments() {
    var formData = new FormData();
    let file = $('#importAppointments')[0].files[0];
    formData.append("file", file);
    $.ajax({
        url: 'importAppointments',
        dataType: 'json',
        type: 'POST',
        async: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            if (data['code'] == 200) {
                xtip.msg(data['message']);
                setTimeout("reload()", 2000);
            } else if (data['code'] == 303) {
                xtip.win({
                    type: 'confirm', //alert 或 confirm
                    btn: ['确定'],
                    tip: data['data'],
                    icon: 'w',
                    title: data['message'],
                    min: true,
                    width: '500px',
                    shade: false,
                    shadeClose: false,
                    lock: false,
                    btn1: function () {
                        reload();
                    },
                    zindex: 99999,
                });
            } else {
                xtip.msg(data['message']);
            }
        },
        error: function (response) {
            console.log(response);
        }
    });
}

/**
 * 报名
 */
function apply(id) {
    $.ajax({
        type: "POST",
        url: "applyMeeting",
        data: {id: id},
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reload()", 1000);
            }
        }
    });
}

/**
 * 通过学年和学期搜索会议
 */
function searchMeetings() {
    let year = $("#year").find('option:selected').val();
    let semester = $("#semester").find('option:selected').val();
    reloadTo("/report-statistics?year=" + year + "&semester=" + semester);
}

/**
 * 清空消息中心
 */
function deleteAllMessages() {
    $.ajax({
        type: "POST",
        url: "deleteAllMessages",
        data: {},
        dataType: "json",
        success: function (data) {
            xtip.msg(data['message']);
            if (data['code'] == 200) {
                // 等待1秒钟
                setTimeout("reload()", 1000);
            }
        }
    });
}