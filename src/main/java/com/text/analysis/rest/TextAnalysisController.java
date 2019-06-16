/**
 * @Author patiltejasviv@gmail.com
 * License: MIT
 */
package com.text.analysis.rest;

import com.text.analysis.Response.CommentAnalysisResponse;
import com.text.analysis.constants.TextAnalysisConstants;
import com.text.analysis.service.TextValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Rest endpoints for text analysis
 */
@RestController
@RequestMapping(TextAnalysisConstants.BASE_URL)
public class TextAnalysisController {

    @Autowired
    private TextValidator textValidator;

    private final static Logger logger = LoggerFactory.getLogger(TextAnalysisController.class);

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public @ResponseBody CommentAnalysisResponse validateText(@RequestParam("userComment") String userComment) {
        logger.info("validating user comment/feedback- entry");
        CommentAnalysisResponse commentAnalysisResponse = textValidator.validateText(userComment);
        logger.info("validating user comment/feedback- exit");
        return commentAnalysisResponse;
    }
}
