package com.admin.hanfusiadmin.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EditFeilei {
    private MultipartFile file;
    private String title;
    private int classifyId;
}
