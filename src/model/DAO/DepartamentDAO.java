package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		PreparedStatement ps = null;
		String inserir = "INSERT INTO department "
				         +"(Id, Name) "
				         +"VALUES(?, ?)";
		try {
			ps = conn.prepareStatement(inserir);
			ps.setInt(1, t.getId());
			ps.setString(2, t.getName());
			if(findById(t.getId())==null) {
				if((t.getId()!=0) && t.getName()!=null) {
				ps.execute();
				}else {
					throw new DbException("Dados Faltando!");
				}
			}
			else {
				throw new DbException("Id já cadastrado!");
			}
		}
			catch(SQLException e) {
				throw new DbException(e.getMessage());
			}catch(DbException e) {
				System.out.println(e.getMessage());
			}finally {
				DB.closeStatement(ps);
			}
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
	    String sql = "SELECT * from Department where "
	    		     +"id = ?";
	    try {
	    	ps = conn.prepareStatement(sql);
	    	ps.setInt(1, id);
	    	rs = ps.executeQuery();
	    	if(rs.next()) {
	    	return instantiateDepartment(rs);
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
	
	private Departament instantiateDepartment(ResultSet rs) throws SQLException {
		Departament department = new Departament();
    	department.setId(rs.getInt("Id"));
    	department.setName(rs.getString("Name"));
    	return department;
	}

	@Override
	public List<Departament> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Departament> lista = new ArrayList<>();
		String querySql = "SELECT * from department";
		try {
			ps = conn.prepareStatement(querySql);
			rs = ps.executeQuery();
			while(rs.next()) {
			Departament s = instantiateDepartment(rs);
			lista.add(s);	
			}
            return lista;
		}
		catch(SQLException e) {
	    	throw new DbException(e.getMessage());
		}
		finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
		}
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

}