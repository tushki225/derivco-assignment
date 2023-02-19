package test.pages;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import test.config.Config;
import test.utils.RestUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CocktailObject {

	//Get response based on path
	public Response searchCocktailDB(String path) { 
		Response response = RestUtils.getResponsebyPath(path); 
    	return response;    		
    }	
	
	//Validate response fields from response string
	public boolean validateResponseFields(Response response, String fields) { 
		try {
			String[] expectedFields = fields.split(",");
	    	JsonPath  js = RestUtils.getJsonPath(response);
	    	Map<String, String> valuesList = (Map<String, String>) js.getList("ingredients").get(0);
	    	List<String> actualFields= new ArrayList<String>();
	    	for (Map.Entry<String, String> entry : valuesList.entrySet())
				actualFields.add(entry.getKey());
	    	
	    	for (int i = 0; i < expectedFields.length; i++) {
	    		boolean exists = actualFields.contains(expectedFields[i]);
	    		if (!exists)
	    			return false;
			}	
	    	System.out.println("Response fields validated successfully...");
	    	return true;
		}
		catch (Exception e) {
			return false;
		}    	 		
    }	
	
	//Get Ingredients Details
	public Map<String, String> getIngredientDetails(Response response, String ingredientName) { 
		try {
	    	JsonPath  js = RestUtils.getJsonPath(response);
	    	Map<String, String> valuesList = (Map<String, String>) js.getList("ingredients").get(0);	    
			if (valuesList.get("strIngredient").equalsIgnoreCase(ingredientName)) 
				return valuesList;
			return null;
	    	    	
		}
		catch (Exception e) {
			return null;
		}    	 		
    }	
	
	//Get IngredientList
	public Map<String, String> getIngredientsList(Response response) { 
		try {
			JsonPath  js = RestUtils.getJsonPath(response);
			Map<String, String> valuesList = (Map<String, String>) js.getList("ingredients");
			return valuesList;	    	    	
		}
		catch (Exception e) {
			return null;
		}    	 		
    }
	
	//Validate Cocktail Name 
	public boolean validateCocktailName(Response response, String cocktailName) { 
		try {
			
	    	JsonPath  js = RestUtils.getJsonPath(response);
	    	List<Map<String, String>> allDrinks = js.getList("drinks");
	    	if (allDrinks.size()>0) {
	    		for (Iterator<Map<String, String>> iterator = allDrinks.iterator(); iterator.hasNext();) {
					Map<String, String> drinks = (Map<String, String>) iterator.next();
					System.out.println("Drinks Name:" + drinks.get("strDrink"));	
					if(!drinks.get("strDrink").toLowerCase().contains(cocktailName.toLowerCase()))
						return false;
				}
	    		return true;
	    	} 
	    	else
	    		return false;
	    	
		}
		catch (Exception e) {
			System.out.println("No drinks found...");
			return false;
		}		    	 		
    }
	
	//Get Cocktail Drinks list
	public List<String> getCocktailDrinksList(Response response) { 
		try {			
	    	JsonPath  js = RestUtils.getJsonPath(response);
	    	List<Map<String, String>> allDrinks = js.getList("drinks");
	    	if (allDrinks.size()>0) {
	    		List<String> drinksID = new ArrayList<String>();
	    		for (Iterator<Map<String, String>> iterator = allDrinks.iterator(); iterator.hasNext();) {
					Map<String, String> drinks = (Map<String, String>) iterator.next();
					System.out.println("Drinks id:" + drinks.get("idDrink"));	
					drinksID.add(drinks.get("idDrink"));
				}
	    		return drinksID;
	    	} 
	    	else
	    		return null;
	    	
		}
		catch (Exception e) {
			System.out.println("No drinks found...");
			return null;
		}		    	 		
    }
	
	//Validate Json schema
	public void validateSchema(String path, String jsonSchema) { 
		try {
			File schema = new File(Config.getJsonSchemaPath()+jsonSchema);
			RestUtils.jSONSchemaValidation(path, schema);				    	    	
		}
		catch (Exception e) {
			
		}    	 		
    }
	
	//Validate all filtered alcholic drinks have id
	public boolean validateFilteredDrinks(Response response) { 
		try {			
	    	JsonPath  js = RestUtils.getJsonPath(response);
	    	List<Map<String, String>> allDrinks = js.getList("drinks");
	    	if (allDrinks.size()>0) {
	    		for (Iterator<Map<String, String>> iterator = allDrinks.iterator(); iterator.hasNext();) {
					Map<String, String> drinks = (Map<String, String>) iterator.next();	
					if(null == drinks.get("idDrink"))
						return false;
				}
	    		return true;
	    	} 
	    	else
	    		return false;
	    	
		}
		catch (Exception e) {
			System.out.println("No drinks found...");
			return false;
		}		    	 		
    }
}
