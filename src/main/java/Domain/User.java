package Domain;


import java.io.Serializable;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class User implements Comparable<User>, Serializable {
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
	
	private String username;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String password;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private String role;

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
	 * @ordered
	 */
	
	private Librarian librarian;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 */
	public User(){
		super();
	}

	public User(int id, String username, String password, Reader reader, Librarian librarian, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.reader = reader;
		this.librarian = librarian;
		this.role = role;
	}

	public User(int id, String username, String password, Reader reader) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.reader = reader;
	}

	public User(int id, String username, String password, Librarian librarian) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.librarian = librarian;
	}

	public User(int id, String username, String password, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public Librarian getLibrarian() {
		return librarian;
	}

	public void setLibrarian(Librarian librarian) {
		this.librarian = librarian;
	}

    @Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.id - o.id;
	}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", reader=" + reader +
                ", librarian=" + librarian +
                '}';
    }
}

