package RestAPI;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import resources.dynamic_payloads.payload;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(payload.CoursePrice());
		// 1. Print No of courses returned by API
		int No_of_Courses = js.getInt("courses.size()");
		System.out.println("\n\nTotal No. of Courses in this API Response is: " + No_of_Courses);

		// 2.Print Purchase Amount
		int PurchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("\n\nTotal Purchase Amount in this API Response is: " + PurchaseAmount);

		// 3. Print Title of the first course
		String FirstCourseTitle = js.getString("courses[0].title");
		System.out.println("\n\nFirst Course Title in this API Response is: " + FirstCourseTitle);

		// 4. Print All course titles and their respective Prices
		for (int i = 0; i < No_of_Courses; i++) {
			String CourseTitle = js.getString("courses[" + i + "].title");
			int CoursePrice = js.getInt("courses[" + i + "].price");
			System.out.println("\n\n Course Title is: " + CourseTitle + "  and its Course Price is : " + CoursePrice);
		}

		// 5. Print no of copies sold by RPA Course
		for (int i = 0; i < No_of_Courses; i++) {
			String CourseTitle = js.getString("courses[" + i + "].title");
			if (CourseTitle.equalsIgnoreCase("RPA")) {
				int CourseCopies = js.getInt("courses[" + i + "].copies");
				System.out.println("\n\nCourse Copies  for RPA in this API Response is: " + CourseCopies);
				break;
			}
		}

		// 6. Verify if Sum of all Course prices matches with Purchase Amount

		int SumOfAllCoursePrices = 0;
		for (int i = 0; i < No_of_Courses; i++) {
			int CoursePrice = js.getInt("courses[" + i + "].price");
			int CourseCopies = js.getInt("courses[" + i + "].copies");
			SumOfAllCoursePrices = SumOfAllCoursePrices + CoursePrice * CourseCopies;
		}
		System.out.println("\n\nSum Of All Course Prices in this API Response is: " + SumOfAllCoursePrices);
		Assert.assertEquals(SumOfAllCoursePrices, PurchaseAmount);
		System.out.println(
				"\n\nSum Of All Course Prices in this API Response is same as the Total amount of the Course Purchase ");
	}
}
