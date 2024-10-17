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
        IContatoDao contatoDao = DaoFactory.createContatoDao();
        Contato contato;
        String confirma;
        boolean isRunning = true;
        while(isRunning) {
            Scanner scanner = new Scanner(System.in);
            String resp= scanner.nextLine();
            String[] splitResp = resp.split(" ");

            switch (splitResp[0].toLowerCase()){
                case "help":
                    System.out.println("""
                            Comandos: \
                            
                            contato add nome=? email=? telefone=?                   / Cria um novo contato sendo o '?' um valor que deve ser inserido \
                            
                            contato update nome=? email=? telefone=? where id=?     / Altera um contato existente sendo o ? um valor que deve ser inserido, caso queira deixar em branco, usar o ? \
                            
                            contato remove where id=?                               / Deleta um contato com id especifico\
                            
                            contato findbyid id=?                                   / Realiza uma busca por id\
                            
                            contato findall                                         / Mostra todos os contatos\
                            """);
                    break;
                case "contato":
                    switch (splitResp[1]){
                        case "add":
                            contato = new Contato();
                            contato.setNome(splitResp[2].replaceAll("(?i)nome=", ""));
                            contato.setEmail(splitResp[3].replaceAll("(?i)email=", ""));
                            contato.setTelefone(splitResp[4].replaceAll("(?i)telefone=", ""));
                            System.out.println(contato);
                            System.out.println("""
                                    Inserir o contato no banco de dados?\
                                    
                                    S-Sim/N-Nao""");
                            confirma = scanner.nextLine().toLowerCase();
                            if(confirma.equals("s")){
                                contatoDao.insert(contato);
                            }
                            break;
                        case "update":
                            contato = contatoDao.findById(Integer.parseInt(splitResp[6].replaceAll("(?i)id=","")));
                            if(!splitResp[2].replaceAll("(?i)nome=", "").equals("?")) {
                                contato.setNome(splitResp[2].replaceAll("(?i)nome=", ""));
                            }
                            if(!splitResp[3].replaceAll("(?i)email=", "").equals("?")) {
                                contato.setEmail(splitResp[3].replaceAll("(?i)email=", ""));
                            }
                            if(!splitResp[4].replaceAll("(?i)telefone=", "").equals("?")) {
                                contato.setTelefone(splitResp[4].replaceAll("(?i)telefone=", ""));
                            }
                            System.out.println(contato);
                            System.out.println("""
                                    Inserir o contato no banco de dados?\
                                    
                                    S-Sim/N-Nao""");
                            confirma = scanner.nextLine().toLowerCase();
                            if(confirma.equals("s")){
                                contatoDao.update(contato);
                            }
                            break;
                        case "remove":
                            contato=contatoDao.findById(Integer.parseInt(splitResp[2].replaceAll("(?i)id=","")));
                            System.out.println("Deseja remover o seguinte contato?");
                            System.out.println(contato);
                            System.out.println("S-Sim/N-Nao");
                            confirma = scanner.nextLine().toLowerCase();
                            if(confirma.equals("s")){
                                contatoDao.deleteById(contato.getId());
                            }
                            System.out.println();
                            break;
                        case "findbyid":
                            System.out.println(contatoDao.findById(Integer.parseInt(splitResp[2].replaceAll("(?i)id=",""))));
                            break;
                        case "findall":
                            List<Contato> contatos = contatoDao.findAll();

                            for(Contato c : contatos){
                                System.out.println(c);
                            }
                            break;
                        default:
                            System.out.println("Comando não reconhecido, favor usar o help para ver todos os comandos.");
                            break;
                    }
                    break;
                default:
                    System.out.println("Comando não reconhecido, favor usar o help para ver todos os comandos.");
                    break;
            }

        }

        AppGUI gui = new AppGUI();

    }
}
