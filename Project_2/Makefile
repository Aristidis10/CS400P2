run: compile
	java StoreDriver

compile: Store.java StoreDriver.java RedBlackTree.java
	javac RedBlackTree.java
	javac Store.java
	javac StoreDriver.java

test: TestStore2.java
	javac -cp .:junit5.jar TestStore2.java
	java -jar junit5.jar --class-path . --scan-classpath --details tree
clean:
	$(RM) *.class
