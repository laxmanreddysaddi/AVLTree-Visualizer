import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;

public class Server {

    static AVLTree tree = new AVLTree();

    public static void main(String[] args) throws Exception {

        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "9000"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/", (HttpExchange exchange) -> {

            addCors(exchange);

            String response = "AVL Tree Backend Running";

            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.createContext("/reset", (HttpExchange exchange) -> {

            addCors(exchange);

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            tree = new AVLTree();

            String response = "Tree reset";

            exchange.sendResponseHeaders(200, response.length());

            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.createContext("/insert", (HttpExchange exchange) -> {

            addCors(exchange);

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(exchange.getRequestBody())
            );

            String value = br.readLine();
            int val = Integer.parseInt(value);

            List<Integer> path = new ArrayList<>();

            tree.insertValue(val);

            collectPath(tree.root, val, path);

            String json =
                    "{ \"tree\": " + treeToJson(tree.root) +
                    ", \"rotation\": \"" + tree.lastRotation + "\"" +
                    ", \"path\": " + path.toString() +
                    " }";

            byte[] response = json.getBytes();

            exchange.getResponseHeaders().add("Content-Type", "application/json");

            exchange.sendResponseHeaders(200, response.length);

            OutputStream os = exchange.getResponseBody();
            os.write(response);
            os.close();
        });

        server.start();
        System.out.println("Server running on port " + port);
    }

    static void addCors(HttpExchange exchange){
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    static void collectPath(AVLNode node, int value, List<Integer> path) {

        if (node == null) return;

        path.add(node.value);

        if (value < node.value)
            collectPath(node.left, value, path);
        else if (value > node.value)
            collectPath(node.right, value, path);
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