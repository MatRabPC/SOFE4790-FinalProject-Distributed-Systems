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



TODO: shazam; song look up; queue; nice gui; auto set <- this one is easy, once we get the right GUI up we just use the set code
		also rename files, etc
		
		in client there is a look up i just need to separate xml tags and grab relevant info buftiasdhkjldsm

Holdups: song look up. look up libraries/databases are difficult to access, or im just bad at this. Mostly me bad at this