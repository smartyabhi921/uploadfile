package com.example.demo.Service;
import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

    String processExcelFile(MultipartFile file) throws Exception;
}
