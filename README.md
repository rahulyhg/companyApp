## Database Setup

Create new db(company) with access to user company password company:

1. su - postgres
2. CREATE USER company WITH PASSWORD 'company';
3. CREATE DATABASE company;
4. GRANT ALL PRIVILEGES ON DATABASE "company" to company;

## Ruby, Gem Setup

Install ruby and gem (Comes preinstalled with Mac)

Run: `gem install sass`

## Mvn command to deploy and start

`mvn clean install tomcat7:run-war -Dspring.profiles.active=test`

**Note:** *frontend-maven-plugin* for executing the bower and grunt while
  running the build. No need to install node, npm, bower. The maven will take
  care of every thing.

## App Login

**URL:** localhost:8080/company-dashboard

The application uses Spring's form based login for authentication. 
I've commented AppSecurityConfig LOC to disable the same. 
Please uncomment them in case you want to try authentication.
For this authentication different UIs need to submit the request to /login
url. If the information is correct the Spring will respond with success else
it'll return the error message. In such a case different UIs can develop
different logics/ UI interfaces for success and failure login.


For making a service redundant. I'll apply the deprecated annotation over method
and can configure AppSecurityConfig to deny all requests to the same url.