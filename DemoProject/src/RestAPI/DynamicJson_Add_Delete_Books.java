package RestAPI;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;

public class DynamicJson_Add_Delete_Books {
	@Test(dataProvider = "BooksData")

	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";

		String AddBook_resp = given().

				header("Content-Type", "application/json").body(payload.Addbook(isbn, aisle)).when()
				.post("/Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath js_AddBook_resp = ReUsableMethods.rawToJson(AddBook_resp);

		String Book_id = js_AddBook_resp.get("ID");
		System.out.println("\nAdded Book_id is : " + Book_id + "\n\n");

		// deleteBOok
		String DeleteBook_resp = given().

				header("Content-Type", "application/json").body(payload.Deletebook(Book_id)).when()
				.post("/Library/DeleteBook.php").then().assertThat().statusCode(200).extract().response().asString();

		JsonPath js_DeleteBook_resp = ReUsableMethods.rawToJson(DeleteBook_resp);

		String Book_Deleted_SuccMsg = js_DeleteBook_resp.get("msg");
		System.out.println("\nBook_Deleted_Success Msg is : " + Book_Deleted_SuccMsg + "\n\n");
		System.out.println("\nDeleted Book_id is : " + Book_id + "\n\n");
	}

	@DataProvider(name = "BooksData")

	public Object[][] getData() {
		Object[][] BooksData = new Object[2][2];

		// Enter data to row 0 column 0
		BooksData[0][0] = "Saurav1";
		// Enter data to row 0 column 1
		BooksData[0][1] = "0001";
		// Enter data to row 1 column 0
		BooksData[1][0] = "Saurav2";
		// Enter data to row 1 column 0
		BooksData[1][1] = "0002";
		// return arrayobject to BooksData
		return BooksData;

	}

}
