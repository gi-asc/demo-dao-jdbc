package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
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
		PreparedStatement ps = null;
		String inserir = "INSERT INTO seller "
				         +"(Id, Name, email, BirthDate, BaseSalary, DepartmentId) "
				         +"VALUES(?, ?, ?, ?, ?, ?)";
		try {
			ps = conn.prepareStatement(inserir);
			ps.setInt(1, t.getId());
			ps.setString(2, t.getNome());
			ps.setDate(3, t.getAniversario());
			ps.setDouble(4, t.getSalario());
			ps.setInt(5, t.getDepartament().getId());
			if(findById(t.getId())==null) {
				if((t.getId()!=0) && t.getNome()!=null && t.getAniversario()!=null && t.getSalario()!=0 && t.getDepartament()!=null && t.getEmail()!=null) {
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
	public void update(Seller t) {
		PreparedStatement ps = null;
		String alterar = "UPDATE seller "
				         +"SET name=?, email=?, BirthDate=?, BaseSalary=? DepartmentId=?  "
				         +"WHERE id=?";
		try {
			ps = conn.prepareStatement(alterar);
			ps.setInt(5, t.getId());
			ps.setString(1, t.getNome());
			ps.setDate(2, t.getAniversario());
			ps.setDouble(3, t.getSalario());
			ps.setInt(4, t.getDepartament().getId());
			if(findById(t.getId())!=null) {
				if((t.getId()!=0) && t.getNome()!=null && t.getAniversario()!=null && t.getSalario()!=0 && t.getDepartament()!=null && t.getEmail()!=null) {
				ps.execute();
				}else {
					throw new DbException("Dados Faltando!");
				}
			}
			else {
				throw new DbException("Id Inexistente.");
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
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		String deletar = "DELETE FROM seller "
				         +"WHERE id=?";
		try {
			ps = conn.prepareStatement(deletar);
			ps.setInt(1, id);
			if(findById(id)!=null) {
				ps.execute();
				}else {
					throw new DbException("Usuario Inexistente");
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
