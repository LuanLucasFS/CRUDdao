package org.example.App;

import org.example.Entities.Contato;
import org.example.Models.Dao.DaoFactory;
import org.example.Models.Dao.IContatoDao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;
import java.awt.GridLayout;

public class AppGUI {
    IContatoDao contatoDao = DaoFactory.createContatoDao();

    JFrame mainFrame;
    JPanel p1_mainFrame, p2_mainFrame;
    JTable table_mainFrame;
    JButton btnCriar_mainFrame, btnRemover_mainFrame, btnAtualizar_mainFrame, btnAlterar_mainFrame;
    DefaultTableModel tableModel;
    // Metodo para atualização da table_mainFrame
    public void updateTable(){
        String[] Colunas = {"ID", "NOME", "EMAIL", "TELEFONE"};
        List<Contato> contatos = contatoDao.findAll();
        String[][] Dados = listToArray(contatos);
        tableModel = new DefaultTableModel(Dados, Colunas);
        table_mainFrame.setModel(tableModel);

    }

    // Metodo para conversao de List para array, 
    // tive que fazer pra colocar os dados do db
    // dentro da tabela. Depois vejo uma forma de arrumar.
    public String[][] listToArray(List<Contato> contatos){
        String[][] Dados = new String[contatos.size()][4];

        for (int i = 0; i < contatos.size(); i++) {
            Contato contato = contatos.get(i);
            Dados[i][0] = String.valueOf(contato.getId());
            Dados[i][1] = contato.getNome();
            Dados[i][2] = contato.getEmail();
            Dados[i][3] = contato.getTelefone();
        }
        return Dados;
    }

    // Criação do frame principal
    public AppGUI(){
        mainFrame = new JFrame();
        mainFrame.setSize(500, 500);
        mainFrame.setLayout(new GridLayout(2,1));
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        p1_mainFrame = new JPanel();
        p1_mainFrame.setPreferredSize(new Dimension(500, 100));
        btnCriar_mainFrame = new JButton();
        btnCriar_mainFrame.setText("Adicionar contato");
        btnCriar_mainFrame.setPreferredSize(new Dimension(100, 24));
        btnCriar_mainFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenCriarFrame();
            }
        });

        btnRemover_mainFrame = new JButton("Remover");
        btnRemover_mainFrame.setPreferredSize(new Dimension(100, 24));
        btnRemover_mainFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenRemoverFrame();
            }
        });
        btnAlterar_mainFrame = new JButton("Alterar");
        btnAlterar_mainFrame.setPreferredSize(new Dimension(100, 24));
        btnAlterar_mainFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenAlterarFrame();
            }
        });

        btnAtualizar_mainFrame = new JButton("Atualizar");
        btnAtualizar_mainFrame.setPreferredSize(new Dimension(100, 24));
        btnAtualizar_mainFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTable();
            }
        });
        p1_mainFrame.setLayout(new GridLayout(4,3, 3,3));
        p1_mainFrame.add(btnCriar_mainFrame);
        p1_mainFrame.add(btnRemover_mainFrame);
        p1_mainFrame.add(btnAtualizar_mainFrame);
        p1_mainFrame.add(btnAlterar_mainFrame);
        
        p2_mainFrame = new JPanel();

        table_mainFrame = new JTable();
        updateTable();
        table_mainFrame.setPreferredScrollableViewportSize(new Dimension(400, 190));
        
        JScrollPane sp = new JScrollPane( table_mainFrame );
        p2_mainFrame.add(sp);
        sp.repaint();
        //p2_mainFrame.add(table_mainFrame);
        mainFrame.add(p1_mainFrame);
        mainFrame.add(p2_mainFrame);

        mainFrame.setVisible(true);
    }



    JFrame criarFrame;
    JPanel p1_criarFrame, p2_criarFrame;
    JLabel nomeLbl_criarFrame, emailLbl_criarFrame, telefoneLbl_criarFrame;
    JTextField nomeTxtField_criarFrame, emailTxtField_criarFrame, telefoneTxtField_criarFrame;
    JButton confirmaBtn_criarFrame, cancelaBtn_criarFrame;
    // Criação do frame de inserção de contatos
    public void OpenCriarFrame(){
        criarFrame = new JFrame();
        criarFrame.setSize(200, 300);
        criarFrame.setLayout(new GridLayout(2,1));
        criarFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        p1_criarFrame = new JPanel();
        nomeLbl_criarFrame = new JLabel("Nome:");
        nomeLbl_criarFrame.setPreferredSize(new Dimension( 70, 24 ) );
        nomeLbl_criarFrame.setLabelFor(nomeTxtField_criarFrame);
        nomeTxtField_criarFrame = new JTextField();
        nomeTxtField_criarFrame.setPreferredSize( new Dimension( 100, 24 ) );
        emailLbl_criarFrame = new JLabel("Email:");
        emailLbl_criarFrame.setPreferredSize(new Dimension( 70, 24 ) );
        emailTxtField_criarFrame = new JTextField();
        emailTxtField_criarFrame.setPreferredSize( new Dimension( 100, 24 ) );
        telefoneLbl_criarFrame = new JLabel("Telefone:");
        telefoneLbl_criarFrame.setPreferredSize(new Dimension( 70, 24 ) );
        telefoneTxtField_criarFrame = new JTextField();
        telefoneTxtField_criarFrame.setPreferredSize( new Dimension( 100, 24 ) );

        p2_criarFrame = new JPanel();
        confirmaBtn_criarFrame = new JButton("Confirmar");
        confirmaBtn_criarFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Verifica se algum dos valores passado é nulo
                // caso seja, mostra um popup de erro
                if(nomeTxtField_criarFrame.getText().isEmpty() || emailTxtField_criarFrame.getText().isEmpty() || telefoneTxtField_criarFrame.getText().isEmpty()){
                    JOptionPane.showMessageDialog(criarFrame,
                            "Preencha todos os campos",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else{
                    Contato contato = new Contato();
                    contato.setNome(nomeTxtField_criarFrame.getText());
                    contato.setEmail(emailTxtField_criarFrame.getText());
                    contato.setTelefone(telefoneTxtField_criarFrame.getText());
                    contatoDao.insert(contato);
                    updateTable();
                    criarFrame.dispose();
                }
            }
        });

        cancelaBtn_criarFrame = new JButton("Cancelar");
        cancelaBtn_criarFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                criarFrame.dispose();
            }
        });
        p1_criarFrame.add(nomeLbl_criarFrame);
        p1_criarFrame.add(nomeTxtField_criarFrame);
        p1_criarFrame.add(emailLbl_criarFrame);
        p1_criarFrame.add(emailTxtField_criarFrame);
        p1_criarFrame.add(telefoneLbl_criarFrame);
        p1_criarFrame.add(telefoneTxtField_criarFrame);
        p2_criarFrame.add(confirmaBtn_criarFrame);
        p2_criarFrame.add(cancelaBtn_criarFrame);

        criarFrame.add(p1_criarFrame);
        criarFrame.add(p2_criarFrame);
        criarFrame.setVisible(true);
    }

    JFrame alterarFrame;
    JPanel p1_alterarFrame, p2_alterarFrame;
    JLabel idLbl_alterarFrame, nomeLbl_alterarFrame, emailLbl_alterarFrame, telefoneLbl_alterarFrame;
    JTextField idTxtField_alterarFrame, nomeTxtField_alterarFrame, emailTxtField_alterarFrame, telefoneTxtField_alterarFrame;
    JButton confirmaBtn_alterarFrame, cancelaBtn_alterarFrame;

    // Criação do frame para alteração de um contato
    // Tenho que arrumar pra puxar o contato diretamente pelo id
    public void OpenAlterarFrame(){
        alterarFrame = new JFrame();
        alterarFrame.setSize(200, 300);
        alterarFrame.setLayout(new GridLayout(2,1));
        alterarFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        p1_alterarFrame = new JPanel();
        idLbl_alterarFrame = new JLabel("ID: ");
        idLbl_alterarFrame.setPreferredSize(new Dimension( 70, 24 ) );
        idTxtField_alterarFrame = new JTextField();
        idTxtField_alterarFrame.setPreferredSize( new Dimension( 100, 24 ) );
        nomeLbl_alterarFrame = new JLabel("Nome:");
        nomeLbl_alterarFrame.setPreferredSize(new Dimension( 70, 24 ) );
        nomeTxtField_alterarFrame = new JTextField();
        nomeTxtField_alterarFrame.setPreferredSize( new Dimension( 100, 24 ) );
        emailLbl_alterarFrame = new JLabel("Email:");
        emailLbl_alterarFrame.setPreferredSize(new Dimension( 70, 24 ) );
        emailTxtField_alterarFrame = new JTextField();
        emailTxtField_alterarFrame.setPreferredSize( new Dimension( 100, 24 ) );
        telefoneLbl_alterarFrame = new JLabel("Telefone:");
        telefoneLbl_alterarFrame.setPreferredSize(new Dimension( 70, 24 ) );
        telefoneTxtField_alterarFrame = new JTextField();
        telefoneTxtField_alterarFrame.setPreferredSize( new Dimension( 100, 24 ) );

        p2_alterarFrame = new JPanel();
        confirmaBtn_alterarFrame = new JButton("Confirmar");
        confirmaBtn_alterarFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Verifica se o id está vazio, caso esteja mostra um popup de erro
                if(idTxtField_alterarFrame.getText().isEmpty()){
                    JOptionPane.showMessageDialog(alterarFrame,
                            "Preencha um id",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else{
                    Contato contato = contatoDao.findById(Integer.parseInt(idTxtField_alterarFrame.getText()));
                    if(!nomeTxtField_alterarFrame.getText().isEmpty()) {
                        contato.setNome(nomeTxtField_alterarFrame.getText());
                    }
                    if(!emailTxtField_alterarFrame.getText().isEmpty()) {
                        contato.setEmail(emailTxtField_alterarFrame.getText());
                    }
                    if(!telefoneTxtField_alterarFrame.getText().isEmpty()) {
                        contato.setTelefone(telefoneTxtField_alterarFrame.getText());
                    }
                    contatoDao.update(contato);
                    updateTable();
                    alterarFrame.dispose();

                }
            }
        });

        cancelaBtn_alterarFrame = new JButton("Cancelar");
        cancelaBtn_alterarFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                    alterarFrame.dispose();
            }
        });
        p1_alterarFrame.add(idLbl_alterarFrame);
        p1_alterarFrame.add(idTxtField_alterarFrame);
        p1_alterarFrame.add(nomeLbl_alterarFrame);
        p1_alterarFrame.add(nomeTxtField_alterarFrame);
        p1_alterarFrame.add(emailLbl_alterarFrame);
        p1_alterarFrame.add(emailTxtField_alterarFrame);
        p1_alterarFrame.add(telefoneLbl_alterarFrame);
        p1_alterarFrame.add(telefoneTxtField_alterarFrame);

        p2_alterarFrame.add(confirmaBtn_alterarFrame);
        p2_alterarFrame.add(cancelaBtn_alterarFrame);

        alterarFrame.add(p1_alterarFrame);
        alterarFrame.add(p2_alterarFrame);
        alterarFrame.setVisible(true);
    }

    JFrame removerFrame;
    JLabel idLbl_removerFrame;
    JTextField idTxtField_removerFrame;
    JButton confirmaBtn_removerFrame, cancelaBtn_removerFrame;
    // Criação do frame de remoção por id
    public void OpenRemoverFrame(){
        removerFrame = new JFrame();
        removerFrame.setLayout(new GridLayout(2,2));
        removerFrame.setSize(200, 100);
        idLbl_removerFrame = new JLabel("Id:");
        idLbl_removerFrame.setPreferredSize(new Dimension( 70, 24 ) );
        idTxtField_removerFrame = new JTextField();
        idTxtField_removerFrame.setPreferredSize( new Dimension( 100, 24 ) );
        confirmaBtn_removerFrame = new JButton("Confirmar");
        confirmaBtn_removerFrame.setPreferredSize(new Dimension( 70, 24 ) );
        confirmaBtn_removerFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contatoDao.deleteById(Integer.parseInt(idTxtField_removerFrame.getText()));
                updateTable();
                removerFrame.dispose();
            }
        });
        cancelaBtn_removerFrame = new JButton("Cancela");
        cancelaBtn_removerFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removerFrame.dispose();
            }
        });
        removerFrame.add(idLbl_removerFrame);
        removerFrame.add(idTxtField_removerFrame);
        removerFrame.add(confirmaBtn_removerFrame);
        removerFrame.add(cancelaBtn_removerFrame);
        removerFrame.setVisible(true);
    }
}
