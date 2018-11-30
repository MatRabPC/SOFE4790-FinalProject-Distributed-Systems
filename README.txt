Server:
	to compile: javac *.java
	to run: java -Djava.security.policy=policy.txt FibServer
	(dont forget to run rmi registry)

	
	Fib.java/.class
		- ignore, no significant changes need to be made
	FibServer.java/
		- ignore, no significant changes need to be made
		
	FibImpl
		- fileChooseReturnPath(): opens file chooser, returns path of selected file


Client:
	to compile: javac -cp mp3agic.jar; *.java
	to run: java -cp mp3agic.jar;.\ FibClient

.
.
.



TODO: Clean UI, migrate computations to server-side, add functionality for empty buttons, add new/useful methods
