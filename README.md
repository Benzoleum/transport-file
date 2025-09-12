# This application receives a file over HTTP, parses it and returns a JSON file.

**Please note that the file has to be a text file (.txt extension), text/plain has to be specified in the Content-Type header, and the file has to be sent as a multipart/form-data. Please also note that the contents of the file have to follow the format specified in the sample in the instructions, i.e., it has to have a valid UUID, followed by an ID, the name, transport, average speed, and the top speed. The last 2 have to be a double. All fields must be delimited by a pipe (|). If the format is not followed, the application will return an error.**

The application can be started by running the jar file in the jar folder with the following command:

`java -jar transport-file-0.0.1-SNAPSHOT.jar`

The application is running on port 8080.

**If you wish to disable file validation, you can set validateFile to false in application.yml.**

A sample request can be made with the following curl command:

`curl -X POST -F "inputFile=@SampleEntryFile.txt" http://localhost:8080/api/v1/upload`

**Please note that the "inputFile" key has to be specified in the request for the application to recognize the file.**

Alternatively, you can use the Postman collection attached in the Postman folder.
