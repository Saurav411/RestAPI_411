package RestAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class sumValidation {
	@Test
	public void SumOfAllCoursePrices()
	{
		JsonPath js = new JsonPath(payload.CoursePrice());
		int PurchaseAmount = js.getInt("dashboard.purchaseAmount");
		int No_of_Courses = js.getInt("courses.size()");
		int SumOfAllCoursePrices =0;
		for(int i=0 ; i<No_of_Courses; i++)
		{
			int CoursePrice= js.getInt("courses["+i+"].price");
			int CourseCopies = js.getInt("courses["+i+"].copies"); 
			SumOfAllCoursePrices = SumOfAllCoursePrices + CoursePrice*CourseCopies;
		}
		 System.out.println("\n\nSum Of All Course Prices in this API Response is: "+SumOfAllCoursePrices);
		Assert.assertEquals(SumOfAllCoursePrices,PurchaseAmount );
		System.out.println("\n\nSum Of All Course Prices in this API Response is same as the Total amount of the Course Purchase ");
	}
}
