## Run configuration

Prod Profile :  `-Dspring.profiles.active=prod`

Setting the DB credentials in the **environment variables** sectioin
> `DB_URL=jdbc:postgresql://localhost:5432/conference_app; DB_USER_NAME=postgres; DB_PASSWORD=welcome`

## Run without IDE

> java -jar target/2-conference-bootstrap-0.0.1-SNAPSHOT.jar

#

## Docker Postgres Setup

Create Docker container with Postgres database:

    docker create --name conference-app-postgres-db -e POSTGRES_PASSWORD=welcome -p 5432:5432 postgres

Start container:

    docker container start conference-app-postgres-db

Stop container:

    docker container stop conference-app-postgres-db

**Note: This stores the data inside the container - when you delete the container, the data is
deleted as well.**
And that is the reason we mount the storage for persisting the data, so even if the container is
deleted/killed (not stopped) we can retrieve the data

### Connect to PSQL prompt from docker:

    docker exec -it postgres-demo psql -U postgres

## Application Database Setup

Create the Database:

    psql> create database conference_app;

> **`;`** is important

Set up the Tables:

    psql -d conference_app -f create_tables.sql

Install the Data:

    psql -d conference_app -f insert_data.sql

**Note: The if you are using Docker, the last two steps can be done like so:**

Set up the Tables:

    docker cp create_tables.sql postgres-demo:/create_tables.sql
    docker exec -it postgres-demo psql -d conference_app -f create_tables.sql -U postgres

Install the Data:

    docker cp insert_data.sql postgres-demo:/insert_data.sql
    docker exec -it postgres-demo psql -d conference_app -f insert_data.sql -U postgres

## Connection Info:

    JDBC URL: `jdbc:postgresql://localhost:5432/<bdname>`

    Username: `postgres`

    Password: `welcome`

------------
------------
------------

## Profile

> ```-Dspring.profiles.active=local```

## Dependency

### Postgres DB Setup

* ##### Prerequisite
  * Install docker (docker for desktop will work)
  * Run docker app (so that docker daemon starts)

<br>

[Reference - HackerNoon Article](https://hackernoon.com/dont-install-postgres-docker-pull-postgres-bee20e200198)

* ##### Pull the postgres image
  * > ``docker pull postgres:[tag_you_want]``
* ##### Check the Image
  * > ``docker image ls``

* ##### Create a volume to be mounted to the DB
  * In this set up the volume directory is : `/Users/prashant.meene/docker/volumes/postgres`
    ie, `$HOME/docker/volumes/postgres`
  * You can create your own, just make sure to be consistent, while using that in the command

* ##### Run the Postgres Container
  * > ``docker run --rm --name pg-owcc -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=SecretSauce -e POSTGRES_DB=owcc -d -p 5432:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql/data postgres``
  * make sure to correctly specify the directory according to your setup

* ##### Check if the container is Running
  * Once the container is up, you can check, if the container is running or not
    * > ``docker container ls``

<br>
<br>

* ##### Connect to the postgres container
  * ###### Using ``psql`` Tool
    * Install psql on your system
      *  > ``brew install libpq``
    * execute psql command
      * > psql --host 127.0.0.1 -U postgres -d postgres -p 5432
  * ###### Using Intellij ultimate
    * You can use DataGrip (a JetBrains product) to connect to the DB.
    * You just need to provide the `database_name`, `host`, `username` and `password` in the
      database-wizard to connect
    * You can check in the below Image the connection is successful

    ![Postgress Successful Connection](src/main/resources/static/postgress-properties.png)

<br>
<br>

* ##### Setting up the Schema
  * For this to properly work, you need to set the schema to this postgres instance
  * Head over to the `src/main/java/resource/db.migration` folder of this project and
    locate [V1__create_contact_tables.sql  file](https://bitbucket.org/zetaengg/owcc-ssc/src/master/src/main/resources/db/migration/V1__create_contact_tables.sql)
  * ###### Using docker
    * Copy the file to the docker Container
    * > `docker cp ./V1__create_contact_tables.sql pg-owcc:/V1__create_contact_tables.sql`
    * Execute and build the sql file from docker.
    * > `docker exec -u postgres pg-owcc psql owcc postgres -f /V1__create_contact_tables.sql`
  * ###### Using Intellij ultimate
    * You need to run the schema present in this file
    * Open the file in Intellij, It will give you a run button on that file
    * Here also I have used the Intellij Ide to run the schema

* ##### Stop the DB
  * Once done you can stop the postgres container
  * > ``docker container stop pg-owcc``
  * > ``docker container ls --all``
* ##### Restart the DB
  * And if you wish to use it again you can just do
  * > ``docker container start pg-owcc``

<br>
<br>

* ##### Application Connection Issue?
  * If you are facing connection issues with the DB, you should check the credentials that your
    application is using to connect to the DB
  * And make sure the should match to the one that you have used while seting up the DB locally as a
    Docker container
  * eg. Currently, The datasource related properties in the `application-local.properties` are
  ```## PostgreSQL
  spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/owcc
  spring.datasource.username=postgres
  spring.datasource.password=SecretSauce
  ```
  * And these correcly match to the command arguments that we used while running & setting up the
    postgres db

## Executing jar

With local Profile
> ```java -jar -Dspring.profiles.active=local  target/owcc-ssc.jar```

### Executing tests

> ```mvn test```


Build from local (Until CI job is ready)

* > ```mvn clean package```
* > ```docker build -t 813361731051.dkr.ecr.ap-south-1.amazonaws.com/owcc-ssc:<version>```
* > ```docker push 813361731051.dkr.ecr.ap-south-1.amazonaws.com/owcc-ssc:<version>```

### Project Configuration

![Run Configurations](src/main/resources/static/Run_Debug_Configuration.png)


Pending tasks
--------

* Revisit Dockerfile
  * Try alpine
* CI/CD
  * Jenkinsfile
  * BuildJob which builds helm chart as well
* Logging
* Monitoring
  * Micrometer
  * Enable ServiceMonitor
  * JMX metrics
* Error handling for APIS
* Security
  * Do not use argo's bearer tokens, create new ones with readonly permissions
