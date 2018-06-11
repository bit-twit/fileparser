clean:
	 mvn clean

build:
	mvn package

run:
	java -jar target/fileparser-1.0-SNAPSHOT.jar
