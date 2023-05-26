package sk.umb.dvestodola.bazarik.email.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmailFactory {
	private String fileName;

	public EmailFactory(String fileName) {
		this.fileName = fileName;
	}

	public String getEmailBody(String code) {
		String emailBody = readUsingBufferedReaderCharArray(fileName);
		return emailBody.replace("{{code}}", code);
	}

	private static String readUsingBufferedReaderCharArray(String fileName) {
		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		char[] buffer = new char[10];
		try {
			reader = new BufferedReader(new FileReader(fileName));
			while (reader.read(buffer) != -1) {
				stringBuilder.append(new String(buffer));
				buffer = new char[10];
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return stringBuilder.toString();
	}

}
