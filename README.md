# Transport text file to JSON

Receive file over HTTP, parse it, and return a JSON file.

## Description

This application receives a file containing some data over HTTP, validates it (can be switched on and off), parses it, and returns a JSON file with parsed data.

**Please note that the file has to be a text file (.txt extension), text/plain has to be specified in the Content-Type header of the boundary, and the file has to be sent as a multipart/form-data. Please also note that the contents of the file have to follow the format specified in the sample in the instructions, i.e., it has to have a valid UUID, followed by an ID, the name, transport, average speed, and top speed. The last 2 have to be a double. All fields must be delimited by a pipe (|). If the format is not followed, the application will return an error.**

## Getting Started

### Dependencies

* Java 21
* Postman (optional)

### Installing

* The application can be started by running the jar file in the jar folder
* You can build the application locally with Maven using the following command:

`mvn clean install`

### Executing program

* The jar file can be run with the following command:

`java -jar transport-file-0.0.1-SNAPSHOT.jar`

**If you wish to disable file validation, you can set validateFile to false in application.yml**

* A sample request can be made with the following curl command:

`curl -X POST -F "inputFile=@SampleEntryFile.txt" http://localhost:8080/api/v1/upload`

**Please note that the "inputFile" key has to be specified in the request for the application to recognize the file.**

Alternatively, you can use the Postman collection attached in the Postman folder. Be sure to change the file in the body of the request.
