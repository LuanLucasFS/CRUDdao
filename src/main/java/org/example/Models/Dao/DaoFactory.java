package org.example.Models.Dao;

import org.example.DbConnection.DbConnection;
import org.example.Models.Dao.impl.ContatoDao;

public class DaoFactory {
    public static IContatoDao createContatoDao(){
        return new ContatoDao((DbConnection.getConnection()));
    }
}
