package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.entities.Departament;
import model.entities.Seller;

public class SellerDAO implements DAO<Seller> {
	
	private Connection conn;
	
	public SellerDAO(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller t) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Seller t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	    	String sql = "SELECT seller.*,department.Name as DepName "
	    		    +"FROM seller INNER JOIN department "
	    		    +"ON seller.DepartmentId = department.Id "
	    		    +"WHERE seller.Id = ?";
	    	ps = conn.prepareStatement(sql);
	    	ps.setInt(1, id);
	    	rs = ps.executeQuery();
	    	if(rs.next()) {
	    	Departament department = instantiateDepartment(rs);
	    	return instantiateSeller(rs, id, department);
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
    	department.setId(rs.getInt("DepartmentId"));
    	department.setName(rs.getString("DepName"));
    	return department;
	}

	private Seller instantiateSeller(ResultSet rs, int id, Departament department) throws SQLException {
		Date niver = rs.getDate("BirthDate");
    	Double salario = rs.getDouble("BaseSalary");
    	String email = rs.getString("email");
    	String nome = rs.getString("name");
    	return new Seller(id, nome, email, niver, salario, department);
	}
	
	public List<Seller> findByDepartment(Departament department){
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Seller> lista = new ArrayList<>();
		String querySql = "SELECT seller.*, department.Name as DepName "
				          +"from seller INNER JOIN department "
				          +"WHERE DepartmentId = ?";
		try {
			ps = conn.prepareStatement(querySql);
			ps.setInt(1, department.getId());
			rs = ps.executeQuery();
			while(rs.next()) {
			Seller s = instantiateSeller(rs, rs.getInt("Id"), department);
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
	
	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Seller> lista = new ArrayList<>();
		String querySql = "SELECT seller.*, department.Name as DepName "
		          +"from seller INNER JOIN department ";
		try {
			ps = conn.prepareStatement(querySql);
			rs = ps.executeQuery();
			Map<Integer, Departament> dps = new HashMap<>();
			while(rs.next()) {
			if(dps.get(rs.getInt("DepartmentId"))==null) {
				dps.put(rs.getInt("DepartmentId"), instantiateDepartment(rs));
			}
			Seller s = instantiateSeller(rs, rs.getInt("Id"), dps.get(rs.getInt("DepartmentId")));
				dps.put(rs.getInt("DepartmentId"), instantiateDepartment(rs));
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

}
