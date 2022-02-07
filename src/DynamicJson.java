import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payloads;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class DynamicJson {
	
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI ="http://216.10.245.166";
		
		String response = given().log().all().header("content-Type","application/json").body(Payloads.addBook(isbn,aisle))
		.when().post("Library/Addbook.php").then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath jp = Payloads.jsonPathSet(response);
		String id = jp.get("ID");
		String message = jp.get("Msg");
	
		//System.out.println(id);
		Assert.assertEquals(message, "successfully added");
	}
	
	@DataProvider(name="BooksData")
	public Object [] [] getData()
	{
		return new Object [][]  {{"sharda","200"},{"virshu","300"}};
	}
}
