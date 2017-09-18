#Stock


Stock Application 

A Rest Application developed by Spring-Boot and Gradle.

Functions
1. Calculate the monthly average of open and close price of a single or multiple ticker/s.
2.Calculate the maximum profit of a ticker within a month/s. 

Usage.

This application can run either from web page or Command Line 
From COMMAND LINE 
 Run the app 
./gradlew build && java -jar build/libs/gs-spring-boot-0.1.0.jar
 
Curl Request
1.Avarage Monthly open and close price 

curl "http://localhost:8080/stock?ticker=GOOGL,COF,MSFT&startDate=20170101&endDate=20170601"

2.With manximum profite option 

curl "http://localhost:8080/stock?ticker=GOOGL,COF,MSFT&startDate=20170101&endDate=20170601&opt=max-daily-profit"


From WebPage
 
1. For Monthy Average close and open price :
Required Parameters
ticker 
startDate
endDate

Eg:
http://localhost:8080/stock?ticker=GOOGL,COF,MSFT&startDate=20170101&endDate=20170601


2.To calculate maximum Profit of each month :
Add an optional parameter  as
opt=max-daily-profit

Eg:
http://localhost:8080/stock?ticker=GOOGL,COF,MSFT&startDate=20170101&endDate=20170601&opt=max-daily-profit



PN. The code is developed in IntelliJ IDE.


-- 





M E R O N
