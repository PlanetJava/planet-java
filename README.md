# planet-java

#log
* Create a new log file `planet-java.log` in `/var/log/planet-java` directory if it doesn't exist. Don't forget to change ownership add write permission.

        cd /var/log
        sudo mkdir planet-java/
        sudo chown rokon -R planet-java/
        sudo chmod -R 755 planet-java/
        cd planet-java/ 
        sudo touch planet-java.log
        

#Database setup
* create a database named `planet-java`
* change/set the mysql username/password in `application.properties`
* run the application ( from main the method - `PlanetJavaApplication.java`) or `gradle bootRun`. This will create the tables.
* Then run the following query - 

        SET NAMES utf8mb4;
        ALTER DATABASE `planet-java` CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci;
        