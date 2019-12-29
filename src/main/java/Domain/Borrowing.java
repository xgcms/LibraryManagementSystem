package Domain;
import java.util.Date;


/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Borrowing {
	private int id;
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private Date date;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private Book book;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private Reader reader;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Borrowing(){
		super();
	}

	public Borrowing(Date date, Book book, Reader reader) {
		this.date = date;
		this.book = book;
		this.reader = reader;
	}

	public Borrowing(int id, Date date, Book book, Reader reader) {
		this.id = id;
		this.date = date;
		this.book = book;
		this.reader = reader;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Borrowing{" +
				"id=" + id +
				", date=" + date +
				", book=" + book +
				", reader=" + reader +
				'}';
	}
}

