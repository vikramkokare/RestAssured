

import java.util.Scanner;

import files.Payloads;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath jPath = Payloads.jsonPathSet(Payloads.coursePrice());
		int count = jPath.getInt("courses.size()");
		
		System.out.println("Course count is :"+count);
		
		int purchaseAmount= jPath.get("dashboard.purchaseAmount");
		System.out.println("Purchase amount is :"+purchaseAmount);
		
		String firstCourseName = jPath.get("courses[0].title");
		System.out.println("First Course name is :"+firstCourseName);
		
		for(int i=0;i<count;i++) {
			String courseTitle = jPath.get("courses["+i+"].title");
			int coursePrice = jPath.getInt("courses["+i+"].price");
			
			System.out.println("Course title is: "+courseTitle);
			System.out.println("Course price is :"+coursePrice);
			System.out.println("    ");
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter course name to get price");
		String courseName = scanner.next();
		scanner.close();
		
		for(int i=0;i<count;i++) {
			String courseTitle = jPath.get("courses["+i+"].title");
			
			if(courseTitle.equalsIgnoreCase(courseName)) {
				int copies = jPath.get("courses["+i+"].copies");
				System.out.println(courseTitle+" sold copies are "+copies);
				break;
			}
		}
		
		
		

	}

}
