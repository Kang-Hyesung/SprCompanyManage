/*
 *   #10. DepartmentDAO.java
 *   - 데이터베이스 액션 처리 클래스
 *   - 부서 데이터 CRUD 액션
 *     (Create / Read / Update / Delete)
 *   - Connection 객체에 대한 의존성 주입을 위한 준비
 *     -> 인터페이스 형태의 속성 구성(DataSource)
 *     -> Setter 구성
 */

package com.test.mvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

public class DepartmentDAO implements IDepartmentDAO
{
	// 인퍼에이스 자료형을 속성으로 구성
	DataSource datasource;
	
	// setter 구성
	public void setDataSource(DataSource datasource)
	{
		this.datasource = datasource;
	}
	
	// 부서 전체 리스트
	@Override
	public ArrayList<Department> list() throws SQLException
	{
		ArrayList<Department> lists = new ArrayList<Department>();
		Connection conn = datasource.getConnection();
		
		String sql = "SELECT DEPARTMENTID, DEPARTMENTNAME, DELCHECK "
				+ "   FROM DEPARTMENTVIEW "
				+ "   ORDER BY DEPARTMENTID";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Department department = new Department();
			
			department.setDepartmentId(rs.getString(1));
			department.setDepartmentName(rs.getString(2));
			department.setDelCheck(rs.getInt(3));
			
			lists.add(department);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return lists;
	}
	
	// 부서 데이터 등록(입력, 추가)
	@Override
	public int add(Department department) throws SQLException
	{
		int result = 0;
		
		Connection conn = datasource.getConnection();
		
		String sql = "INSERT INTO DEPARTMENT(DEPARTMENTID, DEPARTMENTNAME) "
				+ "   VALUES(DEPARTMENTSEQ.NEXTVAL, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, department.getDepartmentName());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 부서데이터 삭제
	@Override
	public int remove(String departmentId) throws SQLException
	{
		int result = 0;
		Connection conn = datasource.getConnection();
		
		String sql = "DELETE "
				+ "   FROM DEPARTMENT "
				+ "   WHERE DEPARTMENTID = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(departmentId));
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// 부서 데이터 변경
	@Override
	public int modify(Department department) throws SQLException
	{
		int result = 0;
		
		Connection conn = datasource.getConnection();
		
		String sql = "UPDATE DEPARTMENT "
				+ "   SET DEPARTMENTNAME=? "
				+ "   WHERE DEPARTMENTID=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, department.getDepartmentName());
		pstmt.setInt(2, Integer.parseInt(department.getDepartmentId()));
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 부서 번호에 맞는 행 검색
	@Override
	public Department searchId(String departmentId) throws SQLException
	{
		Connection conn = datasource.getConnection();
		
		Department department = new Department();
		
		String sql = "SELECT DEPARTMENTID, DEPARTMENTNAME, DELCHECK "
				+ "   FROM DEPARTMENTVIEW "
				+ "   WHERE DEPARTMENTID = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, departmentId);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
		{
			department.setDepartmentId(rs.getString(1));
			department.setDepartmentName(rs.getString(2));
			department.setDelCheck(rs.getInt(3));
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return department;
	}

}
