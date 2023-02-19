# Functional API Testing

The Cocktail DB is a public database of cocktails and drinks from around the world https://www.thecocktaildb.com/. The Cocktail DB has a public API to retrieve data about ingredients and drinks. 

See the documentation here https://www.thecocktaildb.com/api.php.

# Highlights of the framework
1. API test tool - Restassured
2. Language - Java
3. Maven - Deployment and dependency tool
4. TestNg - Test Configuration
5. Eclipse IDE preferred
6. POM (Page Object Model) design pattern followed

# Recommended tools to install
1. Eclispe IDE for Java 2022 (If running via IDE)
2. Java 8 or higher
3. Maven 3.9 or higher
4. Latest TestNg from Eclipse Marketplace

# Functional Test Scenarios covered as a part of requirements
1. Search ingredient by name and validate return fields
2. Validate if ingredient is alcohalic then ABV is not null
3. Validate if ingredient is non alcohalic then ABV is null
4. Validate if ingredient is not available then no drinks found
5. Search cocktail by name which exists
6. Search cocktail by name which doesnt exists
7. Search cocktail by name case insensitive
8. Search cocktail by name and validate API response schema

# Additional Test Scenarios covered as a part of requirements
1. Filter Drinks by type
2. Filter Drinks by category

# Steps to execute	
1. Install Maven 3.9(or above), Git Client(Not mandatory if using Eclipse IDE) and Java 8(or above) on local 
2. Clone the repository on your local machine using git	
	(https://github.com/tushki225/restassured-project.git)
	
3. You can run the project in 2 different ways as mentioned below-

    a) Command Prompt. Navigate to project root folder in command prompt and run 'mvn test'
    
    b) Eclipse. Make sure to have TestNg plugin added to Eclipse. Then, Import as Maven Project from Git repository. Update project by right click pom.xml and click 'Update Project'. Then, right click testsuite.xml (testsuites-->testsuite.xml) and Run As--> TestNg Suite