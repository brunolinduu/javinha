package com.mycompany.batalhanaval;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class BatalhaNaval {
    public static void main(String[] args) {
        Scanner Ler = new Scanner(System.in);
        int escolhaModo = 0;

        
        do {

            
    System.out.println("Escolha o modo de jogo:");
    System.out.println("1 - Player vs. Player");
    System.out.println("2 - Player vs. PC");

    try {
        escolhaModo = Ler.nextInt();
        Ler.nextLine(); // Limpar o buffer do teclado

        if (escolhaModo < 1 || escolhaModo > 2) {
            System.out.println("Não deu. Tente novamente.");
            escolhaModo = 0; // Definir um valor inválido para repetir o loop
        }
    } catch (InputMismatchException e) {
        System.out.println("Não deu. Tente novamente.");
        Ler.nextLine();
    }
    } while (escolhaModo < 1 || escolhaModo > 2);

        System.out.print("Jogador 1, digite seu nome:");
        String nomeJogador1 = Ler.nextLine();
        Tabuleiro jogador1 = criarTabuleiro(nomeJogador1, Ler);

        Tabuleiro jogador2;
        String nomeJogador2;

        if (escolhaModo == 1) {
            System.out.print("\nJogador 2, digite seu nome:");
            nomeJogador2 = Ler.nextLine();
            jogador2 = criarTabuleiro(nomeJogador2, Ler);
        } else {
            nomeJogador2 = "Máquina";
            jogador2 = criarTabuleiroMaquina(nomeJogador2);
        }

        // Iniciar jogo
        System.out.println("\nComeço do jogo");
        boolean jogoFinalizado = false;
        Tabuleiro jogadorAtual = jogador1;
        Tabuleiro oponente = jogador2;

        while (!jogoFinalizado) {
            System.out.println("\nVez de " + jogadorAtual.getNomeJogador());
            oponente.exibirMapaAtaque();

            boolean posicaoValida = false;
            int linha, coluna;

            if (jogadorAtual.getNomeJogador().equals("Máquina")) {
                // Jogada da máquina (aleatória)
                Random Aleatorio = new Random();
                do {
                    linha = Aleatorio.nextInt(Tabuleiro.TAMANHO);
                    coluna = Aleatorio.nextInt(Tabuleiro.TAMANHO);

                    if (oponente.posicaoJaAtacada(linha, coluna)) {
                        System.out.println("A Máquina já atacou. Escolha outra posição.");
                    } else {
                        posicaoValida = true;
                    }
                } while (!posicaoValida);
            } else {
                // Jogada do jogador

             do {
            System.out.println("Digite aonde atacar (1-10):");
            linha = Ler.nextInt();
            Ler.nextLine();

            linha--;

            System.out.println("Digite a coluna para atacar (A-J):");
            char colunaChar = Character.toUpperCase(Ler.nextLine().charAt(0));
            coluna = colunaChar - 'A';

            if (linha < 0 || linha >= 10 || coluna < 0 || coluna >= 10) {
                System.out.println("Coordenadas inválidas. vai denovo.");
            } else if (oponente.posicaoJaAtacada(linha, coluna)) {
                System.out.println("Você já atacou nessa posição. Escolha outra posição.");
            } else {
                posicaoValida = true;
            }
        } while (!posicaoValida);
                    }

            boolean acertou = oponente.atacarPosicao(linha , coluna);

            if (acertou) {
                System.out.println("Parabéns! Você acertou um barco!");
                oponente.exibirMapaAtaque();
                if (oponente.verificarFimDeJogo()) {
                    System.out.println("\n*** " + jogadorAtual.getNomeJogador() + " VENCEU! ***");
                    jogoFinalizado = true;
                }
            } else {
                System.out.println("Água! Não foi dessa vez...");
                oponente.exibirMapaAtaque();
                jogadorAtual = (jogadorAtual == jogador1) ? jogador2 : jogador1; // Trocar de jogador
                oponente = (oponente == jogador1) ? jogador2 : jogador1;
            }
        }
    }

    private static Tabuleiro criarTabuleiro(String nomeJogador, Scanner Ler) {
        Tabuleiro tabuleiro = new Tabuleiro(nomeJogador);
        Scanner ler = new Scanner(System.in);
        int escolha = 0;

        do {
    System.out.println("Escolha o tipo de locação de barcos:");
    System.out.println("1 - Locação Manual ");
    System.out.println("2 - Locação Aleatória");

    try {
        escolha = ler.nextInt();
        ler.nextLine(); 

        if (escolha < 1 || escolha > 2) {
            System.out.println("Opção inválida. Tente novamente.");
            escolha = 0;
        }
    } catch (InputMismatchException e) {
        System.out.println("Opção inválida. Tente novamente.");
        ler.nextLine(); 
    }
} while (escolha < 1 || escolha > 2);

        if (escolha == 1) {
            System.out.println(nomeJogador + ", posicione seus barcos manualmente:");
            System.out.println("exemplo(A1)");
            tabuleiro.preencherTabuleiroManualmente(Ler);
        } else if (escolha == 2) {
            System.out.println(nomeJogador + ", posicionando seus barcos automaticamente...");
            tabuleiro.preencherTabuleiroAutomaticamente();
        }

        return tabuleiro;
    }

    private static Tabuleiro criarTabuleiroMaquina(String nomeJogador) {
        Tabuleiro tabuleiro = new Tabuleiro(nomeJogador);
        System.out.println("Posicionando os barcos da Máquina automaticamente...");
        tabuleiro.preencherTabuleiroAutomaticamente();
        return tabuleiro;
    }
}

