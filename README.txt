Server:
	to compile: javac *.java
	to run: java -Djava.security.policy=policy.txt -cp mp3agic.jar;.\ TaggrServer
	(dont forget to run rmi registry)

	
	Fib.java/.class
		- ignore, no significant changes need to be made
	FibServer.java/
		- ignore, no significant changes need to be made
		
	FibImpl
		- fileChooseReturnPath(): opens file chooser, returns path of selected file


Client:
	to compile: javac -cp mp3agic.jar; *.java
	to run: java -cp mp3agic.jar;.\ TaggrClient
	
	Auto-Sort by Album: sorts all songs in folder into separate folders based on Album
	Auto-Sort by Artist: sorts all songs in folder into separate folders based on Artist
	Rename: Auto renames file by track title tag, if it has a track title tag
	Set All Album: set album tag of all songs in directory to entered name (JTextArea)
	Set All Artist: set artist tag of all songs in directory to entered name (JTextArea)
	Set Directory: select working directory
	Open Directory: open windows explorer to working directory
	Upload to Server: Upload all songs to personalised RMI Server (not completed)
	

.
.
.



TODO: add test cases, re-add QQ's interface for lookup
