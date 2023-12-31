package kg.rakhim.classes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jdk.jfr.Timespan;

import java.util.Date;


@Entity
@Table(name = "books")
public class Book {
      @Id
      @Column(name = "book_id")
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int book_id;


      @Column(name = "title")
      @NotEmpty(message = "Название книги не может быть пустым")
      @Size(min = 2, max = 200, message = "Не должно превышать диапазон 2 - 200")
      private String title;


      @Column(name = "author")
      @NotEmpty(message = "Имя автора книги не может быть пустым")
      @Size(min = 2, max = 200, message = "Не должно превышать диапазон 2 - 200")
      private String author;

      @Column(name = "year")
      @Min(value = 1900, message = "Принимаем книги начиная с 1900 годах")
      private int year;

      @Column(name = "created_at")
      @Temporal(TemporalType.TIMESTAMP)
      private Date taken_at;

      @Transient
      private Boolean expired;

      @ManyToOne
      @JoinColumn(name = "person_id", referencedColumnName = "person_id")
      private Person person;


      public Book() {
      }

      public Book(int book_id, String title, String author, int year) {
            this.book_id = book_id;
            this.title = title;
            this.author = author;
            this.year = year;
      }
      public int getBook_id() {
            return book_id;
      }

      public void setBook_id(int book_id) {
            this.book_id = book_id;
      }

      public String getTitle() {
            return title;
      }

      public void setTitle(String title) {
            this.title = title;
      }

      public String getAuthor() {
            return author;
      }

      public void setAuthor(String author) {
            this.author = author;
      }

      public int getYear() {
            return year;
      }

      public void setYear(int year) {
            this.year = year;
      }

      public Person getPerson() {
            return person;
      }

      public Boolean getExpired() {
            return expired;
      }

      public void setExpired(Boolean expired) {
            this.expired = expired;
      }

      public void setPerson(Person person) {
            this.person = person;
      }

      public Date getTaken_at() {
            return taken_at;
      }

      public void setTaken_at(Date taken_at) {
            this.taken_at = taken_at;
      }

      @Override
      public String toString() {
            return "Book{" +
                    "created_at=" + taken_at +
                    '}';
      }
}
