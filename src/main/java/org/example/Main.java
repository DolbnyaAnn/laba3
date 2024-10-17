package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();
        List<Visitor> visitorsData = null;
        try (FileReader reader = new FileReader("books.json")) {
            Type visitorListType = new TypeToken<List<Visitor>>() {}.getType();
            visitorsData = gson.fromJson(reader, visitorListType);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (visitorsData != null) {
// Задание 1
            System.out.println("Задание 1");
            visitorsData.stream()
                    .map(visitor -> visitor.getFirstName() + " " + visitor.getLastName())
                    .forEach(System.out::println);
            System.out.println("Всего посетителей: " + visitorsData.size());

// Задание 2
            System.out.println("\nЗадание 2");
            visitorsData.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .distinct()
                    .sorted(Comparator.comparing(Book::getTitle))
                    .map(book -> String.format("%s, %s, %d, %s, %s",
                            book.getTitle(), book.getAuthor(), book.getYear(), book.getIsbn(), book.getPublisher()))
                    .forEach(System.out::println);
            long uniqueBooksCount = visitorsData.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .distinct()
                    .count();
            System.out.println("Всего уникальных книг: " + uniqueBooksCount);

// Задание 3
            System.out.println("\nЗадание 3");
            visitorsData.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .distinct()
                    .sorted(Comparator.comparing(Book::getYear))
                    .map(book -> String.format("%s, %s, %d, %s, %s",
                            book.getTitle(), book.getAuthor(), book.getYear(), book.getIsbn(), book.getPublisher()))
                    .forEach(System.out::println);

// Задание 4
            System.out.println("\nЗадание 4");
            boolean hasAuthor = visitorsData.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .anyMatch(book -> "Jane Austen".equalsIgnoreCase(book.getAuthor()));
            if (hasAuthor) {
                System.out.println("Книга этого автора есть у посетителей в избранном");
            } else {
                System.out.println("Книг данного автора нет у посетителей в избранном");
            }

// Задание 5
            System.out.println("\nЗадание 5");
            int maxFavoriteBooks = visitorsData.stream()
                    .map(visitor -> visitor.getFavoriteBooks().size())
                    .max(Comparator.naturalOrder())
                    .orElse(0);
            System.out.println("Максимальное кол-во книг в избранном: " + maxFavoriteBooks);

// Задание 6
            System.out.println("\nЗадание 6");
            double averageBooks = visitorsData.stream()
                    .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                    .average().orElse(0);
            visitorsData.stream()
                    .filter(Visitor::isSubscribed)
                    .map(visitor -> {
                        int favoriteBooksCount = visitor.getFavoriteBooks().size();
                        String message;
                        if (favoriteBooksCount > averageBooks) {
                            message = "You are a Bookworm";
                        } else if (favoriteBooksCount < averageBooks) {
                            message = "Read more";
                        } else {
                            message = "Fine!";
                        }
                        return visitor.getPhoneNumber() + ", " + message;
                    })
                    .forEach(System.out::println);
        } else {
            System.out.println("No such file");
        }
    }
}