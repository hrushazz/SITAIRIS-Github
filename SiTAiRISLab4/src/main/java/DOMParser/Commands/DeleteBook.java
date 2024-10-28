package DOMParser.Commands;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DeleteBook {

    public static void deleteBook(Scanner scanner) throws IOException, ParserConfigurationException, SAXException, TransformerException {

        File file = new File("src/main/resources/books.xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            System.out.println("Введите id книги для удаления: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean result = deleteBookFromXml(document, id);
            if (result) {
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");   //отступы
                DOMSource source = new DOMSource(document);
                StreamResult sResult = new StreamResult(file);
                transformer.transform(source, sResult);
                System.out.println("Книга успешно удалена.");
            } else {
                System.out.println("Книга не найдена.");
            }
        }catch (Exception e){
            System.out.println("Произошла ошибка");
        }
    }

    private static boolean deleteBookFromXml(Document document, int id) {
        NodeList list = document.getElementsByTagName("book");
        boolean result = false;
        int length = list.getLength();
        for (int i = 0; i < length; i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getAttribute("id").equals(String.valueOf(id))) {
                    Node prev = node.getPreviousSibling();
                    if (prev != null && prev.getNodeType() == Node.TEXT_NODE && prev.getNodeValue().trim().length() == 0) {
                        document.getDocumentElement().removeChild(prev);
                    }
                    document.getDocumentElement().removeChild(element);
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
