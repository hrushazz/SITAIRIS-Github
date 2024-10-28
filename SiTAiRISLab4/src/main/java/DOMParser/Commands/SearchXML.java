package DOMParser.Commands;

import DOMParser.Entity.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SearchXML {

    public static void searchBook(Scanner scanner) throws ParserConfigurationException, SAXException, IOException {

        File file = new File("src/main/resources/books.xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            System.out.println("Введите название книги: ");
            String title = scanner.nextLine();
            Book book = getBookByTitle(document, title);
            if (book == null) {
                System.out.println("Книга с названием \"" + title + "\" не найдена.");
            } else {
                System.out.println(book);
            }

        }catch(Exception e){
            System.out.println("Произошла ошибка");
        }
    }

    private static Book getBookByTitle(Document document, String title) {
        NodeList list = document.getElementsByTagName("book");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (title.equalsIgnoreCase(element.getElementsByTagName("title").item(0).getTextContent())) {
                    return ReadXML.getBook(element);
                }
            }
        }
        return null;
    }
}
