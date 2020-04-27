# Simple-AJAX-Application

A simple AJAX app using Apache-Tomcat, SQL, Hibernate, JQuery, AJAX and JAX-RS.

## Setup

##### What you will need
* Tomcat  v9.0 Server
* SQL

##### Building the project

1. First we need to create the SQL database which our program communicates with.  
(Default password to access the database is admin/admin)  
    * Create the schema for our tables
    `CREATE SCHEMA webapp`
    * Create a table users with the specified fields :
       * id : INT, Primary Key, Not Null, Auto Increment
       * name : Varchar length 20 ( can be modified)
       * surname : Varchar length 20 (can be modified)
       * gender : varchar length 20 (can be modified)
       * birthdate : Date

```
	CREATE TABLE webapp.users (  
	    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,  
	    name VARCHAR(20),  
	    surname VARCHAR(20),  
	    gender VARCHAR(20),  
	    birthdate DATE  
	);
```
   - Create a table home_address with the specified fields
     * id : INT, Primary Key, Not Null, Auto Increment
     * user_id : INT, Not Null and the foreign key that connects home_address table with users
     * homeAddress : varchar length 255 (can be modified)   

```
    CREATE TABLE webapp.home_address (
        id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
        user_id INT NOT NULL,
        homeAddress VARCHAR(255),
        FOREIGN KEY (user_id) REFERENCES webapp.users(id)
    );
```
    

   - Create a table work_address with the specified fields
     * id : INT, Primary Key, Not Null, Auto Increment
     * user_id : INT, Not Null and the foreign key that connects work_address table with users
     * workAddress : varchar length 255 (can be modified)
     

```
    CREATE TABLE webapp.work_address (
        id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
        user_id INT NOT NULL,
        workAddress VARCHAR(255),
        FOREIGN KEY (user_id) REFERENCES webapp.users(id)
    );
```

2. Import project as a Maven project and update.
3. Add project to the installed Tomcat server and run.

##### Running the Application
In the home screen you are welcomed with a title and 2 options
* Register a user
    * Registering requires to complete name, surname, gender and birthdate fields.  
		Home and work addresses are not mandatory.
* Display users
    * List of all registered users from which :
       1. we can search from the search bar for a specific name or surname
       2. click on a user and view his data.
    * Clicking on a user gives us the options to :
       1. Delete the user via a prompt message
       2. Edit the users data
