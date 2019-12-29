package Domain;


import java.io.Serializable;

/**
 * <!-- begin-user-doc -->
 * <!--  end-user-doc  -->
 * @generated
 */

public class Reader implements Comparable<Reader>, Serializable {
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
	
	private String age;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String sex;

	/**
	 * <!-- begin-user-doc -->
	 * <!--  end-user-doc  -->
	 * @generated
	 * @ordered
	 */
	
	private String department;

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
	 */
	public Reader(){
		super();
	}

	public Reader(int id, String name, String no, String age, String sex, String department, String telNumber) {
		this.id = id;
		this.name = name;
		this.no = no;
		this.age = age;
		this.sex = sex;
		this.department = department;
		this.telNumber = telNumber;
	}

	public Reader(String name, String no, String age, String sex, String department, String telNumber) {
		this.name = name;
		this.no = no;
		this.age = age;
		this.sex = sex;
		this.department = department;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	@Override
	public String toString() {
		return "Reader{" +
				"id=" + id +
				", name='" + name + '\'' +
				", no='" + no + '\'' +
				", age='" + age + '\'' +
				", sex='" + sex + '\'' +
				", department='" + department + '\'' +
				", telNumber='" + telNumber + '\'' +
				'}';
	}

	@Override
	public int compareTo(Reader o) {
		// TODO Auto-generated method stub
		return this.id - o.id;
	}
}

