package ru.udvybor.model;

import lombok.Data;

@Data
public class Document {
    private Integer id;
    private Integer parent;
    private String pageTitle;
    private String introText;
    private String uri;
    private String content;
    private String imagePath;
}


