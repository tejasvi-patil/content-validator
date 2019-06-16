/**
 * @Author patiltejasviv@gmail.com
 * License: MIT
 */
package com.text.analysis.service.impl;

import com.text.analysis.dto.CommentAnalysisResponse;
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
        CommentAnalysisResponse commentAnalysisResponse = new CommentAnalysisResponse();
        if(!ObjectUtils.isEmpty(userComment)) { // Check first the user comment is not empty or null
            strictMatchMode = Boolean.parseBoolean(this.environment.getProperty("textanalysis.strictMatchMode")); // Get strict mode value.
            logger.debug("strictMatchMode on :{}", strictMatchMode);
            Set<String> inValidWordContentType = textAnalysisUtil.filterOffensiveContentCategory(userComment, strictMatchMode); // Analyse the text
            if(!ObjectUtils.isEmpty(inValidWordContentType)) {
                logger.debug("user comment includes some objectinable content of type :{}" , inValidWordContentType.toString());
                commentAnalysisResponse.setContainsObjectionableContent(Boolean.TRUE);
                commentAnalysisResponse.setMessage(TextAnalysisConstants.OBJECTIONAL_CONTENT_FOUND + " :"+inValidWordContentType.toString());
                logger.info("Successfully analysed comment");
                return commentAnalysisResponse;
            }
            else {
                logger.debug("user comment doesnt includes any objectinable content");
                commentAnalysisResponse.setContainsObjectionableContent(Boolean.FALSE);
                commentAnalysisResponse.setMessage(TextAnalysisConstants.NO_OBJECTIONAL_CONTENT);
                logger.info("Successfully analysed comment");
                return commentAnalysisResponse;
            }
        }
        logger.debug("User comment if either null or empty");
        commentAnalysisResponse.setContainsObjectionableContent(Boolean.FALSE);
        commentAnalysisResponse.setMessage(TextAnalysisConstants.NO_CONTENT);
        return commentAnalysisResponse;
    }


}
