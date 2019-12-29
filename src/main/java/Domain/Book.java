package Domain;


import java.io.Serializable;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Book implements Comparable<Book>, Serializable {
	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private int id;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String no;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String writer;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String press;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String address;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String total;
	private String borrowedNumber;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public Book(){
		super();
	}

	public Book(int id, String name, String no, String writer, String press, String address, String total) {
		this.id = id;
		this.name = name;
		this.no = no;
		this.writer = writer;
		this.press = press;
		this.address = address;
		this.total = total;
	}

	public Book(String name, String no, String writer, String press, String address, String total) {
		this.name = name;
		this.no = no;
		this.writer = writer;
		this.press = press;
		this.address = address;
		this.total = total;
	}

	public Book(int id, String name, String no, String writer, String press, String address, String total, String borrowedNumber) {
		this.id = id;
		this.name = name;
		this.no = no;
		this.writer = writer;
		this.press = press;
		this.address = address;
		this.total = total;
		this.borrowedNumber = borrowedNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getBorrowedNumber() {
		return borrowedNumber;
	}

	public void setBorrowedNumber(String borrowedNumber) {
		this.borrowedNumber = borrowedNumber;
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", name='" + name + '\'' +
				", no='" + no + '\'' +
				", writer='" + writer + '\'' +
				", press='" + press + '\'' +
				", address='" + address + '\'' +
				", total=" + total +
				'}';
	}

	@Override
	public int compareTo(Book o) {
		// TODO Auto-generated method stub
		return this.id - o.id;
	}
}

