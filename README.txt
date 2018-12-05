Server:
	to compile: javac -cp mp3agic.jar; *.java
	to run: java -Djava.security.policy=policy.txt -cp mp3agic.jar;.\ TaggrServer
	(dont forget to run rmi registry)


Client:
	to compile: javac -cp mp3agic.jar; *.java
	to run: java -cp mp3agic.jar;.\ TaggrClient


Code:	
	Provided softcopy or available at https://github.com/MatRabPC/SOFE4790-FinalProject-Distributed-Systems


Testing:
	Run the application, and select a directory with mp3 files (some have been included in the submission
	and may be used for testing -- a back up copy has been included in case of multiple tests). From there,
	select any of the options (sort, rename, tag all).