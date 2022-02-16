import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import javax.naming.Name;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class CustomerUser {

    Properties props = new Properties();
    FileInputStream file = new FileInputStream("./src/test/resources/config.properties");

    public CustomerUser() throws FileNotFoundException {
    }


    public void userLoginApi() throws ConfigurationException, IOException {


        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");

        Response response =
                given()
                        .contentType("application/json")
                        .body("{\n" +
                                "    \"email\":\"salmansrabon@gmail.com\",\n" +
                                "    \"password\":\"1234\"\n" +
                                "}")
                .when()
                        .post("/user/login")
                .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath resObj = response.jsonPath();
        String token = resObj.get("token");
        Utils.setEnvVariable("token", token);
        System.out.println(token);

    }

    public void userList() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                .when()
                        .get("/user/list")
                .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        int id = jsonObj.get("users[0].id");
        Assert.assertEquals(1, id);
    }

    public void searchUser() throws IOException {
        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                .when()
                        .get("/user/search?id=1")
                .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        String name = jsonObj.get("user.name");
        Assert.assertEquals("Salman Rahman", name);

    }

    public void searchUserByRole() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                .when()
                        .get("/user/search/Agent")
                .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        String name = jsonObj.get("users[0].name");
        Assert.assertEquals("Roberta King MD", name);
        
    }
    public Integer RandomUser;
    public Integer RandomAgent;
    public String name;
    public String email;
    public String nid;

    public void generateUser() throws IOException, ConfigurationException {

        props.load(file);
        RestAssured.baseURI = "https://randomuser.me/api";
        Response response =
                given()
                        .contentType("application/json")
                .when()
                        .get()
                .then()
                        .assertThat().statusCode(200).extract().response();

        JsonPath resObj = response.jsonPath();
        RandomUser = (int) Math.floor(Math.random() * (9999999 - 1000000) + 1);
        String user_phone_number = "0152" + RandomUser;
        RandomAgent = (int) Math.floor(Math.random() * (9999999 - 1000000) + 1);
        String agent_phone_number = "0197" + RandomAgent;

        String first = resObj.get("results[0].name.first");
        String last = resObj.get("results[0].name.last");
        name = first + " " + last;
        email = resObj.get("results[0].email");
        nid = resObj.get("results[0].login.uuid");
        Utils.setEnvVariable("name", name);
        Utils.setEnvVariable("email", email);
        Utils.setEnvVariable("nid", nid);
        Utils.setEnvVariable("phone_number", user_phone_number);
        Utils.setEnvVariable("agent_phone_number", agent_phone_number);
        System.out.println(response.asString());


    }
    public void createUser() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"name\":\"" + props.getProperty("name") + "\",\n" +
                                "    \"email\":\"" + props.getProperty("email") + "\",\n" +
                                "    \"password\":\"1234\",\n" +
                                "    \"phone_number\":\"" + props.getProperty("phone_number") + "\",\n" +
                                "    \"nid\":\"" + props.getProperty("nid") + "\",\n" +
                                "    \"role\":\"Customer\"\n" +
                                "}")
                        .when()
                        .post("/user/create")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        System.out.println(response.asString());

    }

    public void transactionSearch() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/search/TXN224229")
                        .then().assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());

    }


}
