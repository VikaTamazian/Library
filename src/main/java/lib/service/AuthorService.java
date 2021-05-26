package lib.service;

import lib.dao.AuthorDao;
import lib.model.Author;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorService {
    AuthorDao authorDao = new AuthorDao();
    Scanner scanner = new Scanner(System.in);
    List<Author> authorList = new ArrayList<>();

    public Author create(Author author) throws SQLException {

        return authorDao.create(author);

    }

    public boolean delete(Integer id) {
        try {
            Author author = new Author();
            //  author.setAuthor_id(id);
            return authorDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Author> readAll() {
        try {
            authorList.addAll(authorDao.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorList;
    }

}
