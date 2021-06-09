package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.entities.Departament;

public class DepartamentDAO implements DAO<Departament> {
	
	private Connection conn;
	
	public DepartamentDAO(Connection conn) {
		this.setConn(conn);
	}

	@Override
	public void insert(Departament t) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Departament t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Departament findById(Integer id) {
		PreparedStatement ps = null;
	    ResultSet rs = null;
	    String sql = "select * Department where "
	    		     +"id = ?";
	    try {
	    	ps = conn.prepareStatement(sql);
	    	ps.setInt(1, id);
	    	rs = ps.executeQuery();
	    	if(rs.next()) {
	    	Departament department = new Departament();
	    	department.setId(rs.getInt("DepartmentId"));
	    	department.setName(rs.getString("DepName"));
	    	return department;
	    	}
	    	return null;
	    }
	    catch(SQLException e) {
	    	throw new DbException(e.getMessage());
	    }
	    finally {
	    	DB.closeStatement(ps);
	    	DB.closeResultSet(rs);
	    }
	}

	@Override
	public List<Departament> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}