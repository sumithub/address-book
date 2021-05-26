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

     Response: 201 Created
     {
      "id": 762698279,
      "name": "finance contacts",
      "contacts": []
    }
    
   2. Add Contact in Address Book
   
     POST http://localhost:8080/api/v1/address-books/{addressBookId}/contacts
     
     ex: POST http://localhost:8080/api/v1/address-books/762698279/contacts

     Payload: 
     {
        "firstName": "John",
        "lastName": "Moore",
        "phoneNumbers": [
           "12343356",
           "566554433"
          ]
      }
      
      Response: 201 Created
    {
      "id": 767781373,
      "firstName": "John",
      "lastName": "Moore",
      "phoneNumbers": [
         "12343356",
         "566554433"
      ]
    }
   
   3. Get Contact list in Address Book
   
    GET http://localhost:8080/api/v1/address-books/762698279

     Response: 200 OK
      {
          "id": 767781373,
          "firstName": "John",
          "lastName": "Moore",
          "phoneNumbers": [
              "12343356",
              "566554433"
          ]
      }
    ]

  4. Update Contact in Address Book

    PUT http://localhost:8080/api/v1/address-books/762698279/contacts

    Payload:
    {
        "id": 767781373,
        "firstName": "Josh",
        "lastName": "Mayor",
        "phoneNumbers": [
           "12343356"
           ]
        }
    Response: 200 OK
    {
        "id": 767781373,
        "firstName": "Josh",
        "lastName": "Mayor",
        "phoneNumbers": [
            "12343356"
        ]
    }
   
  5. Get list of all Address Books

    GET http://localhost:8080/api/v1/address-books
    
  6. Get unique set of Contacts across all Address Books
 
    GET http://localhost:8080/api/v1/address-books/unique-contacts
    
  7. Delete contact in Address Book

    DELETE http://localhost:8080/api/v1/address-book/{addressId}/contact/{contactId}
  
    ex: DELETE http://localhost:8080/api/v1/address-book/1383388594/contact/1964920837
   


