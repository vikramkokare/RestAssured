import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payloads;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCorses()
	{
		JsonPath jPath = Payloads.jsonPathSet(Payloads.coursePrice());
		int count = jPath.getInt("courses.size()");
		int sum=0;
		int purchaseAmount1= jPath.get("dashboard.purchaseAmount");
		System.out.println("Purchase amount is :"+purchaseAmount1);
		//jPath.getInt("dashboard.purchaseAmount")
		for(int i=0;i<count;i++) {
			int price = jPath.get("courses["+i+"].price");
			int copies = jPath.get("courses["+i+"].copies");
			
			int total = price*copies;
			sum=sum+total;
		}
		Assert.assertEquals(purchaseAmount1, sum);
			
		/*
		 * if(purchaseAmount1==sum) { System.out.println("Purchase amount matches");
		 * 
		 * } else { System.out.println("Purchase amount not matching");
		 * 
		 * }
		 */
		}

		
	}

