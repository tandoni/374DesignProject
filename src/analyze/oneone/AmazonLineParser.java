package analyze.oneone;

public class AmazonLineParser implements ILineParser {

	@Override
	public String parse(String line) {
		line = line.replaceAll("ttl", ":");
		return line.replaceAll(", ", "\n");
	}
}
