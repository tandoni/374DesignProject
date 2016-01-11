# JavaCodeToUMLCode

Design of the code
--

<problem.asm>
IClassVistor : for getting the Class that visted Method/Field belongs
ClassMethodVisitor : add function to figure out uses
MethodVisitorHelper : help figuring out associations and uses, been use in ClassMethodVisitor.

<TransferToUML.app>
TransferToUMLApp : starting file
ReadFilesFromAnalyze : help reading files
UMLGenerator : generate code in txt and create the UML graph by dot.

<TransferToUML.api & impl>
IClass Class
IField Field
IMethod Method
IRelation Relation

IModel Model  

UMLTransferOutputStream : visit classes, fields and methods and get infomation then delicate them into code for UML. Also our ideal is to draw the relation arrows together form model at the end.


Ideal we improved
--



Who did what
--
Ruying did all the programming and for both Milestone 1 and 2.
Thais generated the UML test and diagrams for the application.

Instructions
--
The TransferToUMLApp reads the files that are referenced on the static String array named "classes".

<-- To try and make it easier, the app "ReadFilesFromAnalyze" prints on the console the string array representation of all files within the "analyze" package. This can be used to copy and paste the console result on the TransferToUMLApp "classes" array. To do so, first copy all java files that will be read to the "analyze" package and than run it. -->

After the static String array is set, set the name of the output file on line 26 of the "TransferToUMLApp" file than run it. The process is finished and the file now has the textual representation of the UML diagram.

