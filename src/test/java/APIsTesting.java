import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static DataUtils.DataUtil.getPropertyValue;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class APIsTesting {
    @Test
    public void getUsers() throws IOException {
        String path= getPropertyValue("environment","base_url2");
        Response res1= given().baseUri(path)
                .when().get("users");
        String response1= res1.getBody().asString();
        Response res2= get(path+"/users");
        String response2 = res2.getBody().asString();
        Assert.assertNotEquals(response1,response2);
        Response res3= get(path+"/users");
        String response3 = res3.getBody().asString();
        Assert.assertNotEquals(response3,response2);
        Assert.assertNotEquals(response3,response1);
        get(path+"/users")
                .then().assertThat().statusCode(200).assertThat().body("id",hasSize(10)).log().all();
    }

    @Test
    public void getCompanies() throws IOException {
        get(getPropertyValue("environment","base_url2")+"/companies").then().log().ifValidationFails()
                .assertThat().body("size()", equalTo(10)).assertThat().statusCode(200);
    }
    @Test
    public void getTodos() throws IOException {
        get(getPropertyValue("environment","base_url")+"/todos").then().log().all()
                .assertThat().body("id", hasSize(20)).assertThat().statusCode(200);
    }
    @Test
    public void getPosts() throws IOException {
        get(getPropertyValue("environment","base_url")+"/posts")
                .then().log().all().assertThat().body("id", hasSize(10)).assertThat().statusCode(200)
                .assertThat().body("title", everyItem(notNullValue())
                        ,"link", everyItem(notNullValue())
                        ,"body", everyItem(notNullValue()));
    }

    @Test
    public void getContinets() throws IOException {
        get(getPropertyValue("environment","base_url")+"/continents").then().log().all()
                .assertThat().statusCode(200).assertThat().body("name",hasSize(7)
                        ,"name",everyItem(notNullValue())
                        ,"code", everyItem(notNullValue())
                        ,"population",everyItem(notNullValue()),
                        "oceans",everyItem(notNullValue())
                        ,"countries",everyItem(not(empty())),"areaSqKm",everyItem(not(empty())));

    }
}
