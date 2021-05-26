# Address Book
Address Book Assignment developed with Java and Spring Boot

# REST API Features
  - Address book will hold name and phone numbers of contact entries

  - Create a REST API which will have endpoints for the following -

  - Users should be able to add new contact entries 
  
  - Users should be able to remove existing contact entries
  
  - Users should be able to print all contacts in an address book
  
  - Users should be able to maintain multiple address books
  
  - Users should be able to print a unique set of all contacts across multiple address books
  
  # How to start up the REST API
   Test
   
    $ mvn test
   
   Build
   
    $ mvn install
   
   Docker Build
   
    $ docker build -t address-book/address-book-docker .
   
   Docker Run
   
    $ docker run -p 8080:8080 address-book/address-book-docker
   
   API Endpoints
   1. Create new Address Book
   
    POST http://localhost:8080/api/v1/address-books
   
     Payload:
     {
        "name": "finance contacts"
     }

     Response:
     {
      "id": 93921896,
      "name": "finance contacts",
      "contacts": []
    }
    
   2. Add Contact in Address Book
   
     POST http://localhost:8080/api/v1/address-books/{addressBookId}/contacts

     Payload:
     {
          "firstName": "first",
          "lastName": "last",
          "phoneNumbers": [
              "12343356",
              "566554433"
          ]
      }
   


