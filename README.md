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
mvn clean install && java -jar Application/target/application-1.0-SNAPSHOT.jar
```
