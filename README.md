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

Items 1-5 you can easily download from maven repository, just google it.

### Running (IntelliJ IDEA)
Make a main class to execute is "Main".

Working directory (directory to execute from) "your/path/to/project/Migration".

Use classpath of "Migration/src" folder. Make sure that the classpath contains all libraries from dependencies.

In "project structure" menu choose modules -> <your_module> -> Paths -> Compile output -> Use module compile output path ->
set "Output path" to /working/directory/out.

### Workflow
After system initialized, all agents are created, you can input command via CL interface.
There is only two command for now:
1) migrate - make agents to migrate in other regions, or stay in.
2) stats - write statistics in console and log files.

## Extension
You can write your own human agents, just extend AbstractHumanAgent, and override two methods "calcRelevance" and "calcMigration".
First method calculates "scores" for each region in system and creates ordered tree by score value.
Second method calculates possibility of current agent to migrate at highest in tree region.  