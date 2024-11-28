package com.gugawag.so.ipc;

/**
 * Time-of-day server listening to port 6013.
 *
 * Figure 3.21
 *
 * @author Silberschatz, Gagne, and Galvin. Pequenas alterações feitas por Gustavo Wagner (gugawag@gmail.com)
 * Operating System Concepts  - Ninth Edition
 * Copyright John Wiley & Sons - 2013.
 */
import java.net.*;
import java.io.*;
import java.util.Date;

public class DateServer {
	public static void main(String[] args)  {
		try {
			ServerSocket serverSocket = new ServerSocket(6013);
			System.out.println("=== Servidor iniciado ===\n");
			System.out.println("Servidor Amoroso de Juan");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Recebeu comunicação do IP: " + clientSocket.getInetAddress() + "-" + clientSocket.getPort());

				Thread clientThread = new Thread(new ClientHandler(clientSocket));
				clientThread.start();
			}
		}
		catch (IOException e) {
			System.err.println("Erro no servidor: " + e);
		}
	}
}
class ClientHandler implements Runnable {
	private Socket cliente;

	public ClientHandler(Socket cliente){
		this.cliente = cliente;
	}

	@Override
	public void run() {
		try {
			System.out.println("Nova conexão com o cliente " + this.cliente.getInetAddress().getHostAddress());
			PrintWriter pout = new PrintWriter(cliente.getOutputStream(), true);

			pout.println(new Date().toString() + "-Boa noite alunos!");
			BufferedReader bin = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			String line;
			while ((line = bin.readLine()) != null) {
				System.out.println("O cliente me disse: " + line);
			}

			bin.close();
			pout.close();
			cliente.close();

		} catch (IOException e) {
			System.err.println("Erro na comunicação com o cliente: " + e);
		}
	}
}