package com.admin.hanfusiadmin.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TopicForm {

    MultipartFile file;

    private String topicName;

    private int dynamicId;

    private int types;

    private int topicNum;
}
