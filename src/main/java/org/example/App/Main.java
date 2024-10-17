package org.example.App;

import org.example.Entities.Contato;
import org.example.Models.Dao.DaoFactory;
import org.example.Models.Dao.IContatoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        /*
        IContatoDao contatoDao = DaoFactory.createContatoDao();

        contatoDao.insert(new Contato("John", "John@gmail.com", "16 99293-6518"));
        //contatoDao.deleteById(2);
        Contato contato = new Contato();
        Scanner scanner = new Scanner(System.in);
        String hi = scanner.nextLine();
        //contato = contatoDao.findById(1);
        //contato.setNome("Johnathan");
        //contatoDao.update(contato);
        List<Contato> contatos = contatoDao.findAll();
        for(Contato contatoList : contatos){
            System.out.println(contatoList);
        }*/

        AppGUI gui = new AppGUI();
    }
}