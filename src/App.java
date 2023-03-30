import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class App {

    public static void main(String[] args) throws Exception {


        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);


        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        
        for (Map<String,String> filme : listaDeFilmes) {
            var generator = new StickerGenerator();
        
            String UrlImagem = filme.get("image");
            String titulo = filme.get("title");

            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numeroEstrelas = (int) classificacao;

            String textoSticker;
            if (classificacao >= 8){
                textoSticker = "TOP D+!";
            } else if ( classificacao < 8 && classificacao > 4){
                textoSticker = "NEM TANTO...";
            } else {
                textoSticker = "PERDA DE TEMPO...";
            }
            
            InputStream inputStream = new URL(UrlImagem).openStream();
            String sticker = diretorio + titulo.replaceAll("[^a-zA-Z0-9.-]", " ") + ".png";


            generator.cria(inputStream, sticker, textoSticker);

            System.out.println("\u001b[44m\u001b[1mTítulo\u001b[m" + ": " + filme.get("title"));
            System.out.println("\u001b[42m\u001b[1mURL da Imagem\u001b[m"+ ": "+filme.get("image"));
            System.out.println("\u001b[43m\u001b[1mNota de Avaliação\u001b[m"+": "+filme.get("imDbRating"));
            


            for (int i = 1; i <= numeroEstrelas; i++){
                System.out.print("⭐");    
            
            }

            System.out.println("\n");
        }

    }
}