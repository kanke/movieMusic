#  About this application #
A Java console tool which uses at least 2 different public websites API to retrieve information about a given movie or music album.

##  To run this project ##

> mvn clean install

> mvn package

##  Supported APIs ##

> * -Dapi :arrow_right: {music} or {movie} parameter

> * -Dmusic :arrow_right: -Dmusic = {albumname}

> * -Dmovie :arrow_right: -Dmovie = {moviename}

##  Sample usages ##

###  To get information about a given movie ###

> java -Dapi=movie -Dmovie=starwars -jar target/movie-music-1.0-SNAPSHOT.jar


###  To get information about a given album ###

> java -Dapi=music -Dmusic=believe -jar target/movie-music-1.0-SNAPSHOT.jar


**NOTE:** Please enter movie name and music title as one word without spaces in between. For example; -Dmovie=starwars:heavy_check_mark: -Dmovie=star wars:x: -Dmusic=goodgirlgonebad:heavy_check_mark:  -Dmusic=good girl gone bad:x: 
________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


# Exercise #

##  1. In your opinion, what are the main advantages of using Spring in a webapp context ? ##

> * Spring promotes loose coupling and simplifies the use of dependency injection by providing a standard way of configuration and managing the reference to the created objects which leads to easier testing. 
   
> * Like most MVC frameworks, Spring follows the Convention over configuration software paradigm that attempts to replace lots of (tedious and repetitive) explicit code and/or configuration by simple naming and directory structure conventions.

> * Spring has a well designed MVC framework which supports developments of robust and maintainable web applications in a simplified approach.


## When would you use automatic testing? ##
You mean Automated testing?

In automated testing, test cases are executed with the assistance of tools, scripts, and software. 

> * I will use automated testing when there has been a code refactor or a piece of code has been changed that affects the functionality and logic of the application. In this case, a regression test run in a timely manner will be important to maintain code quality and to ensure the application works as expected.

> * Testing which requires the simulation of thousands of concurrent users requires automation as it will be time consuming and prone to error if done manually.

> * Load testing is a good way to do a quality check on how an application scales and whether it can perform under pressure. Using automated test in this scenario will vastly improve test coverage and save money.


## You just received the root access of your project's freshly installed linux box. Describe the main steps you'll take to run the war on it. ##

> * Open SSH into linux box.
                          
> * Update the apt-get package index:

                    sudo apt-get update
> * Install the Java Development Kit package with apt-get:

            sudo apt-get install default-jdk
> * Create Tomcat user to install tomcat. For security purposes, Tomcat should be run as an unprivileged user (i.e. not root). Install, configure Tomcat and update user permission.
            
              sudo useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat
              
              wget http://mirrors.ukfast.co.uk/sites/ftp.apache.org/tomcat/tomcat-8/v8.5.16/bin/apache-tomcat-8.5.16.tar.gz
               
              
> * Create a manager user to deploy to tomcat. Restart Tomcat and deploy a WAR from the command line with wget:

               <tomcat-users>
                    <user username="admin" password="password" roles="manager-gui,admin-gui manager-script"/>
               </tomcat-users> 

               wget --http-user=admin --http-password=password "http://localhost:8080/manager/text/deploy?war=file:/some/path/SomeWar.war&path=/SomeWar" -O - 
              
              
## Once deployed, your users start whining about slow pages, timeouts, failed uploads, etc. As a developer, how would you handle this ? ##

> *  I will look at the application logs first and write performance tests to diagnose the problem and understand what might be causing the timeouts and failed uploads. There could be many factors that can cause the above such as memory leak or the JVM has ran out of space, sudden increase in traffic etcetera. Performance testing will help measure the quality attributes of the system, such as scalability, reliability and resource usage.

