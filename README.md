# Tranfer data in Android App with Localhost server

### Android
1. Create project Android
2. Add a protobuf dependency to the project
3. Write the Login.proto file, and the compilation will generate the corresponding class under the build path
4. Build

#### Update client
Need replace 'localhost' to exactly IP address if we want to run app in emulator in com/lang/protobufdemo/MainActivity.java:54

### Server
1. Copy the proto class generated on the Android side
2. Add CommonConfig parsing

#### Run server
```
cd protobufserver/
mvn wrapper:wrapper
./mvnw clean install
./mvnw spring-boot:run
````
