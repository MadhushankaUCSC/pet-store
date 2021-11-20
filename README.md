# PetStore Application

## Introduction

MicroProfile Starter has generated this MicroProfile application for you.

This project uses Quarkus, the Supersonic Subatomic Java Framework.



## Packaging and running the application

First clone the repo and open with inteliJ IDE and build it using JAVA 13

If you want to build an _??ber-jar_, execute the following command:

    ./gradlew build -Dquarkus.package.type=uber-jar

To run the application:

    java -jar build/petstore-runner.jar

The application can be also packaged using simple:

    ./gradlew build

It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it is not an _??ber-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

To launch the test page, open your browser at the following URL

    http://localhost:8080/index.html

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

    ./gradlew quarkusDev

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Creating a native executable

Mind having GRAALVM_HOME set to your Mandrel or GraalVM installation.

You can create a native executable using:

    ./gradlew build -Dquarkus.package.type=native

## Test using postman
--View Pet Store--

Method - GET
URL http://localhost:8080/pets    (view all pets in the store)
URL http://localhost:8080/pets/1   (view pet which has petId-1 )


--Insert Pet Into Pet Store--

Method - POST
URL http://localhost:8080/pets/add-pet    (add new pet to the pet store)

Body Type - Json

Body -  {"name":"Sukiri",
         "age": 5,
         "type":"Cat"
         }
         
If Insert Success it return Response with pet that recently added to the pet store
If Isert Fail it return Response with Status and Message 
    {"Status":"false", "Message":"New Pet Add Failed!"}
    

--Update Pet in the Pet Store--
 
 Method - PUT
 
 URL http://localhost:8080/pets/update-pet/{petId}   
 
 Body Type - Json

Body -  {"name":"Sukiri",
         "age": 6,
         "type":"Dog"
         }   
         
If Update Success, it returns Response with Status and Message  {"Status":"true", "Message":"Pet Updated Successfully!"} 
If Update Fail it return Response with Status and Message  {"Status":"false", "Message":"Pet Update Failed!"} 

--Delete Pet From Store--

Method - DELETE

URL http://localhost:8080/pets/delete-pet/{petId}

If Delete Success, it returns Response with Status and Message  {"Status":"true", "Message":"Pet Delete Successfully!"} 
If Delete Fail it return Response with Status and Message  {"Status":"false", "Message":"Pet Delete Failed!"} 


--Search Pets In Store--

Method - GET 

URL http://localhost:8080/pets/search-pet



