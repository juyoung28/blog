package com.cos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cos.dto.BoardVO;
import com.cos.util.DBManager;

public class BoardDAO {
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public int insert(BoardVO board) {
		String SQL = "INSERT INTO board_2(title, content, writedate, id,readcount) VALUE(?,?,now(),?,0)";
		Connection conn= DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getId());
			pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return -1;
	}
	public List<BoardVO> select_paging(int pageNum){
		String SQL = "SELECT * FROM board_2 ORDER BY num DESC limit ?, 3";
		Connection conn = DBManager.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, pageNum);
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setWritedate(rs.getString("writedate"));
				board.setReadcount(rs.getInt("readcount"));
				list.add(board);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return null;
	}
	
	// nextPage �쑀臾�
	public int nextPage(int pageNum) {
		String SQL = "SELECT * FROM board_2 ORDER BY num DESC limit ?, 3";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, pageNum);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	public BoardVO select(int num) {
		String SQL = "SELECT * FROM board_2 where num = ?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				BoardVO board = new BoardVO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setTitle(rs.getString("title"));
				board.setContent(rs.getString("content"));
				board.setWritedate(rs.getString("writedate"));
				return board;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return null;
	}
	
	public int delete(int num) {
		String SQL = "DELETE FROM board_2 WHERE num = ?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, num); 
			pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return -1;
	}
	
	public int update(BoardVO board) {
		String SQL = "UPDATE board_2 SET title = ?, content = ? WHERE num =?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setInt(3, board.getNum());
			pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return -1;
	}
	
	public int checkId(int num, String sessionId) {
		String SQL = "SELECT * FROM board_2 WHERE id = ? and num = ?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, sessionId);
			pstmt.setInt(2, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return -1;
	}
	
	public int readcount(int num) {
		String SQL = "UPDATE board_2 SET readcount = readcount+1 WHERE num =?";
		Connection conn = DBManager.getConnection();
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setInt(1, num);			
			pstmt.executeUpdate();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt);
		}
		
		return -1;
	}
	
	public List<BoardVO> hotpost(){
		String SQL = "SELECT num, title, readcount FROM board_2 ORDER BY readcount DESC limit 3";
		Connection conn = DBManager.getConnection();
		
		try {
			pstmt = conn.prepareStatement(SQL);			
			rs = pstmt.executeQuery();
			List<BoardVO> list = new ArrayList<>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setNum(rs.getInt("num"));				
				board.setTitle(rs.getString("title"));				
				board.setReadcount(rs.getInt("readcount"));
				list.add(board);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(conn, pstmt, rs);
		}
		return null;
	}
}

















