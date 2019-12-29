package Domain;


import java.io.Serializable;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Librarian implements Comparable<Librarian>, Serializable {
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
	
	private String telNumber;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */

	public Librarian(){
		super();
	}

	public Librarian(int id, String name, String no, String telNumber) {
		this.id = id;
		this.name = name;
		this.no = no;
		this.telNumber = telNumber;
	}

	public Librarian(String name, String no, String telNumber) {
		this.name = name;
		this.no = no;
		this.telNumber = telNumber;
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

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	@Override
	public String toString() {
		return "Librarian{" +
				"id=" + id +
				", name='" + name + '\'' +
				", no='" + no + '\'' +
				", telNumber='" + telNumber + '\''+
				'}';
	}

	@Override
	public int compareTo(Librarian o) {
		// TODO Auto-generated method stub
		return this.id - o.id;
	}
}

