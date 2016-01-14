# JavaCodeToUMLCode

Design of the code
--

<problem.asm>
IClassVistor : for getting the Class that visted Method/Field belongs
ClassMethodVisitor : add function to figure out uses
MethodVisitorHelper : help figuring out associations and uses, been used in ClassMethodVisitor.

<TransferToUML.app>
TransferToUMLApp : starting file

<TransferToUML.api & impl>
IClass Class
IField Field
IMethod Method
IRelation Relation

IModel Model  

UMLTransferOutputStream : visit classes, fields and methods and get infomation to parse and convert that to code for GraphViz.


Design improvements
--


Who did what
--
Ruying and Max focused on the design making sure it is extensible for both Milestone 1 and 2.
While, Thais and Ishank focused more on the ASM Parsing, generating UML diagrams, and the UML test for the application.

Instructions
--
The TransferToUMLApp reads the files that are referenced on the static String array named "classes". So just adding the classes to that array is all what is needed.


Milestone 3:

Design Improvements:
	In order to extend our app to support sequence diagrams, we had to add a Sequence class which stores the information of from class, to class, the method being called, its arguments internally. Later this class is referred by SDOutputStream to get this info and parse it to create the file to be used by SDEdit. A method to add the sequences was needed in the Model class to basically add the sequences to a List too.

Who did what:
	Ruying and Max focused on implementing the initial design and adding the internal structure. While Ishank focused on parsing, generating the diagrams, and testing by referring the design and internally created structure.

Instructions:
	To test the sequence diagrams, you will have to type in the class names in the TransferToUMLApp.java for the static field classes. After running the same file, it will generate a .sd file in input_output directory which can now be opened by the SDEdit.jar file (We chose to prefer jar due to all members running different OS).



