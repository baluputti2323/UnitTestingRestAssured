package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.example.DefaultPackage.Payload;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@SuppressWarnings("ALL")
public class DynamicJson {
    @Test(dataProvider="BookData")
    public void addBookJson(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        //addplace
        String resp = given().log().all().header("Content-Type", "Application/json")
                .body(Payload.addBook(isbn, aisle)).when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();
        JsonPath js = ReuseableJsonPath.rawToJson(resp);
        String id = js.get("ID");
        System.out.println(id);
    }
        @DataProvider(name = "BookData")
        public Object[][] getData() {
            return new Object[][] {{"asew","6695"},{"acew","6696"},{"asfw","6697"}};

    }
}
