## Database Setup

Create new db(company) with access to user company password company:

1. su - postgres
2. CREATE USER company WITH PASSWORD 'company';
3. CREATE DATABASE company;
4. GRANT ALL PRIVILEGES ON DATABASE "company" to company;

## Mvn command to deploy and start

`mvn clean install tomcat7:run-war`

**Note:** *frontend-maven-plugin* for executing the bower and grunt while
  running the build. No need to install node, npm, bower. The maven will take
  care of every thing.

`mvn clean heroku:deploy-war` will deploy the application  heroku.

Heroku instance of application can be accessed via: [Company App] (http://company-app.herokuapp.com/)

## App Login

**URL:** localhost:8080/company-dashboard

The application uses Spring's custom form based login for authentication. 
I've commented AppSecurityConfig LOC to disable the same. 
Please uncomment them in case you want to try authentication.

For this authentication different UIs need to submit the request to /login
url (Http method: POST). If the information is correct the Spring will respond
with success else it'll return the error message. In such a case different UIs 
can develop different logics/ UI interfaces for success and failure login.

For making a service redundant. I'll apply the deprecated annotation over method
to make developers aware of it and can configure AppSecurityConfig to deny all
requests to the same url.
