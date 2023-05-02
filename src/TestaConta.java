import java.util.Scanner;

public class TestaConta {

  public static void menu() {
    System.out.println("Saldo Insufiente ");
    System.out.println("Saldo Disponivel ");
    System.out.println("****************************************************************");
    System.out.println("1-CONTA POUPANÇA");
    System.out.println("2-CONTA CORRENTE");
    System.out.println("3-CONTA ESPECIAL");
    System.out.println("4-CONTA EMPRESA");
    System.out.println("5-CONTA ESTUDANTIL");
    System.out.println("6-SAIR");
    System.out.print("\nDIGITE A OPÇÃO:");
  }

  public static void menuDois(float conta, String banco) {
    System.out.println("\n\n************************************************************");
    System.out.println("Saldo Insufiente ");
    System.out.println("Saldo Disponivel");
    System.out.println("****************************************************************");
    System.out.println("CONTA" + "[" + banco + "]");
    System.out.println("\nSaldo Atual: R$ " + conta);
    System.out.println("CONTA" + "[" + banco + "]");
    System.out.println("MOVIMENTO - D- débito ou C-crédito: ");
  }

  public static void menuContaEmprestimo(float valorEmprestimo) {
    System.out.println("Emprestimo pré aprovado no valor de R$ " + valorEmprestimo + " Confirma a operação ? S/N: ");
  }
  

  public static void main(String[] args) {

    try (Scanner input = new Scanner(System.in)) {
      ContaPoupanca contaP = new ContaPoupanca(1090, "290.123-38", 4000, true, 22);
      ContaCorrente contaC = new ContaCorrente(89, "320.903.467-38", 5600, true);
      ContaEspecial contaE = new ContaEspecial(1004, "074.764.478-10", 200.00f, true);
      ContaEmpresa contaEmpresa = new ContaEmpresa(2090, "123.456-78", 15000.90f, true);
      ContaEstudantil contaEstudante = new ContaEstudantil(3156, "789.123.456-09", 300.00f, true);
      int opcao, dia, movimentoLoop = 10, qtdCheque;
      char movimento, continuar = 'S', solicitarCheque, pegarEmprestimo = 'S';
      float valor, auxLimite, valorEmprestimo;

      menu();
      opcao = input.nextInt();

      switch (opcao) {
        case 1:
          if (contaP.ativo) {
            while (continuar == 'S' || continuar == 's') {
              if(movimentoLoop != 0){
                if(contaP.saldo > -1){
                  System.out.println("Digite a data aniversario da poupanca: ");
                  dia = input.nextInt();
                  contaP.correcao(dia);
                  menuDois(contaP.saldo, "POUPANÇA");
                  movimento = input.next().charAt(0);
                  System.out.println("Valor de movimentação: R$: ");
                  valor = input.nextFloat();
                  if (movimento == 'D' || movimento == 'd') {
                    contaP.debito(valor);
                  }
                  if (movimento == 'C' || movimento == 'c') {
                    contaP.credito(valor);
                  }
                  movimentoLoop -= 1;
                  System.out.println("Continuar S/N: ");
                  continuar = input.next().charAt(0);
                } else {
                  System.out.println("Conta Negativa!!!");
                  break;
                }
              }
              else {
                System.out.println("Limite de 10 movimentações");
                break;
              }              
            }
          } else {
            System.out.println("Conta Inativa");
          }
          break;
        case 2:
          if (contaC.ativo) {
            while (continuar == 'S' || continuar == 's') {
              if(movimentoLoop != 0) {
               if(contaC.saldo > -1){
                  menuDois(contaC.saldo, "CORRENTE");
                  movimento = input.next().charAt(0);
                  System.out.println("Valor movimentoção: R$: ");
                  valor = input.nextFloat();
                  if (movimento == 'D' || movimento == 'd') {
                    contaC.debito(valor);
                  }
                  if (movimento == 'C' || movimento == 'c') {
                    contaC.credito(valor);
                  }
                  movimentoLoop -= 1;
                  System.out.println("Continuar S/N: ");
                  continuar = input.next().charAt(0);
                } else {
                  System.out.println("Conta negativa!!!");
                  break; 
                }
              }
              else {
                System.out.println("Limite de 10 movimentações");
                break;
              }
            }
            System.out.println("Valor dispinivel de Cheque Especial S/N ? ");
            solicitarCheque = input.next().charAt(0);
            if(contaC.saldo > 0){
              if (solicitarCheque == 's' || solicitarCheque == 's') {
                System.out.println("Digite a quantidade de talões:  ");
                qtdCheque = input.nextInt();
                if (qtdCheque <= 3) {
                  contaC.pedirTalao(qtdCheque);
                  contaC.debito((qtdCheque * 30));
                  menuDois(contaC.saldo, "CORRENTE");
                } else {
                  System.out.println("Limitado até 3 unidades de talões!");
                }
              }
            } else {
              System.out.println("Conta negativa!!!");
              break; 
            }
              
          } else {
            System.out.println("Conta Inativa");
          }
          break;
        case 3:
          if (contaE.ativo) {
            contaE.setLimite(1000.00f);
            while (continuar == 's' || continuar == 's') {
              if(movimentoLoop != 0) {
                menuDois(contaE.saldo, "ESPECIAL");
                movimento = input.next().charAt(0);
                System.out.println("Valor movimento: R$: ");
                valor = input.nextFloat();
                if (contaE.getLimite() != 0) {
                  if (movimento == 'd' || movimento == 'd') {
                    if (contaE.saldo == 0) {
                      contaE.usarLimite(valor);
                      contaE.saldo = 0;
                    }
                    if (contaE.saldo < valor && contaE.saldo != 0) {
                      valor = valor - contaE.saldo;
                      contaE.saldo = 0;
                      contaE.usarLimite(valor);
                    }
                    if (contaE.saldo >= valor) {
                      contaE.debito(valor);
                    }
                    System.out.println("Total de saldo de limite : " + contaE.getLimite());
                    System.out.println("Saldo de Conta Especial é de: " + contaE.saldo);
                  }
                  if (movimento == 'c' || movimento == 'c') {

                    if (contaE.getLimite() < 1000) {
                      auxLimite = 1000 - contaE.getLimite();
                      valor = valor - auxLimite;
                      contaE.saldo = valor;
                      contaE.setLimite(1000.00f);
                    }
                    if (contaE.getLimite() == 1000) {
                      contaE.credito(valor);
                    }
                    System.out.println("Total de limite disponivel: " + contaE.getLimite());
                    System.out.println("Saldo de Conta Especial é de: " + contaE.saldo);
                        menuDois(contaE.saldo, "ESPECIAL");
                    }
                  } else {
                    System.out.println("Excedeu o limite de crédito");
                    break;
                  }
                  movimentoLoop -= 1;
                  System.out.println("Continuar S/N: ");
                  continuar = input.next().charAt(0);
              } 
              else {
                System.out.println("Excedeu o limite de 10 movimentações");
                break;
              }
            }

          } else {
            System.out.println("Conta Inativa");
          }
          break;
        case 4:
       
        if (contaEmpresa.ativo) {
          contaEmpresa.setEmprestimoEmpresa(10000);
          while (continuar == 's' || continuar == 's') {
            if(movimentoLoop != 0) {     
                if(contaEmpresa.saldo > -1) {
                  menuDois(contaEmpresa.saldo, "EMPRESA");
                  movimento = input.next().charAt(0);
                  System.out.println("Valor movimento: R$: ");
                  valor = input.nextFloat();
                  if (movimento == 'd' || movimento == 'd') {
                    contaEmpresa.debito(valor);
                  }
                  if (movimento == 'c' || movimento == 'c') {
                    contaEmpresa.credito(valor);
                  }
                  menuContaEmprestimo(contaEmpresa.getEmprestimoEmpresa());
                  pegarEmprestimo = input.next().charAt(0);
                  if(pegarEmprestimo == 's' || pegarEmprestimo == 's') {
                    if(contaEmpresa.getEmprestimoEmpresa() > 0) {
                      System.out.println("Valor Emprestimo: ");
                      valorEmprestimo = input.nextFloat();
                      contaEmpresa.pedriEmprestimo(valorEmprestimo);
                      contaEmpresa.saldo += valorEmprestimo;
                      System.out.println("Saldo Atual: R$ " + contaEmpresa.saldo);
                      System.out.println("limite disponivel de: R$ " + contaEmpresa.getEmprestimoEmpresa());
                    }else {
                      System.out.println("Excedeu o limite de emprestimo");
                    }                    
                  } 
                  movimentoLoop -= 1;
                  System.out.println("Continuar S/N: ");
                  continuar = input.next().charAt(0);
                } 
                else {
                  System.out.println("Saldo negativo");
                  break; 
                }
            } else {
                if(movimentoLoop == 10) {
                  System.out.println("Valor de emprestimo: ");
                  valorEmprestimo = input.nextFloat();
                  contaEmpresa.pedriEmprestimo(valorEmprestimo);
                  System.out.println("Saldo Atual: R$ " + contaEmpresa.saldo);
                  System.out.println("Limite disponivel de: R$ " + contaEmpresa.getEmprestimoEmpresa());
                }
                System.out.println("Excedeu as 10 movimentações disponíveis");
                break;
              }          
          }

        } else {
          System.out.println("Conta Inativa");
        }
        break;
        case 5:
        if (contaEstudante.ativo) {
          contaEstudante.setLimiteEstudantil(5000.00f);
          while (continuar == 's' || continuar == 's') {
            if(movimentoLoop != 0) {     
                if(contaEstudante.saldo > -1) {
                  menuDois(contaEstudante.saldo, "ESTUDANTIL");
                  movimento = input.next().charAt(0);
                  System.out.println("Valor movimento: R$: ");
                  valor = input.nextFloat();
                  if (movimento == 'd' || movimento == 'd') {
                    contaEstudante.debito(valor);
                  }
                  if (movimento == 'C' || movimento == 'c') {
                    contaEstudante.credito(valor);
                  }
                  menuContaEmprestimo(contaEstudante.getLimiteEstudantil());
                  pegarEmprestimo = input.next().charAt(0);
                  if(pegarEmprestimo == 's' || pegarEmprestimo == 's') {
                    if(contaEstudante.getLimiteEstudantil() > 0) {
                      System.out.println("Valor Emprestimo: ");
                      valorEmprestimo = input.nextFloat();
                      contaEstudante.usarEstudantil(valorEmprestimo);
                      contaEstudante.saldo += valorEmprestimo;
                      System.out.println("Saldo Atual: R$ " + contaEstudante.saldo);
                      System.out.println("Limite disponivel: R$ " + contaEstudante.getLimiteEstudantil());
                    }else {
                      System.out.println("Excedeu o limite de emprestimo");
                    }                    
                  } 
                  movimentoLoop -= 1;
                  System.out.println("Continuar S/N: ");
                  continuar = input.next().charAt(0);
                } 
                else {
                  System.out.println("Conta Negativa");
                  break; 
                }
            } else {
                if(movimentoLoop == 10) {
                  System.out.println("Valor Emprestimo: ");
                  valorEmprestimo = input.nextFloat();
                  contaEstudante.usarEstudantil(valorEmprestimo);
                  System.out.println("Saldo Atual: R$ " + contaEstudante.saldo);
                  System.out.println("Você ainda possui um limite de: R$ " + contaEstudante.getLimiteEstudantil());
                }
                System.out.println("Excedeu as 10 movimentações");
                break;
              }          
          }

        } else {
          System.out.println("Conta Inativa");
        }
        break;
        case 6:
        break;
      }
    }
  }

}
