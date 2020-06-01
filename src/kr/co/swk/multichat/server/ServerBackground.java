package kr.co.swk.multichat.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerBackground {

	// ���ݱ��� ����. GUi������Ű�鼭 ����Gui�� �޽������.
	// ���� �̽�. Gui �󿡼� �ϴ� 1:1 ä���� �ϰ� �ʹ�.
	private ServerSocket serverSocket;
	private Socket socket;
	private ServerGui gui;
	private String msg;

	/** XXX 03. ����° �߿��Ѱ�. ����ڵ��� ������ �����ϴ� ���Դϴ�. */
	private Map<String, DataOutputStream> clientsMap = new HashMap<String, DataOutputStream>();

	public final void setGui(ServerGui gui) {
		this.gui = gui;
	}

	public void setting() throws IOException {
		Collections.synchronizedMap(clientsMap); // �̰� �������� ���ݴϴ�^^
		serverSocket = new ServerSocket(7777);
		while (true) {
			/** XXX 01. ù��°. ������ ���� �д�. ��� ���ӹ޴°�. */
			System.out.println("���� �����...");
			socket = serverSocket.accept(); // ���� ������ ������ ��� �ݺ��ؼ� ����ڸ� �޴´�.
			System.out.println(socket.getInetAddress() + "���� �����߽��ϴ�.");
			// ���⼭ ���ο� ����� ������ Ŭ���� �����ؼ� ���������� �־���߰���?!
			Receiver receiver = new Receiver(socket);
			receiver.start();
		}
	}

	public static void main(String[] args) throws IOException {
		ServerBackground serverBackground = new ServerBackground();
		serverBackground.setting();
	}

	// ���ǳ���(Ŭ���̾�Ʈ) ����� ����
	public void addClient(String nick, DataOutputStream out) throws IOException {
		sendMessage(nick + "���� �����ϼ̽��ϴ�.");
		clientsMap.put(nick, out);
	}

	public void removeClient(String nick) {
		sendMessage(nick + "���� �����̽��ϴ�.");
		clientsMap.remove(nick);
	}

	// �޽��� ���� ����
	public void sendMessage(String msg) {
		Iterator<String> it = clientsMap.keySet().iterator();
		String key = "";
		while (it.hasNext()) {
			key = it.next();
			try {
				clientsMap.get(key).writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// -----------------------------------------------------------------------------
	class Receiver extends Thread {
		private DataInputStream in;
		private DataOutputStream out;
		private String nick;

		/** XXX 2. ���ù��� ������ �ڱ� ȥ�ڼ� ��Ʈ��ũ ó�� ���..���.. ó�����ִ� ��. */
		public Receiver(Socket socket) throws IOException {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			nick = in.readUTF();
			addClient(nick, out);
		}

		public void run() {
			try {// ��� ��⸸!!
				while (in != null) {
					msg = in.readUTF();
					sendMessage(msg);
					gui.appendMsg(msg);
				}
			} catch (IOException e) {
				// ������������ ���⼭ ���� �߻�. �׷������ſ���.. ���⼭ ������ Ŭ���̾�Ʈ ó�� ���ݴϴ�.
				removeClient(nick);
			}
		}
	}
}
