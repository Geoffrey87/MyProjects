# TCP Client-Server Application

This project contains two main classes that implement a basic TCP client-server communication:

- `ServerTCP`: Handles incoming client connections, processes data, and responds to the client.
- `ClientTCP`: Connects to the server, sends data, and processes the server's response.

---

## **How the Application Works**

### **1. ServerTCP**
The `ServerTCP` class does the following:
- Listens on a specific port for incoming client connections.
- Receives a string and a keyword from the client.
- Processes the received data:
  - Anonymizes the keyword in the string (replacing it with `X`s).
  - Counts the occurrences of the keyword in the string.
- Sends the anonymized string and a specific response (`"Socket Programming"`) repeated for each keyword occurrence back to the client.

### **2. ClientTCP**
The `ClientTCP` class performs the following steps:
- Connects to the server using a hostname (or IP address) and a port number.
- Sends a string and a keyword to the server.
- Receives and displays the server's response.

---

## **Usage Instructions**

### **1. Running the Server**
You must start the server first so it can listen for client connections. Follow these steps:

#### **Using IntelliJ IDEA or another IDE:**
1. Open the `ServerTCP` class.
2. Ensure the program argument for the port number is set. Example: `2000`.
   - In IntelliJ:
     - Click on the dropdown menu next to the Play button (green arrow).
     - Select **Edit Configurations**.
     - In the **Program Arguments** field, enter the port number (e.g., `2000`).
3. Run the `ServerTCP` class by clicking the Play button.
4. Confirm the server is running and listening on the specified port. You should see a message like:
   ```
   Server is listening on port 2000
   ```

#### **Using Terminal:**
1. Compile the server class:
   ```bash
   javac ServerTCP.java
   ```
2. Run the server, specifying the port:
   ```bash
   java ServerTCP 2000
   ```

### **2. Running the Client**
After the server is running, you can start the client to connect and communicate with it.

#### **Using IntelliJ IDEA or another IDE:**
1. Open the `ClientTCP` class.
2. Run the program by clicking the Play button.
3. Provide the following inputs when prompted:
   - **Server name or IP address**: Enter the hostname (e.g., `localhost` or `192.168.x.x`).
   - **Port**: Enter the same port number used when starting the server (e.g., `2000`).
   - **String**: Enter the text you want to send to the server.
   - **Keyword**: Enter the keyword to be processed.

#### **Using Terminal:**
1. Compile the client class:
   ```bash
   javac ClientTCP.java
   ```
2. Run the client:
   ```bash
   java ClientTCP
   ```
3. Provide the required inputs when prompted.

---

## **Important Notes**

1. **Start the Server First:**
   - The server must be started first to ensure it is ready to accept client connections. If the client is started before the server, you will encounter a `Connection refused` error.

2. **Use the Same Port:**
   - Ensure the client and server use the same port number (e.g., `2000`). Mismatched ports will prevent the client from connecting to the server.

3. **Firewall and Network Configuration:**
   - If the server and client are running on different machines, ensure that:
     - Both devices are on the same network.
     - The firewall on the server's machine allows incoming connections on the specified port.
   - Use the command `ping <hostname>` or `ping <IP>` to verify connectivity between the client and server machines.

4. **Testing Locally:**
   - If testing locally (on the same machine), use `localhost` or `127.0.0.1` as the hostname when running the client.

---

## **Example Run**

### **Server:**
- Start the server with port `2000`:
  ```bash
  java ServerTCP 2000
  ```
- Server console output:
  ```
  Server is listening on port 2000
  ```

### **Client:**
- Start the client:
  ```bash
  java ClientTCP
  ```
- Provide inputs:
  ```
  Enter server name or IP address: localhost
  Enter port: 2000
  Enter string: I love programming
  Enter keyword: love
  ```
- Client console output:
  ```
  I XXXX programming
  Socket Programming
  ```

---

This project demonstrates a simple TCP-based communication model, showcasing the importance of server initialization before client requests and fundamental socket programming concepts in Java.
