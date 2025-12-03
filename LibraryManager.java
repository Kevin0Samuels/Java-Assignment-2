import java.io.*;
import java.util.*;

class Book implements Comparable<Book> {
    int bookId;
    String title;
    String author;
    String category;
    boolean isIssued;

    public Book(int bookId, String title, String author, String category, boolean isIssued) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isIssued = isIssued;
    }

    public void displayBookDetails() {
        System.out.println("\n--- Book Details ---");
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Category: " + category);
        System.out.println("Issued: " + (isIssued ? "Yes" : "No"));
    }

    public void markAsIssued() {
        isIssued = true;
    }

    public void markAsReturned() {
        isIssued = false;
    }

    @Override
    public int compareTo(Book b) {
        return this.title.compareToIgnoreCase(b.title);
    }
}

class SortByAuthor implements Comparator<Book> {
    public int compare(Book a, Book b) {
        return a.author.compareToIgnoreCase(b.author);
    }
}

class Member {
    int memberId;
    String name;
    String email;
    List<Integer> issuedBooks = new ArrayList<>();

    public Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public void displayMemberDetails() {
        System.out.println("\n--- Member Details ---");
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Issued Books: " + issuedBooks);
    }

    public void addIssuedBook(int bookId) {
        issuedBooks.add(bookId);
    }

    public void returnIssuedBook(int bookId) {
        issuedBooks.remove((Integer) bookId);
    }
}

public class LibraryManager {

    Map<Integer, Book> books = new HashMap<>();
    Map<Integer, Member> members = new HashMap<>();
    Set<String> categories = new HashSet<>();

    Scanner sc = new Scanner(System.in);

    File bookFile = new File("books.txt");
    File memberFile = new File("members.txt");

    public void loadFromFile() {
        try {
            if (!bookFile.exists()) bookFile.createNewFile();
            if (!memberFile.exists()) memberFile.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(bookFile));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String title = data[1];
                String author = data[2];
                String category = data[3];
                boolean issued = Boolean.parseBoolean(data[4]);

                books.put(id, new Book(id, title, author, category, issued));
                categories.add(category);
            }
            br.close();

            br = new BufferedReader(new FileReader(memberFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String email = data[2];

                Member m = new Member(id, name, email);

                if (data.length > 3) {
                    String[] issued = data[3].split(";");
                    for (String s : issued) {
                        if (!s.isEmpty()) m.issuedBooks.add(Integer.parseInt(s));
                    }
                }
                members.put(id, m);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error loading files: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(bookFile));

            for (Book b : books.values()) {
                bw.write(b.bookId + "," + b.title + "," + b.author + "," +
                        b.category + "," + b.isIssued);
                bw.newLine();
            }
            bw.close();

            bw = new BufferedWriter(new FileWriter(memberFile));
            for (Member m : members.values()) {
                bw.write(m.memberId + "," + m.name + "," + m.email + ",");
                for (int bId : m.issuedBooks) bw.write(bId + ";");
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            System.out.println("Error saving files.");
        }
    }

    public void addBook() {
        try {
            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            System.out.print("Enter Category: ");
            String category = sc.nextLine();

            Book b = new Book(id, title, author, category, false);
            books.put(id, b);
            categories.add(category);

            saveToFile();
            System.out.println("Book added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding book.");
        }
    }

    public void addMember() {
        try {
            System.out.print("Enter Member ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Email: ");
            String email = sc.nextLine();

            Member m = new Member(id, name, email);
            members.put(id, m);

            saveToFile();
            System.out.println("Member added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding member.");
        }
    }

    public void issueBook() {
        try {
            System.out.print("Enter Book ID: ");
            int bId = sc.nextInt();

            System.out.print("Enter Member ID: ");
            int mId = sc.nextInt();

            if (!books.containsKey(bId)) {
                System.out.println("Book not found.");
                return;
            }
            if (!members.containsKey(mId)) {
                System.out.println("Member not found.");
                return;
            }

            Book b = books.get(bId);
            Member m = members.get(mId);

            if (b.isIssued) {
                System.out.println("Book already issued!");
                return;
            }

            b.markAsIssued();
            m.addIssuedBook(bId);

            saveToFile();
            System.out.println("Book issued successfully!");

        } catch (Exception e) {
            System.out.println("Error issuing book.");
        }
    }

    public void returnBook() {
        try {
            System.out.print("Enter Book ID: ");
            int bId = sc.nextInt();

            System.out.print("Enter Member ID: ");
            int mId = sc.nextInt();

            Book b = books.get(bId);
            Member m = members.get(mId);

            b.markAsReturned();
            m.returnIssuedBook(bId);

            saveToFile();
            System.out.println("Book returned successfully!");

        } catch (Exception e) {
            System.out.println("Error returning book.");
        }
    }

    public void searchBooks() {
        sc.nextLine();
        System.out.print("Enter keyword (title/author/category): ");
        String key = sc.nextLine().toLowerCase();

        for (Book b : books.values()) {
            if (b.title.toLowerCase().contains(key) ||
                b.author.toLowerCase().contains(key) ||
                b.category.toLowerCase().contains(key)) {
                b.displayBookDetails();
            }
        }
    }

    public void sortBooks() {
        List<Book> list = new ArrayList<>(books.values());

        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");
        System.out.print("Enter choice: ");
        int c = sc.nextInt();

        if (c == 1)
            Collections.sort(list);
        else if (c == 2)
            Collections.sort(list, new SortByAuthor());

        for (Book b : list) {
            b.displayBookDetails();
        }
    }

    public void menu() {
        loadFromFile();

        while (true) {
            System.out.println("\n===== City Library Digital Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int ch = sc.nextInt();

            switch (ch) {
                case 1 -> addBook();
                case 2 -> addMember();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> searchBooks();
                case 6 -> sortBooks();
                case 7 -> {
                    saveToFile();
                    System.out.println("Exiting... Data saved!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    public static void main(String[] args) {
        new LibraryManager().menu();
    }
}
