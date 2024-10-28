package DOMParser;

import DOMParser.Commands.*;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Scanner;

public class CatalogMenu {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1 - добавить");
            System.out.println("2 - удалить");
            System.out.println("3 - просмотреть");
            System.out.println("4 - найти");
            System.out.println("0 - выйти");
            System.out.print("Ваш выбор: ");

            int choice = 0;
            boolean is_correct = true;
            do {
                try {
                    is_correct = true;
                    choice = scanner.nextInt();
                } catch (Exception ex) {
                    System.out.println("Произошла ошибка! Повторите попытку: ");
                    is_correct = false;
                    scanner.next();
                }
            } while (!is_correct);
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    deleteBook(scanner);
                    break;
                case 3:
                    showBooks();
                    break;
                case 4:
                    searchBook(scanner);
                    break;
                case 0:
                    System.out.println("Хорошего дня");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверное значение");
                    break;
            }
        }
    }

    private static void addBook(Scanner scanner) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        AddBook.addBook(scanner);
    }

    private static void deleteBook(Scanner scanner) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DeleteBook.deleteBook(scanner);
    }

    private static void showBooks() throws ParserConfigurationException, IOException, SAXException {
        ReadXML.readBook();
    }

    private static void searchBook(Scanner scanner) throws ParserConfigurationException, IOException, SAXException {
        SearchXML.searchBook(scanner);
    }
}
