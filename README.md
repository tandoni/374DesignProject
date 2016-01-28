# 374DesignProject


Milestone 1 and 2

Design of the code
--

<problem.asm>
IClassVistor : for getting the Class that visted Method/Field belongs
ClassMethodVisitor : add function to figure out uses
MethodVisitorHelper : help figuring out associations and uses, been used in ClassMethodVisitor.

<problem.app>
MyMainApp : starting file

<problem.interfaces & problem.impl>
IClass Class
IField Field
IMethod Method
IRelation Relation
IModel Model  

UMLOutputStream : visit classes, fields and methods and get infomation to parse and convert that to code for GraphViz.


Design improvements
--


Who did what
--
Ruying and Max focused on the design making sure it is extensible for both Milestone 1 and 2.
While, Thais and Ishank focused more on the ASM Parsing, generating UML diagrams, and the UML test for the application.

Instructions
--
The MyMainApp reads the files that are referenced on the static String array named "classes". So just adding the classes to that array is all what is needed.


Milestone 3:

Design Improvements:
	In order to extend our app to support sequence diagrams, we had to add a Sequence class which stores the information of from class, to class, the method being called, its arguments internally. Later this class is referred by SDOutputStream to get this info and parse it to create the file to be used by SDEdit. A method to add the sequences was needed in the Model class to basically add the sequences to a List too.

Who did what:
	Ruying and Max focused on implementing the initial design and adding the internal structure. While Ishank focused on parsing, generating the diagrams, and testing by referring the design and internally created structure.

Instructions:
	To test the sequence diagrams, you will have to type in the class names in the MyMainApp.java for the static field classes. After running the same file, it will generate a GraphForSDEdit.sd file in input_output directory which can now be opened by the SDEdit.jar file (We chose to prefer jar due to all members running different OS).
--

Milestone 4:

Design Evolution:
	In order to implement the Singleton class, we check when a new association is added in the MethodVisitorHelper class. When this happens, we check to see if the association is going to and from the same class. If this is the case, then we call addSingleton(String singleton) in Model. Also, we set the class type of that class to "singleton", so when we write the UML we can identify that class as a Singleton class, and do the special things for it we need to do.
	![Alt text](https://github.com/tandoni/374DesignProject/blob/master/docs/UMLOurProj.png "design UML")

Who did what:
	Max focused on all the design of the singleton and fixing some issues from M3 while Ishank focused on parsing along with looking over design and fixing some issues from M3. Ruying focused on writing test cases and making the manual UML diagrams.

Instructions:
	Exactly the same as generating any UML class diagram but instead we type in class and method name.


Milestone 5:
Design Evolution:
	In order to implement pattern recognition, we have a PatternSpotter which is an abstract class to hold our static string names and any special information pertaining to patter spotters. Our PatternSpotterDec extends this class and visits the classes to spot patterns. We then create a separate concrete class for each Pattern stopper. For example, for adapter, we have a class AdapterSpotter which extends PatterSpotterDec. Similarly for Decorate pattern spotter, we have DecoratorSpotter. We also had to add an accept spotter method in our class.java and getters and setters for stored info.

Who did what:
	Max focused on the design implementing it fixing issues from M3 and M4 as well. Ishank focused on parsing and connecting the design to the parser as well as fixing errors from M3 and M4. Ishank also did some code cleanup. Ruying worked on test cases for M4.

Instructions:
	There are comments in MyMainApp.java specifying what to uncomment in order to run that. By uncommenting those and running the program, it generates a .gv file which can be opened by graphviz.



