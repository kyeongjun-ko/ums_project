package com.bccns.umsserviceweb.api.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SimpleVO {

    public String id;
    public String title;
    public String content;
    public String getId() {
        return id;
    }
    @Size(max = 10)
    @NotNull
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    
    @Size(max = 50)
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    @Size(max = 100)
    public void setContent(String content) {
        this.content = content;
    }


}
