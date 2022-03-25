# About project

This is reward calculating project. when we purchase products we get some points from the company. 
This project is just a little demonstation of how that work.


# Dependencies

>java 11 or higher (In this project i used java 17)  [Download from here](https://www.oracle.com/java/technologies/downloads/#java17)

>Postgresql (I used version 14.2) [Downlaod from here](https://www.postgresql.org/)

>Snonarqube [Download from here](https://www.sonarqube.org/)


# Usage


## Available Endpoints

**To get available customer**

> GET domain/user

**To create new customer**

> POST domain/user

The above endpoint also require reqest body of format

**Example**

{
  name: "xyz",
  age: 10,
  gender: "gender",
  phone: "123456789"
}

**Get customer by specific name**

> GET domain/user/{name}


**Get total points earned by specific customer**

> GET domain/user/{name}/rewards


**Get reward by specific month**

> GET domain/user/{name}/{month}/{year}/rewards


### Next we have product related endpoints


******Get availabe products******

> GET domain/product


### Next we have purchase related endpoints you can use them to purchase products


**There are few people who already purchased products you can get the list of them by**

> GET domain/purchase


**To purchase product**

> POST domain/purchase/user/{customerName}/product/{productName}


**Get purchase by spcific Customer**

> GET domain/purchase/{customerName}


**To get purchase history of specific user between date range you could use**

> POST domain/purchase/{customerName}

Above endpoint require request body of format

{
  startDate: "2021-03-01",
  endDate: "2022-03-01
}


**The format of date should be yyyy-mm-dd. (same for endDate)**


**To get rewards by specific Customer for specific date range**

> POST domain/purchase/{customerName}/rewards

Above endpoint also require request body of format 

{
  startDate: "2021-03-01",
  endDate: "2022-03-01
}


**The format of date should be yyyy-mm-dd. (same for endDate)**
