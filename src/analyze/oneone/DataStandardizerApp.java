package analyze.oneone;

import java.util.HashMap;
import java.util.Map;

public class DataStandardizerApp {

	public static void main(String[] args) {
		// Setup the parser for the companies that we are supporting
		Map<String, ILineParser> companyToParserMap = new HashMap<String, ILineParser>();

		companyToParserMap.put("google", new GoogleLineParser());
		companyToParserMap.put("microsoft", new MicrosoftLineParser());
		companyToParserMap.put("amazon", new AmazonLineParser());

		DataStandardizer unifier = new DataStandardizer(companyToParserMap);
		unifier.execute();
	}
}
