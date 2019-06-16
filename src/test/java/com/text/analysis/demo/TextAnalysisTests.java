package com.text.analysis.demo;

import com.text.analysis.DemoApplication;
import com.text.analysis.constants.TextAnalysisConstants;
import com.text.analysis.dto.CommentAnalysisRequest;
import com.text.analysis.dto.CommentAnalysisResponse;
import com.text.analysis.service.TextValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DemoApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TextAnalysisTests {

	@Autowired
	private TextValidator textValidator;

	@Autowired
	private Environment env;

	private CommentAnalysisResponse expectedReponse;

	@Test
	public void validateText_NotNull_StrictMatchMode_On() throws Exception{
		//Create test Data
		CommentAnalysisRequest commentAnalysisRequest = new CommentAnalysisRequest("tell something about Sex");
		expectedReponse = getExpectedResponse(TextAnalysisConstants.OBJECTIONAL_CONTENT_FOUND);

		//Invoke service
		CommentAnalysisResponse commentAnalysisResponse = textValidator.validateText(commentAnalysisRequest.getUserComment());

		//Assert response
		Assert.assertTrue(commentAnalysisResponse.getContainsObjectionableContent());
		Assert.assertTrue(matchExpectedResponse(expectedReponse, commentAnalysisResponse));
	}

	@Test
	public void validateText_NotNull_StrictMatchMode_On_NegativeScenario() throws Exception{
		//Create test Data
		CommentAnalysisRequest commentAnalysisRequest = new CommentAnalysisRequest("She is Sexy");
		expectedReponse = getExpectedResponse(TextAnalysisConstants.NO_OBJECTIONAL_CONTENT);

		//Invoke service
		CommentAnalysisResponse commentAnalysisResponse = textValidator.validateText(commentAnalysisRequest.getUserComment());

		//Assert response
		Assert.assertFalse(commentAnalysisResponse.getContainsObjectionableContent());
		Assert.assertEquals(expectedReponse.getMessage(),commentAnalysisResponse.getMessage());
	}

	@Test
	public void validateText_NotNull_StrictMatchMode_Off() throws Exception{
		System.setProperty("textanalysis.strictMatchMode", "false");
		//Create test Data
		CommentAnalysisRequest commentAnalysisRequest = new CommentAnalysisRequest("Serving molestat!oncharges");
		expectedReponse = getExpectedResponse(TextAnalysisConstants.OBJECTIONAL_CONTENT_FOUND);

		//Invoke service
		CommentAnalysisResponse commentAnalysisResponse = textValidator.validateText(commentAnalysisRequest.getUserComment());

		//Assert response
		Assert.assertTrue(commentAnalysisResponse.getContainsObjectionableContent());
		Assert.assertTrue(matchExpectedResponse(expectedReponse, commentAnalysisResponse));
	}

	@Test
	public void validateText_NotNull_StrictMatchMode_Off_SpecialCharScenario() throws Exception{

		System.setProperty("textanalysis.strictMatchMode", "false");

		//Create test Data
		CommentAnalysisRequest commentAnalysisRequest = new CommentAnalysisRequest("Serving mole$tationcharges");
		expectedReponse = getExpectedResponse(TextAnalysisConstants.NO_OBJECTIONAL_CONTENT);

		//Invoke service
		CommentAnalysisResponse commentAnalysisResponse = textValidator.validateText(commentAnalysisRequest.getUserComment());

		//Assert response
		Assert.assertFalse(commentAnalysisResponse.getContainsObjectionableContent());
		Assert.assertEquals(expectedReponse.getMessage(),commentAnalysisResponse.getMessage());
	}

	@Test
	public void validateText_Null_StrictMatchMode_Off() throws Exception{

		System.setProperty("textanalysis.strictMatchMode", "false");
		//Create test Data
		CommentAnalysisRequest commentAnalysisRequest = new CommentAnalysisRequest(null);
		expectedReponse = getExpectedResponse(TextAnalysisConstants.NO_CONTENT);

		//Invoke service
		CommentAnalysisResponse commentAnalysisResponse = textValidator.validateText(commentAnalysisRequest.getUserComment());

		//Assert response
		Assert.assertFalse(commentAnalysisResponse.getContainsObjectionableContent());
		Assert.assertEquals(expectedReponse.getMessage(),commentAnalysisResponse.getMessage());
	}

	@Test
	public void validateText_Null_StrictMatchMode_On() throws Exception{

		//Create test Data
		CommentAnalysisRequest commentAnalysisRequest = new CommentAnalysisRequest(null);
		expectedReponse = getExpectedResponse(TextAnalysisConstants.NO_CONTENT);

		//Invoke service
		CommentAnalysisResponse commentAnalysisResponse = textValidator.validateText(commentAnalysisRequest.getUserComment());

		//Assert response
		Assert.assertFalse(commentAnalysisResponse.getContainsObjectionableContent());
		Assert.assertEquals(expectedReponse.getMessage(),commentAnalysisResponse.getMessage());
	}

	private CommentAnalysisResponse getExpectedResponse(String responseMsg) {
		expectedReponse = new CommentAnalysisResponse();
		expectedReponse.setMessage(responseMsg);
		return expectedReponse;
	}

	private boolean matchExpectedResponse(CommentAnalysisResponse expectedReponse, CommentAnalysisResponse actualResponse) {
		return actualResponse.getMessage().contains(expectedReponse.getMessage());
	}


}
