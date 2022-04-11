package sopa;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

public class Scores extends JFrame
{
    //Dados
        //Toolkit & Dimensions
        Toolkit tlk;
        Dimension dim;
        //\Toolkit & Dimensions

        //Paineis
            //Font
            Font font = new Font("Arial", Font.BOLD, 14);
            //\Font

            //Centro
            JPanel centro;
            //\Centro

            //Sul
            JPanel sul;
            //\Sul
        //Paineis

        //Constructores
        public Scores()
        {
            setTitle("Scores");

            //Toolkit & Dimension
            tlk = getToolkit();
            dim = tlk.getScreenSize();
            //\Toolkit & Dimension

            //Paineis
                //Centro
                centro  = new JPanel(new GridBagLayout());
                //\Centro

                //Sul
                sul = new JPanel();
                    JTextField txtf = new JTextField();

                    Border[] border = new Border[3];
                    border[0] = BorderFactory.createLineBorder(Color.GRAY);
                    border[1] = new EmptyBorder(0, 6, 0, 0);
                    border[2] = new CompoundBorder(border[0], border[1]);

                    txtf.setBorder(border[2]);
                    txtf.setPreferredSize(new Dimension(230, 30));
                    txtf.setFont(font);
                    sul.add(txtf);

                    JButton bt = new JButton("OK");
                    bt.setPreferredSize(new Dimension(55, 30));
                    bt.setBackground(Color.white);
                    bt.setFocusPainted(false);
                    sul.add(bt);

                    sul.setVisible(false);
                add(sul, BorderLayout.SOUTH);
                //\Sul
            //\Paineis

            //Frame
            setResizable(false);
            setVisible(true);

            int comp = 300, alt = 300;
            setBounds(dim.width/2 - comp/2, dim.height/2 - alt/2, comp, alt);
            //\Frame
        }
        //\Constructores

        //Métodos
        //\Métodos

        //Main
        public static void main(String[] args) { Scores app = new Scores(); }
        //Main
    //\Dados
}
