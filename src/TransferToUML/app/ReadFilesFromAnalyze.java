package TransferToUML.app;

import java.io.File;

public class ReadFilesFromAnalyze {

	public static void main(String[] args) {
		String filename = "";

		File directory = new File(".//src//analyze");
		
		File[] fList = directory.listFiles();
		String clazzes = "";
		
		for (File file : fList) {
			filename = file.getName();
			if (filename != "") {
				clazzes += "\"analyze." + filename.substring(0, filename.length() - 5) + "\",\n";
			}
		}
		clazzes = clazzes.substring(0, clazzes.length()-2);
		System.out.println(clazzes);
	}

}
