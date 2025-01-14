# Multi Threaded Web Server

## Description
This project, "WebServer.java," is a simple Java-based web server that serves static web content over HTTP. The server supports hosting static files such as HTML, images (JPEG, PNG, GIFs), and can handle HTTP GET requests from clients. Users can access web content through their browsers by navigating to `localhost:<PortNumber>`.

### Features
- Configurable document root directory and listening port via command-line arguments.
- Multithreading to handle multiple client connections concurrently.
- Error handling for various HTTP status codes (400 Bad Request, 404 Not Found, 403 Forbidden, 200 OK).

### Project Structure
- `WebServer.java`: Main server implementation.
- `WebContent`: Contains `index.html`, images, and GIFs.
- `Screenshots`: Screenshots demonstrating the functionality.
- `ReadMe.txt`: Text version of this document.

## Installation and Usage

### Command Line Instructions
To run the program from the command line:
1. Compile the code: javac WebServer.java
2. Run the server: java WebServer -document_root C:\Users\paura\Desktop\PA1-Pauravi_Nagarkar\webcontent -port 1025
3.1. Open `WebServer.java`.
3.2. Edit run configurations to include program parameters:-document_root <path_to_webcontent>
3. Hit Run.

## Default Web Page
The default webpage should load perfectly displaying images and handling different HTTP responses.

## Error Handling
- **400 Bad Request**: Returned when the request cannot be understood by the server.
- **404 Not Found**: Returned when the requested resource is not found.
- **200 OK**: Standard response for successful HTTP requests.

## Contact
For any questions regarding the execution of this project, please contact:pnagarkar02@gmail.com
