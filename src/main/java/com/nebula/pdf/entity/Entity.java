package com.nebula.pdf.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Entity 
{
    private String body;
    private boolean error;
    private int status;
    private String name;
}
