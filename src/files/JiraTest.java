package files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI = "http://localhost:8080";
		SessionFilter session = new SessionFilter();
		
		String expectedMessage = "Hi how are you?";
		// Generate session id
		given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{ \"username\": \"kokare.vikram\", \"password\": \"Fusion@123\" }")
		.filter(session).when().post("/rest/auth/1/session").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		//JsonPath jp = new JsonPath(response);
		//String sessionID = jp.get("value");
		
		// Add Comment
		String commentResponse = given().log().all().pathParam("id", "10003").header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(commentResponse);
		String commentId = js.getString("id");
		
		//Add Attachment
		given().log().all().header("X-Atlassian-Token","nocheck").filter(session).pathParam("id", "10003").multiPart("file",new File("sample.txt")).when().post("/rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//Get issue details
	System.out.println("Get issue details");
	String issueResponse = given().log().all().filter(session).pathParam("ID", 10003).queryParam("fields", "comment").when().get("/rest/api/2/issue/{ID}").then().log().all().extract().response().asString();
	
	System.out.println(issueResponse);
	JsonPath js1 = new JsonPath(issueResponse);
	int commentCount = js1.getInt("fields.comment.comments.size()");
	
	for(int i=0;i<commentCount; i++) {
		String commentIdIssue = js1.get("fields.comment.comments["+i+"].id").toString();
		if(commentIdIssue.equalsIgnoreCase(commentId)) {
			String message = js1.get("fields.comment.comments["+i+"].body").toString();
		System.out.println(message);
		Assert.assertEquals(message, expectedMessage);
		}
	}
	
	
	
	}

}
