package tester;

public class XMLConnectTester {
	
//	private String url = "";

	/*public XMLConnectTester(String url) {
		this.url = url;
	}*/
	
	public static boolean test(String url) {
		boolean connected = false;
		java.net.URLConnection connection = null;
		try {
			connection = new java.net.URL(url).openConnection();
			connection.connect();
			String content = connection.getContentType();
			if (content.startsWith("text/xml;")) {
				connected = true;
			}
		} catch (java.io.IOException e) {
			System.out.println(e.getMessage());
		}
		return connected;
	}
}
