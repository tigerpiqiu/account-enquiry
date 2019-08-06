Account and Transactions Enquiry
================
Account enquiry service to view account transactions information.

### Usage guide
* Make sure Java 8 and Maven 3.6.1 or higher version are installed on your system.
* Check out this repository to your local machine.
* mvn install
* java -jar target/account-enquiry-service-1.0.0.jar
* After spring boot app is started, test accounts and transactions enquiry API using these uris

    http://localhost:8080/account-enquiry-service/100001/accounts

    http://localhost:8080/account-enquiry-service/585309210/transactions

    (account and transactions data is pre-populated for userId 100001 and account number 585309210, 585309209 )

* Or you can use swagger ui by open this url
    http://localhost:8080/account-enquiry-service/swagger-ui.html

    
### Design consideration

* For a complex service app, Data Transfer Objects should be used to interact with clients. For really simple API such as accounts retrieval in this app, however, using domain model for this purpose is okay, to avoid boiler platte objects conversion code. 
* One assumption is, for account transactions retrieval API, the front-end needs the account name fields provided by the back-end. For a singe-page front-end app using React or Angular, this may not be true. In that case, the payload of account transactions retrieval API should be simpler than my implementation, only providing transaction records without such field as account name. That way, we have one less DB query for account record, speeding up the call.