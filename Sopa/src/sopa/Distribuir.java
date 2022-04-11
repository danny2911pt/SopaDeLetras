package sopa;



import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalCheckBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Distribuir extends JFrame implements ActionListener
{
    //Dados
        //Toolkit & Dimensions
        private Toolkit tlk;
        private Dimension dim;
        private int alt = 260, comp = 600;
        //Toolkit & Dimensions

        //Paineis
            private JPanel combinados;
            //Subpaineis
            static JPanel[] panel = new JPanel[6];
                //Font
                private Font font = new Font("Arial", Font.BOLD, 16);
                //\Font

                //Painel 0
                static JComboBox<String> combo;

                private Border[] border = new Border[3];

                static  String[] str_label = {"Tamanho:", "Linha:", "Coluna:"};
                static  JLabel[] label = new JLabel[str_label.length];
                private JLabel label_espaco;
                static JTextField[] txt_f = new JTextField[label.length];
                //\Painel 0

                //Painel 1
                private JLabel titulo_label;
                //\Painel 1

                //Painel 2
                private String[] str_check = {"Diagonal [Esquerda -> Direita]", "Horizontal", "Diagonal [Direita -> Esquerda]", "Vertical"};
                private JCheckBox[] check = new JCheckBox[str_check.length];
                private ButtonGroup grupo;
                private int flag;
                //\Painel 2

                //Painel 3
                private JLabel titulo_label2;
                //\Painel 3

                //Painel 4
                private String[] str_check2 = {"Normal", "Invertida"};
                private JCheckBox[] check2 = new JCheckBox[str_check2.length];
                private ButtonGroup grupo2;
                private boolean flag2 = false;
                //\Painel 4

                //Painel 5
                private String[] str_bt = {"Inserir", "Undo"};
                private JButton[] bt = new JButton[str_bt.length];
                //\Painel 5
            //\SubPaineis
        //Paineis

        //Palavras
        static ArrayList<String> palavras;
        private String palavra;
        String ultima_palavra;
        static boolean fim = false;
        //\Palavras

        //Historico
        private ArrayList<Historico> historicos = new ArrayList<>();
        private Historico undo;
        int ordem = 0;
        //\Historico

        //Chocante
        static int choqueL[]=new int [15];
        static int choqueC[]=new int[15];
        static int choqueQ[][]=new int[15][15];
        static int choqueUp;
        //\Chocante
    //Dados

    //Constructor
    public Distribuir()
    {
        setTitle("Distribuir Palavras");

        //Toolkit & Dimensions
        tlk = getToolkit();
        dim = tlk.getScreenSize();
        //Toolkit & Dimensions

        //Paineis
        combinados = new JPanel(new GridBagLayout());
        GridBagConstraints prime_const = new GridBagConstraints();
            //SubPaineis
                //Painel 0
                panel[0] = new JPanel();

                    combo = new JComboBox<>();
                    combo.addActionListener(this);
                    combo.setPreferredSize(new Dimension(250, 30));
                    combo.setBackground(Color.WHITE);

                    palavras = new ArrayList<>();

                    panel[0].add(combo);

                    border[0] = BorderFactory.createLineBorder(Color.gray);
                    border[1] = new EmptyBorder(0, 6, 0, 0);
                    border[2] = new CompoundBorder(border[0], border[1]);

                    label_espaco = new JLabel(String.format("%3s", " "));
                    panel[0].add(label_espaco);
                    for (int ct = 0; ct < txt_f.length; ct++)
                    {
                        label[ct] = new JLabel(str_label[ct]);
                        panel[0].add(label[ct]);

                        UIManager.put("TextField.inactiveBackground", Color.WHITE);
                        UIManager.put("TextField.disabledBackground", Color.WHITE);
                        UIManager.put("TextField.Background", Color.WHITE);
                        txt_f[ct] = new JTextField();
                        txt_f[ct].setBorder(border[2]);
                        txt_f[ct].setPreferredSize(new Dimension(30,30));
                        txt_f[ct].setFocusable(false);
                        txt_f[ct].setEditable(false);
                        panel[0].add(txt_f[ct]);
                    }
                    prime_const.fill = new GridBagConstraints().BOTH;
                    prime_const.gridy = 0;
                    prime_const.gridx = 0;
                    prime_const.weighty = 0.5;
                combinados.add(panel[0], prime_const);
                //\Painel 0

                //Painel 1
                panel[1] = new JPanel();
                    titulo_label = new JLabel("Orientação da Palavra");
                    titulo_label.setFont(font);
                    panel[1].add(titulo_label);

                    prime_const.fill = new GridBagConstraints().HORIZONTAL;
                    prime_const.gridy = 1;
                    prime_const.insets = new Insets(0, 0,0,0);
                combinados.add(panel[1], prime_const);
                //\Painel 1

                //Painel 2
                panel[2] = new JPanel(new GridBagLayout());
                    GridBagConstraints constraints = new GridBagConstraints();
                    grupo = new ButtonGroup();
                    for (int ct = 0; ct < check.length; ct++)
                    {
                        check[ct] = new JCheckBox(str_check[ct]);
                        check[ct].addActionListener(this);
                        grupo.add(check[ct]);

                        if (ct < 2)
                        {
                            constraints.fill = new GridBagConstraints().HORIZONTAL;
                            constraints.gridx = ct;
                            constraints.gridy = 0;
                            constraints.ipadx = 0;
                        }
                        else
                        {
                            constraints.gridx = ct - 2;
                            constraints.gridy = 1;
                        }

                        panel[2].add(check[ct], constraints);
                    }
                    check[0].setSelected(true);
                    prime_const.gridy = 2;
                combinados.add(panel[2], prime_const);
                //\Painel 2

                //Painel 3
                panel[3] = new JPanel();
                    titulo_label2 = new JLabel("Modo de Escrita");
                    titulo_label2.setFont(font);
                    panel[3].add(titulo_label2);
                    prime_const.gridy = 3;
                    prime_const.ipady = 0;
                combinados.add(panel[3], prime_const);
                //\Painel 3

                //Painel 4
                panel[4] = new JPanel();
                    grupo2 = new ButtonGroup();
                    for(int ct = 0; ct < check2.length; ct++)
                    {
                        check2[ct] = new JCheckBox(str_check2[ct]);
                        check2[ct].addActionListener(this);
                        grupo2.add(check2[ct]);
                        panel[4].add(check2[ct]);
                    }
                    check2[0].setSelected(true);
                    prime_const.gridy = 4;
                combinados.add(panel[4], prime_const);
                //\Painel 4

                //Painel 5
                panel[5] = new JPanel();
                    for (int ct = 0; ct < bt.length; ct++)
                    {
                        bt[ct] = new JButton(str_bt[ct]);
                        bt[ct].setPreferredSize(new Dimension(150, 30));
                        bt[ct].addActionListener(this);
                        panel[5].add(bt[ct]);
                    }
                    prime_const.gridy = 5;
                combinados.add(panel[5], prime_const);
                //\Painel 5
            //\SubPaineis
        add(combinados, BorderLayout.CENTER);
        //\Paineis

        //Frame
        setVisible(false);
        setResizable(false);
        setBounds(dim.width/2 - comp/2, dim.height/2 - alt/2, comp, alt);
        setAlwaysOnTop(true);
        //Frame
    }
    //Constructor

    //Métodos
        //Avisos
        public void aviso(int num)
        {
            String titulo = "WARNING!";

            switch (num)
            {
                case 0:
                    JOptionPane.showMessageDialog(this, "A palavra que pretende inserir ultrapassa os limites do tabuleiro.", titulo, JOptionPane.PLAIN_MESSAGE);
                    break;
                case 1:
                    JOptionPane.showMessageDialog(this, "A palavra "+combo.getSelectedItem()+" entra em conflito com outra no tabuleiro.", titulo, JOptionPane.PLAIN_MESSAGE);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(this, "Selecione um palavra para inserir no tabuleiro.", titulo, JOptionPane.PLAIN_MESSAGE);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(this, "Não existem palavras no tabuleiro.", titulo, JOptionPane.PLAIN_MESSAGE);
                default:
                    break;
            }
        }
        //\Avisos

        //Preencher Historico
        public void preencheHistorico(String c, String s,  int x, int y, int z)
        {
            undo = new Historico();

            undo.setCh(c);
            undo.setPalavra(s);
            undo.setLinha(x);
            undo.setColuna(y);
            undo.setOrdem(z);
            historicos.add(undo);
        }
        //\Preencher Historico

        //Preencher Espaços
        public static void prencherEspacos()
        {
            Random random = new Random();
            String ABC = "ABCDEFGHIJKLMNOPQRSTUVXYZ";
            int index = 0;

            for(int ct = 0; ct < 15; ct++)
            {
                for(int ct1 = 0; ct1 < 15; ct1++)
                {
                    if(Sopa.bt[ct][ct1].getText().equals(""))
                    {
                        index = random.nextInt(ABC.length()-1);
                        Sopa.bt[ct][ct1].setText(String.valueOf(ABC.charAt(index)));
                    }
                }
            }
        }
        //Preencher Espaços

        //ActionListener
        public void actionPerformed(ActionEvent e)
        {
            //Combo
            if (e.getSource() == combo)
            {
                if (combo.getSelectedItem() != combo.getItemAt(0))
                {
                    String tamanho = combo.getSelectedItem().toString();
                    txt_f[0].setText(String.format("%02d", tamanho.length()));
                }
                else { txt_f[0].setText("00"); }
            }
            //\Combo

            //CheckBoxs
            for (int ct = 0; ct < check.length; ct++)
            {
                if(e.getSource() == check[ct])
                {
                    switch (ct)
                    {
                        case 0:
                            flag = 0;
                            break;
                        case 1:
                            flag = 1;
                            break;
                        case 2:
                            flag = 2;
                            break;
                        case 3:
                            flag = 3;
                            break;
                        default:
                            System.out.println("Debug Criar - Check");
                            break;
                    }
                }
            }

            if (e.getSource() == check2[0]) { flag2 = false; }
            if (e.getSource() == check2[1]) { flag2 = true; }
            //\CheckBoxs

            // Butões
            if (e.getSource() == bt[0])
            {
                if (combo.getSelectedItem() != combo.getItemAt(0))
                {
                    String ch;
                    palavra = combo.getSelectedItem().toString();

                    if (flag2)
                    {
                        String palavra_copia = "";
                        for (int ct = palavra.length() - 1; ct >= 0; ct--)
                        {
                            palavra_copia += (palavra.charAt(ct));
                        }
                        palavra = palavra_copia;
                    }

                    boolean escreve = true;
                    int contador = 0;
                    switch (flag)
                    {
                        case 0: //Diagonal [Esquerda -> Direita]
                            //Verificações
                            if (palavra.length() + Sopa.coluna > 15 || palavra.length() + Sopa.linha > 15) { escreve = false; aviso(0); }

                            if (escreve)
                            {
                                for (int ct = 0; ct < palavra.length(); ct++)
                                {
                                    ch = String.valueOf(palavra.charAt(ct));
                                    try
                                    {
                                        if (Sopa.bt[Sopa.linha+ct][Sopa.coluna+ct].getText().equals("") || Sopa.bt[Sopa.linha+ct][Sopa.coluna+ct].getText().equals(ch)) { contador++; }

                                        if (Sopa.bt[Sopa.linha+ct][Sopa.coluna+ct].getText().equals(ch))
                                        {
                                            choqueL[choqueUp]=Sopa.linha+ct;
                                            choqueC[choqueUp]=Sopa.coluna+ct;
                                            choqueQ[Sopa.linha+ct][Sopa.coluna+ct]+=1;
                                            choqueUp++;
                                        }
                                    }
                                    catch (Exception o) {}
                                }
                                if (contador != palavra.length()) { escreve = false; aviso(1);}
                            }
                            //Verificações

                            if (escreve)
                            {
                                for (int ct = 0; ct < palavra.length(); ct++)
                                {
                                    ch = String.valueOf(palavra.charAt(ct));
                                    Sopa.bt[Sopa.linha + ct][Sopa.coluna + ct].setText(ch);
                                    preencheHistorico(ch, palavra, Sopa.linha + ct, Sopa.coluna + ct, ordem);
                                }
                                ultima_palavra = combo.getSelectedItem().toString();
                                combo.removeItem(combo.getSelectedItem());
                                ordem++;
                            }

                            break;
                        case 1: //Horizontal
                            //Verificações
                            if (palavra.length() + Sopa.coluna > 15) { escreve = false; aviso(0);}

                            if (escreve)
                            {
                                for (int ct = 0; ct < palavra.length(); ct++)
                                {
                                    ch = String.valueOf(palavra.charAt(ct));
                                    try
                                    {
                                        if (Sopa.bt[Sopa.linha][Sopa.coluna+ct].getText().equals("") || Sopa.bt[Sopa.linha][Sopa.coluna+ct].getText().equals(ch)) { contador++; }

                                        if (Sopa.bt[Sopa.linha][Sopa.coluna+ct].getText().equals(ch))
                                        {
                                            choqueL[choqueUp]=Sopa.linha;
                                            choqueC[choqueUp]=Sopa.coluna+ct;
                                            choqueQ[Sopa.linha][Sopa.coluna+ct]=1;
                                            choqueUp++;
                                        }
                                    }
                                    catch (Exception o) {}
                                }
                                if (contador != palavra.length()) { escreve = false; aviso(1);}
                            }

                            //\Verificações

                            if (escreve)
                            {
                                for (int ct = 0; ct < palavra.length(); ct++)
                                {
                                    ch = String.valueOf(palavra.charAt(ct));
                                    Sopa.bt[Sopa.linha][Sopa.coluna+ct].setText(String.valueOf(ch));
                                    preencheHistorico(ch, palavra, Sopa.linha, Sopa.coluna+ct, ordem);
                                }
                                ultima_palavra = combo.getSelectedItem().toString();
                                combo.removeItem(combo.getSelectedItem());
                                ordem++;
                            }
                            break;
                        case 2: //Diagonal [Direita -> Esquerda]
                            //Verificações
                            if (Sopa.linha + palavra.length() < 0 || Sopa.coluna - palavra.length() < 0) { escreve = false; aviso(0); }

                            if (escreve)
                            {
                                try
                                {
                                    for (int ct = 0; ct < palavra.length(); ct++)
                                    {
                                        ch = String.valueOf(palavra.charAt(ct));
                                        if (Sopa.bt[Sopa.linha+ct][Sopa.coluna-ct].getText().equals("") || Sopa.bt[Sopa.linha+ct][Sopa.coluna-ct].getText().equals(ch)) { contador++; }

                                        if (Sopa.bt[Sopa.linha+ct][Sopa.coluna-ct].getText().equals(ch))
                                        {
                                            choqueL[choqueUp]=Sopa.linha+ct;
                                            choqueC[choqueUp]=Sopa.coluna-ct;
                                            choqueQ[Sopa.linha+ct][Sopa.coluna-ct]+=1;
                                            choqueUp++;
                                        }
                                    }
                                    if (contador != palavra.length()) { escreve = false; aviso(1); }
                                }
                                catch (Exception o) {}
                            }
                            //\Verificações

                            if (escreve)
                            {
                                for (int ct = 0; ct < palavra.length(); ct++)
                                {
                                    ch = String.valueOf(palavra.charAt(ct));
                                    Sopa.bt[Sopa.linha+ct][Sopa.coluna-ct].setText(ch);
                                    preencheHistorico(ch, palavra, Sopa.linha+ct, Sopa.coluna-ct, ordem);
                                }
                                ultima_palavra = combo.getSelectedItem().toString();
                                combo.removeItem(combo.getSelectedItem());
                                ordem++;
                            }
                            break;
                        case 3: //Vertical
                            //Verificações
                            if (palavra.length() + Sopa.linha > 15) { escreve = false; aviso(0);}

                            if (escreve)
                            {
                                try
                                {
                                    for (int ct = 0; ct < palavra.length(); ct++)
                                    {
                                        ch = String.valueOf(palavra.charAt(ct));
                                        if (Sopa.bt[Sopa.linha+ct][Sopa.coluna].getText().equals("") || Sopa.bt[Sopa.linha+ct][Sopa.coluna].getText().equals(ch)) { contador++; }

                                        if (Sopa.bt[Sopa.linha+ct][Sopa.coluna].getText().equals(ch))
                                        {
                                            choqueL[choqueUp]=Sopa.linha+ct;
                                            choqueC[choqueUp]=Sopa.coluna;
                                            choqueQ[Sopa.linha+ct][Sopa.coluna]+=1;
                                            choqueUp++;
                                        }
                                    }
                                    if (contador != palavra.length()) { escreve = false; aviso(1);}
                                }
                                catch ( Exception o) {}
                            }
                            //\Verificações

                            if (escreve)
                            {
                                for (int ct = 0; ct < palavra.length(); ct++)
                                {
                                    ch = String.valueOf(palavra.charAt(ct));
                                    Sopa.bt[Sopa.linha+ct][Sopa.coluna].setText(String.valueOf(ch));
                                    preencheHistorico(ch, palavra, Sopa.linha+ct, Sopa.coluna, ordem);
                                }
                                ultima_palavra = combo.getSelectedItem().toString();
                                combo.removeItem(combo.getSelectedItem());
                                ordem++;
                            }
                            break;
                        default:
                            System.out.println("Debug Distribuir - Flag");
                            break;
                    }

                    fim = false;
                    if (combo.getItemCount() == 1)
                    {
                        fim = true;

                        JLabel palavras_east;
                        JCheckBox check_east;

                        Border east_border = new EmptyBorder(0,10,0,20);
                        Sopa.east.setBorder(east_border);
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.fill = GridBagConstraints.BOTH;
                        for (int ct = 0; ct < Criar.palavras.size(); ct++)
                        {
                            gbc.gridy = ct;
                            gbc.gridx = 0;
                            gbc.ipady = 2;

                            check_east = new JCheckBox();
                            check_east.setEnabled(false);
                            check_east.setBackground(Color.white);
                            Sopa.a_check.add(check_east);
                            Sopa.east.add(check_east, gbc);

                            gbc.gridx = 1;
                            palavras_east = new JLabel(Criar.palavras.get(ct));
                            palavras_east.setFont(font);
                            Sopa.a_labels.add(palavras_east);
                            Sopa.east.add(palavras_east, gbc);
                        }
                        prencherEspacos();
                        dispose();
                    }
                }
                else {aviso(2);}
            }

            if (e.getSource() == bt[1])
            {
                if (!historicos.isEmpty())
                {
                    String ultima_palavra = "";
                    int var = 0;
                    for (int ct = 0; ct < historicos.size(); ct++)
                    {
                        if (ct == historicos.size()-1)
                        {
                            ultima_palavra = historicos.get(ct).getPalavra();

                            for (int ct2 = ct+historicos.get(ct).getPalavra().length(); ct2 > ct; ct2--)
                            {
                                historicos.remove(ct - var);
                                var++;
                            }
                        }
                    }
                    combo.addItem(ultima_palavra);
                    ordem--;

                    //Limpar
                    for (int ct = 0; ct < Sopa.bt.length; ct++)
                    {
                        for (int ct2 = 0; ct2 < Sopa.bt[ct].length; ct2++) { Sopa.bt[ct][ct2].setText(""); }
                    }
                    //\Limpar

                    //Repovoar
                    for (Historico h : historicos)
                    {
                        Sopa.bt[h.getLinha()][h.getColuna()].setText(h.getCh());
                    }
                    //\Repovoar
                }
                else { aviso(3);}
            }
            //\Butões
        }
        //\ActionListener
    //Métodos

    //Main
    //public static void main(String[] args) { Distribuir app = new Distribuir(); }
    //\Main
}
