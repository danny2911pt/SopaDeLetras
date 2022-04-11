//Rotaçoes para criar diferentes jogos

//Rodar 90º
public void rodaJogo_90(){
for (ct=0;ct<15;ct++){
  for(ct1=15;ct2>=0;ct1--){
  centro.add(joga[ct][ct1]);
  }}}

//Rodar 180º
public void rodaJogo_180(){
for (ct=15;ct>=0;ct--){
  for(ct1=15;ct2>=0;ct1--){
  centro.add(joga[ct][ct1]);
  }}}

//Rodar 270º
public void rodaJogo_270(){
for (ct=15;ct>=0;ct--){
  for(ct1=0;ct2<=15;ct1++){
  centro.add(joga[ct][ct1]);
  }}}








  //->Tens que dar nome as variaveis que apanham a posição escolhida pelo criador do jogo
  //->Para aqui vou por apanha.coluna e apanha.linha

   //Alinhamento das Palavras
 //Horizontal
     //Esquerda-Direita
     //A coluna mantem-se constante o que muda é a posiçao da linha
     public void rodaPalavra_E-D(){
     ct1=apanha.coluna;
     for(int ct=apanha.linha;ct<apanha.linha+palavra.length;ct++){
		for(int ct2=0;ct2< (variavel com a palavra).length;ct2++){
		joga[ct][ct1]=Button[(variavel com a palvra).charAt(ct2)];
}}}
     //Direita-Esquerda
     public void rodaPalavra_D-E(){
     ct1=apanha.coluna;
     for(int ct=apanha.linha;ct<apanha.linha+palavra.length;ct--){
        for(int ct2=0;ct2< [(variavel com a palavra).length;ct2++)]{
		joga[ct][ct1]=Button[(variavel com a palvra).charAt(ct2)];
}}}

  //Vertical
  //Cima-Baixo
  //A linha mantem-se constante o que muda e a posiçao da coluna
  public void rodapalavra_C-B(){
  ct1=apanha.linha;
  for
  }