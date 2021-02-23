# Emotional Support Pet Store

## Story

In Codecool we had a task to create a webshop of an arbitrary topic. We choose Emotional Support Pets, because we love animals and think that mental well-being and social support is very important. Please consider that this project was made only for practicing programming techniques. We highly respect animal rights and don't consider pets as products.

## Technical details
We created a dynamic web page in `Java` with `servlets`. Database was not used, only in memory storage with `DAO` design pattern to store data. In order to get data from backend, `Thymeleaf` templating engine was used.

## The site

On the index page you can see the pets of the default category. 

<img src ="https://user-images.githubusercontent.com/57525123/108702669-deea8900-7509-11eb-9f83-719020f24915.png">

You can see title, description and price below the images.

<p align="center">
<img src ="https://user-images.githubusercontent.com/57525123/108702759-faee2a80-7509-11eb-8711-9ecf28f4b136.png">
</p>

You can filter the support pets by category or supplier. 

You can put pets into your shopping cart, change the quantities, see the total price of your selected items.
<p align="center">
  <img src ="https://user-images.githubusercontent.com/57525123/108703025-57514a00-750a-11eb-8cf4-b041b9d93952.png">
</p>

After filling a pseudo payment form you also can view the order of your details.
<img src ="https://user-images.githubusercontent.com/57525123/108703113-78b23600-750a-11eb-8544-dfeb29857c29.png">

## Starting the application
This is a maven project, so pom.xml will install all the dependencies you need in order to run this application.
1. To start the application run
```
mvn install
```
command in terminal in the root directory of the project. 

2. Set up the configuration in your IDE: to comman line field enter the jetty run command along with e-mail account credentials, which is used to send confirmation e-mail. In case your e-mail account applies 2-factor authentication, you need to create an Application Password for running this application.
```
jetty:run -Demail=<email> -Dpassword=<password>
```
3. Start running the application and visit your `http://localhost:8080/` to see the `index` page.
