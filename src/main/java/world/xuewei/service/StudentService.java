package world.xuewei.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import world.xuewei.constant.Role;
import world.xuewei.dto.ResponseResult;
import world.xuewei.entity.College;
import world.xuewei.entity.Student;
import world.xuewei.entity.Teacher;
import world.xuewei.entity.User;
import world.xuewei.mapper.StudentMapper;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学生服务
 *
 * <p>
 * ==========================================================================
 * 郑重说明：本项目免费开源！原创作者为：薛伟同学，严禁私自出售。
 * ==========================================================================
 * B站账号：薛伟同学
 * 微信公众号：薛伟同学
 * 作者博客：http://xuewei.world
 * ==========================================================================
 * 陆陆续续总会收到粉丝的提醒，总会有些人为了赚取利益倒卖我的开源项目。
 * 不乏有粉丝朋友出现钱付过去，那边只把代码发给他就跑路的，最后还是根据线索找到我。。
 * 希望各位朋友擦亮慧眼，谨防上当受骗！
 * ==========================================================================
 *
 * @author <a href="http://xuewei.world/about">XUEW</a>
 */
@Slf4j
@Service
public class StudentService {

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherService teacherService;

    @Resource
    private CollegeService collegeService;

    public boolean insert(Student student) {
        return studentMapper.insert(student) == 1;
    }


    public boolean deleteById(Integer id) {
        return studentMapper.deleteById(id) == 1;
    }


    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }


    public Student getByNo(String no) {
        return studentMapper.getByNo(no);
    }


    public List<Student> listStudents() {
        return studentMapper.listStudents();
    }


    public List<Student> listStudents(Student student) {
        return studentMapper.listStudents(student);
    }


    public int countStudents(Student student) {
        return studentMapper.countStudents(student);
    }


    public boolean update(Student student) {
        return studentMapper.update(student) == 1;
    }

    /**
     * 修改邮箱
     */
    public ResponseResult updateEmail(String email, User loginUser, Student loginStudent) {
        ResponseResult result = new ResponseResult();
        String role = loginUser.getRole();
        if (Role.STUDENT.equals(role)) {
            // 学生角色
            loginStudent.setEmail(email);
            if (update(loginStudent)) {
                result.setCode(200);
                result.setMessage("邮箱修改成功");
            } else {
                result.setCode(500);
                result.setMessage("服务器错误");
            }
        } else {
            result.setCode(501);
            result.setMessage("修改失败");
        }
        return result;
    }

    public ResponseResult updatePhone(String phone, User loginUser, Student loginStudent, Teacher loginTeacher) {
        ResponseResult result = new ResponseResult();
        String role = loginUser.getRole();
        if (Role.STUDENT.equals(role)) {
            // 学生角色
            loginStudent.setPhone(phone);
            if (update(loginStudent)) {
                result.setCode(200);
                result.setMessage("手机号修改成功");
            } else {
                result.setCode(500);
                result.setMessage("服务器错误");
            }
        } else if (Role.TEACHER.equals(role)) {
            // 教师角色
            loginTeacher.setPhone(phone);
            if (teacherService.update(loginTeacher)) {
                result.setCode(200);
                result.setMessage("手机号修改成功");
            } else {
                result.setCode(500);
                result.setMessage("服务器错误");
            }
        } else {
            result.setCode(501);
            result.setMessage("修改失败");
        }
        return result;
    }

    /**
     * 分页查询学生
     */
    public Object pageAllStudents(Integer page, Integer rows, String searchField, String searchString) {
        Map<String, Object> map = new HashMap<>();
        List<Student> students = new ArrayList<>();
        if (StrUtil.isNotEmpty(searchString)) {
            // 搜索
            Student search = null;
            if ("no".equals(searchField)) {
                search = Student.builder().no(searchString).build();
            }
            if ("name".equals(searchField)) {
                search = Student.builder().name(searchString).build();
            }
            if ("phone".equals(searchField)) {
                search = Student.builder().phone(searchString).build();
            }
            if ("email".equals(searchField)) {
                search = Student.builder().email(searchString).build();
            }
            students = listStudents(search);
        } else {
            // 分页查询
            PageHelper.startPage(page, rows);
            students = listStudents();
        }
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        // 将查询结果放入map
        map.put("rows", dealStudent(students));
        // 总页数
        map.put("total", pageInfo.getPages());
        // 总条数
        map.put("records", pageInfo.getTotal());
        return map;
    }

    /**
     * 处理完善学生对象
     */
    private List<Student> dealStudent(List<Student> list) {
        list.forEach(student -> {
            // 设置学院
            Integer collegeId = student.getCollegeId();
            College college = collegeService.getById(collegeId);
            student.setCollegeName(college.getName());
            // 设置出生日期字符串
            student.setBirthdayStr(String.format("%tF", student.getBirthday()));
        });
        return list;
    }

    /**
     * 编辑删除学生
     */
    public void editStudent(Student student, String action) {
        log.info("student = " + student + ", action = " + action);
        if ("edit".equals(action)) {
            // 编辑
            update(student);
        }
        if ("del".equals(action)) {
            // 删除
            deleteById(student.getId());
        }
    }

    /**
     * 导出学生
     */
    public void exportStudents(HttpServletResponse response) throws IOException {
        response.setHeader("content-disposition",
                "attachment;fileName=" + URLEncoder.encode("学生列表.xls", "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        // 查询所有学生
        List<Student> students = listStudents();
        // 生成文件的信息
        ExportParams params = new ExportParams();
        params.setTitle("XX大学-学生列表");
        params.setSheetName("学生列表");
        Workbook workbook = ExcelExportUtil.exportExcel(params, Student.class, dealStudent(students));
        // 输出
        workbook.write(outputStream);
        // 关闭资源
        outputStream.close();
        workbook.close();
    }

    /**
     * 导入学生
     */
    public ResponseResult importStudents(MultipartFile file) {
        ResponseResult result = new ResponseResult();
        // 参数1：文件流
        InputStream stream = null;
        try {
            stream = file.getInputStream();
        } catch (IOException e) {
            log.error(e.getMessage());
            result.setCode(500);
            result.setMessage("读取文件失败");
            return result;
        }
        // 参数2：导入类型
        ImportParams params = new ImportParams();
        // 标题占用多少行
        params.setTitleRows(1);
        // 头部属性占用多少行
        params.setHeadRows(1);
        // 对Excel进行合法参数校验
        String[] array = new String[]{"学号", "姓名", "性别", "邮箱", "手机号码", "出生日期", "年级", "专业", "班级", "院系"};
        params.setImportFields(array);
        List<Student> students = null;
        try {
            students = ExcelImportUtil.importExcel(stream, Student.class, params);
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setCode(302);
            result.setMessage("请检查上传的Excel文件是否严格遵守模板规定");
            return result;
        }
        Map<String, String> error = new HashMap<>();
        int emptyNo = 0;
        // 添加学生
        for (Student student : students) {
            if (StrUtil.isEmpty(student.getNo())) {
                emptyNo++;
            } else if (StrUtil.isEmpty(student.getName())) {
                error.put(student.getNo(), "姓名不能为空");
            } else if (StrUtil.isEmpty(student.getSex())) {
                error.put(student.getNo(), "性别不能为空");
            } else if (ObjectUtil.isEmpty(student.getBirthday())) {
                error.put(student.getNo(), "出生日期不能为空");
            } else if (ObjectUtil.isEmpty(student.getCollegeName())) {
                error.put(student.getNo(), "院系不能为空");
            } else {
                // 查询院系信息
                String collegeName = student.getCollegeName();
                College college = College.builder().name(collegeName).build();
                List<College> colleges = collegeService.listColleges(college);
                if (colleges.size() == 0) {
                    error.put(student.getNo(), "院系名称错误");
                } else {
                    student.setCollegeId(colleges.get(0).getId());
                    student.setPassword(student.getNo());
                    Student build = Student.builder().no(student.getNo()).build();
                    // 检查该编号是否已存在
                    List<Student> exist = listStudents(build);
                    if (exist.size() > 0) {
                        error.put(student.getNo(), "编号被占用");
                    } else {
                        insert(student);
                    }
                }
            }
        }
        // 成功导入的记录数
        int success = students.size() - error.size() - emptyNo;
        if (success == students.size()) {
            // 全部成功
            result.setCode(200);
            result.setMessage("成功导入" + students.size() + "条记录");
        } else {
            result.setCode(303);
            result.setMessage("成功导入" + success + "条记录，共 " + students.size() + "条");
            if (emptyNo > 0) {
                error.put("编号为空", emptyNo + "条");
            }
            // 生成错误信息
            String errorHtml = "";
            for (String s : error.keySet()) {
                errorHtml += s + "：" + error.get(s) + "、";
            }
            errorHtml = errorHtml.substring(0, errorHtml.length() - 1);
            result.setData(errorHtml);
        }
        return result;
    }
}