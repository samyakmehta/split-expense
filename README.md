# Tax Service

## Local Setup

##### Pre-requisites

* Maven 3
* Java

##### Steps to get started

1. Create `Personal Access Tokens` on Gitlab
2. Create `settings.xml` file in $HOME/.m2 folder using following snippet.
     ```xml
     <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
        <servers>
           <server>
               <id>gitlab-maven</id>
               <configuration>
                   <httpHeaders>
                       <property>
                           <name>private-token</name>
                           <value>{token}</value>
                       </property>
                   </httpHeaders>
               </configuration>
           </server>
        </servers>
     </settings>
     ```
3. Add Code Style `.editorconfig` from the repository to your IDE settings.
4. Get the libraries needed using command ```mvn install```
5. Start the application using command ```mvn spring-boot:run```