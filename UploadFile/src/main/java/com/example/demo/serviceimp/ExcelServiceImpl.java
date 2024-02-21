package com.example.demo.serviceimp;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Service.ExcelService;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Value("${upload.path}")
    private String uploadPath;

    @SuppressWarnings("resource")
	@Override
    public String processExcelFile(MultipartFile file) throws Exception {
        // Create a new file in the upload directory
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File excelFile = new File(uploadPath + File.separator + fileName);
        file.transferTo(excelFile);

        // Read the uploaded Excel file
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1); // Update the second row (index 1) as an example
        Cell cell = row.createCell(0);
        cell.setCellValue("Updated Value");

        // Write the changes back to the file
        try (FileOutputStream outputStream = new FileOutputStream(excelFile)) {
            workbook.write(outputStream);
        }

        return "File uploaded and updated successfully.";
    }
}
