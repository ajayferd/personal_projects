# In this code, client sends simple calculator instructions and the server 
# receives it and respond with the correct answer. This code uses 
# TCP protocol to send packets.
# This code is based on the lectures of CNT4704, UCF 2022, Abdulmajeed Alghamdi. 
# Ack. Dr. Kurose & Ross (Authors of textbook)
# eustis3.eecs.ucf.edu
from socket import *
serverName = '10.173.204.63'
serverName = 'localhost'
serverPort = 12001



# Create socket called clientSocket and establish a TCP connection with the server
clientSocket = socket(AF_INET, SOCK_STREAM)
# Port number may change according to the serverName and serverPort
clientSocket.connect((serverName, serverPort))
print('Connected with server on {}'.format(serverName))


equation = input()

clientSocket.send(equation.encode())
answer = clientSocket.recv(1024)
print('Answer from server:', answer.decode())
clientSocket.close()
