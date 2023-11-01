package sk.umb.dvestodola.bazarik.email.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class EmailFactory {
	private String fileName;

	public EmailFactory(String fileName) {
		this.fileName = fileName;
	}

	public String getEmailBody(String code) {
		String emailBody = getHtmlDocument();
		return emailBody.replace("{{code}}", code);
	}

	private static String readUsingBufferedReaderCharArray(String fileName) {
		System.out.println("Working directory: " + System.getProperty("user.dir"));
		System.out.println("Filename: " + fileName);

		BufferedReader reader = null;
		StringBuilder stringBuilder = new StringBuilder();
		char[] buffer = new char[10];
		try {
			InputStream inputStream = EmailFactory.class.getResourceAsStream(fileName);
			Reader targetReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(targetReader);
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

	private String getHtmlDocument() {
		return """
		<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width", initial-scale="1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<style>
		tbody {
			background-color: #e79600;
		}
		tr {
			border-spacing: 0;
			border-collapse: collapse;
			border-color: #e79600;
		}
		td {
			padding: 16pt 24pt;
			max-width: 480px;
			text-align: center;
		}
		.title {
			font-size: 2rem;
			border-bottom: black 1px solid;
			padding: 2pt 0;
		}
		.bazarik {
			margin: 12pt 0;
			margin-block-start: 0;
		    margin-block-end: 0;
		    margin-inline-start: 0;
		    margin-inline-end: 0;
		}
		td.code {
			text-align: center;
			vertical-align: middle;
			padding: 8pt 16pt 16pt;
		}
		.verification-code {
			line-height: 100px;
			height: 100px;
		    font-size: 42pt;
		    background-color: #FFFFFF;
		    border: 1px solid;
			font-weight: bold;
			letter-spacing: 4pt;
			margin: 0;
		}
	</style>
</head>
<body>
	<table>
		<tbody>
			<tr>
				<td class="title">
					<h1 class="bazarik">Bazárik</h1>
				</td>
			</tr>
			<tr>
				<td class="code">
					<h2>Váš overovací kód:</h2>
	   				<p class="verification-code">{{code}}</p>
				</td>
			</tr>
			<tr>
				<td>
					(?) Tento e-mail ste dostali pretože bol zadaný na overenie pridania alebo úpravy inzerátu na www.bazarik.sk
					<br>
					Ak ste nevykonali žiadnu z týchto akcií, tento e-mail môžete ignorovať.
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>""";
	}
}
