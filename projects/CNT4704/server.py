# In this code, client sends simple calculator instructions and the server 
# receives it and respond with the correct answer. This code uses 
# TCP protocol to send packets.
# This code is based on the lectures of CNT4704, UCF 2022, Abdulmajeed Alghamdi. 
# Ack. Dr. Kurose & Ross (Authors of textbook)
# eustis3.eecs.ucf.edu
from socket import *

# Create a TCP server socket
# (AF_INET is used for IPv4 protocols)
# (SOCK_STREAM is used for TCP)

serverSocket = socket(AF_INET,SOCK_STREAM)

# Assign a port number
serverPort = 12002
serverAddress = 'eustis3.eecs.ucf.edu'
# serverAddress = 'localhost'
# Bind the socket to server address and server port
serverSocket.bind((serverAddress, serverPort))
# Listen to at most 1 connection at a time
serverSocket.listen(1)

# Server should be up and running and listening to the incoming connections
print('Connected with server on {}'.format(serverAddress))
while True:
    # Set up a new connection from the client
    connectionSocket, addr = serverSocket.accept()
    
    # Receives the request sentence from the client
    equation = connectionSocket.recv(1024).decode()
    splicedEquation = equation.replace(' ', '').rstrip(equation[-1])
    
    if splicedEquation == '0/0':
        print('Recieved question "{}"; end the server program'.format(equation))
        connectionSocket.send('X'.encode())
        connectionSocket.close()
        break
    
    # evaluates the math equation
    try:
        answer = eval(splicedEquation)
        print('Recieved question "{}"; send back answer {}'.format(equation, answer))
        connectionSocket.send(str(answer).encode())
    except Exception:
        connectionSocket.send('Input error, Re-type the math question again.'.encode())
    
    
    # Close the client connection socket
connectionSocket.close()
