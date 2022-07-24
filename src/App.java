import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    private static final String URL_FILMES_IMDB = "https://alura-filmes.herokuapp.com/conteudos";
    
    private static final String ANSI_REDEFINIDO = "\u001b[0m";
    private static final String ANSI_NEGRITO = "\u001b[1m";
    private static final String ANSI_TEXTO_AZUL = "\u001b[34m";
    private static final String ANSI_FUNDO_AMARELO = "\u001b[43m";

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os top 250 filmes
        String body = executarRequisicao(URL_FILMES_IMDB);

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados 
        for (Map<String,String> filme : listaDeFilmes) {
            System.out.println(ANSI_NEGRITO + "Nome: " + ANSI_REDEFINIDO + filme.get("name"));
            System.out.println(ANSI_NEGRITO + "Imagem: " + ANSI_REDEFINIDO + filme.get("image"));

            float rating = Float.parseFloat(filme.get("imDbRating"));
            System.out.println(ANSI_NEGRITO + "Classificação: " + ANSI_REDEFINIDO 
                    + ANSI_FUNDO_AMARELO + ANSI_TEXTO_AZUL + rating + ANSI_REDEFINIDO);

            System.out.println();
        }
    }

    private static String executarRequisicao(String URL) throws Exception {
        URI endereco = URI.create(URL);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        return response.body();
    }

}
