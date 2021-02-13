main: *.java
	javac *.java

clean: *.java
	rm -f *.class
	javac *.java

remove: *.class
	rm -f *.class

jar: *.java *.png *.xml
	javac *.java
	jar cmf manifest.mf ../jar/GravityFlux.jar *.java *.class *.png scores.dat levels.xml help.xml