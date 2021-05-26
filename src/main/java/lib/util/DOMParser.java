package lib.util;

import lib.model.Author;
import lib.model.Book;
import lib.model.Genre;
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
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class DOMParser {

    public List<Book> createBook() {

        File file = Path.of("src", "main", "resources", "data", "library.xml").toFile();
        List<Book> bookList = new ArrayList<>();

        try {
            DocumentBuilder factory = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = factory.parse(file);

            Node node = document.getDocumentElement();
            NodeList bookNodes = node.getChildNodes();


            for (int i = 0; i < bookNodes.getLength(); i++) {
                Node book = bookNodes.item(i);

                if (book.getNodeType() != Node.TEXT_NODE) {
                    bookList.add(getBook(book));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return bookList;

    }

    private static Book getBook(Node node) {
        Book book = new Book();

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            book.setId(Integer.parseInt(getTagValue("id", element)));
            book.setName(getTagValue("name", element));
            book.setIsbn(getTagValue("isbn", element));

            NodeList parameters = node.getChildNodes();

            for (int i = 0; i < parameters.getLength(); i++) {
                if (parameters.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element parameter = (Element) parameters.item(i);

                    switch (parameter.getNodeName()) {
                        case "author":
                            book.getAuthor().setAuthor_id(Integer.parseInt(getTagValue("id", element)));
                            book.getAuthor().setAuthor_name(getTagValue("author_name", element));
                        case "genre":
                            book.getGenre().setGenre_id(Integer.parseInt(getTagValue("id", element)));
                            book.getGenre().setGenre_name(getTagValue("genre_name", element));
                    }
                }
            }
        }
        return book;

    }

    public List<Author> createAuthor() {
        File file = Path.of("Library", "src", "main", "resources", "data", "library.xml").toFile();
        List<Author> authorList = new ArrayList<>();

        try {
            DocumentBuilder factory = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = factory.parse(file);

            Node node = document.getDocumentElement();
            NodeList authorNodes = node.getChildNodes();

            for (int i = 0; i < authorNodes.getLength(); i++) {
                Node author = authorNodes.item(i);

                if (author.getNodeType() != Node.TEXT_NODE) {
                    authorList.add(getAuthor(author));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return authorList;

    }

    private static Author getAuthor(Node node) {
        Author author = new Author();

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            author.setAuthor_id(Integer.valueOf(getTagValue("id", element)));
            author.setAuthor_name(getTagValue("author_name", element));


        }
        return author;

    }

    public List<Genre> createGenre() {
        File file = Path.of("Library", "src", "main", "resources", "data", "library.xml").toFile();
        List<Genre> genreList = new ArrayList<>();

        try {
            DocumentBuilder factory = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = factory.parse(file);

            Node node = document.getDocumentElement();
            NodeList genreNodes = node.getChildNodes();

            for (int i = 0; i < genreNodes.getLength(); i++) {
                Node genre = genreNodes.item(i);

                if (genre.getNodeType() != Node.TEXT_NODE) {
                    genreList.add(getGenre(genre));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return genreList;

    }

    private Genre getGenre(Node node) {
        Genre genre = new Genre();

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            genre.setGenre_id(Integer.parseInt(getTagValue("id", element)));
            genre.setGenre_name(getTagValue("genre_name", element));

        }
        return genre;

    }


    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}

