/*
 *   #12. PositionDAO.java
 *   - 데이터베이스 액션 처리 클래스
 *   - 직위 데이터 CRUD 액션
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

public class PositionDAO implements IPositionDAO
{
	DataSource dataSource;
	
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	
	// 전체 Postion 리시트 뽑는 메소드
	@Override
	public ArrayList<Position> list() throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		ArrayList<Position> lists = new ArrayList<Position>();
		
		String sql = "SELECT POSITIONID, POSITIONNAME, MINBASICPAY, DELCHECK "
				+   " FROM POSITIONVIEW"
				+   " ORDER BY POSITIONID";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Position position = new Position();
			
			position.setPositionId(rs.getString("POSITIONID"));
			position.setPositionName(rs.getString("POSITIONNAME"));
			position.setMinBasicPay(rs.getInt("MINBASICPAY"));
			position.setDelCheck(rs.getInt("DELCHECK"));
			
			lists.add(position);
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return lists;
	}
	
	// Position 추가하는 메소드
	@Override
	public int add(Position position) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "INSERT INTO POSITION(POSITIONID, POSITIONNAME, MINBASICPAY) "
				+   " VALUES(POSITIONSEQ.NEXTVAL, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, position.getPositionName());
		pstmt.setInt(2, position.getMinBasicPay());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// positionId 에 해당하는 position 삭제하는 메소드
	@Override
	public int remove(String positionId) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE "
				+   " FROM POSITION"
				+   " WHERE POSITIONID=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(positionId));
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// Position 수정 메소드
	@Override
	public int modify(Position position) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "UPDATE POSITION "
				+   " SET POSITIONNAME=?, MINBASICPAY=? "
				+   " WHERE POSITIONID=?";
		
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, position.getPositionName());
		pstmt.setInt(2, position.getMinBasicPay());
		pstmt.setInt(3, Integer.parseInt(position.getPositionId()));
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	
	// positionId로 position 검색하는 메소드
	@Override
	public Position searchId(String positionId) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT POSITIONID, POSITIONNAME, MINBASICPAY, DELCHECK "
				+ "   FROM POSITIONVIEW "
				+ "   WHERE POSITIONID = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, positionId);
		
		ResultSet rs = pstmt.executeQuery();
		Position position = new Position();
		
		if(rs.next())
		{	
			position.setPositionId(rs.getString(1));
			position.setPositionName(rs.getString(2));
			position.setMinBasicPay(rs.getInt(3));
			position.setDelCheck(rs.getInt(4));
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return position;
	}
}
