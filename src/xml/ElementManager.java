package xml;

import org.w3c.dom.Element;

public class ElementManager {

	public static String getElementContent(Element element, String name) {
		return element.getElementsByTagName(name).item(0).getTextContent();
	}
	
	public static void main(String[] args) {

	}

}
