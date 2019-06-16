/**
 * @Author patiltejasviv@gmail.com
 * License: MIT
 */
package com.text.analysis.dto;

import java.io.Serializable;

/**
 * dto template for validating user input
 * with offensive words
 */
public class CommentAnalysisResponse implements Serializable {

    private static final long serialVersionUUID = -123L;

    private boolean containsObjectionableContent;

    private String message;

    public boolean getContainsObjectionableContent() {
        return containsObjectionableContent;
    }

    public void setContainsObjectionableContent(boolean containsObjectionableContent) {
        this.containsObjectionableContent = containsObjectionableContent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
