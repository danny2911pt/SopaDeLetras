package sopa;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Relogio
{
    //Dados
     int segundos, minutos, horas;
        //Timer
        Timer relogio = new Timer();
        TimerTask task = new TimerTask()
        {
            public void run()
            {
                segundos++;

                if (segundos == 60)
                {
                    segundos = 0;
                    minutos++;
                }

                if (minutos == 60)
                {
                    minutos = 0;
                    horas++;
                }

                Sopa.tempo.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
}
        };
        //\Timer
    //\Dados

    //Metodos
    public void corre()
    {
        relogio = new Timer();
        relogio.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void para()
    {
        relogio.cancel();
    }
    //Metodos
}
