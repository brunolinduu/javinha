package com.mycompany.batalhanaval;

import java.util.Random;
import java.util.Scanner;



class Tabuleiro {
    public static final int TAMANHO = 10;
    private char[][] grade;
    private char[] letras;
    private String nomeJogador;
    private boolean[][] posicoesAtacadas;

    public Tabuleiro(String nomeJogador) {
        this.nomeJogador = nomeJogador;
        grade = new char[TAMANHO][TAMANHO];
        letras = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        posicoesAtacadas = new boolean[TAMANHO][TAMANHO];
        inicializarTabuleiro();
    }
    
    private void inicializarTabuleiro() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                grade[i][j] = '-';
            }
        }
    }

    public void preencherTabuleiroAutomaticamente() {
        int[] tamanhos = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        Random Aleatorio= new Random();

        for (int tamanho : tamanhos) {
            boolean posicaoDefinida = false;

            while (!posicaoDefinida) {
                int linha = Aleatorio.nextInt(TAMANHO);
                int coluna = Aleatorio.nextInt(TAMANHO);
                boolean vertical = Aleatorio.nextBoolean();

                if (posicaoDisponivel(linha, coluna, tamanho, vertical)) {
                    posicionarBarco(linha, coluna, tamanho, vertical);
                    posicaoDefinida = true;
                }
            }
        }
    }

    public void preencherTabuleiroManualmente(Scanner Ler) {
    int[] tamanhos = { 4, 3, 3, 2, 2, 2, 1, 1, 1, 1 };

    for (int tamanho : tamanhos) {
        boolean posicaoValida = false;

        while (!posicaoValida) {
            exibirMapa();
            System.out.printf("Digite as coordenadas para um barco de tamanho %d: ", tamanho);
            String coordenadas = Ler.nextLine().toUpperCase();

            if (coordenadas.length() < 2) {
                System.out.println("Coordenadas inválidas. Tente novamente.");
                continue;
            }

            char colunaChar = coordenadas.charAt(0);
            if (colunaChar < 'A' || colunaChar > 'J') {
                System.out.println("Coordenadas inválidas. Tente novamente.");
                continue;
            }
            int coluna = colunaChar - 'A';

            try {
                int linha = Integer.parseInt(coordenadas.substring(1)) - 1;

                System.out.print("Digite a orientação (V para vertical, H para horizontal): ");
                char orientacao = Ler.nextLine().toUpperCase().charAt(0);
                boolean vertical = (orientacao == 'V');

                if (posicaoDisponivel(linha, coluna, tamanho, vertical)) {
                    posicionarBarco(linha, coluna, tamanho, vertical);
                    System.out.println("Barco posicionado com sucesso!");
                    posicaoValida = true;
                } else {
                    System.out.println("Posição inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Coordenadas inválidas. Tente novamente.");
            }
        }

        exibirMapa();
    }
}


    private boolean posicaoDisponivel(int linha, int coluna, int tamanho, boolean vertical) {
        if (vertical) {
            if (linha + tamanho > TAMANHO) {
                return false;
            }

            for (int i = linha; i < linha + tamanho; i++) {
                if (grade[i][coluna] != '-') {
                    return false;
                }
            }
        } else {
            if (coluna + tamanho > TAMANHO) {
                return false;
            }

            for (int j = coluna; j < coluna + tamanho; j++) {
                if (grade[linha][j] != '-') {
                    return false;
                }
            }
        }

        return true;
    }

    private void posicionarBarco(int linha, int coluna, int tamanho, boolean vertical) {
        if (vertical) {
            for (int i = linha; i < linha + tamanho; i++) {
                grade[i][coluna] = 'B';
            }
        } else {
            for (int j = coluna; j < coluna + tamanho; j++) {
                grade[linha][j] = 'B';
            }
        }
    }

    public void exibirMapa() {
        System.out.print("\n  ");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(letras[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < TAMANHO; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(grade[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void exibirMapaAtaque() {
        System.out.print("\n  ");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(letras[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < TAMANHO; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < TAMANHO; j++) {
                if (posicoesAtacadas[i][j]) {
                    System.out.print(grade[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }


    public boolean atacarPosicao(int linha, int coluna) {
    posicoesAtacadas[linha][coluna] = true;
    if (grade[linha][coluna] == 'B') {
        grade[linha][coluna] = 'X';
        return true;
    } else {
        grade[linha][coluna] = 'O';
        return false;
    }
}

    public boolean posicaoJaAtacada(int linha, int coluna) {
        return posicoesAtacadas[linha][coluna];
    }

    public boolean verificarFimDeJogo() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (grade[i][j] == 'B' && !posicoesAtacadas[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }
}

