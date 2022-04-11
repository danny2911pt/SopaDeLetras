package sopa;


import jdk.nashorn.internal.scripts.JO;
import sun.reflect.annotation.ExceptionProxy;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Sopa extends JFrame implements ActionListener
{
    //Dados
        //Menus
        static JMenuBar mnb;
        static JLabel tempo;
            //Jogo
            private JMenu m_jogo;
                private String[] str_mni_jogo = {"Novo Jogo", "Scores", "Demonstração", "Desbloquear \"Criar\"", "Sair"};
                private JMenuItem[] mni_jogo = new JMenuItem[str_mni_jogo.length];
            //\Jogo

            //Criar
            private JMenu m_criar;
                private String mongo = "";
                private String[] str_mni_criar = {"Criar Palavras", "Distribuir Palavras", "Apagar Tabuleiro", "Abrir Jogo", "Gravar Jogo", "Criar Demonstração"};
                private JMenuItem[] mni_criar = new JMenuItem[str_mni_criar.length];

            private Criar criar_palavras;
            static Distribuir distribuir;
            //\Criar

            //Ajuda
            private JMenu m_ajuda;
                private JMenuItem tutorial;
                private JMenu sobre;
                    private String[] str_mni_sobre = {"Linguagem", "Programa", "Autor"};
                    private JMenuItem[] mni_sobre = new JMenuItem[str_mni_sobre.length];
                        private String[][] str_dlg_sobre =
                                {
                                        {"Linguagem : Java\nVersão: 1.8.X\n\n Fabricante: Oracle Corporation", "Sobre - Linguagem"},
                                        {"Sopa de Letras\n\n Versão: BETA\n Data: --/--/--", "Sobre - Programa"},
                                        {"Autores: Vasco & Daniel\n\n Curso/Turma: TPSI1018\n UFCD: 5117", "Sobre - Autores"}
                                };
            //\Ajuda
        //\Menus

        //Paineis
            //Centro
                private JPanel centro;
                static JButton[][] bt = new JButton[15][15];
                static int linha = 0, coluna = 0;
            //\Centro

            //East
                static JPanel east;
                private Font font = new Font("Arial", Font.BOLD, 16);

                private int[][] arco_iris =
                {
                    {255, 204, 204}, {255, 217, 204}, {255, 230, 204}, {255, 242, 204}, {255, 255, 204},
                    {242, 255, 204}, {230, 255, 204}, {217, 255, 204}, {204, 255, 204}, {204, 255, 217},
                    {204, 255, 230}, {204, 255, 242}, {204, 255, 255}, {204, 242, 255}, {204, 230, 255},
                    {204, 217, 255}, {204, 204, 255}, {217, 204, 255}, {230, 204, 255}
                };
            //\East
        //\Paineis

        //Relógio
        private Relogio relogio = new Relogio();
        //\Relógio

        //Abrir & Guardar
        private String caminho = Paths.get(".").toAbsolutePath().normalize().toString();
        private FileNameExtensionFilter filtro;

        static ArrayList<JLabel> a_labels;
        static ArrayList<JCheckBox> a_check;
        //\Abrir & Guardar

        //Jogada
        String compara = "";
        boolean jogar = false;
        int ct_palavras = 0;

        int jogadaL[] =new int[15];
        int jogadaC[] =new int[15];
        int loucura;
        int ajuda = 0;
        int cor = 0;
        int desconta=1;
        boolean check[][] = new boolean[15][15];
        char reset[] = new char [15];
        int red[]=new int[10];
        int green[]=new int[10];
        int blue[]=new int[10];
        int corCheckerL[]=new int[15];
        int corCheckerC[]=new int[15];
        //\Jogada
    //\Dados

    //Constructor
    private Sopa()
    {
        setTitle("Sopa de Letras");

        //Toolkit & Dim
        Toolkit tlk = getToolkit();
        Dimension dim = tlk.getScreenSize();
        //\Toolkit & Dim

        //Menus
        mnb = new JMenuBar();
        setJMenuBar(mnb);

            //Jogo
            m_jogo = new JMenu("Jogo");
                for (int ct = 0; ct < mni_jogo.length; ct++)
                {
                    if (ct == 0)
                    {
                        mongo = "1";
                    }

                    mni_jogo[ct] = new JMenuItem(str_mni_jogo[ct]);
                    mni_jogo[ct].addActionListener(this);
                    m_jogo.add(mni_jogo[ct]);

                    if (ct == 1 || ct == 3)
                    {
                        m_jogo.addSeparator();
                    }
                }
            mnb.add(m_jogo);
            mongo += "q";
            //\Jogo

            //Criar
            m_criar = new JMenu("Criar");
            m_criar.setEnabled(false);
                mongo += "a";
                for (int ct = 0; ct < mni_criar.length; ct++)
                {
                    mni_criar[ct] = new JMenuItem(str_mni_criar[ct]);
                    mni_criar[ct].addActionListener(this);
                    m_criar.add(mni_criar[ct]);

                    if (ct == 2 || ct == 4)
                    {
                        m_criar.addSeparator();
                    }
                }

                criar_palavras = new Criar();
                distribuir = new Distribuir();
                mongo += "z";
            mnb.add(m_criar);
            //\Criar

            //Ajuda
            m_ajuda = new JMenu("Ajuda");
                tutorial = new JMenuItem("Tutorial");
                tutorial.addActionListener(this);
                m_ajuda.add(tutorial);
                mongo += "2";
                sobre = new JMenu("Sobre");
                    for (int ct = 0; ct < mni_sobre.length; ct++)
                    {
                        mni_sobre[ct] = new JMenuItem(str_mni_sobre[ct]);
                        mni_sobre[ct].addActionListener(this);
                        sobre.add(mni_sobre[ct]);
                    }
                m_ajuda.add(sobre);
                mongo += "w";
            mnb.add(m_ajuda);
            //\Ajuda

            //Tempo
            tempo = new JLabel("00:00:00");

            Border b_tempo;
            b_tempo = new EmptyBorder(0, 0, 0, 20);
            tempo.setBorder(b_tempo);

            mnb.add(Box.createHorizontalGlue());
            mnb.add(tempo);
            //\Tempo
        //\Menus

        //Paineis
            //Centro
            centro = new JPanel(new GridLayout(15,15));
                Border centro_border = new EmptyBorder(0,0,0,0);
                centro.setBorder(centro_border);

                for (int ct = 0; ct < bt.length; ct++)
                {
                    for (int ct2 = 0; ct2 < bt[ct].length; ct2++)
                    {
                        bt[ct][ct2] = new JButton();
                        bt[ct][ct2].setFocusPainted(false);
                        bt[ct][ct2].setBackground(Color.white);
                        bt[ct][ct2].addActionListener(this);
                        bt[ct][ct2].setUI(new MetalButtonUI() { protected Color getDisabledTextColor() { return Color.BLACK; }});
                        centro.add(bt[ct][ct2]);
                    }
                }
                mongo += "s";
            add(centro, BorderLayout.CENTER);
            //\Centro

            //East
            east = new JPanel(new GridBagLayout());
            add(east, BorderLayout.EAST);
            //\East
        //\Paineis

        //OptionPane - UIManager
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.focus", new ColorUIResource(new Color(0,0,0,0)));
        //\OptionPane - UIManager

        //Abrir & Guardar
        new File(caminho+"/Jogo").mkdir();
        filtro = new FileNameExtensionFilter("Ficheiros txt", "txt");

        a_check = new ArrayList<>();
        a_labels = new ArrayList<>();
        //\Abrir & Guardar

        //Frame
        setVisible(true);
        setResizable(false);

        int alt = 700, comp = 900;
        mongo += "x";
        setBounds(dim.width/2 - comp/2, dim.height/2 - alt/2, comp, alt);
        //\Frame
        }
    //\Constructor

    //Métodos



    //Jogada
    public void Jogada(int ct, int ct2)
    {
        check[ct][ct2]=!check[ct][ct2];

        if(check[ct][ct2])
        {
            //PRIMEIRO BOTÃO PREMIDO
            if(jogadaL[0] == '\0' && jogadaC[0] == '\0')
            {
                compara += bt[ct][ct2].getText();
                bt[ct][ct2].setBackground(new Color(arco_iris[cor][0],arco_iris[cor][1], arco_iris[cor][2]));

                jogadaL[0]=ct;
                jogadaC[0]=ct2;
            }
            //\PRIMEIRO BOTÃO PREMIDO
            //SEGUNDO BOTAO PREMIDO
            else if(jogadaL[1] == '\0' && jogadaC[1] == '\0') {
                if (jogadaL[0] + 1 == ct && jogadaC[0] == ct2 || jogadaL[0] - 1 == ct && jogadaC[0] == ct2 || jogadaL[0] == ct && jogadaC[0] + 1 == ct2 || jogadaL[0] == ct && jogadaC[0] - 1 == ct2 || jogadaL[0] + 1 == ct && jogadaC[0] + 1 == ct2 || jogadaL[0] + 1 == ct && jogadaC[0] - 1 == ct2 || jogadaL[0] - 1 == ct && jogadaC[0] + 1 == ct2 || jogadaL[0] - 1 == ct && jogadaC[0] - 1 == ct2) {
                    compara += bt[ct][ct2].getText();
                    bt[ct][ct2].setBackground(new Color(arco_iris[cor][0], arco_iris[cor][1], arco_iris[cor][2]));

                    jogadaL[1] = ct;
                    jogadaC[1] = ct2;

                    //COMPARAR A STRING DO JOGADOR COM AS PALAVRAS DO JOGO
                    for(int ct3 = 0; ct3<a_labels.size(); ct3++)
                    {
                        if(compara.equals(a_labels.get(ct3).getText()))
                        {
                            a_labels.get(ct3).setForeground(new Color(arco_iris[cor][0], arco_iris[cor][1], arco_iris[cor][2]));
                            a_check.get(ct3).setSelected(true);
                            a_check.get(ct3).setOpaque(true);

                            //\VER REPETIÇOES
                            for(int cr=0;cr<compara.length();cr++)
                            {
                                for(int cr2=0;cr2<Distribuir.choqueUp;cr2++)
                                {
                                    if(jogadaL[cr] == Distribuir.choqueL[cr2] && jogadaC[cr] == Distribuir.choqueC[cr2] && Distribuir.choqueQ[jogadaL[cr]][jogadaC[cr]]!='\0')
                                    {
                                        //Guardar a cor
                                        red[cr2]=arco_iris[cor][0];
                                        green[cr2]=arco_iris[cor][1];
                                        blue[cr2]=arco_iris[cor][2];
                                        corCheckerC[cr2]+=1;
                                        corCheckerL[cr2]+=1;
                                        //Guardar a cor caso seja deselecionado
                                        Distribuir.choqueQ[jogadaL[cr]][jogadaC[cr]]-=1;

                                        bt[jogadaL[cr]][jogadaC[cr]].setEnabled(true);
                                        check[jogadaL[cr]][jogadaC[cr]] = false;
                                        loucura = 1;

                                        break;
                                    }
                                }

                                if(loucura==0)
                                {
                                    bt[jogadaL[cr]][jogadaC[cr]].setEnabled(false);
                                    check[jogadaL[cr]][jogadaC[cr]]=true;
                                }
                                else { loucura = 0; }
                            }
                            //\VER REPETIÇOES

                            compara = "";
                            cor++;
                            ajuda = 0;

                            for(int cl = 0; cl <= 10; cl++)
                            {
                                if(jogadaC[cl] == '\0' && jogadaL[cl]== '\0') { break; }
                                else
                                {
                                    jogadaL[cl]='\0';
                                    jogadaC[cl]='\0';
                                }
                            }
                        }


                    }
                    //\COMPARAR A STRING DO JOGADOR COM AS PALAVRAS DO JOGO
                } else {
                    for (int cl = 0; cl <compara.length(); cl++) {
                        //VERIFICA SE ALGUM DOS BUTOES QUE VAI SER DESELCIONADO ENTRA NA LISTA DOS REPETIDOS E SE JÁ TEM UMA COR DIFERENTE DA BRANCA
                        for (int cl2 = 0; cl2 < Distribuir.choqueC.length; cl2++) {
                            if (Distribuir.choqueL[cl2] == jogadaL[cl] && Distribuir.choqueC[cl2] == jogadaC[cl] & corCheckerC[cl2]!='\0' && corCheckerL[cl2]!='\0') {

                                bt[jogadaL[cl]][jogadaC[cl]].setBackground(new Color(red[cl2], green[cl2], blue[cl2]));
                                break;
                            }else {

                                bt[jogadaL[cl]][jogadaC[cl]].setBackground(Color.white);
                            }
                        }
                        check[jogadaL[0]][jogadaC[0]] = false;

                        compara = "";

                        jogadaL[0] = ct;
                        jogadaC[0] = ct2;

                        compara += bt[ct][ct2].getText();
                        bt[ct][ct2].setBackground(new Color(arco_iris[cor][0], arco_iris[cor][1], arco_iris[cor][2]));
                    }
                }
            }

            //\SEGUNDO BOTÃO PREMIDO
            //TERCEIRO BOTÃO PREMIDO >
            else
            {
                if(jogadaL[ajuda] - jogadaL[ajuda+1] == jogadaL[ajuda+1] - ct && jogadaC[ajuda] - jogadaC[ajuda+1] == jogadaC[ajuda+1] - ct2) {
                    compara += bt[ct][ct2].getText();
                    bt[ct][ct2].setBackground(new Color(arco_iris[cor][0], arco_iris[cor][1], arco_iris[cor][2]));
                    bt[ct][ct2].setEnabled(false);

                    jogadaL[2 + ajuda] = ct;
                    jogadaC[2 + ajuda] = ct2;

                    ajuda++;

                    //COMPARAR A STRING DO JOGADOR COM AS PALAVRAS DO JOGO
                    for (int ct3 = 0; ct3 < a_labels.size(); ct3++) {
                        if (compara.equals(a_labels.get(ct3).getText())) {
                            a_labels.get(ct3).setForeground(new Color(arco_iris[cor][0], arco_iris[cor][1], arco_iris[cor][2]));
                            a_check.get(ct3).setSelected(true);
                            a_check.get(ct3).setOpaque(true);

                            //\VER REPETIÇOES
                            for (int cr = 0; cr < compara.length(); cr++) {
                                for (int cr2 = 0; cr2 < Distribuir.choqueUp; cr2++) {
                                    if (jogadaL[cr] == Distribuir.choqueL[cr2] && jogadaC[cr] == Distribuir.choqueC[cr2] && Distribuir.choqueQ[jogadaL[cr]][jogadaC[cr]] != '\0') {
                                        corCheckerC[cr2]+=1;
                                        corCheckerL[cr2]+=1;
                                        red[cr2] = arco_iris[cor][0];
                                        green[cr2] = arco_iris[cor][1];
                                        blue[cr2] = arco_iris[cor][2];
                                        Distribuir.choqueQ[jogadaL[cr]][jogadaC[cr]] -= 1;

                                        bt[jogadaL[cr]][jogadaC[cr]].setEnabled(true);
                                        check[jogadaL[cr]][jogadaC[cr]] = false;
                                        loucura = 1;

                                        break;
                                    }
                                }

                                if (loucura == 0) {
                                    bt[jogadaL[cr]][jogadaC[cr]].setEnabled(false);
                                    check[jogadaL[cr]][jogadaC[cr]] = true;
                                } else {
                                    loucura = 0;
                                }
                            }
                            //\VER REPETIÇOES

                            compara = "";
                            cor++;
                            ajuda = 0;

                            for (int cl = 0; cl <= 10; cl++) {
                                if (jogadaC[cl] == '\0' && jogadaL[cl] == '\0') {
                                    break;
                                } else {
                                    jogadaL[cl] = '\0';
                                    jogadaC[cl] = '\0';
                                }
                            }
                        }


                    }
                    //\COMPARAR A STRING DO JOGADOR COM AS PALAVRAS DO JOGO

                }else {
                    for(int cl=0;cl<=compara.length(); cl++) {
                        for (int cl2 = 0; cl2 <Distribuir.choqueC.length; cl2++) {
                            if (Distribuir.choqueL[cl2] == jogadaL[cl] && Distribuir.choqueC[cl2] == jogadaC[cl] && corCheckerC[cl2]!='\0' && corCheckerL[cl2]!='\0') {

                                bt[jogadaL[cl]][jogadaC[cl]].setBackground(new Color(red[cl2],green[cl2],blue[cl2]));
                                break;
                            }
                            else{

                                bt[jogadaL[cl]][jogadaC[cl]].setBackground(Color.white);
                            }
                        }



                        bt[jogadaL[cl]][jogadaC[cl]].setEnabled(true);
                        check[jogadaL[cl]][jogadaC[cl]] = false;
                        if(jogadaC[cl] == '\0' && jogadaL[cl] == '\0') { break; }
                        else
                        {
                            jogadaL[cl]='\0';
                            jogadaC[cl]='\0';
                        }
                    }

                    ajuda = 0;
                    compara = "";
                    compara += bt[ct][ct2].getText();

                    jogadaL[0] = ct;
                    jogadaC[0] = ct2;

                    bt[ct][ct2].setBackground(new Color(arco_iris[cor][0],arco_iris[cor][1], arco_iris[cor][2]));
                }
            }
        }
        //\TERCEIRO BOTÃO PREMIDO
    }
    //\Jogada

        //Abandonar
        private void abandonar()
        {
            int sair = JOptionPane.showConfirmDialog(this, "Tem a certeza que pretende abandonar a aplicação?", "Abandonar", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (sair == JOptionPane.YES_OPTION)
            {
                System.exit(0);
                System.gc();
            }
        }
        //\Abandonar

        //ActionListener
        public void actionPerformed(ActionEvent e)
        {
            //Jogo
            for (int ct = 0; ct < mni_jogo.length; ct++ )
            {
                if (e.getSource() == mni_jogo[ct])
                {
                    switch (ct)
                    {
                        case 0:
                            boolean limpo = false;
                            for (int ct2 = 0; ct2 < bt.length; ct2++)
                            {
                                for (int ct3 = 0; ct3 < bt[ct2].length; ct3++)
                                {
                                    if (bt[ct2][ct3].getText().equals("")) { limpo = true; }
                                }
                            }

                            if (!limpo)
                            {
                                relogio.para();
                                tempo.setText("00:00:00");
                                relogio = new Relogio();
                                relogio.corre();
                                jogar = true;
                            }
                            break;
                        case 1:
                            break;
                        case 2:

                            break;
                        case 3:
                            System.out.println(mongo);
                            try
                            {
                                String mongo2 = JOptionPane.showInputDialog(this, "Insira a password:", "Criar - Password", JOptionPane.PLAIN_MESSAGE);
                                if (mongo2.equals(mongo)) { m_criar.setEnabled(true); }
                            }
                            catch ( Exception el) {}
                            break;
                        case 4:
                            abandonar();
                            break;
                        default:
                            System.out.print("actionPerformed - Jogo - Debug");
                            break;
                    }
                }
            }
            //\Jogo


            //Criar
            for (int ct = 0; ct < mni_criar.length; ct++)
            {
                if (e.getSource() == mni_criar[ct])
                {
                    switch (ct)
                    {
                        case 0:
                            criar_palavras.setVisible(true);
                            break;
                        case 1:
                            if (Distribuir.fim)
                            {
                                east = new JPanel(new GridBagLayout());
                                add(east, BorderLayout.EAST);
                            }

                            if (Distribuir.palavras.size() != 0)
                            {
                                distribuir.setVisible(true);
                            }
                            else { JOptionPane.showMessageDialog(this, "Não existem palavras para inserir.", "WARNING!", JOptionPane.PLAIN_MESSAGE); }
                            break;
                        case 2: //Limpar Tabuleiro
                            for (int ct2 = 0; ct2 < Sopa.bt.length; ct2++)
                            {
                                for (int ct3 = 0; ct3 < Sopa.bt[ct].length; ct3++) { Sopa.bt[ct2][ct3].setText(""); }
                            }

                            east.removeAll();
                            east.setBorder(null);

                            distribuir = new Distribuir();
                            Criar.updateDistribuir();
                            break;
                        case 3: //Abrir
                            JFileChooser abre = new JFileChooser(caminho+"/Jogo");
                            abre.setFileFilter(filtro);
                            abre.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                            int escolha_abre = abre.showOpenDialog(Sopa.this);
                            String abre_caminho = abre.getSelectedFile().getPath();

                            try
                            {
                                FileReader arq = new FileReader(abre_caminho);
                                BufferedReader lerArq = new BufferedReader(arq);
                                String linhas = lerArq.readLine();

                                int ct2 = 0, ct3 = 0;
                                while (!linhas.equals("/"))
                                {
                                    if (ct3 > 14)
                                    {
                                        ct3 = 0;
                                        ct2++;
                                        linhas = lerArq.readLine();
                                    }

                                    try
                                    {
                                        char ch = linhas.charAt(ct3);
                                        bt[ct2][ct3].setText(String.valueOf(ch));
                                        ct3++;
                                    }
                                    catch( Exception io) {}
                                }

                                Border east_border = new EmptyBorder(0,10,0,20);
                                east.setBorder(east_border);

                                a_labels = new ArrayList<>();
                                a_check = new ArrayList<>();

                                int ct4 = 0, ct5 = 0;
                                linhas = lerArq.readLine();
                                try
                                {
                                    while (!linhas.equals("/"))
                                    {
                                        JLabel palavras_east;
                                        JCheckBox check_east;

                                        GridBagConstraints gbc = new GridBagConstraints();
                                        gbc.fill = GridBagConstraints.BOTH;

                                        gbc.gridy = ct4;
                                        gbc.gridx = 0;
                                        gbc.ipady = 2;
                                        check_east = new JCheckBox();
                                        check_east.setEnabled(false);
                                        check_east.setBackground(Color.white);
                                        a_check.add(check_east);
                                        Sopa.east.add(check_east, gbc);

                                        gbc.gridx = 1;
                                        palavras_east = new JLabel(linhas);
                                        palavras_east.setFont(font);
                                        a_labels.add(palavras_east);
                                        Sopa.east.add(palavras_east, gbc);

                                        linhas = lerArq.readLine();
                                        ct4++;
                                    }
                                }
                                catch ( Exception o) {}

                                linhas = lerArq.readLine();

                                Distribuir.choqueQ = new int[15][15];
                                Distribuir.choqueL = new int[15];
                                Distribuir.choqueC = new int[15];
                                Distribuir.choqueUp = 0;

                                ct2 = 0;
                                ct3 = 0;
                                while (!linhas.equals("/"))
                                {
                                    if (ct3 > 14)
                                    {
                                        ct3 = 0;
                                        ct2++;
                                        linhas = lerArq.readLine();
                                    }

                                    try
                                    {
                                        int numero = (linhas.charAt(ct3)-'0');
                                        Distribuir.choqueQ[ct2][ct3] = numero;
                                        ct3++;
                                    }
                                    catch ( Exception o) {}
                                }

                                linhas = lerArq.readLine();
                                ct2 = 0;
                                while ( ct2 < 15)
                                {
                                    int numero = (linhas.charAt(ct2) - '0');
                                    Distribuir.choqueC[ct2] = numero;
                                    ct2++;
                                }
                                lerArq.readLine();

                                linhas = lerArq.readLine();
                                ct2 = 0;
                                while (ct2 < 15)
                                {
                                    int numero = (linhas.charAt(ct2) - '0');
                                    Distribuir.choqueL[ct2] = numero;
                                    ct2++;
                                }
                                lerArq.readLine();
                                linhas = lerArq.readLine();
                                Distribuir.choqueUp = (linhas.charAt(0) - '0');

                                arq.close();
                            }
                            catch ( IOException io) {}

                            //Debbug
                            for (int ct2 = 0; ct2 < Distribuir.choqueQ.length; ct2++)
                            {
                                for (int ct3 = 0; ct3 < Distribuir.choqueQ[ct2].length; ct3++)
                                {
                                    System.out.print(Distribuir.choqueQ[ct2][ct3]);
                                }
                                System.out.println();
                            }
                            System.out.println("/");
                            for (int ct2 = 0; ct2 < Distribuir.choqueC.length; ct2++)
                            {
                                System.out.print(Distribuir.choqueC[ct2]);
                            }
                            System.out.println("/");
                            for (int ct2 = 0; ct2 < Distribuir.choqueL.length; ct2++)
                            {
                                System.out.print(Distribuir.choqueL[ct2]);
                            }
                            System.out.println("/");
                            System.out.println(Distribuir.choqueUp);
                            //\Debbug
                            break;
                        case 4: //Guardar
                            JFileChooser guarda = new JFileChooser(caminho+"/Jogo");
                            guarda.setFileFilter(filtro);
                            guarda.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                            int escolha_guarda = guarda.showSaveDialog(Sopa.this);
                            String salva_caminho = guarda.getSelectedFile().getPath();

                            try
                            {
                                FileWriter Arq = new FileWriter(salva_caminho);
                                PrintWriter GravarArq = new PrintWriter(Arq);

                                if (caminho != null)
                                {
                                    for (int ct2 = 0; ct2 < bt.length; ct2++)
                                    {
                                        for (int ct3 = 0; ct3 < bt[ct].length; ct3++)
                                        {
                                            GravarArq.print(bt[ct2][ct3].getText());
                                        }
                                        GravarArq.println();
                                    }

                                    GravarArq.println("/");

                                    for (int ct2 = 0; ct2 < Criar.palavras.size(); ct2++)
                                    {
                                        GravarArq.println(Criar.palavras.get(ct2));
                                    }

                                    GravarArq.println("/");
                                    //Choques
                                    for (int ct2 = 0; ct2 < Distribuir.choqueQ.length; ct2++)
                                    {
                                        for (int ct3 = 0; ct3 < Distribuir.choqueQ[ct2].length; ct3++)
                                        {
                                            GravarArq.print(Distribuir.choqueQ[ct2][ct3]);
                                        }
                                        GravarArq.println();
                                    }

                                    GravarArq.println("/");

                                    for (int ct2 = 0; ct2 < Distribuir.choqueC.length; ct2++)
                                    {
                                        GravarArq.print(Distribuir.choqueC[ct2]);
                                    }
                                    GravarArq.println();

                                    GravarArq.println("/");
                                    for (int ct2 = 0; ct2 < Distribuir.choqueL.length; ct2++)
                                    {
                                        GravarArq.print(Distribuir.choqueL[ct2]);
                                    }
                                    GravarArq.println();

                                    GravarArq.println("/");
                                    GravarArq.println(Distribuir.choqueUp);
                                    //\Choques
                                }
                                GravarArq.close();
                            }
                            catch(IOException io){}
                            break;
                        case 5:
                            break;
                        default:
                            System.out.print("actionPerformed - Criar - Debug");
                            break;
                    }
                }
            }
            //\Criar

            //Ajuda
                //Tutorial
                    if (e.getSource() == tutorial) {}
                //\Tutorial

                //Sobre
                for (int ct = 0; ct < mni_sobre.length; ct++)
                {
                    if (e.getSource() == mni_sobre[ct])
                    {
                        JOptionPane.showMessageDialog(this, str_dlg_sobre[ct][0], str_dlg_sobre[ct][1], JOptionPane.PLAIN_MESSAGE);
                    }
                }
                //\Sobre
            //\Ajuda

            //Tabuleiro
                //Apanhar posição para escrever palavra
                for (int ct = 0; ct < bt.length; ct++)
                {
                    for (int ct2 = 0; ct2 < bt[ct].length; ct2++)
                    {
                        if (e.getSource() == bt[ct][ct2])
                        {
                            linha = ct;
                            coluna = ct2;
                            Distribuir.txt_f[1].setText(String.format("%02d", ct));
                            Distribuir.txt_f[2].setText(String.format("%02d", ct2));
                        }
                    }
                }
                //\Apanhar posição para escrever palavra

                //Jogada
                if (jogar)
                {
                    for (int ct = 0; ct < bt.length; ct++)
                    {
                        for (int ct2 = 0; ct2 < bt[ct].length; ct2++)
                        {
                            if (e.getSource() == bt[ct][ct2]) { Jogada(ct,ct2); }
                        }
                    }
                }
                //\Jogada
            //\Tabuleiro
        }
        //\ActionListener
    //\Métodos

    //Main
    public static void main (String[] args)
    {
        Sopa app = new Sopa();
    }
    //\Main
}
