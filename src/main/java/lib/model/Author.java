package lib.model;

import java.util.Objects;

public class Author {
    private Integer author_id;
    private String author_name;

    public Author() {

    }

    public Author(Integer id, String name) {
        this.author_id = id;
        this.author_name = name;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(author_id, author.author_id) &&
                Objects.equals(author_name, author.author_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author_id, author_name);
    }

    @Override
    public String toString() {
        return "Author{" +
                "author_id=" + author_id +
                ", author_name='" + author_name + '\'' +
                '}';
    }
}
