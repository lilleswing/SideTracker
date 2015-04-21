To Compile

``` bash
mvn clean package
```

this will create a snapshot war file in target/sidetracker-1.0-SNAPSHOT.war

You can deploy this war using any java container

``` bash
cd <tomcat_home>
bash bin/shutdown.sh
cd <tomcat_home>/webapps/ROOT
rm -rf *
cp <git_home/target/sidetracker-1.0-SNAPSHOT.war ./
jar -xvf *.war
cd ../..
bash bin/startup.sh
```
