package lmrcomp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import sun.util.logging.resources.logging;
/**
 * Projeto da Disciplina – Linguagem de Manipulação de Robô
 * Assuntos: Gramática e Autômatos Finitos Determinísticos
 * 
 *  Classe que implementa parte de um compilador da linguagem LMR (Linguagem de Manipulação de um Robô)
 *  
 *  Este programa permite realizar a “compilação” de um programa fonte em LMR, 
 *  disponível em um arquivo “<nome>.lmr” gerando um outro arquivo “<nome>.cmp” 
 *  contendo o resultado avaliado pelo AFD.
 *  
 * @author Ivan Carlos Alcântara de Oliveira
 *      <INSERIR AQUI OS NOMES DOS COMPONENTES DO GRUPO com seus Registros de Matrícula> 
 * @version 1.0, 06/04/2016
 */
public class LMRComp {
	// Variável global que armazena o programa fonte lido do arquivo <nome>.lmr
	private static StringBuilder fonte;
	// Variável global que armazena o programa fonte compilado
	private static StringBuilder compilado;
	// Constante que informa que o nome do arquivo de entrada foi fornecido pela linha de comando
	private static final char COMANDO = '0';
	// Constante que informa que o nome do arquivo de entrada foi fornecido pelo windows	
	private static final char WINDOWS = '1';
	// Variável global que armazena o tipo de entrada COMANDO ou WINDOWS
	private static char tipoSistema;
	// Constante do caracter Espaço em branco
	private static final char ESPACO = ' ';
	// Constante do caracter tab (tabulação)
	private static final char TAB = '\t';
	// Constante do caracter nova linha (pula linha)
	private static final char NOVA_LINHA = '\n';
	// Constante que informa que a cadeia foi aceita
	private static final int ACEITA = 0;
	// Constante que informa que a cadeia foi rejeitada 
	private static final int REJEITADA = 1;
        
        private static ArrayList<String> fila = new ArrayList();
			
	static int i=0;
	 /**
	  * Método: leArquivo(String caminhoNome)
	  * @param: caminhoNome: nome do arquivo para leitura contendo o caminho completo.
	  * @return: StringBuilder contendo os dados lidos do arquivo com o nome passado como parâmetro.
	  * Funcionalidade: lê os dados armazenados em arquivo do tipo texto, 
	  * 				armazena em uma StringBuffer e retorna o conteúdo lido.
	  */
	public static StringBuilder leArquivo(String caminhoNome){
		StringBuilder fonteLMR = new StringBuilder("");
		try { 

			FileReader arq = new FileReader(caminhoNome); 
			BufferedReader lerArq = new BufferedReader(arq); 
			String linha = lerArq.readLine(); 
			
			// lê a primeira linha 
			// a variável "linha" recebe o valor "null" quando o processo 
                        //retira todos os espaco no texto
			// de repetição atingir o final do arquivo texto 
                        
                        String resposta="";
			while (linha != null) { 
                           linha= linha.replace(ESPACO,NOVA_LINHA);
                           linha=linha.trim();
                           fonteLMR.append(linha+"\n");
                           System.out.printf("%s\n", linha); 
                           linha = lerArq.readLine(); 
                           
				// lê da segunda até a última linha 
				} 
			arq.close(); 

			} catch (IOException e) { 
				System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
			}
            
	    return fonteLMR;
	}
	
	 /**
	  * Método: gravaArquivo(String caminhoNome, String dados)
	  * @param: caminhoNome: nome do arquivo com o caminho completo para gravação.
	  *         dados: String contendo os dados a serem gravados.
	  * @return: nenhum valor de retorno.
	  * Funcionalidade: grava os dados em arquivo de "caminhoNOme" do tipo texto.
	  * 
	  */
	public static void gravaArquivo(String caminhoNome, String dados){
		try { 
			FileWriter arq = new FileWriter(caminhoNome); 
			PrintWriter gravarArq = new PrintWriter(arq); 
			gravarArq.printf(dados); 
			arq.close(); 
		} catch (IOException e) { 
			System.err.printf("Erro na gravação do arquivo: %s.\n", e.getMessage()); 
		}
	}
	
	 /**
	  * Método: escreveMensagem(String msg, char tipoSistema)
	  * @param: msg: String contendo os dados a serem escritos/aapresentados.
	  * 		tipoSistema: armazena o tipo de apresentação COMANDO ou WINDOWS
	  * @return: nenhum valor de retorno.
	  * Funcionalidade: escreve a mensagem msg no sistema (COMANDO ou WINDOWS).
	  * 
	  */
    public static void escreveMensagem(String msg, char tipoSistema){
    	if (tipoSistema == COMANDO)
	        System.out.println(msg);
    	else
    		JOptionPane.showMessageDialog(null, msg);
    }
    
    /**
     * Método: main()
     * @param: String [] args: vetor de argumentos fornecidos na linha de comando durante a chamada do sistema.
     * 						   se utilizado linha de comando (COMANDO) o args[0] armazena o caminho/nome do arquivo
     *  					   para leitura.
     * @return: nenhum valor de retorno.
     * Funcionalidades:
     *       le o programa fonte da LMR contido em um arquivo de entrada fornecido pela linha de COMANDO ou 
     *       pela interface gráfica do WINDOWS armazenando em uma StringBuider. 
     *       Aplica o código do AFD desenvolvido para a LMR em cada elemento do programa fonte 
     *       gerando uma outra StringBuilder contendo o nome do elemento da LMR seguida de ACEITA ou REJEITA
     *       e grava arquivo "compilado" contendo em cada linha o elemento da LMR seguida de ACEITA ou REJEITA.
     */ 
	public static void main(String args[]){
	  	// Obtém o nome do arquivo que contém o código fonte na linguagem LMR
		// Se o usuário utilizou linha de comando, obtém o nome pelo parâmetro
		// caso contrário, solicita por janela windows.
		String pathName, pathNameCompilado; 
		if (args.length != 0) {
		    tipoSistema = COMANDO;
			pathName = args[0];
		}else{
			tipoSistema = WINDOWS;
			pathName = JOptionPane.showInputDialog("Digite o nome do Arquivo: " ); 
		}
        // Verifica se extensão do arquivo é lmr		
		if (pathName.substring(pathName.indexOf('.')+1).equalsIgnoreCase("lmr")!=true){
           escreveMensagem("A extensão do arquivo deve ser \"lmr\".", tipoSistema);
		}
		// le arquivo
		fonte = leArquivo(pathName);
		escreveMensagem(fonte.toString(), tipoSistema);
		
		// compilado recebe String que vem da Class compilador,que faz a leitura do arquivo lido e retorna se linguagem é ACEITA ou REIJEITADA
		// Grava arquivo compilado 
		compilado = compilador(); // Atribuído somente para teste
		pathNameCompilado = pathName.substring(0, pathName.indexOf('.')+1)+"cmp";
		gravaArquivo(pathNameCompilado, compilado.toString());
	}
        /*
        Copilador pega o Arquivo que foi lido identificando cada item léxico por meio de um AFD e armazenando os dados
	na StringBuider, e retorna o mesmo dizendo se aceita aquela linguagem ou não
        */
            public static StringBuilder compilador(){
                StringBuilder fonteLMR = new StringBuilder("");
                
                String resposta;
                Scanner scan = new Scanner(fonte.toString());
                                while (scan.hasNextLine()) {
                                   String linha = scan.nextLine();
                                   if((q0(linha.toString(),0) == 0)){
                                        resposta="aceita";
                                    }else{
                                        resposta="rejeita";
                                    }
                                    fonteLMR.append(linha+" "+resposta+"\r\n");
                                }
                 return fonteLMR;
            }
	
	public static int q0(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, REJEITADA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
		  switch( cadeia.charAt(pos)){
		  	case 'i': situacao = q1(cadeia, pos+1);
		  		break;
                        case 'f': situacao = q7(cadeia, pos+1);
		  		break;
                        case 'm': situacao = q9(cadeia, pos+1);
		  		break;
                        case 's': situacao = q16(cadeia, pos+1);
		  		break;
                        case 'g': situacao = q23(cadeia, pos+1);
		  		break;
                        case 'p': situacao = q30(cadeia, pos+1);
		  		break;
                        case '+': situacao = q40(cadeia, pos+1);
		  		break;
                        case '-': situacao = q40(cadeia, pos+1);
		  		break;
                          case '1': situacao = q41(cadeia, pos+1);
				  break;
                          case '2': situacao = q41(cadeia, pos+1);
				  break;
                          case '3': situacao = q41(cadeia, pos+1);
				  break;
                          case '4': situacao = q41(cadeia, pos+1);
				  break;
                          case '5': situacao = q41(cadeia, pos+1);
				  break;
                          case '6': situacao = q41(cadeia, pos+1);
				  break;
                          case '7': situacao = q41(cadeia, pos+1);
				  break;
                          case '8': situacao = q41(cadeia, pos+1);
				  break;
                          case '9': situacao = q41(cadeia, pos+1);
				  break;
		  	default: situacao = REJEITADA;
		  }
		}
		if (situacao != ACEITA) return REJEITADA;
		else return ACEITA;
	}
	
	public static int q1(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, REJEITADA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'n': situacao = q2(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
	}
	
	public static int q2(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, REJEITADA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'i': situacao = q3(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
        
        public static int q3(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, REJEITADA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'c': situacao = q4(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
        
          public static int q4(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, REJEITADA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'i': situacao = q5(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
          
            public static int q5(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, REJEITADA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'o': situacao = q6(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
               public static int q7(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, REJEITADA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'i': situacao = q9(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                     public static int q8(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'm': situacao = q6(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
             public static int q9(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'o': situacao = q10(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
             public static int q10(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'v': situacao = q11(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
            public static int q11(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case '_': situacao = q12(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }   
               public static int q12(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'h': situacao = q13(cadeia, pos+1);
				  break;
                          case 'v': situacao = q15(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q13(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'o': situacao = q14(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
               public static int q14(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'r': situacao = q6(cadeia, pos+1);
				  break;
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                 public static int q15(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'e': situacao = q14(cadeia, pos+1);
				  break;
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q16(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'e': situacao = q20(cadeia, pos+1);
				  break;
                          case 'o': situacao = q17(cadeia, pos+1);
				  break;
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q17(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'l': situacao = q18(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q18(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 't': situacao = q19(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                    public static int q19(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'a': situacao = q6(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                  public static int q20(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'g': situacao = q21(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                  
                      public static int q21(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'u': situacao = q22(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q22(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'r': situacao = q19(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
             
                public static int q23(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'i': situacao = q24(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                       
                public static int q24(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'r': situacao = q25(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q25(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'a': situacao = q26(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q26(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case '_': situacao = q27(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q27(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'g': situacao = q28(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q28(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'a': situacao = q29(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q29(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'r': situacao = q22(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q30(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'o': situacao = q31(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q31(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 's': situacao = q32(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
             public static int q32(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case '_': situacao = q33(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
              public static int q33(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'i': situacao = q34(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
               public static int q34(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'n': situacao = q35(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
              public static int q35(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'i': situacao = q36(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
              public static int q36(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'c': situacao = q37(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
              public static int q37(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'i': situacao = q38(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
              public static int q38(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'a': situacao = q39(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                public static int q39(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
			  case 'l': situacao = q6(cadeia, pos+1);
				  break;
                          
                         
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
               public static int q40(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
                          
			  case '1': situacao = q41(cadeia, pos+1);
				  break;
                          case '2': situacao = q41(cadeia, pos+1);
				  break;
                          case '3': situacao = q41(cadeia, pos+1);
				  break;
                          case '4': situacao = q41(cadeia, pos+1);
				  break;
                          case '5': situacao = q41(cadeia, pos+1);
				  break;
                          case '6': situacao = q41(cadeia, pos+1);
				  break;
                          case '7': situacao = q41(cadeia, pos+1);
				  break;
                          case '8': situacao = q41(cadeia, pos+1);
				  break;
                          case '9': situacao = q41(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
               public static int q41(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = ACEITA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
                          case '0': situacao = q41(cadeia, pos+1);
				  break;
			  case '1': situacao = q41(cadeia, pos+1);
				  break;
                          case '2': situacao = q41(cadeia, pos+1);
				  break;
                          case '3': situacao = q41(cadeia, pos+1);
				  break;
                          case '4': situacao = q41(cadeia, pos+1);
				  break;
                          case '5': situacao = q41(cadeia, pos+1);
				  break;
                          case '6': situacao = q41(cadeia, pos+1);
				  break;
                          case '7': situacao = q41(cadeia, pos+1);
				  break;
                          case '8': situacao = q41(cadeia, pos+1);
				  break;
                          case '9': situacao = q41(cadeia, pos+1);
				  break;
                          case '.': situacao = q42(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != ACEITA) return REJEITADA;
		else return ACEITA;
        }
                 public static int q42(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = REJEITADA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
                          case '0': situacao = q43(cadeia, pos+1);
				  break;
			  case '1': situacao = q43(cadeia, pos+1);
				  break;
                          case '2': situacao = q43(cadeia, pos+1);
				  break;
                          case '3': situacao = q43(cadeia, pos+1);
				  break;
                          case '4': situacao = q43(cadeia, pos+1);
				  break;
                          case '5': situacao = q43(cadeia, pos+1);
				  break;
                          case '6': situacao = q43(cadeia, pos+1);
				  break;
                          case '7': situacao = q43(cadeia, pos+1);
				  break;
                          case '8': situacao = q43(cadeia, pos+1);
				  break;
                          case '9': situacao = q43(cadeia, pos+1);
				  break;
                          
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != REJEITADA) return ACEITA;
		else return REJEITADA;
        }
                 
             public static int q43(String cadeia, int pos){
		// Variável local que indica a situaçao de avaliação inicial da cadeia 
		// ao entrar no estado, ou seja, ACEITA.
		int situacao = ACEITA;
		if (cadeia.length()> pos){
			  switch( cadeia.charAt(pos)){
                          case '0': situacao = q43(cadeia, pos+1);
				  break;
			  case '1': situacao = q43(cadeia, pos+1);
				  break;
                          case '2': situacao = q43(cadeia, pos+1);
				  break;
                          case '3': situacao = q43(cadeia, pos+1);
				  break;
                          case '4': situacao = q43(cadeia, pos+1);
				  break;
                          case '5': situacao = q43(cadeia, pos+1);
				  break;
                          case '6': situacao = q43(cadeia, pos+1);
				  break;
                          case '7': situacao = q43(cadeia, pos+1);
				  break;
                          case '8': situacao = q43(cadeia, pos+1);
				  break;
                          case '9': situacao = q43(cadeia, pos+1);
				  break;
			  default: situacao = REJEITADA;
			}
		}
		if (situacao != ACEITA) return REJEITADA;
		else return ACEITA;
        }
            public static int q6(String cadeia, int pos){
		// Ao entrar no q6 que é um Estado final que dizer,que aquele linguagem foi aceita!
                //E já passou por toda as Verificações possiveis
                int situacao = ACEITA;
		if (cadeia.length()> pos){
                   situacao = REJEITADA;
                }
               
                if (situacao != ACEITA) return REJEITADA; 
		return ACEITA;
        }
}

