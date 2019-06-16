/**
 * @Author patiltejasviv@gmail.com
 * License: MIT
 */
package com.text.analysis.service.impl;

import com.text.analysis.Response.CommentAnalysisResponse;
import com.text.analysis.Util.TextAnalysisUtil;
import com.text.analysis.constants.TextAnalysisConstants;
import com.text.analysis.service.TextValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Provides service to prevent customers from posting
 * objectionable content.
 */
@Service
public class TextValidatorImpl implements TextValidator {

    @Autowired
    private Environment environment;

    @Autowired
    private TextAnalysisUtil textAnalysisUtil;

    private boolean strictMatchMode;

    private final static Logger logger = LoggerFactory.getLogger(TextValidatorImpl.class);

    /**
     * This API validates the user comments/feedback for any objectionable
     * contents used
     * @param userComment
     * @return
     */
    @Override
    public CommentAnalysisResponse validateText(String userComment) {
        logger.debug("validateText()-- Validating user comment: {}" , userComment);
        CommentAnalysisResponse commentAnalysisResponse = null;
        if(!ObjectUtils.isEmpty(userComment)) {
            strictMatchMode = Boolean.parseBoolean(this.environment.getProperty("textanalysis.strictMatchMode"));
            logger.debug("strictMatchMode on :{}", strictMatchMode);
            Set<String> inValidWordContentType = textAnalysisUtil.filterOffensiveContentCategory(userComment, strictMatchMode);
            commentAnalysisResponse = new CommentAnalysisResponse();
            if(!ObjectUtils.isEmpty(inValidWordContentType)) {
                logger.debug("user comment includes some objectinable content of type :{}" , inValidWordContentType.toString());
                commentAnalysisResponse.setContainsObjectionableContent(Boolean.TRUE.toString());
                commentAnalysisResponse.setMessage(TextAnalysisConstants.OBJECTIONAL_CONTENT_FOUND + " :"+inValidWordContentType.toString());
            }
            else {
                logger.debug("user comment doesnt includes any objectinable content");
                commentAnalysisResponse.setContainsObjectionableContent(Boolean.FALSE.toString());
                commentAnalysisResponse.setMessage(TextAnalysisConstants.NO_OBJECTIONAL_CONTENT);
            }
        }
        return commentAnalysisResponse;
    }


}
