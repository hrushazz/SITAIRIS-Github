package DOMParser.Commands;

import DOMParser.Entity.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReadXML {
    public static void readBook() {

        File file = new File("src/main/resources/books.xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();
            String rootNode = document.getDocumentElement().getNodeName();
            System.out.println("Корневой элемент: " + rootNode);
            List<Book> books = getBooksData(document);
            for (Book book : books) {
                System.out.println(book);
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    private static List<Book> getBooksData(Document document) {
        NodeList list = document.getElementsByTagName("book");
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                Book book = getBook(element);
                books.add(book);
            }
        }
        return books;
    }

    static Book getBook(Element element) {
        int id = Integer.parseInt(element.getAttribute("id"));
        String title = getBookDetails(element, "title");
        String author = getBookDetails(element, "author");
        String genre = getBookDetails(element, "genre");
        double price = Double.parseDouble(getBookDetails(element, "price"));
        int ammount = Integer.parseInt(getBookDetails(element, "ammount"));
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPrice(price);
        book.setAmmount(ammount);
        return book;
    }

    private static String getBookDetails(Element element, String property) {
        return element.getElementsByTagName(property).item(0).getTextContent();
    }
}
