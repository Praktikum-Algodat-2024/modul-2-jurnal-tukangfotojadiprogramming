class BookNode {
    String title;
    String author;
    String genre;
    boolean isCursed;
    BookNode next;

    public BookNode(String title, String author, String genre, boolean isCursed) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isCursed = isCursed;
        this.next = null;
    }
}

class Stack {
    BookNode top;

    public void push(String title, String author, String genre, boolean isCursed) {
        BookNode newBook = new BookNode(title, author, genre, isCursed);
        newBook.next = top;
        top = newBook;
    }

    public BookNode pop() {
        if (top == null) throw new IllegalStateException("Stack is empty");
        BookNode removedBook = top;
        top = top.next;
        return removedBook;
    }

    public void display() {
        BookNode current = top;
        while (current != null) {
            System.out.println("=====================================");
            System.out.println("Judul Buku : " + current.title);
            System.out.println("Pengarang : " + current.author);
            System.out.println("Genre : " + current.genre);
            System.out.println("Status Buku : " + (current.isCursed ? "Cursed" : "Normal"));
            System.out.println("=====================================");
            current = current.next;
        }
    }

    // Move the first cursed book found to the top of the stack
    public boolean moveTwoBooks(String title1, String title2) {
        if (top == null || top.next == null) return false;
    
        BookNode prev1 = null, node1 = top;
        BookNode prev2 = null, node2 = top;
    
        // Find the first book with title1
        while (node1 != null && !node1.title.equals(title1)) {
            prev1 = node1;
            node1 = node1.next;
        }
    
        // Find the second book with title2
        while (node2 != null && !node2.title.equals(title2)) {
            prev2 = node2;
            node2 = node2.next;
        }
    
        if (node1 == null || node2 == null) return false; // Either book not found
    
        // Swap the nodes
        if (prev1 != null) prev1.next = node2; else top = node2;
        if (prev2 != null) prev2.next = node1; else top = node1;
    
        BookNode temp = node1.next;
        node1.next = node2.next;
        node2.next = temp;
    
        return true;
    }

    public boolean removeBook(String title) {
        if (top == null) return false; // Stack is empty

        if (top.title.equals(title)) { // Book is at the top of the stack
            top = top.next;
            return true;
        }

        // Search for the book in the stack
        BookNode current = top;
        while (current.next != null && !current.next.title.equals(title)) {
            current = current.next;
        }

        if (current.next == null) {
            return false; // Book not found
        }

        // Remove the book
        current.next = current.next.next;
        return true;
    }
    
    
}

class QueueNode {
    String name;
    boolean hasSpecialCard;
    Stack borrowedBooks;
    QueueNode next;

    public QueueNode(String name, boolean hasSpecialCard) {
        this.name = name;
        this.hasSpecialCard = hasSpecialCard;
        this.borrowedBooks = new Stack();
        this.next = null;
    }
}

class Queue {
    QueueNode front;
    QueueNode rear;

    public void enqueue(String name, boolean hasSpecialCard) {
        QueueNode newUser = new QueueNode(name, hasSpecialCard);
        if (rear == null) {
            front = rear = newUser;
        } else {
            rear.next = newUser;
            rear = newUser;
        }
    }

    public QueueNode dequeue() {
        if (front == null) throw new IllegalStateException("Queue is empty");
        QueueNode removedUser = front;
        front = front.next;
        if (front == null) rear = null;
        return removedUser;
    }

    public boolean swap(String name1, String name2) {
        if (front == null || name1.equals(name2)) return false;

        // Initialize pointers to find nodes and their previous nodes
        QueueNode prev1 = null, node1 = front;
        QueueNode prev2 = null, node2 = front;

        // Search for the first node
        while (node1 != null && !node1.name.equals(name1)) {
            prev1 = node1;
            node1 = node1.next;
        }

        // Search for the second node
        while (node2 != null && !node2.name.equals(name2)) {
            prev2 = node2;
            node2 = node2.next;
        }

        // Check if both nodes were found
        if (node1 == null || node2 == null) return false;

        // If node1 is not at the front, link its previous node to node2
        if (prev1 != null) prev1.next = node2;
        else front = node2; // If node1 is the front, make node2 the new front

        // If node2 is not at the front, link its previous node to node1
        if (prev2 != null) prev2.next = node1;
        else front = node1; // If node2 is the front, make node1 the new front

        // Swap the next pointers
        QueueNode temp = node1.next;
        node1.next = node2.next;
        node2.next = temp;

        // Update rear if necessary
        if (node1.next == null) rear = node1;
        else if (node2.next == null) rear = node2;

        return true;
    }

    public void displayQueueOnly() {
        QueueNode current = front;
        int position = 1;

        int bookCount = 0;
        BookNode temp = current.borrowedBooks.top;
        while (temp != null) {
            bookCount++;
            temp = temp.next;
        }

        while (current != null) {
            System.out.println("=====================================");
            System.out.println("Nama : " + current.name);
            System.out.println("Antrian ke : " + position);
            System.out.println("Jumlah Buku : " + bookCount);
            System.out.println("Kartu Spesial : " + (current.hasSpecialCard ? "Ya" : "Tidak"));
            System.out.println("=====================================");

            current = current.next;
            position++;
        }
    }

    public void displayUserBooks(QueueNode user) {
        System.out.println("=====================================");
        System.out.println("=          BUKU " + user.name + "           =");
        user.borrowedBooks.display();
        System.out.println("=====================================");
    }
    public boolean removeByName(String name) {
        if (front == null) return false; // Queue is empty

        if (front.name.equals(name)) {
            front = front.next;
            if (front == null) rear = null; // If the queue becomes empty, set rear to null
            return true;
        }

        QueueNode current = front;
        while (current.next != null && !current.next.name.equals(name)) {
            current = current.next;
        }

        if (current.next == null) {
            return false; // Name not found
        }

        // Remove the node
        current.next = current.next.next;
        if (current.next == null) {
            rear = current; // Update rear if we removed the last node
        }
        return true;
    }

    public boolean moveTwoBooks(String userName, String title1, String title2) {
        QueueNode current = front;
    
        // Find the user with the specified name
        while (current != null) {
            if (current.name.equals(userName)) {
                // Call moveTwoBooks on this user's borrowedBooks stack
                return current.borrowedBooks.moveTwoBooks(title1, title2);
            }
            current = current.next;
        }
        return false; // User not found
    }
}

public class tes2 {
    public static void main(String[] args) {
        Queue queue = new Queue();
        // Stack stack = new Stack();

        // Menambahkan pengguna ke dalam queue
        queue.enqueue("Kazuma", false);
        queue.front.borrowedBooks.push("Belajar Java", "Raysen", "Edukasi", false);
        queue.front.borrowedBooks.push("Cara Menjadi Orang Kaya", "Teguh", "Fantasi", false);

        queue.enqueue("Hu Tao", true);
        queue.front.next.borrowedBooks.push("Cara Tidur Cepat", "Teguh", "Edukasi kayaknya", true);
        queue.front.next.borrowedBooks.push("Belajar C++", "Raysen", "Edukasi", false);
        queue.front.next.borrowedBooks.push("Belajar Ilmu Hitam", "Megumin", "Unknown", true);

        queue.enqueue("Kafka", false);
        queue.front.next.next.borrowedBooks.push("Raysen the Forgotton One", "Unknown", "Sejarah", true);
        queue.front.next.next.borrowedBooks.push("Misteri Menghilangnya Nasi Puyung", "Optimus", "Misteri", false);
        queue.front.next.next.borrowedBooks.push("Cara Menjadi Milioner Dalam 1 Jam", "Master Oogway", "Edukasi", false);

        queue.enqueue("Xiangling", false);
        queue.front.next.next.next.borrowedBooks.push("gatau", "gatau", "gatau", true);

        System.out.println("=====================================");
        System.out.println("=          DAFTAR ANTRIAN           =");
        queue.displayQueueOnly();

        while (queue.front != null) {
            QueueNode user = queue.dequeue();
            System.out.println("\nMengeluarkan " + user.name + " dari antrian...");
            queue.displayUserBooks(user);
            break;
        }

        System.out.println("=====================================");
        System.out.println("=          DAFTAR ANTRIAN           =");
        queue.displayQueueOnly();

        while (queue.front != null) {
            QueueNode user = queue.dequeue();
            System.out.println("\nMengeluarkan " + user.name + " dari antrian...");
            queue.displayUserBooks(user);
            break;
        }

        queue.enqueue("Sucrose", true);
        queue.rear.borrowedBooks.push("Resurection", "Unknown", "Unknown", true);
        queue.rear.borrowedBooks.push("Alcemy", "Albedo", "Science", true);
        queue.rear.borrowedBooks.push("Durin The Forgotton Dragon", "Gold", "Misteri", false);

        System.out.println("=====================================");
        System.out.println("=          DAFTAR ANTRIAN           =");
        queue.displayQueueOnly();

        queue.removeByName("Xiangling");

        System.out.println("=====================================");
        System.out.println("=          DAFTAR ANTRIAN           =");
        queue.displayQueueOnly();

        queue.swap("Kafka", "Sucrose");

        System.out.println("=====================================");
        System.out.println("=          DAFTAR ANTRIAN           =");
        queue.displayQueueOnly();

        while (queue.front != null) {
            QueueNode user = queue.dequeue();
            System.out.println("\nMengeluarkan " + user.name + " dari antrian...");
            queue.displayUserBooks(user);
            break;
        }

        System.out.println("=====================================");
        System.out.println("=          DAFTAR ANTRIAN           =");
        queue.displayQueueOnly();

        while (queue.front != null) {
            QueueNode user = queue.front;  // Access the current front user without dequeuing
        
            // Check if the user has a special card
            if (!user.hasSpecialCard) {
                QueueNode current = queue.front;
                while (current != null) {
                System.out.println("\nMenampilkan buku dari " + current.name + "...");
                    queue.displayUserBooks(current); // Tampilkan buku tanpa dequeue
                    break; // Pindah ke pengguna berikutnya dalam antrian
                }
                
                // Check if any book in the user's stack is cursed
                BookNode currentBook = user.borrowedBooks.top;
                boolean hasCursedBook = false;
                while (currentBook != null) {
                    if (currentBook.isCursed) {
                        hasCursedBook = true;
                        break;
                    }
                    currentBook = currentBook.next;
                }
        
                if (hasCursedBook) {
                    System.out.println("Tidak Bisa meminjam buku");
                    break;
                }
            }

        }

        queue.moveTwoBooks("Kafka", "Raysen the Forgotton One", "Cara Menjadi Milioner Dalam 1 Jam");

        QueueNode current = queue.front;
        while (current != null) {
            System.out.println("\nMenampilkan buku dari " + current.name + "...");
            queue.displayUserBooks(current); // Tampilkan buku tanpa dequeue
            current = current.next; // Pindah ke pengguna berikutnya dalam antrian
        }
    }
}
