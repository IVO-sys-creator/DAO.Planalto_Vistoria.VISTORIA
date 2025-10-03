package dao;

import model.Vistoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import DB.Conexao;

public class VistoriaDAO {
	
	public void salvar(Vistoria vistoria) {
		String sql = "UPDATE vistoria SET dataVistoria=?, resultado=?, statusPagamento=?, observacoes=?, idAgendamento=?, idFuncionario=? "
                + "WHERE idVistoria=?";
		
		 try (Connection conn = Conexao.conectar();
			  PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			 stmt.setInt(1, vistoria.getIdVistoria());
			 stmt.setDate(2, vistoria.getDataVistoria());
			 stmt.setString(3, vistoria.getResultado());
			 stmt.setString(4, vistoria.getStatusPagamento());
			 stmt.setString(5, vistoria.getObservacoes());
			 stmt.setInt(6, vistoria.getIdAgendamento());
			 stmt.setInt(7, vistoria.getIdFuncionario());
			 
			 stmt.executeUpdate();
			 
			 try (ResultSet rs = stmt.getGeneratedKeys()){
				 if (rs.next()) {
					 vistoria.setIdVistoria(rs.getInt(1));
				 }
			 }
			 
			 System.out.println("Vistoria salva com sucesso. Id Gerado: " + vistoria.getIdVistoria());
			 
			 System.out.println("Vistoria atualizada.");
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
					rs.getString("itensVerificados"),
					rs.getString("resultado"),
					rs.getString("status_pagamento"),
					rs.getString("observacoes"),
					rs.getInt("idPagamento"),
					rs.getInt("idFuncionario")
					);
			vistorias.add(v);
		}
	}catch(SQLException e) {
		System.out.println("Erro ao lista vistorias: " + e.getMessage());
	}
	
	return vistorias;
}

public void atualizar(Vistoria vistoria) {
	String sql = "UPDATE vistoria SET data_vistoria=?, itens_verificados=?, resultado=?, status_pagamento=?, observacoes=?, idPagamento=?, idFuncionario=? WHERE idVistoria=";
	
	try(Connection conn = Conexao.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
			
				 stmt.setInt(1, vistoria.getIdVistoria());
				 stmt.setDate(2, vistoria.getDataVistoria());
				 stmt.setString(3, vistoria.getItensVerificados());
				 stmt.setString(4, vistoria.getResultado());
				 stmt.setString(4, vistoria.getStatusPagamento());
				 stmt.setString(6, vistoria.getObservacoes());
				 stmt.setInt(7, vistoria.getIdAgendamento());
				 stmt.setInt(8, vistoria.getIdFuncionario());
				 
				 stmt.executeUpdate();
				 System.out.println("Vistoria atualizada.");
				 
			} catch (SQLException e) {
				System.out.println("Erro ao atualizar a vistoria: " + e.getMessage());
			}
}

public void excluir(int idVistoria) {
	String sql = "DELETE FROM vistoria WHERE idVistoria=?";
	
	try (Connection conn = Conexao.conectar();
			PreparedStatement stmt = conn.prepareStatement(sql)) {
		
		stmt.setInt(1, idVistoria);
		stmt.executeUpdate();
		System.out.println("Vistoria excluída com sucesso.: ");
		
	} catch (SQLException e) {
		System.out.println("Erro ao excluir vistoria: " + e.getMessage());
	}
  }
}
