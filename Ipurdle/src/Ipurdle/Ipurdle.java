/**
 * @author Sérgio Domingues (nº 51994)
 * @author Miguel Tristão (nº 62501)
 */

package Ipurdle;

import java.util.Scanner;

public class Ipurdle {
    public static void main(String[] args) {
    	
        int maxAttempts = 6;
        DictionaryIP gameWordsDictionary = new DictionaryIP(5);
        DictionaryIP puzzlesDictionary = new DictionaryIP(5);
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= maxAttempts; i++) {
            System.out.print("Palavra a jogar?: ");
            String guess = scanner.next().toUpperCase();

            if (guess.length() != 5 || !guess.matches("[A-Z]+")) {
                System.out.println("Palavra inválida, tamanho errado.");
                continue;
            }

            if (!gameWordsDictionary.isValid(guess)) {
                System.out.println("Palavra inválida, não existe no dicionário.");
                continue;
            }

            int clue = playGuess(puzzlesDictionary, guess);
            printClue(guess, clue);

            if (clue == 33333) {
                System.out.println("Parabéns, encontraste a palavra secreta!");
                break;
            }
        }
        
        scanner.close();
    }
    
    /**
     * Avalia a tentativa de adivinhar a palavra
     *
     * @param puzzlesDictionary O dicionário de palavras associado ao jogo.
     * @param guess A palavra a ser avaliada.
     * @requires puzzlesDictionary != null && guess.length() == 5
     * @return O valor inteiro da pista
     * @ensures O dicionário de palavras é modificado, removendo palavras inválidas com base na tentativa.
     */

    public static int playGuess(DictionaryIP puzzlesDictionary, String guess) {
        int clue = betterClueForGuess(puzzlesDictionary, guess);
        removeInvalidWords(puzzlesDictionary, guess, clue);
        return clue;
    }
    
    /**
     * Determina a pista mais apropriada para uma tentativa de palavra em um jogo de adivinhação.
     *
     * @param puzzlesDictionary O dicionário de palavras associado ao jogo.
     * @param guess A palavra a ser avaliada em comparação com as palavras do dicionário.
     * @requires puzzlesDictionary != null && guess.length() == 5
     * @return O valor inteiro da melhor pista
     * @ensures Que é retornada a melhor pista para o jogador
     */

    public static int betterClueForGuess(DictionaryIP puzzlesDictionary, String guess) {
    	int size = puzzlesDictionary.getWordSize();
        int bestClue = minClue(size);
        int bestCount = Integer.MAX_VALUE;

        for (int i = 0; i < puzzlesDictionary.lenght(); i++) {
            String word = puzzlesDictionary.getWord(i);
            int clue = clueForGuessAndWord(guess, word);
            int count = howManyWordsWithClue(puzzlesDictionary, clue, guess);

            if (count < bestCount) {
                bestCount = count;
                bestClue = clue;
            }
        }
        return bestClue;
        
    }
    
    /**
     * Remove palavras inválidas do dicionário com base em uma tentativa de palavra e sua dica.
     *
     * @param gameWordsDictionary O dicionário de palavras associado ao jogo.
     * @param guess A tentativa de palavra.
     * @param clue A dica associada à tentativa de palavra.
     * @ensures O dicionário é modificado, removendo palavras que não têm a mesma dica que a fornecida.
     */

    public static void removeInvalidWords(DictionaryIP gameWordsDictionary, String guess, int clue) {
        for (int i = 0; i < gameWordsDictionary.lenght(); i++) {
            String word = gameWordsDictionary.getWord(i);
            if (clueForGuessAndWord(guess, word) != clue) {
            	gameWordsDictionary.selectForRemove(i);
            }
        }
        gameWordsDictionary.removeSelected();
    }
    
    /**
     * Determina a pista para uma tentativa de palavra comparada com uma palavra específica.
     *
     * @param guess A palavra a ser comparada.
     * @param word A palavra com a qual a tentativa será comparada.
     * @requires guess.length() == word.length()
     * @return O valor inteiro da pista da palavra a adivinhar.
     * @ensures A pista é calculada corretamente quando é comparada a guess com a word.
     */

    public static int clueForGuessAndWord(String guess, String word) {
        int clue = 0;
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            char wordChar = word.charAt(i);

            if (guessChar == wordChar) {
                clue = clue * 10 + 3;
            } else if (word.contains(String.valueOf(guessChar))) {
                clue = clue * 10 + 2;
            } else {
                clue = clue * 10 + 1;
            }
        }
        return clue;
    }
    
    /**
     * Conta quantas palavras no dicionário têm a mesma pista que a fornecida para uma tentativa de palavra.
     *
     * @param puzzlesDictionary O dicionário de palavras associado ao jogo.
     * @param clue A dica para a qual as palavras serão comparadas.
     * @param guess A palavra de referência para comparação.
     * @requires puzzlesDictionary != null && clue >= 0 && guess.length() == 5
     * @return O valor inteiro do número de palavras no dicionário que têm a mesma pista que a fornecida para a tentativa.
     */

    public static int howManyWordsWithClue(DictionaryIP puzzlesDictionary, int clue, String guess) {
        int count = 0;
        for (int i = 0; i < puzzlesDictionary.lenght(); i++) {
            String word = puzzlesDictionary.getWord(i);
            if (clueForGuessAndWord(guess, word) == clue) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Imprime a palavra colorida conforme a pista da palavra a adivinhar.
     *
     * @param guess A palavra a ser imprimida.
     * @param clue A pista associada à tentativa.
     * @ensures A palavra imprimida é colorida conforme a pista.
     */

    public static void printClue(String guess, int clue) {
        StringBuilder colorido = new StringBuilder();

        int divisor = (int) Math.pow(10, guess.length() - 1);

        for (int i = 0; i < guess.length(); i++) {
            char c = guess.charAt(i);
            String cor;

            int digito = clue / divisor;
            clue %= divisor;
            divisor /= 10;

            if (digito == 3) {
                cor = StringColouring.toColoredString(Character.toString(c), StringColouring.GREEN);
            } else if (digito == 2) {
                cor = StringColouring.toColoredString(Character.toString(c), StringColouring.YELLOW);
            } else {
                cor = StringColouring.toColoredString(Character.toString(c), StringColouring.BLACK);
            }

            colorido.append(cor);
        }

        System.out.println(colorido.toString());
    }
    
    /**
    * Gera uma pista minima com base no tamanho especificado.
    *
    * Este metodo cria uma cadeia de caracteres de pista com todos os digitos
    * definidos como '1' e devolve o valor inteiro correspondente. O parametro
    * size determina o comprimento da cadeia de pista. Se o tamanho for menor
    * ou igual a 0, imprime uma mensagem a indicar que a pista é invalida e
    * retorna o valor 0.
    *
    * @param size O tamanho da pista.
    * @requires size > 0
    * @ensures Que temos a menor pista para uma palavra de tamanho size
    * @return O valor inteiro da pista.
    */

    public static int minClue(int size) {
        if (size <= 0) {
            System.out.println("Pista Invalida");
            return 0;
        } else {
            StringBuilder menorPista = new StringBuilder();
            for (int i = 1; i <= size; i++) {
                menorPista.append("1");
            }
            return Integer.valueOf(menorPista.toString());
        }
    }
    
    /**
     * Verifica se um numero inteiro clue representa uma pista valida para uma palavra de tamanho especificado size, seguindo as condicoes especificadas:
     * @param clue O numero da pista
     * @param size O tamanho da pista
     * @requires (clue <= 0)
     * @return {@code false} se não for válido {@code true} se for válido
     */

    public static boolean validClue(int clue, int size) {
        if (clue <= 0) {
            return false;
        }
        String clueStr = Integer.toString(clue);
        if (clueStr.length() != size) {
            return false;
        }
        for (int i = 0; i < clueStr.length(); i++) {
            char digit = clueStr.charAt(i);
            if (digit != '1' && digit != '2' && digit != '3') {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Verifica se a pista dada e a maior pista para palavras de um determinado tamanho.
     *
     * @param clue A pista para palavras.
     * @param size O tamanho das palavras associadas a pista.
     * @requires size > 0
     * @return {@code true} se a pista dada e a maior para o tamanho especificado, {@code false} caso contrario.
     */

    public static boolean isMaxClue(int clue, int size) {
        if (size <= 0) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Calcula o numero que representa a proxima pista para palavras de um tamanho especificado.
     *
     * @param clue A pista atual.
     * @param size O tamanho desejado das palavras.
     * @requires {@code clue / divisor > 0 && !found}
     * @ensures Que é calculado a pista logo a seguir (Exemplo, clue = 11112; nextClue = 11113;)  
     * @return O valor inteiro do numero que representa a proxima pista, ou um valor negativo se houver um erro.
     */

    public static int nextClue(int clue, int size) {
        int divisor = 1;
        boolean found = false;
        while (clue / divisor > 0 && !found) {
            int digito = (clue / divisor) % 10;

            if (clue == 23333) {
                clue = 33333;
                found = true;
            } else if (digito == 3) {
                clue -= 2 * divisor;
                found = true;
            } else if (digito == 2) {
                clue += divisor;
                found = true;
            } else {
                clue += divisor;
                found = true;
            }
            divisor *= 10;
        }
        return clue;
    }
}
