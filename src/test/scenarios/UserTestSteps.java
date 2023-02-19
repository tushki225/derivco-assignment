package test.scenarios;

import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.config.Config;
import test.pages.CocktailObject;
import test.utils.Constants;

public class UserTestSteps{
	public Response response = null;
	CocktailObject cocktailObject = new CocktailObject();
	
	@BeforeSuite
	public void getConfig() {
		Config.readPropertiesFile();		
	}	
    
    @Test
    @Parameters({"ingredientName", "fields"})
    public void searchIngredientsByName(String ingredientName, String fields, ITestContext context) {
    	System.out.println(Constants.TESTS_STARTED + context.getName());  
    	
    	String searchPath = "/search.php?i="+ingredientName;
    	response = cocktailObject.searchCocktailDB(searchPath);    	
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!");   
    	Assert.assertTrue(cocktailObject.validateResponseFields(response, fields));
    	
    	System.out.println(Constants.TESTS_ENDED + context.getName());
    }
    
    @Test
    @Parameters({"ingredientName"})
    public void validateABV(String ingredientName, ITestContext context) {
    	System.out.println(Constants.TESTS_STARTED + context.getName());  
    	
    	String searchPath = "/search.php?i="+ingredientName;
    	response = cocktailObject.searchCocktailDB(searchPath);    	
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!");   
    	Map<String, String> details = cocktailObject.getIngredientDetails(response, ingredientName);
    	
    	if(details.get("strAlcohol").equals("Yes"))
    		Assert.assertNotNull(details.get("strABV"));	    			
    	else
    		Assert.assertNull(details.get("strABV"));	
    	
    	System.out.println(Constants.TESTS_ENDED + context.getName());
    }
    
    @Test
    @Parameters({"ingredientName"})
    public void validateABVIfNodrinks(String ingredientName, ITestContext context) {
    	System.out.println(Constants.TESTS_STARTED + context.getName());  
    	
    	String searchPath = "/search.php?i="+ingredientName;
    	response = cocktailObject.searchCocktailDB(searchPath);    	
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!"); 
    	Assert.assertNull(cocktailObject.getIngredientsList(response));	    		
    	
    	System.out.println(Constants.TESTS_ENDED + context.getName());
    }
    
    @Test
    @Parameters({"cocktailName"})
    public void searchCocktailByName(String cocktailName, ITestContext context) {
    	System.out.println(Constants.TESTS_STARTED + context.getName());  
    	
    	String searchPath = "/search.php?s="+cocktailName;
    	response = cocktailObject.searchCocktailDB(searchPath);    	
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!"); 
    	Assert.assertTrue(cocktailObject.validateCocktailName(response, cocktailName));
    	
    	System.out.println(Constants.TESTS_ENDED + context.getName());
    }
    
    @Test
    @Parameters({"cocktailName"})
    public void searchCocktailByNameInvalid(String cocktailName, ITestContext context) {
    	System.out.println(Constants.TESTS_STARTED + context.getName());  
    	
    	String searchPath = "/search.php?s="+cocktailName;
    	response = cocktailObject.searchCocktailDB(searchPath);    	
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!"); 
    	Assert.assertFalse(cocktailObject.validateCocktailName(response, cocktailName));
    	
    	System.out.println(Constants.TESTS_ENDED + context.getName());
    }
    
    @Test
    @Parameters({"cocktailName1", "cocktailName2","cocktailName3"})
    public void searchCocktailNameCaseInsensitive(String cocktailName1, String cocktailName2, String cocktailName3, ITestContext context) {
    	System.out.println(Constants.TESTS_STARTED + context.getName());  
    	
    	String searchPath1 = "/search.php?s="+cocktailName1;
    	String searchPath2 = "/search.php?s="+cocktailName2;
    	String searchPath3 = "/search.php?s="+cocktailName3;
    	
    	response = cocktailObject.searchCocktailDB(searchPath1); 
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!"); 
    	List<String> firstList = cocktailObject.getCocktailDrinksList(response);
    	
    	response = cocktailObject.searchCocktailDB(searchPath2);
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!"); 
    	List<String> secondList = cocktailObject.getCocktailDrinksList(response);
    	
    	response = cocktailObject.searchCocktailDB(searchPath3);
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!"); 
    	List<String> thirdList = cocktailObject.getCocktailDrinksList(response);
    	
    	Assert.assertTrue(firstList.equals(secondList));
    	Assert.assertTrue(secondList.equals(thirdList));
    	Assert.assertTrue(thirdList.equals(firstList));
    	
    	System.out.println(Constants.TESTS_ENDED + context.getName());
    }
    
    @Test
    @Parameters({"cocktailName", "jsonSchema"})
    public void validateSearchCocktailSchema(String cocktailName, String jsonSchema, ITestContext context) {
    	System.out.println(Constants.TESTS_STARTED + context.getName());  
    	
    	String searchPath = "/search.php?s="+cocktailName;
    	response = cocktailObject.searchCocktailDB(searchPath);    	
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!"); 
    	Assert.assertTrue(cocktailObject.validateCocktailName(response, cocktailName));
    	cocktailObject.validateSchema(searchPath, jsonSchema);
    	
    	System.out.println(Constants.TESTS_ENDED + context.getName());
    }
    
    @Test
    @Parameters({"type"})
    public void filterByDrinksType(String type, ITestContext context) {
    	System.out.println(Constants.TESTS_STARTED + context.getName());  
    	
    	String searchPath = "/filter.php?a="+type;
    	response = cocktailObject.searchCocktailDB(searchPath);    	
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!"); 
    	Assert.assertTrue(cocktailObject.validateFilteredDrinks(response));
    	
    	System.out.println(Constants.TESTS_ENDED + context.getName());
    }
    
    @Test
    @Parameters({"category"})
    public void filterByCategory(String category, ITestContext context) {
    	System.out.println(Constants.TESTS_STARTED + context.getName());  
    	
    	String searchPath = "/filter.php?c="+category;
    	response = cocktailObject.searchCocktailDB(searchPath);    	
    	Assert.assertEquals(response.getStatusCode(),200, "Status Code Check Failed!"); 
    	Assert.assertTrue(cocktailObject.validateFilteredDrinks(response));
    	
    	System.out.println(Constants.TESTS_ENDED + context.getName());
    }
}
