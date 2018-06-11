clean:
	 mvn clean

build:
	mvn package

run: clean build
	java -jar target/fileparser-1.0-SNAPSHOT.jar

generate-test-files: 
	mvn surefire:test -Dtest=org.bittwit.fileparser.generator.TestGenerator
