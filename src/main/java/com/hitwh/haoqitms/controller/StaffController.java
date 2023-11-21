package com.hitwh.haoqitms.controller;

import com.hitwh.haoqitms.entity.ResultInfo;
import com.hitwh.haoqitms.entity.StudentCourse;
import com.hitwh.haoqitms.service.StaffService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/staff")
@RestController
public class StaffController {
    private final StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    /**
     * 现场工作人员根据相关信息获取学员列表
     * @param request 请求
     * @param courseId 课程id
     * @param name 学员姓名
     * @param phone 学员电话
     * @param companyName 学员公司名称
     * @return 学员列表
     */
    @GetMapping("/students_list/{course_id}/{name}/{phone}/{company_name}")
    public ResultInfo getStudentList(HttpServletRequest request,
                                     @PathVariable("course_id") Integer courseId,
                                     @PathVariable(value = "name", required = false) String name,
                                     @PathVariable(value = "phone", required = false) String phone,
                                     @PathVariable(value = "company_name", required = false) String companyName) {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourseId(courseId);
        studentCourse.setName(name.equals("null") ? null : name);
        studentCourse.setPhone(phone.equals("null") ? null : phone);
        studentCourse.setCompanyName(companyName.equals("null") ? null : companyName);

        ResultInfo info = new ResultInfo();
        try {
            info.setFlag(true);
            info.setData(staffService.getStudents(studentCourse));
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("获取学员列表失败");
        }
        return info;
    }

    /**
     * 现场工作人员根据课程id获取评价列表
     * @param request 请求
     * @param courseId 课程id
     * @return 评价列表
     */
    @GetMapping("/evaluation/{course_id}")
    public ResultInfo getEvaluation(HttpServletRequest request, @PathVariable("course_id") Integer courseId) {
        ResultInfo info = new ResultInfo();
        try {
            info.setFlag(true);
            info.setData(staffService.getTrainingEvaluationByCourseId(courseId));
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("获取评价列表失败");
        }
        return info;
    }

    /**
     * 现场工作人员根据课程id获取调查报告
     * @param request 请求
     * @param courseId 课程id
     * @return 调查报告
     */
    @GetMapping("/report/{course_id}")
    public ResultInfo getReport(HttpServletRequest request, @PathVariable("course_id") Integer courseId) {
        ResultInfo info = new ResultInfo();
        try {
            info.setFlag(true);
            info.setData(staffService.getCourseSurveyReport(courseId));
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("获取调查报告失败");
        }
        return info;
    }

    /**
     * 现场工作人员根据学生id和课程id更新学生缴费状态
     * @param request 请求
     * @param studentId 学生id
     * @param courseId 课程id
     * @return 是否更新成功
     */
    @PutMapping("/payment/{student_id}/{course_id}")
    public ResultInfo updatePayStatus(HttpServletRequest request,
                                      @PathVariable("student_id") Integer studentId,
                                      @PathVariable("course_id") Integer courseId) {
        ResultInfo info = new ResultInfo();
        try {
            info.setFlag(staffService.updateStudentPayStatus(studentId, courseId));
            if (!info.getFlag()) {
                info.setErrorMsg("更新缴费状态失败");
            }
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("更新缴费状态失败");
        }
        return info;
    }

    @PutMapping("/attendance/{student_id}/{course_id}/{attendance}")
    public ResultInfo updateAttendanceStatus(HttpServletRequest request,
                                             @PathVariable("student_id") Integer studentId,
                                             @PathVariable("course_id") Integer courseId,
                                             @PathVariable("attendance") Boolean attendance) {
        ResultInfo info = new ResultInfo();
        try {
            info.setFlag(staffService.updateStudentAttendanceStatus(studentId, courseId, attendance));
            if (!info.getFlag()) {
                info.setErrorMsg("更新考勤状态失败");
            }
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("更新考勤状态失败");
        }
        return info;
    }

    @PutMapping("/report/{course_id}")
    public ResultInfo updateReport(HttpServletRequest request,
                                   @PathVariable("course_id") Integer courseId,
                                   @RequestBody String report) {
        ResultInfo info = new ResultInfo();
        try {
            info.setFlag(staffService.updateCourseSurveyReport(courseId, report));
            if (!info.getFlag()) {
                info.setErrorMsg("更新调查报告失败");
            }
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("更新调查报告失败");
        }
        return info;
    }

    @DeleteMapping("/evaluation/{evaluation_id}")
    public ResultInfo deleteEvaluation(HttpServletRequest request,
                                       @PathVariable("evaluation_id") Integer evaluationId) {
        ResultInfo info = new ResultInfo();
        try {
            info.setFlag(staffService.deleteTrainingEvaluation(evaluationId));
            if (!info.getFlag()) {
                info.setErrorMsg("删除评价失败");
            }
        } catch (Exception e) {
            info.setFlag(false);
            info.setErrorMsg("删除评价失败");
        }
        return info;
    }

}
