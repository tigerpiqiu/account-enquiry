Account and Transactions Enquiry
================
Account enquiry app to view account transactions information
˚

### Design consideration

* For a fairly complex service app, Data Transfer Objects should be used to interact with clients. For really simple API such as accounts retrieval in this app, however, using domain model for this purpose is okay, to avoid boiler platte objects conversion code. 
* One assumption is, for account transactions retrieval API, the front-end needs the account number and account name fields provided by the back-end. For a singe-page front-end app using Reat or Angular, this may not be true. In that case, the payload of account transactions retrieval API should be simpler than my implementation, only providing transaction records without such field as account name.