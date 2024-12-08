package types;

import java.util.Scanner;

import types.Bottle;
import types.Filling;
import types.Game;
import types.Table;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Water Sort Puzzle!");

        // Criando uma mesa de jogo com símbolos, número de símbolos, semente e capacidade
        Filling[] symbols = {Filling.SMILE, Filling.SAD, Filling.ANGEL, Filling.BLIINK};
        int numberOfUsedSymbols = 4;
        int seed = 12345;
        int capacity = 5;
        Table table = new Table(symbols, numberOfUsedSymbols, seed, capacity);

        // Criando o jogo com a mesa criada
        Game game = new Game(symbols, numberOfUsedSymbols, seed, capacity);

        // Exibindo a mesa inicial
        System.out.println("Estado inicial da mesa:");
        System.out.println(table);

        // Iniciando o jogo
        int round = 1;
        while (!game.isRoundFinished()) {
            System.out.println("----- Rodada " + round + " -----");
            System.out.println("Por favor, faça uma jogada. Insira o número do recipiente de origem e o número do recipiente de destino (Exemplo: 1 2):");
            int sourceIndex = scanner.nextInt();
            int destinationIndex = scanner.nextInt();

            // Fazendo a jogada
            game.play(sourceIndex, destinationIndex);

            // Exibindo o estado atual da mesa
            System.out.println("Estado atual da mesa:");
            System.out.println(table);

            round++;
        }

        // Final do jogo
        System.out.println("Parabéns! Você completou todas as rodadas do jogo.");
        System.out.println("Pontuação final: " + game.score());

        scanner.close();
    }
}

