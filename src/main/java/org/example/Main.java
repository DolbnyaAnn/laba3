package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Gson gson=new Gson();
        try(FileReader reader = new FileReader("C:\\work\\laba3\\src\\main\\java\\org\\example\\books.json")){
            Type visitorListType = new TypeToken<List<Visitor>>() {}.getType();
// Читаем и парсим JSON
            List<Visitor> visitors = gson.fromJson(reader, visitorListType);

// Выводим данные для проверки
            for (Visitor visitor : visitors) {
                System.out.println("Имя: " + visitor.getFirstName());
                System.out.println("Фамилия: " + visitor.getLastName());
                System.out.println("Телефон: " + visitor.getPhoneNumber());
                System.out.println("Подписан на рассылку: " + visitor.isSubscribed());

                System.out.println("Любимые книги:");
                for (Book book : visitor.getFavoriteBooks()) {
                    System.out.println("Название: " + book.getTitle());
                    System.out.println("Автор: " + book.getAuthor());
                    System.out.println("Год: " + book.getYear());
                    System.out.println("ISBN: " + book.getIsbn());
                    System.out.println("Издатель: " + book.getPublisher());
                }
                System.out.println("--------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
