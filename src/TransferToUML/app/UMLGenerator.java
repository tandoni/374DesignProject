package TransferToUML.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class UMLGenerator {
	String fileName;

	public UMLGenerator(String fileName) {
		this.fileName = fileName;
	}

	public void execute() {
		try {
			String f = new File(".\\test\\UML.txt").getAbsoluteFile().getPath();
			String y = ".\\test\\" + this.fileName + ".png";
			String z = new File(y).getAbsoluteFile().getPath();
			String path = "\"C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot\" -Tpng " + "\"" + f + "\"";
			Process p = Runtime.getRuntime().exec(path);
			InputStream inputStream = p.getInputStream();
			@SuppressWarnings("resource")
			OutputStream outputStream = new FileOutputStream(new File(z));
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		UMLGenerator unifier = new UMLGenerator("milestone1-test");
		unifier.execute();
	}
}
