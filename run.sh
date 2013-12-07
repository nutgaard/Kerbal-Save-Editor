if [ ! $# -eq 0 ]; then	
	echo "Recompiling parameters: $@"
	if [ "$1" = "clean" ]; then
		echo "Removing all jars from directory"
		find -type f -name '*.jar' | while read f;do 
			echo "	$f"
			rm -f "$f"
		done
		if [ "${*:2}" = "" ]; then
			echo "No build target specified, exiting"
			exit
		fi
		echo "Rebuilding with maven: ${*:2}"
		mvn clean install -P${*:2}
	else
		echo "Rebuilding with maven: $@"
		mvn clean install -P$@
	fi
else
	echo "No recompiling parameters found"
fi

if [ ! -f application-1.0-SNAPSHOT.jar ]; then
        echo "Did not find application.jar, recompiling complete project"
        mvn clean install -Pall
else
        echo "Application jar found. Starting application"
fi
java -jar application-1.0-SNAPSHOT.jar
echo "Application started"
