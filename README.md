Kerbal-Save-Editor
==================
WebLAF and Java Simple Plugin Framework(JSPF) does not exist in any common maven repository. 
Please follow the commands below in order to install them into your local repository.

**WebLAF**
```
git clone git@github.com:mgarin/weblaf.git
cd weblaf/
mvn -f build/pom.xml clean install
```

**JSPF**
```
hg clone https://code.google.com/p/jspf/
cd jspf/
ant
cd dist/
mvn install:install-file -Dfile=jspf.core-1.0.3.jar -DgroupId=net.xeon -DartifactId=jspf.core -Dversion=1.0.3 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=jspf.core-1.0.3-sources.jar -DgroupId=net.xeon -DartifactId=jspf.core -Dversion=1.0.3 -Dpackaging=jar -Dclassifier=sources -DgeneratePom=true
mvn install:install-file -Dfile=jspf.core-1.0.3-javadoc.jar -DgroupId=net.xeon -DartifactId=jspf.core -Dversion=1.0.3 -Dpackaging=jar -Dclassifier=javadoc -DgeneratePom=true
```

Build and run
```
bash run.sh                 #Tries to start application, if file does not exists: "mvn clean install -Pall"
bash run.sh clean           #Removes all jar files recursively
bash run.sh clean <profile> #Removes all jar files recursively, "mvn clean install -P<profile>", then starts application
bash run.sh <profile>       #"mvn clean install -P<profile>", then starts application
```
