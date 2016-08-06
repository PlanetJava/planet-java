# planet-java

#log
* Create a new log file `planet-java.log` in `/var/log/planet-java` directory if it doesn't exist. Don't forget to change ownership add write permission.

        cd /var/log
        sudo mkdir planet-java/
        sudo chown rokon -R planet-java/
        sudo chmod -R 755 planet-java/
        cd planet-java/ 
        sudo touch planet-java.log