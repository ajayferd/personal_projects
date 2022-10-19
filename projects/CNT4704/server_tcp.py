# In this code, client sends lowercase string using sockets, and the server receives it and respond with the uppercase format of the same text. This code uses TCP protocol to send packets.
# This code is based on the lectures of CNT4704, UCF 2022, Abdulmajeed Alghamdi. 
# Ack. Dr. Kurose & Ross (Authors of textbook)

from socket import *

# Create a TCP server socket
# (AF_INET is used for IPv4 protocols)
# (SOCK_STREAM is used for TCP)

serverSocket = socket(AF_INET,SOCK_STREAM)

# Assign a port number
serverPort = 12051

# Bind the socket to server address and server port
serverSocket.bind(('eustis3.eecs.ucf.edu',serverPort))

# Listen to at most 1 connection at a time
serverSocket.listen(1)

# Server should be up and running and listening to the incoming connections

print ('The server is ready to receive')
while True:

    # Set up a new connection from the client
    connectionSocket, addr = serverSocket.accept()
    
    # Receives the request sentence from the client
    sentence = connectionSocket.recv(1024).decode()
    
    # Capitalizes the received sentence
    capitalizedSentence = sentence.upper()
    
    # Send the capitalized sentence to the connection socket
    connectionSocket.send(capitalizedSentence.encode())
    
    # Close the client connection socket
    connectionSocket.close()
