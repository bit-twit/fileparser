clean:
	 mvn clean

build:
	mvn package

run: clean build
	java -jar target/fileparser-1.0-SNAPSHOT.jar
