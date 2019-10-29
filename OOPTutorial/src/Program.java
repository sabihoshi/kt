// https://java.is-la.me/ngejtZD
/*
Sample Dialog:

How many people are in this bus?: 3

Enter your name: Kao
Enter your age: 17
Enter your birthday: January 7, 2002

[...] x 3

People:
Kao, 17, January 7, 2002
Neil, 16, February 2, 2003
Don, 14, April 21, 2006

*/

public class Program {
    static Template template = new Template();
    public static void main(String[] args) {
        template.print("How many books are in this shelf?");
        int books = template.scanner.nextInt();
        template.scanner.nextLine();
        Shelf bookShelf = new Shelf(books);
        for (int i = 0; i < books; i++) {
            String name = getString("Name of the book: ");
            String genre = getString("Genre of the book: ");
            String author = getString("Author of the book: ");
            bookShelf.books[i] = new Book(name, author, genre);
        }
        for (int i = 0; i < books; i++) {
            template.println(bookShelf.books[i].name + ", " +
                    bookShelf.books[i].author + ", " +
                    bookShelf.books[i].genre);
        }
    }

    private static String getString(String s) {
        template.print(s);
        return template.scanner.nextLine();
    }
}