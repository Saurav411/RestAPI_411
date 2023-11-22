package RestAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class staticJson_attachmentAsPayload {
	@Test()
//content of the file should be converted to Byte code and then to be converted to String which we can use as String in body.
	public void AddPlace_AttachmentAsPayload() throws IOException {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String DirectoryPath = System.getProperty("user.dir");
		String AddPlace_JsonFilePath = DirectoryPath+"/src/resources/static_payloads/addPlace.json";
		System.out.println("AddPlace_JsonFilePath is : "+AddPlace_JsonFilePath);
		String AddPlace_resp = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(new String(Files.readAllBytes(Paths.get(AddPlace_JsonFilePath)))).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		JsonPath js_AddPlace_resp = ReUsableMethods.rawToJson(AddPlace_resp);
		String place_id = js_AddPlace_resp.get("place_id");
		System.out.println("\nplace_id is : " + place_id + "\n\n");

	}

}
