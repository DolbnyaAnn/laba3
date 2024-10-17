package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class MainTest {
    List <Visitor> visitorsData;
    @BeforeEach
    void setUp() {
        visitorsData = Main.ParseFile("books.json");
    }

    @Test
    void getVisitorNames() {
        List<String> Visitors = Arrays.asList("John Doe", "Jane Smith", "Michael Johnson", "Emily Brown", "David Wilson", "Olivia Miller", "William Davis", "Sophia Garcia", "James Martinez", "Isabella Anderson", "Ethan Taylor", "Ava Thomas", "Jack Hill", "Lily Jones", "Oliver Baker");
        assertLinesMatch(Visitors,Main.getVisitorNames(visitorsData));
    }

    @Test
    void getVisitorCount() {
        assertEquals(15,Main.getVisitorCount(visitorsData));
    }

    @Test
    void getUniqueFavoriteBooks() {
        List<String> books = Arrays.asList("1984, George Orwell, 1949, 0451534852, Signet Classics", "Brave New World, Aldous Huxley, 1932, 0060860495, Harper Perennial", "Gone Girl, Gillian Flynn, 2012, 0316205775, Crown", "Harry Potter and the Philosopher's Stone, J.K. Rowling, 1997, 0747532735, Bloomsbury", "Pride and Prejudice, Jane Austen, 1813, 0525472125, Penguin Classics", "The Alchemist, Paulo Coelho, 1988, 0060920508, HarperOne", "The Book Thief, Markus Zusak, 2005, 0375831004, Knopf", "The Catcher in the Rye, J.D. Salinger, 1951, 0316769487, Little, Brown", "The Da Vinci Code, Dan Brown, 2003, 0385504209, Doubleday", "The Fault in Our Stars, John Green, 2012, 0316038746, Dutton", "The Girl on the Train, Paula Hawkins, 2015, 0007555445, Riverhead Books", "The Great Gatsby, F. Scott Fitzgerald, 1925, 0743273567, Scribner", "The Hitchhiker's Guide to the Galaxy, Douglas Adams, 1979, 034539082X, Del Rey", "The Hunger Games, Suzanne Collins, 2008, 0439023483, Scholastic", "The Kite Runner, Khaled Hosseini, 2003, 0385506982, Riverhead Books", "The Lord of the Rings, J.R.R. Tolkien, 1954, 0395026468, Allen & Unwin", "The Martian, Andy Weir, 2011, 0553418438, Crown", "The Nightingale, Kristin Hannah, 2015, 0385387035, St. Martin's Press", "The Shack, William P. Young, 2007, 0316067860, Windblown Media", "To Kill a Mockingbird, Harper Lee, 1960, 0446310759, HarperPerennial");
        assertLinesMatch(books,Main.getUniqueFavoriteBooks(visitorsData));
    }

    @Test
    void getUniqueFavoriteBooksCount() {
        assertEquals(20,Main.getUniqueFavoriteBooksCount(visitorsData));
    }

    @Test
    void getUniqueFavoriteBooksSortedByYear() {
        List<String> books = Arrays.asList("Pride and Prejudice, Jane Austen, 1813, 0525472125, Penguin Classics", "The Great Gatsby, F. Scott Fitzgerald, 1925, 0743273567, Scribner", "Brave New World, Aldous Huxley, 1932, 0060860495, Harper Perennial", "1984, George Orwell, 1949, 0451534852, Signet Classics", "The Catcher in the Rye, J.D. Salinger, 1951, 0316769487, Little, Brown", "The Lord of the Rings, J.R.R. Tolkien, 1954, 0395026468, Allen & Unwin", "To Kill a Mockingbird, Harper Lee, 1960, 0446310759, HarperPerennial", "The Hitchhiker's Guide to the Galaxy, Douglas Adams, 1979, 034539082X, Del Rey", "The Alchemist, Paulo Coelho, 1988, 0060920508, HarperOne", "Harry Potter and the Philosopher's Stone, J.K. Rowling, 1997, 0747532735, Bloomsbury", "The Kite Runner, Khaled Hosseini, 2003, 0385506982, Riverhead Books", "The Da Vinci Code, Dan Brown, 2003, 0385504209, Doubleday", "The Book Thief, Markus Zusak, 2005, 0375831004, Knopf", "The Shack, William P. Young, 2007, 0316067860, Windblown Media", "The Hunger Games, Suzanne Collins, 2008, 0439023483, Scholastic", "The Martian, Andy Weir, 2011, 0553418438, Crown", "The Fault in Our Stars, John Green, 2012, 0316038746, Dutton", "Gone Girl, Gillian Flynn, 2012, 0316205775, Crown", "The Nightingale, Kristin Hannah, 2015, 0385387035, St. Martin's Press", "The Girl on the Train, Paula Hawkins, 2015, 0007555445, Riverhead Books");
        assertLinesMatch(books,Main.getUniqueFavoriteBooksSortedByYear(visitorsData));
    }

    @Test
    void isAuthorInFavorites() {
        assertTrue(Main.isAuthorInFavorites(visitorsData, "Jane Austen"));
        assertFalse(Main.isAuthorInFavorites(visitorsData, "Anny's Dany"));
    }

    @Test
    void getMaxFavoriteBooksCount() {
        assertEquals(7,Main.getMaxFavoriteBooksCount(visitorsData));
    }

    @Test
    void getAverageFavoriteBooksCount() {
        assertEquals(2.8,Main.getAverageFavoriteBooksCount(visitorsData));
    }
}