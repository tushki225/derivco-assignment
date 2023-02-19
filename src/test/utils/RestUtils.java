package test.utils;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import test.config.Config;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import java.io.File;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RestUtils {

    //Returns response by given path
    public static Response getResponsebyPath(String path) {
    	RestAssured.baseURI = Config.baseURI;
        return RestAssured.given().when().get(path).then().extract().response();
    }     
    
    //Validate Json Schema
    public static void jSONSchemaValidation(String path, File schema) {
    	RestAssured.baseURI = Config.baseURI;
        RestAssured.given().get(path).then().body(matchesJsonSchema(schema)); 
    } 
	
    
    //Create Resource with body
    public static Response createResource(String uri, String requestBody) {
    	return RestAssured.given().contentType(Constants.APPLICATION_JSON).body(requestBody).when().post(uri);
    } 
    
    //Updates a Resource with request body
    public static Response updateResource(String uri, String requestBody) {
    	return RestAssured.given().contentType(Constants.APPLICATION_JSON).body(requestBody).when().put(uri);
    }
    
    //Deletes a Resource
    public static Response deleteResource(String uri) {
    	return RestAssured.given().contentType(Constants.APPLICATION_JSON).when().delete(uri);
    }

    //Returns JsonPath object
    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        return new JsonPath(json);
    }

    public static JSONObject getJsonParsed(String body) {
        JSONParser parser = new JSONParser();
        try {
            return (JSONObject)parser.parse(body);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
