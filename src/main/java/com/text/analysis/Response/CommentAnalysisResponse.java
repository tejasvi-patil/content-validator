/**
 * @Author patiltejasviv@gmail.com
 * License: MIT
 */
package com.text.analysis.Response;

import java.io.Serializable;

/**
 * Response template for validating user input
 * with offensive words
 */
public class CommentAnalysisResponse implements Serializable {

    private static final long serialVersionUUID = -123L;

    private String containsObjectionableContent;

    private String message;

    public String getContainsObjectionableContent() {
        return containsObjectionableContent;
    }

    public void setContainsObjectionableContent(String containsObjectionableContent) {
        this.containsObjectionableContent = containsObjectionableContent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
