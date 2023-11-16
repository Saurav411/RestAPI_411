package RestAPI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class AddUpdateGetPlace {

	public static void main(String[] args) {
// 		validate if Add Place API is workimg as expected 
//    	Add place-> Update Place with New Address -> Get Place to validate if New address is present in response

		// given - all input details
		// when - Submit the API -resource,http method
		// Then - validate the response
//		********************************************************************************************
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String Addresponse = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(payload.AddPlace()).when()
				.post("maps/api/place/add/json").then().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		System.out.println(Addresponse);
		JsonPath add_js = new JsonPath(Addresponse);
		String place_id = add_js.getString("place_id");
		System.out.println("\n\nPlace ID is:  " + place_id);
//		********************************************************************************************
		String newAddress = "Hyderabad India";

		String PutResponse = given().log().all().queryParam("key", "qaclick123")
				.header("Content-Type", "application/json").body(payload.updatedAddress(place_id, newAddress)).when()
				.put("maps/api/place/update/json").then().log().all().extract().response().asString();

		System.out.println(PutResponse);
		JsonPath put_js = new JsonPath(PutResponse);
		String SuccessMsg = put_js.getString("msg");
		System.out.println("\n\nSuccess Msg is:  " + SuccessMsg);

		Assert.assertEquals(SuccessMsg, "Address successfully updated");
//		********************************************************************************************
		String GetResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id).when()
				.get("maps/api/place/get/json").then().log().all().extract().response().asString();

		System.out.println(GetResponse);
		JsonPath get_js = new JsonPath(GetResponse);
		String UpdatedAddress = get_js.getString("address");
		System.out.println("\n\nUpdated Address is:  " + UpdatedAddress);

		Assert.assertEquals(UpdatedAddress, newAddress);
		System.out.println("\n\nAddress is validated in the Response.");

//		********************************************************************************************
	}

}
