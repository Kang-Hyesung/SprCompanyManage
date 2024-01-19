/*
 *   #09. EmployeeDAO.java
 *   - 데이터베이스 액션 처리 클래스
 *   - 직원 데이터 CRUD 액션
 *   - Connection 객체에 대한 의존성 주입을 위한 준비
 *     -> 인터페이스 형태의 속성 구성(dataSource)
 *     -> Setter 구성
 */

package com.test.mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class EmployeeDAO implements IEmployeeDAO
{
	
	DataSource dataSource;
	
	public void setdataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	// Employee 모든 행 뽑아내는 메소드
	@Override
	public ArrayList<Employee> list() throws SQLException
	{
		Connection conn = dataSource.getConnection();
		ArrayList<Employee> list = new ArrayList<Employee>();

		String sql = "select * from employeeview order by employeeid";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Employee employee = new Employee();
			
			employee.setEmployeeId(rs.getString(1));
			employee.setName(rs.getString(2));
			employee.setSsn(rs.getString(3));
			employee.setBirthday(rs.getString(4));
			employee.setLunar(rs.getInt(5));
			employee.setLunarName(rs.getString(6));
			employee.setTelephone(rs.getString(7));
			employee.setDepartmentId(rs.getString(8));
			employee.setDepartmentName(rs.getString(9));
			employee.setPositionId(rs.getString(10));
			employee.setPositionName(rs.getString(11));
			employee.setRegionId(rs.getString(12));
			employee.setRegionName(rs.getString(13));
			employee.setBasicPay(rs.getInt(14));
			employee.setExtraPay(rs.getInt(15));
			employee.setPay(rs.getInt(16));
			employee.setGrade(rs.getInt(17));
			
			list.add(employee);
			
		}

		rs.close();
		pstmt.close();
		conn.close();
		
		
		return list;
	}
	
	// Region 모든 행 뽑아내는 메소드
	@Override
	public ArrayList<Region> regionList() throws SQLException
	{
		Connection conn = dataSource.getConnection();
		ArrayList<Region> regionList = new ArrayList<Region>();	

		String sql = "select * from regionview";
		PreparedStatement pstmt = conn.prepareStatement(sql);
			
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Region region = new Region();
			
			region.setRegionId(rs.getString(1));
			region.setRegionName(rs.getString(2));
			region.setDelCheck(rs.getInt(3));
			
			regionList.add(region);
		}
	
		rs.close();
		pstmt.close();
		conn.close();
		
		return regionList;
	}
	
	// Department 모든 행 뽑아내는 메소드
	@Override
	public ArrayList<Department> departmentList() throws SQLException
	{
		Connection conn = dataSource.getConnection();
		ArrayList<Department> departmentList = new ArrayList<Department>();
		
		String sql = "select * from departmentview";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Department department = new Department();
			
			department.setDepartmentId(rs.getString(1));
			department.setDepartmentName(rs.getString(2));
			department.setDelCheck(rs.getInt(3));
			
			departmentList.add(department);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return departmentList;
	}
	
	// Position 모든 행 뽑아내는 메소드
	@Override
	public ArrayList<Position> positionList() throws SQLException
	{
		Connection conn = dataSource.getConnection();
		ArrayList<Position> departmentList = new ArrayList<Position>();
		
		String sql = "select * from positionview";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Position position = new Position();
			position.setPositionId(rs.getString(1));
			position.setPositionName(rs.getString(2));
			position.setMinBasicPay(rs.getInt(3));
			position.setDelCheck(rs.getInt(4));
			
			departmentList.add(position);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return departmentList;
	}
	
	// 직위 아이디에 따른 최소 기본급 확인/검색
	@Override
	public int getMinBasicPay(String positionId) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		int result = 0;
		
		String sql = "SELECT MINBASICPAY "
				+ "   FROM POSITION "
				+ "   WHERE POSITIONID=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(positionId));
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
			result = rs.getInt(1);
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 직원 데이터 추가
	@Override
	public int employeeAdd(Employee employee) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		int result = 0;
		
		String sql = "INSERT INTO EMPLOYEE(EMPLOYEEID, NAME, BIRTHDAY, LUNAR" + 
				"                    , TELEPHONE, DEPARTMENTID, POSITIONID, REGIONID" + 
				"                    , BASICPAY, EXTRAPAY, SSN1, SSN2)" + 
				" 			 VALUES(EMPLOYEESEQ.NEXTVAL, ?, TO_DATE(?, 'YYYY-MM-DD'), ?" + 
				"          , ?, ?, ?, ?, ?, ?" + 
				"          , ?, CRYPTPACK.ENCRYPT(?, ?))";
			
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, employee.getName());
		pstmt.setString(2, employee.getBirthday());
		pstmt.setInt(3, employee.getLunar());
		pstmt.setString(4, employee.getTelephone());
		pstmt.setInt(5, Integer.parseInt(employee.getDepartmentId()));
		pstmt.setInt(6, Integer.parseInt(employee.getPositionId()));
		pstmt.setInt(7, Integer.parseInt(employee.getRegionId()));
		pstmt.setInt(8, employee.getBasicPay());
		pstmt.setInt(9, employee.getExtraPay());
		pstmt.setString(10, employee.getSsn1());
		pstmt.setString(11, employee.getSsn2());
		pstmt.setString(12, employee.getSsn2());
		
		result = pstmt.executeUpdate();

		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 직원 데이터 삭제
	@Override
	public int remove(String employeeId) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		int result = 0;
		
		String sql = "DELETE "
				+ "   FROM EMPLOYEE "
				+ "   WHERE EMPLOYEEID=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(employeeId));
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	@Override
	public int modify(Employee employee) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		int result = 0;
		String sql = "UPDATE EMPLOYEE "
				+ "   SET NAME=?, BIRTHDAY = TO_DATE(?, 'YYYY-MM-DD'), LUNAR=?, TELEPHONE=?, "
				+ "       DEPARTMENTID=?, POSITIONID=?, REGIONID=?, BASICPAY=?, EXTRAPAY=?,"
				+ "       SSN1=?, SSN2=CRYPTPACK.ENCRYPT(?, ?) "
				+ "   WHERE EMPLOYEEID=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, employee.getName());
		pstmt.setString(2, employee.getBirthday());
		pstmt.setInt(3, employee.getLunar());
		pstmt.setString(4, employee.getTelephone());
		pstmt.setInt(5, Integer.parseInt(employee.getDepartmentId()));
		pstmt.setInt(6, Integer.parseInt(employee.getPositionId()));
		pstmt.setInt(7, Integer.parseInt(employee.getRegionId()));
		pstmt.setInt(8, employee.getBasicPay());
		pstmt.setInt(9, employee.getExtraPay());
		pstmt.setString(10, employee.getSsn1());
		pstmt.setString(11, employee.getSsn2());
		pstmt.setString(12, employee.getSsn2());
		pstmt.setInt(13, Integer.parseInt(employee.getEmployeeId()));
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 아이디로 직원 검색
	@Override
	public Employee searchId(String employeeId) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		String sql = "SELECT EMPLOYEEID, NAME, SSN1, TO_CHAR(BIRTHDAY, 'YYYY-MM-DD') AS BIRTHDAY, "
				+ "          LUNAR, TELEPHONE, DEPARTMENTID, POSITIONID, REGIONID, BASICPAY, EXTRAPAY "
				+ "   FROM EMPLOYEE "
				+ "   WHERE EMPLOYEEID=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, employeeId);
		
		ResultSet rs = pstmt.executeQuery();
		Employee employee = null;
		
		if(rs.next())
		{
			employee = new Employee();
			
			employee.setEmployeeId(rs.getString(1));
			employee.setName(rs.getString(2));
			employee.setSsn1(rs.getString(3));
			employee.setBirthday(rs.getString(4));
			employee.setLunar(rs.getInt(5));
			employee.setTelephone(rs.getString(6));
			employee.setDepartmentId(rs.getString(7));
			employee.setPositionId(rs.getString(8));
			employee.setRegionId(rs.getString(9));
			employee.setBasicPay(rs.getInt(10));
			employee.setExtraPay(rs.getInt(11));
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return employee;
	}
	
	// 일반 직원 로그인
	@Override
	public String login(String id, String pw) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		String sql = "SELECT NAME "
				+ "   FROM EMPLOYEE "
				+ "   WHERE EMPLOYEEID=? AND SSN2 = CRYPTPACK.ENCRYPT(?, ?)";
		
		// String result = "" 이렇게 해버리면 나중에 로그인 성공/실패를 가르는 isnull 에서 
		// 실패해도 null 이 아니라고 나오기 때문에 이런 경우에는 항상 null로 초기화 해준다. 
		String result = null;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(id));
		pstmt.setString(2, pw);
		pstmt.setString(3, pw);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
			result = rs.getString(1);
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 관리자 로그인(Grade 조건 추가)
	@Override
	public String loginAdmin(String id, String pw) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		String sql = "SELECT NAME "
				+ "   FROM EMPLOYEE "
				+ "   WHERE EMPLOYEEID = ? AND SSN2 = CRYPTPACK.ENCRYPT(?, ?) AND GRADE = 0";
		
		String result = null;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, Integer.parseInt(id));
		pstmt.setString(2, pw);
		pstmt.setString(3, pw);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
			result = rs.getString(1);
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

}
