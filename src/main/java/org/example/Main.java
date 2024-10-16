package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Visitor> visitorsData = ParseFile("C:\\work\\laba3\\src\\main\\java\\org\\example\\books.json");
        if (visitorsData != null) {
            System.out.println("Задание 1");
            getVisitorNames(visitorsData).forEach(System.out::println);
            System.out.println("Всего посетителей: " + getVisitorCount(visitorsData));
            System.out.println("\nЗадание 2");
            getUniqueFavoriteBooks(visitorsData).forEach(System.out::println);
            System.out.println("Всего уникальных книг: " + getUniqueFavoriteBooksCount(visitorsData));
            System.out.println("\nЗадание 3");
            getUniqueFavoriteBooksSortedByYear(visitorsData).forEach(System.out::println);
            System.out.println("\nЗадание 4");
            if (isAuthorInFavorites(visitorsData, "Jane Austen")) {
                System.out.println("Книга этого автора есть у посетителей в избранном");
            } else {
                System.out.println("Книг данного автора нет у посетителей в избранном");
            }
            System.out.println("\nЗадание 5");
            System.out.println("Максимальное кол-во книг в избранном:" + getMaxFavoriteBooksCount(visitorsData));
            System.out.println("\nЗадание 6");
            List<smsMessage> smsMessages = generateSmsMessages(visitorsData);
            smsMessages.forEach(System.out::println);
        } else {
            System.out.println("No such file");
        }
    }

    public static List<String> getVisitorNames(List<Visitor> visitorsData) {
        return visitorsData.stream().map(visitor -> visitor.getFirstName() + " " + visitor.getLastName()).collect(Collectors.toList());
    }

    public static int getVisitorCount(List<Visitor> visitorsData) {
        return getVisitorNames(visitorsData).size();
    }

    public static List<String> getUniqueFavoriteBooks(List<Visitor> visitorsData) {
        return visitorsData.stream()
                .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                .distinct()
                .sorted(Comparator.comparing(Book::getTitle))
                .map(book -> String.format("%s, %s, %d, %s, %s",
                        book.getTitle(), book.getAuthor(), book.getYear(), book.getIsbn(), book.getPublisher()))
                .collect(Collectors.toList());
    }

    public static int getUniqueFavoriteBooksCount(List<Visitor> visitorsData) {
        return getUniqueFavoriteBooks(visitorsData).size();
    }

    public static List<String> getUniqueFavoriteBooksSortedByYear(List<Visitor> visitorsData) {
        return visitorsData.stream()
                .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                .distinct()
                .sorted(Comparator.comparing(Book::getYear))
                .map(book -> String.format("%s, %s, %d, %s, %s",
                        book.getTitle(), book.getAuthor(), book.getYear(), book.getIsbn(), book.getPublisher()))
                .collect(Collectors.toList());
    }

    public static boolean isAuthorInFavorites(List<Visitor> visitorsData, String author) {
        return visitorsData.stream()
                .flatMap(visitor -> visitor.getFavoriteBooks().stream()).anyMatch(book -> author.equalsIgnoreCase(book.getAuthor()));
    }

    public static int getMaxFavoriteBooksCount(List<Visitor> visitorsData) {
        return visitorsData.stream()
                .map(visitor -> visitor.getFavoriteBooks().size())
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    public static List<Visitor> ParseFile(String path) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(path)) {
            Type visitorListType = new TypeToken<List<Visitor>>() {
            }.getType();
            return gson.fromJson(reader, visitorListType);
        } catch (IOException e) {
            System.out.println(e.toString());
            return null;
        }
    }

    @Data
    @AllArgsConstructor
    public static class smsMessage {
        private String phone;
        private String message;
        public String toString()
        {
            return this.phone+", "+this.message;
        }
    }

    public static double getAverageFavoriteBooksCount(List<Visitor> visitorsData) {
        return visitorsData.stream().mapToInt(visitor -> visitor.getFavoriteBooks().size()).average().orElse(0);
    }

    public static List<smsMessage> generateSmsMessages(List<Visitor> visitorsData){
        double average = getAverageFavoriteBooksCount(visitorsData);
        return visitorsData.stream().filter(Visitor::isSubscribed).map(visitor -> {
            int favoriteBooksCount = visitor.getFavoriteBooks().size();
            String msg;
            if (favoriteBooksCount>average)
            {
                msg="You are a Bookworm";
            }else if (favoriteBooksCount<average){
                msg="Read more";
            }else{
                msg="Fine!";
            }
            return new smsMessage(visitor.getPhoneNumber(),msg);
        } ).collect(Collectors.toList());
    }
}
