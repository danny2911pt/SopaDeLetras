package sopa;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static sopa.Distribuir.palavras;

public class Criar extends JFrame implements ActionListener, FocusListener, KeyListener
{
    //Dados
        //Toolkit & Dimensions
        private Toolkit tlk;
        private Dimension dim;
        private int alt = 120, alt2 = 0, comp = 800;
        //\Toolkit & Dimensions

        //Paineis
            //Norte
            private JPanel norte;

            private JTextField txt_f;
            private Font font_txt_f = new Font("Arial", Font.BOLD, 14);
            private Border[] txt_border = new Border[3];

            private String[] str_bt = {"Inserir", "Eliminar", "Update"};
            private JButton[] bt = new JButton[str_bt.length];
            //\Norte

            //Sul
            private JPanel sul;

            static ArrayList<String> palavras;
            private int ct_palavras = 0;

            private ArrayList<Character> spc_char;

            private ArrayList<JButton> a_bt_palavras;
            private JButton bt_palavras;
            //\Sul
        //\Paineis
    //\Dados

    //Constructor
    public Criar()
    {
        setTitle("Criar Palavras");

        //ToolKit & dimensions
        tlk = getToolkit();
        dim = tlk.getScreenSize();
        //\Toolkit & dimensions

        //Paineis
            //Norte
            norte = new JPanel();
                txt_border[0] = BorderFactory.createLineBorder(Color.gray);
                txt_border[1] = new EmptyBorder(0, 10, 0, 0);
                txt_border[2] = new CompoundBorder(txt_border[0], txt_border[1]);

                for (int ct = 0; ct < bt.length; ct++)
                {
                    if (ct == 0)
                    {
                        txt_f = new JTextField("Escreva aqui palavra que pretende inserir.");
                        txt_f.addFocusListener(this);
                        txt_f.addActionListener(this);
                        txt_f.addKeyListener(this);
                        txt_f.setPreferredSize(new Dimension(420, 30));
                        txt_f.setFont(font_txt_f);
                        txt_f.setBorder(txt_border[2]);
                        norte.add(txt_f);
                    }

                    bt[ct] = new JButton(str_bt[ct]);
                    bt[ct].addActionListener(this);
                    bt[ct].setFocusPainted(false);
                    bt[ct].setBackground(Color.WHITE);
                    bt[ct].setPreferredSize(new Dimension(108, 30));
                    norte.add(bt[ct]);
                }
             add(norte, BorderLayout.NORTH);
            //\Norte

            //Sul
            sul = new JPanel();
                palavras = new ArrayList<>();
                a_bt_palavras = new ArrayList<>();
                spc_char = new ArrayList<>();

                for (int ct = 32; ct < 65; ct++)
                {
                    spc_char.add((char)ct);
                }

                for (int ct = 91; ct < 97; ct++)
                {
                     spc_char.add((char)ct);
                }

                for (int ct = 123; ct < 256; ct++)
                {
                    spc_char.add((char)ct);
                }
            add(sul, BorderLayout.CENTER);
            //\Sul
        //\Paineis

        //OptionPanes
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.focus", new ColorUIResource(new Color(0,0,0,0)));
        //\OptionPanes

        //Frame
        setVisible(false);
        setResizable(false);
        setBounds(dim.width/2 - comp/2, dim.height/2 - alt/2, comp, alt);
        setAlwaysOnTop(true);
        //\Frame
    }
    //\Constructor

    //Métodos
        //Update Distribuir
        static void updateDistribuir()
        {
            Distribuir.palavras = palavras;
            Distribuir.combo.removeAllItems();
            String nada = " ";
            Distribuir.combo.addItem(nada);
            for (int ct = 0; ct < palavras.size(); ct++)
            {
                Distribuir.combo.addItem(Distribuir.palavras.get(ct));
            }
        }
        //\Update Distribuir

        //ActionListener
        public void actionPerformed(ActionEvent e)
        {
            //Buttons
            if (e.getSource() == bt[0] || e.getSource() == txt_f)
            {
                //Verificações
                boolean continua = true;

                for (int ct = 0; ct < txt_f.getText().length(); ct++)
                {
                    for (int ct2 = 0; ct2 < spc_char.size(); ct2++)
                    {
                        if (txt_f.getText().charAt(ct) == spc_char.get(ct2)) { continua = false; }
                    }
                }

                for (int ct = 0; ct < palavras.size(); ct++)
                {
                    if (txt_f.getText().toUpperCase().equals(palavras.get(ct))) { continua = false; }
                }

                if (txt_f.getText().length() > 15 || txt_f.getText().length() == 0) { continua = false; }
                //\Verificações

                if (continua)
                {
                    palavras.add(txt_f.getText().toUpperCase());

                    //Butões
                    bt_palavras = new JButton(palavras.get(ct_palavras));
                    bt_palavras.setEnabled(false);
                    bt_palavras.setUI(new MetalButtonUI() { protected Color getDisabledTextColor() { return Color.BLACK; }});
                    bt_palavras.setBackground(Color.WHITE);
                    bt_palavras.addActionListener(this);
                    bt_palavras.setPreferredSize(new Dimension(250, 30));
                    a_bt_palavras.add(bt_palavras);
                    ct_palavras++;
                    //\Butões

                    sul.removeAll();
                    //Repovoar
                    for (int ct = 0; ct < palavras.size(); ct++)
                    {
                        sul.add(a_bt_palavras.get(ct));
                    }

                    if(palavras.size()%3 == 0)
                    {
                        alt = alt+33;
                        setBounds(dim.width/2 - comp/2, dim.height/2 - alt/2, comp, alt);
                    }
                    //\Repovoar
                    sul.revalidate();
                    sul.repaint();

                    if (palavras.size() < 19) { txt_f.setText(""); }
                    else
                    {
                        txt_f.setText("Atingido o número máximo de palavras!");
                        txt_f.setFocusable(false);
                        txt_f.setEditable(false);
                        bt[0].setEnabled(false);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Impossível inserir:\n\n- Números\\Espaços;\n- Palavras Repetidas;\n- Palavras > 15 letras!", "WARNING!", JOptionPane.PLAIN_MESSAGE);
                }
            }

            if (e.getSource() == bt[1])
            {
                if (palavras.size() != 0)
                {
                    //Desbloquear Palavras
                    for (int ct = 0; ct < palavras.size(); ct++) { a_bt_palavras.get(ct).setEnabled(true); }
                    //\Desbloquear palavras
                }
                else { JOptionPane.showMessageDialog(this, "Não existem palavras a eliminar!", "WARNING!", JOptionPane.PLAIN_MESSAGE); }
            }

            if (e.getSource() == bt[2])
            {
                updateDistribuir();
                setVisible(false);
                Sopa.distribuir.setVisible(true);
            }

                //Buttons - Palavras
                for (int ct = 0; ct < palavras.size(); ct++)
                {
                    if (e.getSource() == a_bt_palavras.get(ct))
                    {
                        palavras.remove(ct);
                        a_bt_palavras.remove(ct);

                        sul.removeAll();
                        //Repovoar
                        if (palavras.size()%3 == 0 && palavras.size() != 0)
                        {
                            alt = alt-33;
                            setBounds(dim.width/2 - comp/2, dim.height/2 - alt/2, comp, alt);
                        }
                        for (int ct2 = 0; ct2 < palavras.size(); ct2++)
                        {
                            a_bt_palavras.get(ct2).setEnabled(false);
                            sul.add(a_bt_palavras.get(ct2));
                        }
                        //\Repovoar
                        sul.revalidate();
                        sul.repaint();
                        ct_palavras--;

                        if (palavras.size() < 19)
                        {
                            txt_f.setFocusable(true);
                            txt_f.setEditable(true);
                            bt[0].setEnabled(true);
                        }
                        break;
                    }
                }
                //Buttons - Palavras
            //\Buttons
        }
        //\ActionListener

        //FocusListener
        public void focusGained(FocusEvent e)
        {
            if (e.getSource() == txt_f) { txt_f.setText(""); }
        }

        public void focusLost(FocusEvent e)
        {
            if (e.getSource() == txt_f) { txt_f.setText("Escreva aqui palavra que pretende inserir."); }
        }
        //FocusListener

        //KeyListener
        public void keyTyped(KeyEvent e) {}
        public void keyPressed(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
        //\KeyListener
    //\Métodos

    //Main
    //public static void main(String[] args) { Criar app = new Criar(); }
    //\Main
}
