import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Transaction {

    Properties props = new Properties();
    FileInputStream file = new FileInputStream("./src/test/resources/config.properties");


    public Transaction() throws FileNotFoundException {
    }

    public  void transactionStatementByAccount() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/statement/01686606909")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        int id = jsonObj.get("transactions[0].id");
        Assert.assertEquals(7,id);

    }

    public void searchTransactionListByTrnxId() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/search/TXN224229")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        int id = jsonObj.get("transactions[0].id");
        Assert.assertEquals(14,id);

    }

    public void transactionList() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/list")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());
        JsonPath jsonObj = response.jsonPath();
        int id = jsonObj.get("transactions[0].id");
        Assert.assertEquals(1,id);
    }

    public void checkBalance() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .when()
                        .get("/transaction/balance/01522828745")
                        .then()
                        .assertThat().statusCode(200).extract().response();

        System.out.println(response.asString());

    }

    public void checkDeposite() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"from_account\":\"01753851797\",\n" +
                                "    \"to_account\":\"01753851797\",\n" +
                                "    \"amount\":1000\n" +
                                "}")
                        .when()
                        .post("/transaction/deposit")
                        .then()
                        .assertThat().statusCode(208).extract().response();

        System.out.println(response.asString());

    }

    public void sendMoney() throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"from_account\":\"01522828745\",\n" +
                                "    \"to_account\":\"01686606909\",\n" +
                                "    \"amount\":200\n" +
                                "}")
                        .when()
                        .post("/transaction/sendmoney")
                        .then()
                        .assertThat().statusCode(201).extract().response();

        System.out.println(response.asString());

    }

    public void withdraw () throws IOException {

        props.load(file);
        RestAssured.baseURI = props.getProperty("baseUrl");
        Response response =
                given()
                        .contentType("application/json")
                        .header("Authorization", props.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", props.getProperty("secretAuthKey"))
                        .body("{\n" +
                                "    \"from_account\":\"01522828745\",\n" +
                                "    \"to_account\":\"01686606909\",\n" +
                                "    \"amount\":200\n" +
                                "}")
                        .when()
                        .post("/transaction/withdraw")
                        .then()
                        .assertThat().statusCode(208).extract().response();

        System.out.println(response.asString());
    }
}
