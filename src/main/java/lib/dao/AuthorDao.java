package lib.dao;

import lib.model.Author;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorDao extends AbstractDao<Author> {
    Scanner scanner = new Scanner(System.in);

    public Author addManually() throws SQLException {
        Author author = new Author();
        System.out.println("Please, enter author: ");
        author.setAuthor_name(scanner.next());
        String SELECT = "SELECT author_name FROM my_library.author WHERE author_name = '" + author.getAuthor_name() + "'";

        try (PreparedStatement statement = getConnection().prepareStatement(SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("author exists");
            } else {
                String INSERT = "INSERT INTO my_library.author (author_name) VALUES(?)";
                PreparedStatement nextStatement = getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS
                );
                nextStatement.setString(1, author.getAuthor_name());
                nextStatement.executeUpdate();
                ResultSet rs = nextStatement.getGeneratedKeys();
                while (rs.next()) {
                    author.setAuthor_id(rs.getInt(1));
                }
                System.out.println("author added");
            }
        }
        return author;

    }

    @Override
    public Author create(Author author) throws SQLException {
        if (checkUniqueAuthor(author)) {
            String INSERT = "INSERT INTO my_library.author (author_name) VALUES(?)";
            try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
                statement.setString(1, author.getAuthor_name());

                statement.executeUpdate();
                System.out.println("New author added.");
            }
        } else {
            System.out.println("This author has been already added before.");
        }
        return author;
    }

    @Override
    public Author readOne(Integer id) throws SQLException {
        String INSERT = "SELECT * FROM my_library.author WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return fetchAuthor(resultSet);
            } else {
                System.out.println("Have no record.");
                return null;
            }
        }

    }

    @Override
    public List<Author> readAll() throws SQLException {
        String INSERT = "SELECT id, author_name FROM my_library.author";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT)) {
            List<Author> authorList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(INSERT);

            while (resultSet.next()) {
                Author author = fetchAuthor(resultSet);
                authorList.add(author);
            }
            return authorList;
        }
    }

    @Override
    public boolean update(Integer author) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Integer author) throws SQLException {
        String DELETE = "DELETE FROM my_library.author WHERE id=?";
        try (PreparedStatement statement = getConnection().prepareStatement(DELETE)) {
            statement.setInt(1, author);
            int row = statement.executeUpdate();
            if (row > 0) {
                System.out.println("Author deleted");
                return true;

            } else {
                System.out.println("Operation failed, no such author in library");
                return false;

            }
        }
    }

    private boolean checkUniqueAuthor(Author author) throws SQLException {

        String INSERT = "SELECT author_name FROM my_library.author WHERE author_name = '" + author.getAuthor_name() + "'";
        try (PreparedStatement statement = getConnection().prepareStatement(INSERT);
        ) {
            ResultSet resultSet = statement.executeQuery();
            return !resultSet.next();
        }

    }


    private Author fetchAuthor(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setAuthor_id(resultSet.getInt("id"));
        author.setAuthor_name(resultSet.getString("author_name"));
        return author;
    }
}
