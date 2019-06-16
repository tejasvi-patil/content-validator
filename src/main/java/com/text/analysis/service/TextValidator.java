/**
 * @Author patiltejasviv@gmail.com
 * License: MIT
 */
package com.text.analysis.service;

import com.text.analysis.Response.CommentAnalysisResponse;

public interface TextValidator {

    /**
     * This API will validate the user comments/feedback for
     * any objectionable content
     * @param userComment
     * @return
     */
    CommentAnalysisResponse validateText(String userComment);
}
