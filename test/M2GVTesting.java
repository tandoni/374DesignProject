import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import problem.app.MyMainApp;
import problem.asm.DesignParser;

public class M2GVTesting {

	private DesignParser parser;
	
	@Before
	public void setUp() throws Exception {
		parser = new DesignParser();
	}

	@Test
	public final void testModel() throws IOException {
		String[] classes = { "problem.impl.Model", "problem.interfaces.IModel", "problem.interfaces.IClass",
				"problem.interfaces.IRelation"};
		MyMainApp.classes = classes;
		MyMainApp.main(classes);
		parser.main(classes);
		
		FileReader reader = new FileReader("./input_output/GraphForGraphViz.gv");
		BufferedReader buffer = new BufferedReader(reader);
		
		FileReader reader2 = new FileReader("./input_output/testModel.gv");
		BufferedReader buffer2 = new BufferedReader(reader2);

		String file = "";
		String line = "";
		
		String file2 = "";
		String line2 = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		while ((line2 = buffer2.readLine()) != null) {
			file2 += line2;
		}

		System.out.println(file);
		String gv = "digraph model{rankdir = BT;Model [shape=\"record\",label = \"{Model| - callDepth: int\\"+"l- startingMethod: ISubMethod\\"+"l- classes: IClass\\"+"l- relations: IRelation\\"+"l- classStrings: String\\"+"l- methodStrings: String\\"+"l|+ init() : void\\"+"l+ getMethodStrings() : String\\"+"l+ getClassStrings() : String\\"+"l+ getCallDepth() : int\\"+"l+ setCallDepth(int) : void\\"+"l+ setStartingMethod(ISubMethod) : void\\"+"l+ init(IClass) : IClass\\"+"l+ getRelations() : IRelation\\"+"l+ setRelations(IRelation) : IRelation\\"+"l+ addRelation(IRelation) : void\\"+"l+ getClasses() : IClass\\"+"l+ accept(IModelVisitor) : void\\"+"l+ toString() : String\\"+"l+ addClazz(IClass) : void\\"+"l+ getClazz(String) : IClass\\"+"l+ acceptSequence(IModelVisitor; ISubMethod; int) : void\\"+"l}\"];IModel [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIModel| + getClasses() : IClass\\"+"l+ addClazz(IClass) : void\\"+"l+ getClazz(String) : IClass\\"+"l+ getRelations() : IRelation\\"+"l+ getClassStrings() : String\\"+"l+ getMethodStrings() : String\\"+"l}\"];IClass [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIClass| + getName() : String\\"+"l+ getMethods() : IMethod\\"+"l+ getFields() : IField\\"+"l+ addMethod(IMethod) : void\\"+"l+ addField(IField) : void\\"+"l+ getIsClass() : boolean\\"+"l}\"];IRelation [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIRelation| + setName(String) : void\\"+"l+ getName() : String\\"+"l+ setRelatedClass(String) : void\\"+"l+ getRelatedClass() : String\\"+"l+ setType(RelationType) : void\\"+"l+ getType() : RelationType\\"+"l}\"];//writing relations between classes nowModel -> IModel [arrowhead = \"empty\", style = \"dashed\"];Model -> IClass [arrowhead = \"vee\"];Model -> IRelation [arrowhead = \"vee\"];IModel -> IClass [arrowhead = \"vee\", style = \"dashed\"];}";
		assertEquals(file, file2);

		buffer.close();
		reader.close();
		
		buffer2.close();
		reader2.close();
	}
	
	@Test
	public final void testIModelVisitor() throws IOException {
		String[] classes = { "problem.visitor.IVisitor", "problem.interfaces.IModel", "problem.interfaces.IClass",
				"problem.interfaces.IRelation", "problem.interfaces.IField", "problem.interfaces.IMethod"};
		MyMainApp.classes = classes;
		parser.main(classes);
		FileReader reader = new FileReader("./input_output/GraphForGraphViz.gv");
		BufferedReader buffer = new BufferedReader(reader);

		FileReader reader2 = new FileReader("./input_output/testIModelVisitor.gv");
		BufferedReader buffer2 = new BufferedReader(reader2);

		String file = "";
		String line = "";
		
		String file2 = "";
		String line2 = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		while ((line2 = buffer2.readLine()) != null) {
			file2 += line2;
		}

		System.out.println(file);
		String gv = "digraph model{rankdir = BT;IModelVisitor [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIModelVisitor| + preVisit(IModel) : void\\"+"l+ preVisit(IClass) : void\\"+"l+ preVisit(IMethod) : void\\"+"l+ preVisit(IField) : void\\"+"l+ preVisit(IRelation) : void\\"+"l+ visit(IModel) : void\\"+"l+ visit(IClass) : void\\"+"l+ visit(IMethod) : void\\"+"l+ visit(IField) : void\\"+"l+ visitSuperClasses(IRelation) : void\\"+"l+ visitInterfaces(IRelation) : void\\"+"l+ postVisit(IModel) : void\\"+"l+ postVisit(IClass) : void\\"+"l+ postVisit(IMethod) : void\\"+"l+ postVisit(IField) : void\\"+"l+ postVisit(IRelation) : void\\"+"l+ visitRelations(IModel) : void\\"+"l+ intermediateVisit(IClass) : void\\"+"l}\"];IModel [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIModel| + getClasses() : IClass\\"+"l+ addClazz(IClass) : void\\"+"l+ getClazz(String) : IClass\\"+"l+ getRelations() : IRelation\\"+"l+ getClassStrings() : String\\"+"l+ getMethodStrings() : String\\"+"l}\"];IClass [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIClass| + getName() : String\\"+"l+ getMethods() : IMethod\\"+"l+ getFields() : IField\\"+"l+ addMethod(IMethod) : void\\"+"l+ addField(IField) : void\\"+"l+ getIsClass() : boolean\\"+"l}\"];IRelation [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIRelation| + setName(String) : void\\"+"l+ getName() : String\\"+"l+ setRelatedClass(String) : void\\"+"l+ getRelatedClass() : String\\"+"l+ setType(RelationType) : void\\"+"l+ getType() : RelationType\\"+"l}\"];IField [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIField| + getType() : String\\"+"l+ getAccess() : String\\"+"l+ getName() : String\\"+"l}\"];IMethod [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIMethod| + getAccess() : String\\"+"l+ getName() : String\\"+"l+ getArgs() : String\\"+"l+ getReturnType() : String\\"+"l+ getSignature() : String\\"+"l+ getClazz() : IClass\\"+"l+ getSubMethods() : ISubMethod\\"+"l}\"];//writing relations between classes nowIModelVisitor -> IModel [arrowhead = \"vee\", style = \"dashed\"];IModelVisitor -> IClass [arrowhead = \"vee\", style = \"dashed\"];IModelVisitor -> IMethod [arrowhead = \"vee\", style = \"dashed\"];IModelVisitor -> IField [arrowhead = \"vee\", style = \"dashed\"];IModelVisitor -> IRelation [arrowhead = \"vee\", style = \"dashed\"];IModel -> IClass [arrowhead = \"vee\", style = \"dashed\"];IClass -> IMethod [arrowhead = \"vee\", style = \"dashed\"];IClass -> IField [arrowhead = \"vee\", style = \"dashed\"];}";
		assertEquals(file, file2);

		buffer.close();
		reader.close();
		
		buffer2.close();
		reader2.close();
	}
	
	@Test
	public final void testClassDeclarationVisitor() throws IOException {
		String[] classes = { "problem.asm.ClassDeclarationVisitor", "problem.asm.IClassVisitor", "problem.impl.Model", "problem.impl.Class", "problem.impl.Relation"};
		MyMainApp.classes = classes;
		parser.main(classes);
		FileReader reader = new FileReader("./input_output/GraphForGraphViz.gv");
		BufferedReader buffer = new BufferedReader(reader);
		
		FileReader reader2 = new FileReader("./input_output/testClassDeclarationVisitor.gv");
		BufferedReader buffer2 = new BufferedReader(reader2);

		String file = "";
		String line = "";
		
		String file2 = "";
		String line2 = "";

		while ((line = buffer.readLine()) != null) {
			file += line;
		}
		
		while ((line2 = buffer2.readLine()) != null) {
			file2 += line2;
		}
		
		System.out.println(file);

		String gv = "digraph model{rankdir = BT;ClassDeclarationVisitor [shape=\"record\",label = \"{ClassDeclarationVisitor| - model: Model\\"+"l- clazz: IClass\\"+"l|+ init(int; Model) : void\\"+"l+ visit(int; int; String; String; String; String[]) : void\\"+"l+ getClazz() : IClass\\"+"l+ splitOnSlash(String) : String\\"+"l}\"];IClazzGetter [shape=\"record\",label = \"{\\"+"<\\"+"<interface\\"+">\\"+">\\nIClazzGetter| + getClazz() : IClass\\"+"l}\"];Model [shape=\"record\",label = \"{Model| - callDepth: int\\"+"l- startingMethod: ISubMethod\\"+"l- classes: IClass\\"+"l- relations: IRelation\\"+"l- classStrings: String\\"+"l- methodStrings: String\\"+"l|+ init() : void\\"+"l+ getMethodStrings() : String\\"+"l+ getClassStrings() : String\\"+"l+ getCallDepth() : int\\"+"l+ setCallDepth(int) : void\\"+"l+ setStartingMethod(ISubMethod) : void\\"+"l+ init(IClass) : IClass\\"+"l+ getRelations() : IRelation\\"+"l+ setRelations(IRelation) : IRelation\\"+"l+ addRelation(IRelation) : void\\"+"l+ getClasses() : IClass\\"+"l+ accept(IModelVisitor) : void\\"+"l+ toString() : String\\"+"l+ addClazz(IClass) : void\\"+"l+ getClazz(String) : IClass\\"+"l+ acceptSequence(IModelVisitor; ISubMethod; int) : void\\"+"l}\"];Class [shape=\"record\",label = \"{Class| - name: String\\"+"l- methods: IMethod\\"+"l- fields: IField\\"+"l- isClass: boolean\\"+"l|+ init(String; boolean) : void\\"+"l+ init() : void\\"+"l+ getName() : String\\"+"l+ getMethods() : IMethod\\"+"l+ getFields() : IField\\"+"l+ addMethod(IMethod) : void\\"+"l+ addField(IField) : void\\"+"l+ accept(IModelVisitor) : void\\"+"l+ getIsClass() : boolean\\"+"l+ acceptSequence(IModelVisitor; ISubMethod; int) : void\\"+"l}\"];Relation [shape=\"record\",label = \"{Relation| - name: String\\"+"l- relatedClass: String\\"+"l- type: RelationType\\"+"l|+ init() : void\\"+"l+ init(String; String; RelationType) : void\\"+"l+ getName() : String\\"+"l+ setName(String) : void\\"+"l+ getRelatedClass() : String\\"+"l+ setRelatedClass(String) : void\\"+"l+ getType() : RelationType\\"+"l+ setType(RelationType) : void\\"+"l+ accept(IModelVisitor) : void\\"+"l+ hashCode() : int\\"+"l+ equals(Object) : boolean\\"+"l+ acceptSequence(IModelVisitor; ISubMethod; int) : void\\"+"l}\"];RelationType [shape=\"record\",label = \"{RelationType| + superclass: RelationType\\"+"l+ interfaces: RelationType\\"+"l+ uses: RelationType\\"+"l+ association: RelationType\\"+"l- ENUM$VALUES: RelationType[]\\"+"l|~ clinit() : void\\"+"l- init(String; int) : void\\"+"l+ values() : RelationType[]\\"+"l+ valueOf(String) : RelationType\\"+"l}\"];//writing relations between classes nowClassDeclarationVisitor -> IClazzGetter [arrowhead = \"empty\", style = \"dashed\"];ClassDeclarationVisitor -> Model [arrowhead = \"vee\"];ClassDeclarationVisitor -> Class [arrowhead = \"vee\", style = \"dashed\"];ClassDeclarationVisitor -> Relation [arrowhead = \"vee\", style = \"dashed\"];Relation -> RelationType [arrowhead = \"vee\"];}";
		assertEquals(file, file2);

		buffer.close();
		reader.close();
		
		buffer2.close();
		reader2.close();
	}
	
}
