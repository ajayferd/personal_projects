#In this code, client sends lowercase string using sockets, and the server receives it and respond with the uppercase format of the same text. This code uses TCP protocol to send packets.
#This code is based on the lectures of CNT4704, UCF 2022, Abdulmajeed Alghamdi. 
#Ack. Kurose & Ross

from socket import *
serverName = 'eustis3.eecs.ucf.edu'
serverPort = 12051

# Create socket called clientSocket and establish a TCP connection with the server

while True:
    # Port number may change according to the serverName and serverPort
    clientSocket = socket(AF_INET, SOCK_STREAM)
    clientSocket.connect((serverName,serverPort))
    sentence = input('Input lowercase sentence:')
    clientSocket.send(sentence.encode())
    modifiedSentence = clientSocket.recv(1024)
    print ('From Server:', modifiedSentence.decode())
    clientSocket.close()
