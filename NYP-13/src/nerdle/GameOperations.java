package nerdle;

import java.util.ArrayList;

public class GameOperations {

	private Equation randomEquation;
	private Equation inputEquation;
	private ArrayList<String> colors;
	private int numberOfPredictions;
	private boolean isMatch;

	public GameOperations(Equation randomEquation) {

		this.randomEquation = randomEquation;//generateEquation tarafýndan oluþturulan rastgele denklem
		colors = new ArrayList<String>();//renkleri tutacaðýmýz arraylist'in tamamýný kýrmýzýya boyuyoruz
		for (int i = 0; i < randomEquation.getEquationLength(); i++) {
			colors.add("red");
		}
		inputEquation = new Equation(randomEquation.getEquationLength(), "0");//kullanýcý tarafýndan girilen input denklem
		numberOfPredictions = 0;
		isMatch = false;

	}

	public boolean isMatch() {
		return isMatch;
	}

	public void setInputEquation(Equation inputEquation) {
		this.inputEquation = inputEquation;
	}

	public ArrayList<String> getColors() {
		return colors;
	}

	public int getNumberOfPredictions() {
		return numberOfPredictions;
	}

	public void setNumberOfPredictions(int numberOfPredictions) {
		this.numberOfPredictions = numberOfPredictions;
	}

	public void resetColors() {
		for (int i = 0; i < colors.size(); i++) {
			colors.set(i, "red");
		}
	}

	public boolean compareEquations() {

		char a, c, b;
		int i, j;
		String tmp = randomEquation.getEquation();
		String tmp2 = inputEquation.getEquation();
		ArrayList<Integer> found = new ArrayList<Integer>();

		for (i = 0; i < randomEquation.getEquationLength(); i++) {
			found.add(0);//sarýlarýn yeþillerin üstüne yazmamak için found adýnda geçici bir arraylist tutup içini 0 lýyoruz.
		}

		for (i = 0; i < tmp.length(); i++) {
			a = tmp.charAt(i);//ayný indekslerinin deðeri aynýysa yeþile boya ve found dizisinin o indisini 2'ye ata.
			b = tmp2.charAt(i);
			if (a == b) {
				colors.set(i, "green");
				found.set(i, 2);
			}
		}

		for (i = 0; i < tmp2.length(); i++) {

			a = tmp2.charAt(i);
			j = 0;
			while (j < tmp.length()) {
				c = tmp.charAt(j);// farklý indislerinin deðeri aynýysa ve kýrmýzýysa sarýya boya.
				if (a == c && i != j && found.get(j) == 0) {
					colors.set(i, "yellow");
					found.set(j, 1);
					j = tmp.length();
				}else {
					j++;
				}

			}

		}

		numberOfPredictions++;
		return true;

	}

	public boolean isEquation(String equation) {
		inputEquation.clearLists();

		String[] tmp = equation.split("=");

		try {
			if (tmp[0].length() == randomEquation.getEquationLength()) {//denklemde eþittir yoksa false returnle.
				return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

		for (int i = 0; i < tmp[0].length(); i++) {//hesaplamak için Equation nesnelerinin arraylistlerini dolduruyoruz.
			if (tmp[0].charAt(i) == '+') {
				inputEquation.getOperators().add("+");
			} else if (tmp[0].charAt(i) == '-') {
				inputEquation.getOperators().add("-");
			} else if (tmp[0].charAt(i) == '/') {
				inputEquation.getOperators().add("/");
			} else if (tmp[0].charAt(i) == '*') {
				inputEquation.getOperators().add("*");
			}
		}

		String[] tmp2 = tmp[0].split("[+|\\-|\\*|\\/]");// herhangi bir operatör karþýlaþýnca stringi ayýr.

		if (tmp2[0].length() == tmp[0].length()) {//operatör yoksa false returnle.
			return false;
		}

		for (String str : tmp2) {
			if (str.length() == 0) {//artarda 2 operatör varsa false returnle.
				return false;
			}
			inputEquation.getNumbers().add(Integer.parseInt(str));
		}

		try {
			if (inputEquation.Calculate() == Integer.parseInt(tmp[1])) {//hesaplama sonucu doðru çýkarsa true returnle
				inputEquation.setEquation(equation);
				return true;
			}

			inputEquation.clearLists();//hesaplama sonucu yanlýþsa false returnle
			return false;

		} catch (IndexOutOfBoundsException e) {
			return false;
		}
	}

	public void control(ArrayList<String> colors) {//iki denklem eþitse isMatchi true'ya setle
		if (colors.contains("yellow") || colors.contains("red")) {
			isMatch = false;
		} else {
			isMatch = true;
		}
	}

}