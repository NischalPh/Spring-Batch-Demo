package com.nischal.springbatchpoc.config;

import com.nischal.springbatchpoc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Nischal Phuyal on 2/28/2025
 */

@Slf4j
@Component
@Scope("prototype")
public class ExcelWriter implements ItemWriter<User> {


    private final List<User> userList = new CopyOnWriteArrayList<>();
    private final String fileName;
    private final Workbook workbook;
    private final Sheet sheet;

    public ExcelWriter() {
        this.fileName = "D:\\users-" + UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0,3) + ".xlsx";
        this.workbook = new SXSSFWorkbook();
        this.sheet = workbook.createSheet("Users");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Phone");
    }

    @Override
    public void write(Chunk<? extends User> users) {
        userList.addAll(users.getItems());
    }

    @AfterStep
    public ExitStatus afterStep(StepExecution stepExecution) {
        log.info("After step executing .... ");
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            AtomicInteger rowNum = new AtomicInteger(1);
            userList.forEach(user -> {
                Row row = sheet.createRow(rowNum.getAndIncrement());
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getName());
                row.createCell(2).setCellValue(user.getEmail());
                row.createCell(3).setCellValue(user.getPhone());
            });
            workbook.write(fileOut);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            return ExitStatus.FAILED;
        }
        return ExitStatus.COMPLETED;
    }
}
