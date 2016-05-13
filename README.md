# WeatherReport
To install and deploy this app:
first pull the lastest from the master branch or by downloading the project as zip file then unzip it.

Requirements:
maven 3.2.3 or above (tested with Maven 3.2.3, should work with 3.3)
Java SE 8 installed (currently using this version: Java(TM) SE Runtime Environment (build 1.8.0_74-b02))
Tomcat 7 or 8 defined as follows in the maven properties:

<server>
	<id>TomcatServer</id>
	<username>**Add you own username**</username>
	<password>**Add you own username**</password>
</server>

This application is installed by running the following command at the root of the application folder (eg: ~/Development/WeatherReport-master):

mvn clean install

It is deployed by running the following command in the same location as above:

mvn tomcat7:deploy

If the app has already been install, please run: 

mvn tomcat7:redeploy

The command above will work for tomcat 7 or tomcat 8.

Assuming the tomcat server is running on localhost:8080 the app will be found at:
http://localhost:8080/WeatherWebApp
Clicking on one of the links will load the weather report for that city.

The list of cite is governed by the comma separated values in the ap.properties file:
src/main/resources/app.properties
currently loaded are London and Honk kong

Feel free to add more cities to the list, however running the following commands will be necessary:

mvn clean install

and 

mvn tomcat7:redeploy

I have developed all of the functionalities necessary and should be catering for all eventualities (bad APi key, no results found for the city as well as many other exceptions).

The testing framework is implemented to test all of the service's functionalities as well as the Enum class, custom exception classes and Utils class. I however ran out of time to properly test the controller, for it to be fully tested mocking pages and request mapping would need to be implemented.

The UI is very rudimentary as I focused on back end reliability and functionality.

I developed the app as a Spring web application as it leverages the MVC design and separation of concern, I did not use a database layer as the implementation is fairly simple. However if a large request number required a DB storage solution or caching could be beneficial.
It is based around a very simple controller having two request mappings and a simple service interface implemented.
The project runs on annotations and should be easy to follow.

The times brought back from supported cites (London, Hong Kong) are in the local timezone. That is highlighted in the UI next to the sunrise and sunset. 

I would like to improve on the model layer, this was a simple implementation of a small API and due to its simplicity I used the basic org.json.JSONObject to retrieve element from the json feed returned.

In a more complicated environment I would create a model layer with POJOs and use Jackson to serialize/deserialize the JSON.




