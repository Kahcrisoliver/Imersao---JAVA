import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        // Fazer uma conexão HTTP e buscar os top 10 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var cliente = HttpClient.newHttpClient(); 
        HttpRequest requisicao = HttpRequest.newBuilder(endereco)
            .GET()
            .build();
        HttpResponse<String> resposta = cliente.send(requisicao, BodyHandlers.ofString());  
        String body = resposta.body();       

        // Extrair só os dados que interessam (Ano, Título, Poster, Classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // Exibir e manipular os dados
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println("Ano: " + filme.get("year"));
            System.out.println("Título: "+ filme.get("title"));
            System.out.println("Poster: " + filme.get("image"));
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            System.out.print("Classificação: ");
            for (int i = 1; i <= classificacao; i++){
                System.out.print("⭐");
            }
            System.out.print(" (" + filme.get("imDbRating") + ")");
            System.out.println("\n");
        }

    }
}