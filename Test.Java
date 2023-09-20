package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Basics {
    public static void main(String[] args) throws IOException {
        //System.out.println("Hello world!");
        //given-> give all the input details
        //when-> submit the api -> resource & HTTP method
        //then->validate the response
        //Content of the file to string->content of file to byte -> byte to string
        //AddPlace -> Update place with New Address -> Get place to validate new address is present in the responce
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response =given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\n1616943\\OneDrive - Liberty Mutual\\Documents\\Json\\Psot.json")))).when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("server","Apache/2.5.19 (Ubuntu)").extract().response().asString();
        System.out.println(response);
        // log.all gives log of values whereas extract gives in single line format
        JsonPath js = new JsonPath(response);// json parsing
       String PlaceID= js.getString("Place_id");
       System.out.println(PlaceID);

       //Update place
        String AddressNew = "Summer waik, USA";
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+PlaceID+"\",\n" +
                        "\"address\":\""+AddressNew+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n" +
                        "\n" +
                        "\n" +
                        "Sample Response for the Provided Place_Id\n" +
                        "{\n" +
                        " \n" +
                        "\t\"location\":{\n" +
                        " \n" +
                        "    \t\"lat\" : -38.383494,\n" +
                        " \n" +
                        "    \t\"lng\" : 33.427362\n" +
                        " \n" +
                        "\t},\n" +
                        " \n" +
                        "\t\"accuracy\":50,\n" +
                        " \n" +
                        "\t\"name\":\"Frontline house\",\n" +
                        " \n" +
                        "\t\"phone_number\":\"(+91) 983 893 3937\",\n" +
                        " \n" +
                        "\t\"address\" : \"29, side layout, cohen 09\",\n" +
                        " \n" +
                        "\t\"types\": [\"shoe park\",\"shop\"],\n" +
                        " \n" +
                        "\t\"website\" : \"http://google.com\",\n" +
                        " \n" +
                        "\t\"language\" : \"French-IN\"\n" +
                        " \n" +
                        "}\n" +
                        " \n" +
                        " \n" +
                        "\n" +
                        "\n").when().put("/maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));
        //Get place
        // for get method there is no need for input so no need for application.json
        String getPlaceResponce =given().log().all().queryParam("key","qaclick123").queryParam("place_id ","PlaceID")
                .when().get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
        JsonPath js1=ReuseableJsonPath.rawToJson(getPlaceResponce);
        String actualAddress = js1.getString("address");
        System.out.println(actualAddress);
       assertEquals(actualAddress,"address");
    }
}
