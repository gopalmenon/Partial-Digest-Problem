import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class PartialDigestClient {

	public static final String PARTIAL_DIGEST_FILE = "PartialDigest.txt";
	public static final String WHITESPACE_REGEX = "\\s";
	
	public static void main(String[] args) {
			
		PartialDigestClient partialDigestClient = new PartialDigestClient();;
		partialDigestClient.reconstructPositionOfPoints();
		
	}
	
	private void reconstructPositionOfPoints() {
		
		PartialDigest partialDigest = new PartialDigest(getPartialDigest());
		if (partialDigest.isReconstructionSuccessful()) {
			try {
				Collection<Integer> positionOfPoints = partialDigest.getPositionOfPoints();
				System.out.print("{");
				boolean firstTime = true;
				for (Integer position : positionOfPoints) {
					if (firstTime) {
						firstTime = false;
					} else {
						System.out.print(", ");
					}
					System.out.print(position.intValue());
				}
				System.out.print("}");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("Could not reconstruct position of points!!!");
		}
		
	}
	
	private ArrayList<Integer> getPartialDigest() {
		
		ArrayList<Integer> returnValue = new ArrayList<Integer>();
		
		try {
			List<String> fileContents = getFileContents(PARTIAL_DIGEST_FILE);
			for (String fileLine : fileContents) {
				String[] tokens = fileLine.split(WHITESPACE_REGEX);
				for (String token : tokens) {
					if (token.trim().length() > 0) {
						returnValue.add(Integer.valueOf(Integer.parseInt(token.trim())));
					}
				}
			}
		} catch (IOException e) {
			System.err.println("IOException thrown while reading from file " + PARTIAL_DIGEST_FILE + ".");
			System.exit(0);
		} catch (NumberFormatException e) {
			System.err.println("NumberFormatException thrown while trying to parse partial digest file " + PARTIAL_DIGEST_FILE + ".");
			System.exit(0);
		}
		
		return returnValue;
		
	}
	
	private List<String> getFileContents(String fileName) throws IOException {
		
		List<String> returnValue = new ArrayList<String>();
		FileReader inputFile = new FileReader(fileName);
		BufferedReader bufferReader = new BufferedReader(inputFile);
		String line = null;
		while ((line = bufferReader.readLine()) != null)   {
			returnValue.add(line);
        }
        bufferReader.close();
		return returnValue;
		
	}

}
