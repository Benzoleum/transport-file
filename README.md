POST http://localhost:8080/api/v1/upload
Content-Type: multipart/form-data; boundary=WebAppBoundary

--WebAppBoundary
Content-Disposition: form-data; name="inputFile"; filename="SampleEntryFile.txt"
Content-Type: text/html

< /Users/benzoleum/Desktop/gift_go_test/transport-file/src/main/resources/SampleEntryFile.txt
--WebAppBoundary--

curl -X POST -F "inputFile=@SampleEntryFile.txt" http://localhost:8080/api/v1/upload
