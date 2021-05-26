package lib.dao;

import lib.model.Author;
import lib.model.Book;
import lib.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BookDao extends AbstractDao<Book> {
    AuthorDao authorDao = new AuthorDao();
    GenreDao genreDao = new GenreDao();

    public Book create(Book book) throws SQLException {

        if (checkUniqueBook(book)) {
            String INSERT = "INSERT INTO my_library.book (name, isbn, author_id, genre_id) VALUES(?,?,?,?)";
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
                statement.setString(1, book.getName());
                statement.setString(2, book.getIsbn());
                statement.setInt(3, book.getAuthor().getAuthor_id());
                statement.setInt(4, book.getGenre().getGenre_id());
                statement.executeUpdate();
                System.out.println("New book added.");
            }
        } else {
            System.out.println("This book has been already added before.");
        }
        return book;
    }

    public Book createFromXML(Book book) throws SQLException {

        if (checkUniqueBook(book)) {
            String INSERT = "INSERT INTO my_library.book (name, isbn, author_id, genre_id) VALUES(?,?,?,?)";
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
                statement.setString(1, book.getName());
                statement.setString(2, book.getIsbn());
                statement.setInt(3, book.getAuthor().getAuthor_id());
                statement.setInt(4, book.getGenre().getGenre_id());
                statement.executeUpdate();
                System.out.println("New book added.");
            }
        } else {
            System.out.println("This book has been already added before.");
        }
        return book;
    }


    public Book readOne(Integer id) throws SQLException {
        String INSERT = "SELECT * FROM my_library.book WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Selected book is: ");
                return fetchBook(resultSet);
            } else {
                System.out.println("Have no record.");
                return null;
            }
        }

    }

    public List<Book> readAll() throws SQLException {
        String INSERT = "SELECT id, name, author_id, genre_id, isbn FROM my_library.book";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
            List<Book> bookList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Book book = fetchBook(resultSet);
                bookList.add(book);
            }
            return bookList;
        }

    }

    public boolean update(Integer id) throws SQLException {
        int count = 0;
        Scanner scanner = new Scanner(System.in);
        String INSERT = "SELECT * FROM my_library.book WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Book book = fetchBook(resultSet);
                String UPDATE = "UPDATE my_library.book SET name=? , author_id =? , genre_id =? WHERE id=?;";
                try (PreparedStatement nextStatement = getConnection().prepareStatement(UPDATE)) {
                    System.out.println("Enter new name: ");
                    String name = scanner.next();
                    nextStatement.setString(1, name);

                    System.out.println("Please, select author: ");
                    List<Author> authorList = authorDao.readAll();
                    System.out.println(authorList);
                    System.out.println("Enter author's id or create new one by printing 0");
                    String author = scanner.next();
                    final int choice = Integer.parseInt(author);//try
                    Optional<Author> found = authorList.stream().filter(aut -> choice == aut.getAuthor_id()).findFirst();
                    if (found.isPresent()) {
                        book.setAuthor(found.get());

                    } else {                                          //make selection for user
                        Author createdAuthor = authorDao.addManually();
                        book.setAuthor(createdAuthor);
                    }
                    nextStatement.setInt(2, book.getAuthor().getAuthor_id());


                    System.out.println("Please, select genre: ");
                    List<Genre> genreList = genreDao.readAll();
                    System.out.println(genreList);
                    System.out.println("Enter genre's id or create new one by printing 0");
                    String genre = scanner.next();
                    final int genreSelection = Integer.parseInt(genre);
                    Optional<Genre> selected = genreList.stream().filter(gen -> genreSelection == gen.getGenre_id()).findFirst();
                    if (selected.isPresent()) {
                        book.setGenre(selected.get());
                    } else {
                        Genre createdGenre = genreDao.addManually();
                        book.setGenre(createdGenre);
                    }
                    nextStatement.setInt(3, book.getGenre().getGenre_id());
                    nextStatement.setInt(4, id);

                    count = nextStatement.executeUpdate();
                    System.out.println("Book updated");

                }

            } else {
                System.out.println("Unknown book id");
            }
        }
        return count > 0;

    }

    public boolean delete(Integer id) throws SQLException {
        String DELETE = "DELETE FROM my_library.book WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
            statement.setInt(1, id);
            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("Book deleted");
                return true;

            } else {
                System.out.println("Operation failed, no such book in library.");
                return false;
            }

        }

    }


    private boolean checkUniqueBook(Book book) throws SQLException {

        String INSERT = "SELECT name FROM my_library.book WHERE name = '" + book.getName() + "'";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT);
        ) {
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next();
        }

    }

    private Book fetchBook(ResultSet resultSet) throws SQLException {

        Book book = new Book();

        book.setId(resultSet.getInt("id"));
        book.setName(resultSet.getString("name"));
        book.setIsbn(resultSet.getString("isbn"));
        List<Author> authorList = authorDao.readAll();
        int author_id = resultSet.getInt("author_id");
        Optional<Author> foundAuthor = authorList.stream().filter(author -> author_id == author.getAuthor_id()).findFirst();
        foundAuthor.ifPresent(book::setAuthor);
        List<Genre> genreList = genreDao.readAll();
        int genre_id = resultSet.getInt("genre_id");
        Optional<Genre> foundGenre = genreList.stream().filter(genre -> genre_id == genre.getGenre_id()).findFirst();
        foundGenre.ifPresent(book::setGenre);

        System.out.println(book);
        return book;

    }
}
