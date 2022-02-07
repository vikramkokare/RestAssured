import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payloads;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String newAddress ="Warje pune";
		
		String response = given().log().all().queryParam("key", "qaclick123").header("content-Type","application/json;charset=UTF-8")
		.body(Payloads.addPlace()).when().post("/maps/api/place/add/json").then().log().all().statusCode(200).body("scope", equalTo("APP")).extract().response().asString();
	
		JsonPath jPath= Payloads.jsonPathSet(response);
		String placeID= jPath.get("place_id");
		
		System.out.println("********************************************");
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		System.out.println("********************************************");
		
		String getReponseString= given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
	
		JsonPath jp1 = Payloads.jsonPathSet(getReponseString);
		String address = jp1.getString("address");
		
		System.out.println(address);
		
		Assert.assertEquals(address, newAddress);
	}

} 
