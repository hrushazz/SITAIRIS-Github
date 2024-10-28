package DOMParser.Commands;

import DOMParser.Entity.Book;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AddBook {

    public static void addBook(Scanner scanner) throws IOException, ParserConfigurationException, SAXException, TransformerException {

        File file = new File("src/main/resources/books.xml");
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            System.out.println("Введите название: ");
            String title = scanner.nextLine();
            System.out.println("Введите автора: ");
            String author = scanner.nextLine();
            System.out.println("Введите жанр: ");
            String genre = scanner.nextLine();
            System.out.println("Введите цену: ");
            double price = 0;
            while (true) {
                try {
                    price = Double.parseDouble(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка! Повторите попытку: ");
                }
            }
            System.out.println("Введите количество: ");
            int ammount = 0;
            while (true) {
                try {
                    ammount = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка! Повторите попытку: ");
                }
            }

            Book book = new Book();
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setPrice(price);
            book.setAmmount(ammount);

            Element element = getBookNode(book, document);
            document.getDocumentElement().appendChild(element);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult sResult = new StreamResult(file);
            transformer.transform(source, sResult);

            System.out.println("Книга успешно добавлена.");
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }

    private static Element getBookNode(Book book, Document document) {
        Element element = document.createElement("book");
        NodeList list = document.getElementsByTagName("book");
        int count = list.getLength();
        element.setAttribute("id", String.valueOf(++count));

        Element title = getPropertyNode("title", document, book.getTitle());
        element.appendChild(title);
        Element author = getPropertyNode("author", document, book.getAuthor());
        element.appendChild(author);
        Element genre = getPropertyNode("genre", document, book.getGenre());
        element.appendChild(genre);
        Element price = getPropertyNode("price", document, String.valueOf(book.getPrice()));
        element.appendChild(price);
        Element ammount = getPropertyNode("ammount", document, String.valueOf(book.getAmmount()));
        element.appendChild(ammount);
        return element;
    }

    private static Element getPropertyNode(String property, Document document, String value) {
        Element element = document.createElement(property);
        element.setTextContent(value);
        return element;
    }

}
