Tokenizer.java
	The Tokenizer Interface which describes all the methods required to implement the Tokenizer.

Tokenizer1.java
	The class which contains the implementation of the methods described in Tokenizer.java which allows for the Tokenizer to have functionality.

ParseTree.java
	The ParseTree Interface which describes all the methods required to implement the ParseTree.

ParseTree1.java
	The class which contains the implementation of the methods described in ParseTree.java which allows for the ParseTree to have functionality.
		
TokenKind.java
	Contains the Enum TokenKind which lists the different kinds of Tokens and can return their int correspondence.
	
Interpreter.java
	Contains main method. Requires two file names for input program and input variables and the command to 'print' or 'doNotPrint'. Contains methods to Parse, Print, and Execute.
	
Compile Program:
	Type in command prompt >javac -d bin src/edu/c3341/TokenKind.java src/edu/c3341/Tokenizer.java src/edu/c3341/Tokenizer1.java src/edu/c3341/ParseTree.java src/edu/c3341/ParseTree1.java src/edu/c3341/Interpreter.java
	
Run Program:
	Type in command prompt >java edu.c3341.Interpreter [Filename - Program] [Filename - Variables] ["print" or "doNotPrint"]
	***NOTE: Two .txt files are needed for the program to be able to Interprete a Core program.