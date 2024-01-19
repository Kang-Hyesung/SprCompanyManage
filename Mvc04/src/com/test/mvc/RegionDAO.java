/*
 *   #11. EmployeeDAO.java
 *   - 데이터베이스 액션 처리 클래스
 *   - 지역 데이터 CRUD 액션
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

public class RegionDAO implements IRegionDAO
{
	DataSource dataSource;
	
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	// Region 리스트 뽑아내는 메소드
	@Override
	public ArrayList<Region> list() throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		ArrayList<Region> lists = new ArrayList<Region>();
		
		String sql = "SELECT REGIONID, REGIONNAME, DELCHECK "
				+   " FROM REGIONVIEW "
				+   " ORDER BY REGIONID";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			Region region = new Region();
			region.setRegionId(rs.getString("REGIONID"));
			region.setRegionName(rs.getString("REGIONNAME"));
			region.setDelCheck(rs.getInt("DELCHECK"));
			
			lists.add(region);
			
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
		return lists;
	}
	
	// Region 추가하는 메소드
	@Override
	public int add(Region region) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "INSERT INTO REGION(REGIONID, REGIONNAME) "
				+   " VALUES(REGIONSEQ.NEXTVAL, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, region.getRegionName());
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// regionId에 해당하는 Region 삭제하는 메소드
	@Override
	public int remove(String regionId) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "DELETE "
				+   " FROM REGION "
				+   " WHERE REGIONID = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Integer.parseInt(regionId));
		
		result = pstmt.executeUpdate();
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	// Region 수정하는 메소드
	@Override
	public int modify(Region region) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		
		String sql = "UPDATE REGION"
				+   " SET REGIONNAME=?"
				+   " WHERE REGIONID=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, region.getRegionName());
		pstmt.setInt(2, Integer.parseInt(region.getRegionId()));
		
		result = pstmt.executeUpdate();
		
		return result;
	}
	
	// regionId 로 검색하는 메소드
	@Override
	public Region searchId(String regionId) throws SQLException
	{
		Connection conn = dataSource.getConnection();
		
		Region region = new Region();

		String sql = "SELECT REGIONID, REGIONNAME, DELCHECK "
				+ "   FROM REGIONVIEW "
				+ "   WHERE REGIONID = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, regionId);
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next())
		{
			region.setRegionId(rs.getString(1));
			region.setRegionName(rs.getString(2));
			region.setDelCheck(rs.getInt(3));
		}
		
		return region;
	}

}
