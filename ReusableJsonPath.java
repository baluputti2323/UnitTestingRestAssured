package org.example;

import io.restassured.path.json.JsonPath;

public class ReuseableJsonPath {

    public static JsonPath rawToJson(String responce){
        JsonPath js1 = new JsonPath(responce);
        return js1;
    }
}
