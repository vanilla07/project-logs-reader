# Introduction

An application to read logs from a `sample.log` file.

* Backend : Java / Dropwizard / Hibernate

* Front : AngularJs / Bootstrap

* Embedded H2 database

# Running The Application

To run the application run the following commands in `project-logs/dropwizard-logs`.

* To package the example run (Maven and Java 8 required).

        mvn package

* To setup the h2 database run.

		java -jar target/dropwizard-logs-1.0.0-SNAPSHOT.jar db migrate config.yml

* To run the server run.

        java -jar target/dropwizard-logs-1.0.0-SNAPSHOT.jar server config.yml

* To run in debug mode.
	
		java -Xdebug -agentlib:jdwp=transport=dt_socket,address=9999,server=y,suspend=n -jar target/dropwizard-logs-1.0.0-SNAPSHOT.jar server config.yml

# Intall The Angular Interface

To test the application run the following commands in `project-logs/angular-logs`.

* To install project dependencies (Node npm required).

		npm install

* To install the project (Bower required).

		bower install

* To build the project (Grunt required).
		
		grunt

# Test The Application

While Java server running, open `project-logs/angular-logs/dist/index.html` in a browser.

# Test The Application without Maven/Bower/Grunt

* Download and unzip `compiled-files.zip`

* In dropwizard-logs folder, run the command (Java 8 required) :
 		
		java -jar target/dropwizard-logs-1.0.0-SNAPSHOT.jar server config.yml

* In `angular-logs/dist` folder, open `index.html` in a web browser.

# Parse Sample file

The `sample.log` file is located in `project-logs/dropwizard-logs` folder.

To parse the file and load the logs into the database.

* Click on "Upload file" button on the homepage

* Or run in console 

		curl -X POST http://localhost:8080/generate


