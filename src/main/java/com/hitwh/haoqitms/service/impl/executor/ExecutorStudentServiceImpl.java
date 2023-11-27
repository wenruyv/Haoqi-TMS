package com.hitwh.haoqitms.service.impl.executor;

import com.hitwh.haoqitms.entity.Student;
import com.hitwh.haoqitms.mapper.StudentMapper;
import com.hitwh.haoqitms.service.ExcelService;
import com.hitwh.haoqitms.service.executor.ExecutorStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class ExecutorStudentServiceImpl implements ExecutorStudentService {
    private final StudentMapper studentMapper;
    private final ExcelService excelService;

    @Autowired
    public ExecutorStudentServiceImpl(StudentMapper studentMapper, ExcelService excelService) {
        this.studentMapper = studentMapper;
        this.excelService = excelService;
    }

    @Override
    public InputStream getAllStudentEmail() {
        List<Student> students = studentMapper.getAllStudent();
        return excelService.generateStudentEmailExcel(students);
    }
}
