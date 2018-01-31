###Simple Stock Application

###Technologies Used: 

Java 8, Spring & Maven

###Test Data: 
Have used the sample data provided in the problem document for stocks, have provided data for trades by myself, also provided market price of stocks by myself somewhat around the par value

###Architecture is layered: 


For accessing *buisness services* for various operations needs to be done,  Junit test cases have been written for every operation to test. buisness services are located in service/service.impl packages. buisness services are taking help of data layer for data retrieval located in dao packages. model package is containing all the model classes like Stock, Trade, StockType, TradeType. And a custom exception has been added to validate the data of Stock and Trade

###To run: 

We can run in maven fashion --> mvn clean install. or we can directly run test cases class file viz SuperSimpleStockAnalyzerAppTest

