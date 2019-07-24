package com.admin.hanfusiadmin.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PopForm {

    private MultipartFile file;

    private int popType;

}
