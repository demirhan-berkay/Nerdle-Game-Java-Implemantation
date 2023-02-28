package nerdle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileOperations {

	private int halfGame;
	private int failedGames;
	private int successfulGames;//istatistikler i�in gereken de�erler.
	private double averageLines;
	private int averageSeconds;

	public FileOperations() {

		String data = readFile();
		if (data == null) {
			this.halfGame = 0;
			this.failedGames = 0;
			this.successfulGames = 0;//file yoksa t�m de�erlere 0 atan�r
			this.averageLines = 0;
			this.averageSeconds = 0;
		} else {
			String[] splitted = data.split("\\s+");
			this.halfGame = Integer.parseInt(splitted[0]);
			this.failedGames = Integer.parseInt(splitted[1]);
			this.successfulGames = Integer.parseInt(splitted[2]);//file varsa t�m sat�r bir string olarak al�n�p parse edilir.
			this.averageLines = Double.parseDouble(splitted[3]);
			this.averageSeconds = Integer.parseInt(splitted[4]);
		}

	}

	public int getHalfGame() {
		return halfGame;
	}

	public int getFailedGames() {
		return failedGames;
	}

	public int getSuccessfulGames() {
		return successfulGames;
	}

	public double getAverageLines() {
		return averageLines;
	}

	public int getAverageSeconds() {
		return averageSeconds;
	}

	public void writeToFile() {

		try {
			File myObj = new File("statistics.txt");
			myObj.createNewFile();//file varsa a��yor,yoksa yeniden olu�turuyor

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter myWriter = new FileWriter("statistics.txt");
			myWriter.write(//statistics.txt ye gerekene istatistik de�erlerini yazar.
					halfGame + " " + failedGames + " " + successfulGames + " " + averageLines + " " + averageSeconds);
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String readFile() {//dosyay� okuyabilirse t�m sat�r� string olarak d�nd�r�r,okuyamazs null.
		String data = null;
		try {
			File myObj = new File("statistics.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			return null;

		}

		return data;

	}

	public void updateWinGames(double averageLines, int averageSeconds) {//oyun kazan�ld���nda yap�lacak g�ncelleme

		this.averageSeconds = (this.averageSeconds * this.successfulGames + averageSeconds)
				/ (this.successfulGames + 1);
		this.averageLines = (this.averageLines * this.successfulGames + averageLines) / (this.successfulGames + 1);
		this.successfulGames++;

	}

	public void updateFailedGames() {//oyun kaybedildi�inde yap�lacak g�ncelleme
		this.failedGames++;
	}

	public void increaseHalfGame() {//oyun yar�da b�rak�ld���nda yap�lacak g�ncelleme
		this.halfGame++;
	}

	public void decreaseHalfGame() {//yar�da oyun varsa devam et butonuna bas�lm��sa yap�lacak g�ncelleme
		this.halfGame--;
	}

}