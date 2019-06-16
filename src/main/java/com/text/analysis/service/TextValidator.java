/**
 * @Author patiltejasviv@gmail.com
 * License: MIT
 */
package com.text.analysis.service;

import com.text.analysis.dto.CommentAnalysisResponse;

public interface TextValidator {

    /**
     * This API will validate the user comments/feedback for
     * any objectionable content
     * @param userComment text passed by user as comment.
     * @return Custom response
     */
    CommentAnalysisResponse validateText(String userComment);
}
