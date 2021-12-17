import java.util.*;

// EP1 - MVGA
// 13/12/2021
// Fabiana Dantas - NUSP 10367266
// Isabel Boroni - NUSP 10284480


// classe que representa uma matriz de valores do tipo double.

class Matriz {

	// constante para ser usada na comparacao de valores double.
	// Se a diferenca absoluta entre dois valores double for menor
	// do que o valor definido por esta constante, eles devem ser
	// considerados iguais.
	public static final double SMALL = 0.000001;
	
	private int lin, col;	
	private double [][] m;
	public String operacao;

	// metodo estatico que cria uma matriz identidade de tamanho n x n.

	public static Matriz identidade(int n){

		Matriz mat = new Matriz(n, n);
		for(int i = 0; i < mat.lin; i++) mat.m[i][i] = 1;
		return mat;
	}	

	// construtor que cria uma matriz de n linhas por m colunas com todas as entradas iguais a zero.

	public Matriz(int n, int m){

		this.lin = n;
		this.col = m;
		this.m = new double[lin][col];
	}
	
	// construtor criado para clonar objeto
	public Matriz (Matriz matriz) {
		this.lin = matriz.lin;
		this.col = matriz.col;
		this.m = new double[lin][col];
		
		for(int i = 0; i < lin; i++){

			for(int j = 0; j < col; j++){
	
				this.m[i][j] = matriz.m[i][j];
			}
		}
	    
	}

	public void set(int i, int j, double valor){

		m[i][j] = valor;
	}

	public double get(int i, int j){

		return m[i][j];
	}

	// metodo que imprime as entradas da matriz.

	public void imprime(){

		for(int i = 0; i < lin; i++){

			for(int j = 0; j < col; j++){
	
				System.out.printf("%7.2f ", m[i][j]);
			}

			System.out.println();
		}
	}

	// metodo que imprime a matriz expandida formada pela combinacao da matriz que 
	// chama o metodo com a matriz "agregada" recebida como parametro. Ou seja, cada 
	// linha da matriz impressa possui as entradas da linha correspondente da matriz 
	// que chama o metodo, seguida das entradas da linha correspondente em "agregada".

	public void imprime(Matriz agregada){

		for(int i = 0; i < lin; i++){

			for(int j = 0; j < col; j++){
	
				System.out.printf("%7.2f ", m[i][j]);
			}

			System.out.print(" |");

			for(int j = 0; j < agregada.col; j++){
	
				System.out.printf("%7.2f ", agregada.m[i][j]);
			}

			System.out.println();
		}
	}
	
	// metodo que troca as linhas i1 e i2 de lugar.

	private void trocaLinha(int i1, int i2){
	
		// TODO: implementar este metodo. -- FEITO
		double aux;
		
		for(int i = 0; i < m[i1].length; i++) {
		    aux = m[i1][i];
		    m[i1][i] = m[i2][i];
		    m[i2][i] = aux;
		}
	}

	// metodo que multiplica as entradas da linha i pelo escalar k

	private void multiplicaLinha(int i, double k){

		// TODO: implementar este metodo. -- FEITO
		for(int j = 0; j < m[i].length; j++) {
		    m[i][j] = m[i][j] * k;
		}
	}

	// metodo que faz a seguinte combinacao de duas linhas da matriz:
	//	
	// 	(linha i1) = (linha i1) + (linha i2 * k)
	//

	private void combinaLinhas(int i1, int i2, double k){

		// TODO: implementar este metodo. -- FEITO
		for(int i = 0; i < m[i1].length; i++) {
		    m[i1][i] = m[i1][i] + (m[i2][i] * k);
		}
		
		
	}

	// metodo que procura, a partir da linha ini, a linha com uma entrada nao nula que
	// esteja o mais a esquerda possivel dentre todas as linhas. Os indices da linha e da 
	// coluna referentes a entrada nao nula encontrada sao devolvidos como retorno do metodo.
	// Este metodo ja esta pronto para voces usarem na implementacao da eliminacao gaussiana
	// e eleminacao de Gauss-Jordan.

	private int [] encontraLinhaPivo(int ini){

		int pivo_col, pivo_lin;

		pivo_lin = lin;
		pivo_col = col;

		for(int i = ini; i < lin; i++){
		
			int j;
			
			for(j = 0; j < col; j++) if(Math.abs(m[i][j]) > 0) break;

			if(j < pivo_col) {

				pivo_lin = i;
				pivo_col = j;
			}
		}

		return new int [] { pivo_lin, pivo_col };
	}

    // Troca a coluna para os calculos de determinante secundaria
    // considerando que a coluna dos termos isolados eh sempre a ultima
    // col eh a coluna da qual ira substituir
    private Matriz trocarColunaPorTermosIsolados(Matriz agregada, int col) {
        Matriz novaMatriz = agregada;
        
        for(int i = 0; i < agregada.lin; i++ ){
            novaMatriz.m[i][col] = agregada.m[i][agregada.col-1];
        }
        
        return novaMatriz;
        
    }



	// metodo que implementa a eliminacao gaussiana, que coloca a matriz (que chama o metodo)
	// na forma escalonada. As operacoes realizadas para colocar a matriz na forma escalonada 
	// tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Este metodo 
	// tambem deve calcular e devolver o determinante da matriz que invoca o metodo. Assumimos 
	// que a matriz que invoca este metodo eh uma matriz quadrada.

	public double formaEscalonada(Matriz agregada){

		// TODO: implementar este metodo.
		int trocaLinha = 1;
		double determinante = 1;
    		
		// Achar o pivô e deixar na forma escalonada
		int j = 0;
		while (j < agregada.m.length-1) {
    		int[] pivo = agregada.encontraLinhaPivo(j);
    		
    		// verifica se o pivo nao eh -0.0
    		if(Math.abs(agregada.m[pivo[0]][pivo[1]]) < SMALL){
    		    agregada.m[pivo[0]][pivo[1]] = 0.0;
    		    pivo = agregada.encontraLinhaPivo(j);
    		}
    		// Se o pivo nao esta na linha j
    		while(pivo[0] != j || agregada.m[pivo[0]][pivo[1]] == 0) {
    		    agregada.trocaLinha(j,pivo[0]);
    		    trocaLinha = trocaLinha * (-1);
    		    pivo = agregada.encontraLinhaPivo(j);
    		}
    		
        	for(int i = pivo[0] + 1; i < agregada.m.length; i++) {
        	    // Achar o multiplicador:
        	    double multiplicador = (agregada.m[i][pivo[1]] / agregada.m[pivo[0]][pivo[1]]) * (-1);
    		    agregada.combinaLinhas(i,pivo[0],multiplicador);
        	}
    		j++;
		}
		
		// Calcular determinante caso seja matriz quadrada **** --verificar
		for (int i = 0; i < agregada.m.length; i++) {
		    determinante = determinante * agregada.m[i][i];
		}
		    
		return determinante * trocaLinha;
		
	}

	// metodo que implementa a eliminacao de Gauss-Jordan, que coloca a matriz (que chama o metodo)
	// na forma escalonada reduzida. As operacoes realizadas para colocar a matriz na forma escalonada 
	// reduzida tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Assumimos que
	// a matriz que invoca esta metodo eh uma matriz quadrada. Não se pode assumir, contudo, que esta
	// matriz ja esteja na forma escalonada (mas voce pode usar o metodo acima para isso).

	public void formaEscalonadaReduzida(Matriz agregada){
	    
		Matriz aux = new Matriz(agregada);
		
		double det = agregada.formaEscalonada(agregada);
		
		// Caso a determinante = 0, Sistema nao eh possivel de se resolver
		if(det == 0.0) {
		    double detSec;
		    // verificar 0s em uma linha
		    for (int i = 0; i < agregada.m.length; i++) {
		        for (int j = 0; j < agregada.m.length; j++) {
		            if(agregada.m[i][j] != 0) {
		                break;
		            }
		            if(j == agregada.m.length - 1 ){
		                if(agregada.m.length != agregada.col && agregada.m[i][j+1] != 0){
		                    
		                    if(agregada.operacao.equals("resolve")){
		                        System.out.println("sistema sem solução");
		                    } else if (agregada.operacao.equals("inverte")) {
		                        System.out.println("matriz singular");
		                    }
		                    System.exit(1);
		                }
		                
		            }
		        }
		    }
		    
		    // verificar determinante secundarias igual a 0
		    for(int i = 0; i < agregada.m.length; i++) {
		        Matriz mi = new Matriz(aux);
		        mi.trocarColunaPorTermosIsolados(mi,i);
		        detSec = mi.formaEscalonada(mi);
		        if(detSec == 0.0) {
		            System.out.println("sistema possui diversas soluções");
		            System.exit(1);
		        }
		    }
		}
		
		// verificar se a matriz tem mais de 1 elemento -- FAZER 
		// escalonado
		int j = 1;
		while (j < agregada.lin){
		    for(int i = j - 1; i >= 0; i--) {
            	// Achar o multiplicador:
            	double multiplicador = (agregada.m[i][j] / agregada.m[j][j]) * (-1);
        		agregada.combinaLinhas(i,j,multiplicador);
            }
		    j++;
		}
		// deixar diagonal principal = 1
		j = 0;
		while (j < agregada.lin){
            double multiplicador = (1 / agregada.m[j][j]);
		    agregada.multiplicaLinha(j,multiplicador);
		    j++;
		}
	}
	
	public Matriz juntarMatrizIdentidade(Matriz agregada, Matriz identidade) {
	    Matriz matrizJuntas = new Matriz(agregada.lin, agregada.col+identidade.col);
	    
	    for (int i = 0; i < agregada.lin;i++) {
			for(int j = 0; j < agregada.col; j++){
			    matrizJuntas.m[i][j] = agregada.m[i][j];
			}
	    }
	    for (int i = 0; i < agregada.lin;i++) {
			for(int j = agregada.col; j < agregada.col*2; j++){
			    matrizJuntas.m[i][j] = identidade.m[i][j - agregada.col];
			}
	    }
	    
	    return matrizJuntas;
	}
	
	public Matriz separarMatrizInversa(Matriz matrizJunta) {
	    Matriz inversa = new Matriz(matrizJunta.lin, matrizJunta.col/2);
	    
	    for (int i = 0; i < matrizJunta.lin;i++) {
			for(int j = matrizJunta.lin; j < matrizJunta.col; j++){
			    inversa.m[i][j-matrizJunta.lin] = matrizJunta.m[i][j];
			}
	    }
	    return inversa;
	}
	
	
}

// Classe "executavel".

public class EP1 {

	// metodo principal.

	public static void main(String [] args){

		Scanner in = new Scanner(System.in);	// Scanner para facilitar a leitura de dados a partir da entrada padrao.
		String operacao = in.next();		// le, usando o scanner, a string que determina qual operacao deve ser realizada.
		int n = in.nextInt();			// le a dimensão da matriz a ser manipulada pela operacao escolhida.

        // TODO: implementar este metodo. -- FEITO
        int cols = 0;
        
        if(operacao.equals("resolve")) {
            cols = n+1;
        } else {
            cols = n;
        }
		
		Matriz matriz = new Matriz(n, cols);
		matriz.operacao = operacao;
		
		for(int i = 0; i < n; i++){
		    for(int j = 0; j < cols; j++){
		        matriz.set(i,j,in.nextDouble());
		    }
		}

		if("resolve".equals(operacao)){
            matriz.formaEscalonadaReduzida(matriz);
		    for(int i = 0; i < n;i++){
		        System.out.printf("%.2f\n",matriz.get(i,n));
		    }
		}
		else if("inverte".equals(operacao)){
		    Matriz identidade = matriz.identidade(n);
		    Matriz matrizJuntas = identidade.juntarMatrizIdentidade(matriz,identidade);
		    matrizJuntas.operacao = operacao;
		    matrizJuntas.formaEscalonadaReduzida(matrizJuntas);
		    Matriz inversa = identidade.separarMatrizInversa(matrizJuntas);
		    inversa.imprime();
		}
		else if("determinante".equals(operacao)){
		    System.out.printf("%.2f\n",matriz.formaEscalonada(matriz));
		}
		else {
			System.out.println("Operação desconhecida!");
			System.exit(1);
		}
	}
}
