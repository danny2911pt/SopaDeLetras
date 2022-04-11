package sopa;

public class Historico
{
    //Dados
    String ch, palavra;
    int linha, coluna, ordem;
    //\Dados

    //Constructores
    public Historico() {}
    public Historico(String ch, String palavra, int linha, int coluna, int ordem)
    {
        this.ch = ch;
        this.palavra = palavra;
        this.linha = linha;
        this.coluna = coluna;
        this.ordem = ordem;
    }
    //\Constructores

    //Métodos
        //Ch
        public String getCh()
        {
            return ch;
        }
        public void setCh(String ch) { this.ch = ch; }
        //\Ch

        //Palavra
        public String getPalavra() { return palavra; }
        public void setPalavra(String palavra) { this.palavra = palavra;}

        //\Palavra

        //Linha
        public int getLinha()
        {
            return linha;
        }
        public void setLinha(int linha)
        {
            this.linha = linha;
        }
        //\Linha

        //Coluna
        public int getColuna()
        {
            return coluna;
        }
        public void setColuna(int coluna) { this.coluna = coluna; }
        //\Coluna

        //Ordem
        public int getOrdem()
        {
            return ordem;
        }
        public void setOrdem(int ordem) { this.ordem = ordem; }
        //\Order
    //\Métodos
}
