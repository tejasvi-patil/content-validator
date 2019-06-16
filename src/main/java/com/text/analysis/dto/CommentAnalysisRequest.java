package com.text.analysis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentAnalysisRequest {

    @JsonProperty("userComment")
    private String userComment;

    public CommentAnalysisRequest() {
    }

    public CommentAnalysisRequest(String userComment) {
        this.userComment = userComment;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
