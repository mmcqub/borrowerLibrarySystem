
import java.util.ArrayList;
/**
 * This class describes the attributes and behaviour
 * of a lending library member.
 * 
 * Analyse and document this class by adding
 * JavaDoc and other comments to explain the purpose of
 * the instance variables and the behaviour of
 * the methods.
 * 
 * @author Paul Sage
 *
 */
public class Borrower {
	private String name;
	private String address;
	private String id;
	private ArrayList<Book> booksOnLoan;
	
	public Borrower(String id, String name, String address) {
		this.name = name;
		this.address = address;
		this.id = id;
		booksOnLoan = new ArrayList<Book>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getId() {
		return id;
	}
	
	public String getDetails() {
		String res = "";
		res+="ID:"+id+" Name:"+name+" Address:"+address;
		return res;
	}
	
	public boolean borrowBook(Book bk) {
		if ( bk != null && bk.isAvailable() ) {
			booksOnLoan.add(bk);
			bk.setAvailable(false);
			return true;
		}
		return false;
	}
	
	public ArrayList<Book> getAllBooks() {
		return booksOnLoan;
	}
	
}
