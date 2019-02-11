package ru.udvybor.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Data
@XmlRootElement
public class Document implements Serializable {
    private Integer id;
    private Integer parent;
    private String pageTitle;
    private String introText;
    private String uri;
    private String content;
    private String imagePath;
}


