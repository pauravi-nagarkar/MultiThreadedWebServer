import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class MultiThreadedWebServer {
    private static final int PORT = 1025;
    private static final String ROOT_DIRECTORY = "C:\\\\Users\\\\paura\\\\Desktop\\\\webcontent";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Web server listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    OutputStream outputStream = clientSocket.getOutputStream();
            ) {
                String request = reader.readLine();
                if (request == null) {
                    return;
                }

                String[] requestParts = request.split(" ");
                if (requestParts.length != 3) {
                    sendResponse(outputStream, 400, "Bad Request");
                    return;
                }

                String method = requestParts[0];
                String path = requestParts[1];
                String httpVersion = requestParts[2];

                if (!method.equalsIgnoreCase("GET")) {
                    sendResponse(outputStream, 400, "Bad Request");
                    return;
                }

                File file = new File(ROOT_DIRECTORY + path);
                if (path.equals("/") || path.isEmpty()) {
                    file = new File(ROOT_DIRECTORY + "/index.html");
                }

                if (!file.exists() || !file.canRead()) {
                    sendResponse(outputStream, 404, "Not Found");
                    return;
                }

                String contentType = getContentType(file.getName());
                sendResponse(outputStream, 200, "OK", contentType);
                transmitFile(file, outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendResponse(OutputStream outputStream, int statusCode, String statusText, String contentType) throws IOException {
            String response = "HTTP/1.0 " + statusCode + " " + statusText + "\r\n" +
                    "Content-Type: " + contentType + "\r\n\r\n";
            outputStream.write(response.getBytes());
        }

        private void transmitFile(File file, OutputStream outputStream) throws IOException {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }

        private String getContentType(String fileName) {
            Map<String, String> contentTypes = new HashMap<>();
            contentTypes.put("html", "text/html");
            contentTypes.put("htm", "text/html");
            contentTypes.put("jpg", "image/jpeg");
            contentTypes.put("jpeg", "image/jpeg");
            contentTypes.put("png", "image/png");
            contentTypes.put("gif", "image/gif");

            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex != -1) {
                String fileExtension = fileName.substring(dotIndex + 1);
                return contentTypes.getOrDefault(fileExtension, "application/octet-stream");
            }
            return "application/octet-stream";
        }
    }
}
