# In this code, client sends simple calculator instructions and the server 
# receives it and respond with the correct answer. This code uses 
# TCP protocol to send packets.
# This code is based on the lectures of CNT4704, UCF 2022, Abdulmajeed Alghamdi. 
# Ack. Dr. Kurose & Ross (Authors of textbook)

# eustis.eecs.ucf.edu
from socket import *

serverName = 'eustis3.eecs.ucf.edu'
serverPort = 12002

print('Connected with client on {}'.format(serverName))
while True:
    # Create socket called clientSocket and establish a TCP connection with the server
    clientSocket = socket(AF_INET, SOCK_STREAM)
    # Port number may change according to the serverName and serverPort
    clientSocket.connect((serverName, serverPort))
    # take input and splice
    equation = input()
    
    # sends the equation to the server
    clientSocket.send(equation.encode())

    # recieves the solution from the server
    answer = clientSocket.recv(1024)
    if answer.decode() == 'X':
        print('User input ends; end the client program')
        clientSocket.close()
        break
    
    print('Answer from server:', answer.decode())
    clientSocket.close()
