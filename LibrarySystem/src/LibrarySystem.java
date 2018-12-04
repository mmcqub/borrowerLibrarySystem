
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * This class describes the attributes and behaviour
 * of a menu-driven library system application.
 * 
 * Analyse and document this class by adding
 * JavaDoc and other comments to explain the purpose of
 * defined data and the behaviour of
 * the methods.
 * 
 * @author Paul Sage
 *
 */

public class LibrarySystem {

	static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		String options[] = { 	"1. Create Borrower", 
								"2. Display Borrower",
								"3. Borrow Book",
								"4. Display all Borrowers",
								"5. Return a book",
								"6. Add a new book to library system",
								"7. Sort library by ID",
								"8. Sort library by title",
								"9. Display all books in library",
								"10. Quit"};
		Borrower b = null;
		boolean finished = false;
		Book[] library = addBooks();
		ArrayList<Borrower> userList = new ArrayList<Borrower>();
		
		do {
			displayMenu(options);
			int choice = getUserChoice();
			switch(choice) {
			case 1	:	userList.add(createBorrower());
						break;
						
			case 2	:	if (!userList.isEmpty()) {
							displayBorrower(selectBorrowerFromList(userList));
						}else {
							userListIsEmpty();
						}
						break;
						
			case 3	:	if(!userList.isEmpty()) {
							Book bk = chooseBook(library);
							b = selectBorrowerFromList(userList);
							borrowBookFromLibrary(b.borrowBook(bk), b);
						}else {
							userListIsEmpty();
						}
						break;
						
			case 4:		if(!userList.isEmpty()) {
							displayAllBorrowers(userList);
						}else {
							userListIsEmpty();
						}
						break;
						
			case 5:     if(!userList.isEmpty()) {
							returnBook(userList);
						}else {
							userListIsEmpty();
						}	
						break;
						
			case 6:     library = addNewBookToSystem(library);		
						break;
						
			case 7:     library = sortLibraryById(library);		
						break;
						
			case 8:     library = sortLibraryByTitle(library);		
						break;
						
			case 9: 	displayAllBooksInLibrary(library);
						break;
						
			case 10	:	finished = true;
						break;
			default	:	System.out.println("Error - Invalid Choice");
			}
		}
		while( !finished );
		System.out.println("Goodbye!");
	}
	
	
	
	//NEW METHOD - This method is used to display the ID and Name of all the borrowers from the userList
	static void displayAllBorrowers(ArrayList<Borrower> userList) {
		int listNumber = 1;
		for(Borrower b : userList) {
			System.out.println(listNumber + ". " + "ID: " + b.getId() + " Name: " + b.getName());
			listNumber++;
		}
	}
	
	//NEW METHOD - This method will allow the user to select a borrower from the userList
	//and return that borrower object
	static Borrower selectBorrowerFromList(ArrayList<Borrower> userList) {
		System.out.println("Please choose the Borrower list number from the list below.");
		displayAllBorrowers(userList);
		
		int listNumberChoice = getValidNumberFromScanner();
		
		while(listNumberChoice <= 0 || listNumberChoice > userList.size()) {
			System.out.println("Invalid list number. Please try again");
			listNumberChoice = getValidNumberFromScanner();
		}
		
		Borrower choosenBorrower = userList.get(listNumberChoice -1);
		
		return choosenBorrower;
	}

	//NEW METHOD - This method will display when the userList is empty, i.e no borrowers have been created yet
	static void userListIsEmpty() {
		System.out.println("There are currently no borrowers in the library system.");
		System.out.println("Please create a borrower to continue.");
	}
	
	//NEW METHOD - This method can be used throughout the program to ensure the user has selected an integer.
	static int getValidNumberFromScanner() {
		int number = 0;
		boolean isNumber;
		
		do {
			if (input.hasNextInt()) {
				number = input.nextInt();
				isNumber = true;
			} else {
				System.out.println("Invalid input. Please enter a number");
				isNumber = false;
				input.next();
			} 
		} while (!isNumber);
		
		return number;
	}
	
	//NEW METHOD - This method is used when the borrower wants to borrow a book from the library.
	//This method checks if the book is available, if so the book is added to the borrowers 'booksOnLoan'
	//If the book is unavailable, the borrower is prompted to choose another book.
	static void borrowBookFromLibrary(boolean bookIsAvailable, Borrower b) {
		if(bookIsAvailable) {
			System.out.println(b.getName() + " has successfully borrowed the book from the library.");
		}else {
			System.out.println("The current book is unavailable, please select another book");
		}
	}

	//NEW METHOD - This method will allow a borrower to return a book.
	static void returnBook(ArrayList<Borrower> userList) {
		System.out.println("Please select the borrower who would like to return a book");
		
		Borrower b = selectBorrowerFromList(userList);
		
		if(b.getAllBooks().isEmpty()) {
			System.out.println("The current borrower has no books on loan");
		
		}else {
			System.out.println("Which book would you like to return? Please select a number from the list below");
			
			int bookListNumber = 1;
			for(Book bk : b.getAllBooks()) {
				System.out.println(bookListNumber + ". " + bk.toString());
			}
			
			int listNumberChoice = getValidNumberFromScanner();
			
			while(listNumberChoice <=0 || listNumberChoice > b.getAllBooks().size()) {
				System.out.println("You have select an invalid list number. Please try again");
				listNumberChoice = getValidNumberFromScanner();
			}
			
			//Make book available again
			Book bookToReturn = b.getAllBooks().get(listNumberChoice-1);
			bookToReturn.setAvailable(true);
			//Remove book from users current books on loan
			b.getAllBooks().remove(listNumberChoice-1);
			System.out.println("The book: ");
			System.out.println(bookToReturn.toString());
			System.out.println("has successfully been returned.");
			
		}
		
		
	}
	
	//NEW METHOD - This method is used to add a new book to the library system.
	static Book[] addNewBookToSystem(Book[] library) {
		Book[] newLibrary = Arrays.copyOf(library, library.length + 1);
		input.nextLine();
		System.out.println("Enter book id: ");
		int id = getValidNumberFromScanner();
		input.nextLine();
		System.out.println("Enter book isbn: ");
		String isbn = input.nextLine();
		System.out.println("Enter book title: ");
		String title = input.nextLine();
		System.out.println("Enter book author: ");
		String author = input.nextLine();
		
		Book newBookToBeAdded = new Book(id, isbn, title, author);
		
		int lastIndexInLibrary = newLibrary.length - 1;
		newLibrary[lastIndexInLibrary] = newBookToBeAdded;
		
		System.out.println("The book: ");
		System.out.println(newBookToBeAdded.toString());
		System.out.println(" has been added to the library system.");
		
		return newLibrary;
	}

	//NEW METHOD - This method is used to sort the library array in ascending order by ID
	static Book[] sortLibraryById(Book[] library) {
		
		int libSize = library.length;
		
		Book[] orderedLibrary = new Book[libSize];
		int[] idArray = new int[libSize];
		
		for(int i=0; i < libSize; i++) {
			idArray[i] = library[i].getId();
		}
		Arrays.sort(idArray);
		
		//Testing if the int array has been correctly populated
		for(int i : idArray) {
			System.out.println(i);
		}
		
		for(int i=0; i < libSize; i++) {
			for(Book b : library) {
				if(b.getId() == idArray[i]) {
					orderedLibrary[i] = b;
				}
			}
		}
		
		return orderedLibrary;
	}
	
	//NEW METHOD - This method is used to set the library array in alphabetical order
	static Book[] sortLibraryByTitle(Book[] library) {
		
		int libSize = library.length;
		
		Book[] orderedLibrary = new Book[libSize];
		String[] titleArray = new String[libSize];
		
		for(int i=0; i < libSize; i++) {
			String title = library[i].getTitle();
			titleArray[i] = (title);
		}
		
		Arrays.sort(titleArray);
		
		
		for(int i=0; i < libSize; i++) {
			for(Book b : library) {
				if(b.getTitle().equals(titleArray[i])) {
					orderedLibrary[i] = b;
				}
			}
		}
		
		return orderedLibrary;
	}
	
	//NEW METHOD - This method is used to display all the books in the library
	static void displayAllBooksInLibrary(Book[] data) {
		System.out.println("Book List");
		System.out.println("=========\n");
		
		for(int index=0;index<data.length;index++) {
			Book bk1 = data[index];
			System.out.println((index+1) +". "+ bk1);
		}
		System.out.println();
	}
	
	static void displayBorrower(Borrower b) {
		System.out.println("\nLibrary Member Details");
		System.out.println("======================");
		
		System.out.println("\t"+b.getDetails()+"\n");
		
		System.out.println("Books on Loan");
		System.out.println("=============");
		
		if(b.getAllBooks().isEmpty()) {
			System.out.println("\t No books currently on loan");
		}else {
			System.out.println( "\t"+b.getAllBooks() );
		}
		
		System.out.println();
	}
	
	static void displayMenu(String data[]) {
		System.out.println("Main Menu");
		System.out.println("=========\n");
		
		for(String s: data) {
			System.out.println(s);
		}
		
		System.out.println();
	}

	static int getUserChoice() {
		int choice = getValidNumberFromScanner();
		return choice;
	}
	
	static Borrower createBorrower() {	
		input.nextLine();
		System.out.print("Enter Id:");
		String id = input.nextLine();
		System.out.print("Enter Name:");
		String name = input.nextLine();
		System.out.print("Enter Address:");
		String add = input.nextLine();
		System.out.println();
		Borrower b = new Borrower(id,name,add);
		return b;
	}
	
	static Book[] addBooks() {
		Book theBooks[] = new Book[2];
		theBooks[0] = new Book(24, "45-12-14", "The Sky at Night", "Moore");
		theBooks[1] = new Book(23, "12-12-12", "The Cosmos", "Sagan");
		return theBooks;
	}
	
	static Book chooseBook(Book[] data) {
		Book pick = null;
		System.out.println("Book List");
		System.out.println("=========\n");
		
		for(int index=0;index<data.length;index++) {
			Book bk1 = data[index];
			System.out.println((index+1) +". "+ bk1);
		}
		System.out.println();
		int choice = getUserChoice();
		
		while(choice <=0 || choice > data.length) {
			System.out.println("Invalid choice. Please select a list number");
			choice = getUserChoice();
		}
		
		
		pick = data[choice-1];
		System.out.println();
		return pick;
	}
	
}
