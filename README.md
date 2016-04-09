# Migration
to master degree

### Download
Simple.

~~~
git clone git@github.com:Uncrowned/Migration.git
~~~
~~~
cd Migration
~~~
~~~
git checkout develop
~~~

### Dependencies

java version "1.8.*"

To start my program you have to download these libraries:  
1) com.fasterxml.jackson.core:jackson-annotations:2.7.2  
2) com.fasterxml.jackson.core:jackson-core:2.7.0  
3) com.fasterxml.jackson.core:jackson-databind:2.7.0  
4) org.apache.logging.log4j:log4j-api:2.2  
5) org.apache.logging.log4j:log4j-core:2.2  
6) jade-4.4.0 (http://jade.tilab.com/download/jade/)  

Items 1-5 you can easily download from maven repository, just google it.

### Running
Make a main class to execute is "jade.Boot". It is in the jade.jar file. So its should be in your classpath.

Program arguments "-gui Godlike:agents.GodlikeAgent()".

Working directory (directory to execute from) "your/path/to/project/Migration".

Use classpath of "Migration/src" folder. Make shure that the classpath contains all libraries from dependencies.

### Workflow
You can send messages to Godlike agent through a gui interface.  
Find Godlike@*/JADE, click right mouse button on him, use row "Send Message".

At "Communicative..." choose type of message "inform". Content might be one of the next:  
1) "preparation" -> this command make agents to download params from regions, to get it or update. This command should be the first every time.  
2) "start" -> execute migrations, look at log files, there will be information about how it goes.  
3) "show params" -> regions will write to log values of their stats, "came" and "gone".  

Idk what will happen if you execute "start" message first) Please use "preparation" before.
