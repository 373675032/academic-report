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
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import world.xuewei.dao.TeacherMapper;
import world.xuewei.dto.ResponseResult;
import world.xuewei.entity.College;
import world.xuewei.entity.Teacher;

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
 * 教师服务
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
public class TeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Resource
    @Lazy
    private CollegeService collegeService;


    public boolean insert(Teacher teacher) {
        return teacherMapper.insert(teacher) == 1;
    }


    public boolean deleteById(Integer id) {
        return teacherMapper.deleteById(id) == 1;
    }


    public Teacher getById(Integer id) {
        return teacherMapper.getById(id);
    }


    public Teacher getByNo(String no) {
        return teacherMapper.getByNo(no);
    }


    public List<Teacher> listTeachers() {
        return teacherMapper.listTeachers();
    }


    public List<Teacher> listTeachers(Teacher teacher) {
        return teacherMapper.listTeachers(teacher);
    }


    public int countTeachers(Teacher teacher) {
        return teacherMapper.countTeachers(teacher);
    }


    public boolean update(Teacher teacher) {
        return teacherMapper.update(teacher) == 1;
    }

    /**
     * 分页查询教师
     */
    public Map<String, Object> pageAllTeachers(Integer page, Integer rows, String searchField, String searchString) {
        Map<String, Object> map = new HashMap<>();
        List<Teacher> teachers = new ArrayList<>();
        if (StrUtil.isNotEmpty(searchString)) {
            // 搜索
            Teacher search = null;
            if ("no".equals(searchField)) {
                search = Teacher.builder().no(searchString).build();
            }
            if ("name".equals(searchField)) {
                search = Teacher.builder().name(searchString).build();
            }
            if ("phone".equals(searchField)) {
                search = Teacher.builder().phone(searchString).build();
            }
            if ("position".equals(searchField)) {
                search = Teacher.builder().position(searchString).build();
            }
            teachers = listTeachers(search);
        } else {
            // 分页查询
            PageHelper.startPage(page, rows);
            teachers = listTeachers();
        }
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachers);
        // 将查询结果放入map
        map.put("rows", dealTeacher(teachers));
        // 总页数
        map.put("total", pageInfo.getPages());
        // 总条数
        map.put("records", pageInfo.getTotal());
        return map;
    }

    /**
     * 处理完善教师对象
     */
    private List<Teacher> dealTeacher(List<Teacher> list) {
        list.forEach(teacher -> {
            // 设置学院
            Integer collegeId = teacher.getCollegeId();
            College college = collegeService.getById(collegeId);
            teacher.setCollegeName(college.getName());
            // 设置出生日期字符串
            teacher.setBirthdayStr(String.format("%tF", teacher.getBirthday()));
        });
        return list;
    }

    /**
     * 处理完善教师对象
     */
    private Teacher dealTeacher(Teacher teacher) {
        // 设置学院
        Integer collegeId = teacher.getCollegeId();
        College college = collegeService.getById(collegeId);
        teacher.setCollegeName(college.getName());
        // 设置出生日期字符串
        teacher.setBirthdayStr(String.format("%tF", teacher.getBirthday()));
        return teacher;
    }

    /**
     * 编辑删除
     */
    public void editTeacher(Teacher teacher, String action) {
        log.info("teacher = " + teacher + ", action = " + action);
        if ("edit".equals(action)) {
            // 编辑
            update(teacher);
        }
        if ("del".equals(action)) {
            // 删除
            deleteById(teacher.getId());
        }
    }

    /**
     * 导出教师数据
     */
    public void exportTeachers(HttpServletResponse response) throws IOException {
        response.setHeader("content-disposition",
                "attachment;fileName=" + URLEncoder.encode("教师列表.xls", "UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        // 查询所有教师
        List<Teacher> teachers = listTeachers();
        // 生成文件的信息
        ExportParams params = new ExportParams();
        params.setTitle("XX大学-教师列表");
        params.setSheetName("教师列表");

        Workbook workbook = ExcelExportUtil.exportExcel(params, Teacher.class, dealTeacher(teachers));

        // 输出
        workbook.write(outputStream);

        // 关闭资源
        outputStream.close();
        workbook.close();
    }

    /**
     * 导入教师数据
     */
    public ResponseResult importTeachers(MultipartFile file) {
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
        String[] array = new String[]{"工号", "姓名", "性别", "手机号码", "出生日期", "职称", "院系"};
        params.setImportFields(array);
        List<Teacher> teachers = null;
        try {
            teachers = ExcelImportUtil.importExcel(stream, Teacher.class, params);
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setCode(302);
            result.setMessage("请检查上传的Excel文件是否严格遵守模板规定");
            return result;
        }
        Map<String, String> error = new HashMap<>();
        int emptyNo = 0;
        // 添加教师
        for (Teacher teacher : teachers) {
            if (StrUtil.isEmpty(teacher.getNo())) {
                emptyNo++;
                continue;
            } else if (StrUtil.isEmpty(teacher.getName())) {
                error.put(teacher.getNo(), "姓名不能为空");
                continue;
            } else if (StrUtil.isEmpty(teacher.getSex())) {
                error.put(teacher.getNo(), "性别不能为空");
                continue;
            } else if (ObjectUtil.isEmpty(teacher.getBirthday())) {
                error.put(teacher.getNo(), "出生日期不能为空");
                continue;
            } else if (ObjectUtil.isEmpty(teacher.getCollegeName())) {
                error.put(teacher.getNo(), "院系不能为空");
                continue;
            }
            // 查询院系信息
            String collegeName = teacher.getCollegeName();
            College college = College.builder().name(collegeName).build();
            List<College> colleges = collegeService.listColleges(college);
            if (colleges.size() == 0) {
                error.put(teacher.getNo(), "院系名称错误");
            } else {
                teacher.setCollegeId(colleges.get(0).getId());
                teacher.setPassword(teacher.getNo());
                Teacher build = Teacher.builder().no(teacher.getNo()).build();
                // 检查该编号是否已存在
                List<Teacher> exist = listTeachers(build);
                if (exist.size() > 0) {
                    error.put(teacher.getNo(), "编号被占用");
                } else {
                    insert(teacher);
                }
            }
        }
        // 成功导入的记录数
        int success = teachers.size() - error.size() - emptyNo;
        if (success == teachers.size()) {
            // 全部成功
            result.setCode(200);
            result.setMessage("成功导入" + teachers.size() + "条记录");
        } else {
            result.setCode(303);
            result.setMessage("成功导入" + success + "条记录，共 " + teachers.size() + "条");
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