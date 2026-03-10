import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;

public class Server {

    static AVLTree tree = new AVLTree();

    public static void main(String[] args) throws Exception {

       int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "9000"));
HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/insert", (HttpExchange exchange) -> {

            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(exchange.getRequestBody())
                );

                String value = br.readLine();

                tree.insertValue(Integer.parseInt(value));

                String json = "{ \"tree\": " + treeToJson(tree.root) +
                              ", \"rotation\": \"" + tree.lastRotation + "\" }";

                byte[] response = json.getBytes();

                exchange.sendResponseHeaders(200, response.length);

                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            }
        });

        server.start();
        System.out.println("Server running on http://localhost:9000");
    }

    static String treeToJson(AVLNode node){

        if(node == null)
            return "null";

        int bf =
                (node.left == null ? 0 : node.left.height) -
                (node.right == null ? 0 : node.right.height);

        return "{ \"value\": " + node.value +
                ", \"height\": " + node.height +
                ", \"bf\": " + bf +
                ", \"left\": " + treeToJson(node.left) +
                ", \"right\": " + treeToJson(node.right) +
                " }";
    }
}