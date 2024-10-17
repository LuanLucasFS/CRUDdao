package org.example.Models.Dao.impl;

import org.example.DbConnection.DbConnection;
import org.example.DbConnection.DbException;
import org.example.Entities.Contato;
import org.example.Models.Dao.IContatoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContatoDao implements IContatoDao {
    private Connection connection;

    public ContatoDao(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Contato contato){
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement("INSERT INTO clientes (Nome, Email, Telefone) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, contato.getNome());
            statement.setString(2, contato.getEmail());
            statement.setString(3, contato.getTelefone());

            int rA = statement.executeUpdate();

            if(rA>0){
                ResultSet rs = statement.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    contato.setId(id);
                }
                DbConnection.closeResultSet(rs);
            } else {
                throw new DbException("No Rows Affected!");
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DbConnection.closeStatement(statement);
        }
    }
    @Override
    public void update(Contato contato){
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement("UPDATE clientes SET nome = ?, Email = ?, Telefone = ? WHERE Id = ?");
            statement.setString(1, contato.getNome());
            statement.setString(2, contato.getEmail());
            statement.setString(3, contato.getTelefone());
            statement.setInt(4, contato.getId());

            statement.executeUpdate();

        } catch(SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DbConnection.closeStatement(statement);
        }
    }

    @Override
    public void deleteById(Integer id){
        PreparedStatement statement = null;

        try{
            statement = connection.prepareStatement("DELETE FROM clientes WHERE Id = ?");
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DbConnection.closeStatement(statement);
        }
    }

    @Override
    public Contato findById(Integer id){
        PreparedStatement statement = null;
        ResultSet rs = null;

        String baseQuery = "SELECT * FROM clientes WHERE Id = ?";

        try {
            statement = connection.prepareStatement(baseQuery);
            statement.setInt(1, id);
            rs = statement.executeQuery();

            if(rs.next()){
                Contato contato = instantiateContato(rs);
                return  contato;
            }

            return null;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DbConnection.closeStatement(statement);
            DbConnection.closeResultSet(rs);
        }
    }

    @Override
    public ArrayList<Contato> findAll(){
        PreparedStatement statement = null;
        ResultSet rs = null;

        try{
            statement = connection.prepareStatement("SELECT * FROM clientes ORDER BY id");
            rs = statement.executeQuery();

            ArrayList<Contato> contatos = new ArrayList<>();
            while(rs.next()) {
                Contato contato = instantiateContato(rs);
                contatos.add(contato);
            }
            return contatos;
        } catch(SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DbConnection.closeStatement(statement);
            DbConnection.closeResultSet(rs);
        }
    }

    private Contato instantiateContato(ResultSet rs) throws SQLException {
        Contato contato = new Contato();
        contato.setId(rs.getInt("id"));
        contato.setNome(rs.getString("Nome"));
        contato.setEmail(rs.getString("Email"));
        contato.setTelefone(rs.getString("Telefone"));
        return contato;
    }
}
