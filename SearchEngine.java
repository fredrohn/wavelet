import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> items = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return items.toString();
        } else if (url.getPath().equals("/search")) {
            ArrayList<String> temp = new ArrayList<>();
            System.out.println("Path: " + url.getPath()); //accessory
                String[] parameters = url.getQuery().split("="); 
                if (parameters[0].equals("s")) {
                    for(int i=0; i < items.size(); i++){
                        if(items.get(i).contains(parameters[1])){
                            temp.add(items.get(i));
                        }
                    }
                    return temp.toString();
                }
            return "404 Not Found!";
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("="); 
                if (parameters[0].equals("s")) {
                    items.add(parameters[1]);
                    return parameters[1] + " added!";
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

