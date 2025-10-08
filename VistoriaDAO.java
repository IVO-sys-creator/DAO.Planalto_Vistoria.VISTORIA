package dao;

import model.Vistoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import DB.Conexao;

public class VistoriaDAO {
	
	public void salvar(Vistoria vistoria) {
		String sql = "INSERT INTO vistoria(Id_Vistoria, Id_Funcionarios, Id_Agendamento, Data_Vistoria, Itens_Verificados, Observacao) VALUES (?, ?, ?, ?, ?, ? )";
		
		 try (Connection conn = Conexao.conectar();
			  PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			 stmt.setInt(1, vistoria.getIdVistoria());
			 stmt.setDate(2, vistoria.getDataVistoria());
			 stmt.setString(3, vistoria.getItensVerificados());
			 stmt.setString(4, vistoria.getObservacao());
			 stmt.setInt(5, vistoria.getIdAgendamento());
			 stmt.setInt(6, vistoria.getIdFuncionario());
			 
			 stmt.executeUpdate();
			 
			 try (ResultSet rs = stmt.getGeneratedKeys()){
				 if (rs.next()) {
					 vistoria.setIdVistoria(rs.getInt(1));
				 }
			 }
			 
			 System.out.println("Vistoria salva com sucesso. Id Gerado: " + vistoria.getIdVistoria());
			 
		} catch (SQLException e) {
			System.out.println("Erro ao salvar vistoria: " + e.getMessage());
		}
	}
	
public List<Vistoria> listar(){
	List<Vistoria> vistorias = new ArrayList<>();
	String sql = "SELECT * FROM vistoria";
	
	try (Connection conn = Conexao.conectar();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql)){
		
		while (rs.next()) {
			Vistoria v = new Vistoria(
					rs.getInt("idVistoria"),
					rs.getDate("data_vistoria"),
					rs.getString("itens_Verificados"),
					rs.getString("observacao"),
					rs.getInt("id_Agendamento"),
					rs.getInt("id_Funcionarios")
					);
			vistorias.add(v);
		}
	}catch(SQLException e) {
		System.out.println("Erro ao lista vistorias: " + e.getMessage());
	}
	
	return vistorias;
}

public void atualizar(Vistoria vistoria) {
	String sql = "UPDATE vistoria SET data_vistoria=?, itens_verificados=?, observacao=?, id_Agendamento=?, id_Funcionarios=? WHERE id_Vistoria=?";
	
	try(Connection conn = Conexao.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
				 stmt.setInt(1, vistoria.getIdVistoria());
				 stmt.setDate(2, vistoria.getDataVistoria());
				 stmt.setString(3, vistoria.getItensVerificados());
				 stmt.setString(4, vistoria.getObservacao());
				 stmt.setInt(5, vistoria.getIdAgendamento());
				 stmt.setInt(6, vistoria.getIdFuncionario());
				 
				 stmt.executeUpdate();
				 System.out.println("Vistoria atualizada.");
				 
			} catch (SQLException e) {
				System.out.println("Erro ao atualizar a vistoria: " + e.getMessage());
			}
}

public void excluir(int idVistoria) {
	String sql = "DELETE FROM vistoria WHERE id_Vistoria=?";
	
	try (Connection conn = Conexao.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
		
		stmt.setInt(1, idVistoria);
		stmt.executeUpdate();
		System.out.println("Vistoria excluída com sucesso: ");
		
	} catch (SQLException e) {
		System.out.println("Erro ao excluir vistoria. " + e.getMessage());
   }
}
	
	public Vistoria buscarPorId(int idVistoria) {
	    String sql = "SELECT * FROM vistoria WHERE id_Vistoria = ?";
	    Vistoria vistoria = null;

	    try (Connection conn = Conexao.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        
	        stmt.setInt(1, idVistoria);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                vistoria = new Vistoria(
	                        rs.getInt("id_Vistoria"),
	                        rs.getDate("data_vistoria"),
	                        rs.getString("itens_verificados"),
	                        rs.getString("observacoes"),
	                        rs.getInt("id_Agendamento"),
	                        rs.getInt("id_Funcionarios")
	                );
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("Erro ao buscar vistoria: " + e.getMessage());
	    }

	    return vistoria;
  }
}


