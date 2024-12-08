package project;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;

public class VideoClub {
    private Movie[] movies;

    public VideoClub(String fileName, int numberOfMovies) throws FileNotFoundException {
        try (Scanner sc = new Scanner(new File(fileName))) {
        	this.movies = new Movie[numberOfMovies];
            int lidos = 0;
            while (sc.hasNextLine() && lidos < numberOfMovies) {
                String linha = sc.nextLine();
                String[] resultado = linha.split(",");

                String title = resultado[0];
                int year = Integer.parseInt(resultado[1]);
                int quantity = Integer.parseInt(resultado[2]);
                String[] rentals = resultado[3].split(""); 
                double price = Double.parseDouble(resultado[4]);
                double tax = Double.parseDouble(resultado[5]);

                movies[lidos] = new Movie(title, year, quantity, rentals, price, tax);
                lidos++;
            }
            sc.close();
        } catch (NumberFormatException nfe) {
        	System.out.println("Erro");
        }
    }
    public int getNumberOfMovies() {
    	String[] distinto = new String[movies.length];
    	int contador = 0;
    	for(Movie movie: movies) {
    		distinto[contador] = movies[contador].getTitle();
    		contador++;
    	}
    	return contador;
    }
    public int numberAvailableMovies() {
    	
    }
    public double getTotalRevenue() {
    	
    }
    public double getTotalProfit() {
    	
    }
    public String filterByYear(int year) {
    	
    }
    public String filterByPrice(double price) {
    	
    }
    public String filterAvailableMovies() {
    	
    }
    public String activityLog(String rentalsFileName) throws FileNotFoundException {
    	
    }
    public void updateStock(String fileName) throws FileNotFoundException {
    	
    }
}
