package Test_Classes;

import Common_API_Methods.Common_Utility_Methods;
import java.io.IOException;
import java.time.LocalDateTime;
import org.testng.Assert;
import Common_API_Methods.API_Methods;
import Request_Repository.Post_Req_Repository;
import io.restassured.path.json.JsonPath;

public class Post_Evidence {

	public static void extracter () throws IOException{
		
		for (int i = 0; i <= 5; i++) {	
			
		 int statusCode = API_Methods.ResponseStatusCode(Post_Req_Repository.BaseURI(),
				 Post_Req_Repository.Post_Resource(),Post_Req_Repository.Post_Req_TC1());
		 if (statusCode == 201) {
			 System.out.println(statusCode);
			 String ResponseBody = API_Methods.ResponseBody(Post_Req_Repository.BaseURI(),
					 Post_Req_Repository.Post_Resource(),
					 Post_Req_Repository.Post_Req_TC1());
				 String RequestBody= Post_Req_Repository.Post_Req_TC1();
			 System.out.println("ResponseBody \n" +ResponseBody);
			 
			 String RequestBody1 =Post_Req_Repository.Post_Req_TC1();
		 
		    Common_Utility_Methods.EvidenceCreator("Evidence2", RequestBody1, statusCode, ResponseBody);
		    validator(RequestBody1, ResponseBody);
		    	
    		 break;
    		
		 	 } else {
             System.out.println(i+ "Incorrect status code received :" + statusCode);
             }
		}
		
		
		
	}

	public static void validator(String Request, String Response) throws IOException {

		String RequestBody = Post_Req_Repository.Post_Req_TC1();
		String ResponseBody = API_Methods.ResponseBody(Post_Req_Repository.BaseURI(),
				Post_Req_Repository.Post_Resource(), Post_Req_Repository.Post_Req_TC1());

		JsonPath JspRequest = new JsonPath(RequestBody);
		String Req_name = JspRequest.getString("name");
		String Req_job = JspRequest.getString("job");
		LocalDateTime currentdate = LocalDateTime.now();
		String expecteddate = currentdate.toString().substring(0, 11);

		JsonPath JspResponse = new JsonPath(ResponseBody);
		String Res_name = JspResponse.getString("name");
		String Res_job = JspResponse.getString("job");
		String Res_id = JspResponse.getString("id");
		String Res_createdAt = JspResponse.getString("createdAt");
		Res_createdAt = Res_createdAt.substring(0, 11);

		Assert.assertEquals(Res_name, Req_name);
		Assert.assertEquals(Res_job, Req_job);
		Assert.assertNotNull(Res_id);
		Assert.assertNotEquals(Res_id, 0);
		Assert.assertEquals(Res_createdAt, expecteddate);
	}

}