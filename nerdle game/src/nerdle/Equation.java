package nerdle;

import java.util.ArrayList;
import java.util.Random;

public class Equation {

	private String equation;
	private int numberOfElements;
	private ArrayList<Integer> numbers;
	private ArrayList<String> operators;
	private int equationLength;

	public Equation() {// �retilecek rastgele denklem i�in bu constructor kullan�lacakt�r.
		numberOfElements = 1;
		numbers = new ArrayList<>();// Calculate i�lemi arraylistlerde yap�lacakt�r.
		operators = new ArrayList<>();//
		equation = generateEquation();// Rastgele denklem �retmek i�in generateEquation metodu burada �a�r�lacakt�r.
	}

	public Equation(int equationLength, String equation) {//Kullan�c�n�n girdi�i denklem de�erleri bu constructorda olu�turulacakt�r.

		this.equationLength = equationLength;
		this.numberOfElements = equationLength;
		numbers = new ArrayList<>();
		operators = new ArrayList<>();
		this.equation = equation;

	}

	public void setEquation(String equation) {
		this.equation = equation;
	}

	public ArrayList<Integer> getNumbers() {
		return numbers;
	}

	public ArrayList<String> getOperators() {
		return operators;
	}

	public void clearLists() {//generateEquation'da tekrar denklem �retilecekse listeler temizlenir
		numbers.clear();	
		operators.clear();
	}

	public String generateEquation() {

		int result = 0;
		equationLength = generateEquationLength();//rastgele uzunluk �retimi
		int divisionError = 0;

		while (numberOfElements != equationLength) {//eklenen eleman say�s� uzunluk say�s�n� e�it olana kadar say� ve operat�r ekler.
			if (numbers.isEmpty()) {
				generateRandomNumber();
				generateRandomNumber();
				generateRandomOperator();//ilk �nce 2 say� ve 1 i�lem �retir.
				try {
					result = Calculate();//bunun sonucunu hesaplar
				} catch (ArithmeticException e) {
					divisionError = 1;//b�l�nme hatas� calculate metodu taraf�ndan throwlan�r.
				}
				numberOfElements += calculateNumberOfDigits(result);//bunun basamak say�s� elemanlara eklenir.
			}
			while (numberOfElements < equationLength) {//bu while oldu�u s�rece bir say� bir i�lem ekleyip yeniden calculate yap�yoruz.

				try {
					result = Calculate();
				} catch (ArithmeticException e) {
					divisionError = 1;
				}

				numberOfElements -= calculateNumberOfDigits(result);
				generateRandomNumber();
				generateRandomOperator();
				try {
					result = Calculate();
				} catch (ArithmeticException e) {
					divisionError = 1;
				}
				numberOfElements += calculateNumberOfDigits(result);

			}

			if (numberOfElements > equationLength || result < 0 || divisionError == 1) {
				numberOfElements = 1;//herhangi bir istenmeyen durumda if'in i�ine gir ve her �eyi s�f�rlay�p
				numbers.clear();	//yeniden ba�la.
				operators.clear();
				divisionError = 0;
				result = 0;

			}

		}
		try {
			result = Calculate();
		} catch (ArithmeticException e) {

		}

		String tmpequation = numbers.get(0).toString();//sonucu do�ru bulduktan sonra hepsini birle�tirip stringe �eviriyoruz.
		for (int i = 0; i < operators.size(); i++) {
			tmpequation = tmpequation.concat(operators.get(i));
			tmpequation = tmpequation.concat(numbers.get(i + 1).toString());
		}
		tmpequation = tmpequation.concat("=");
		tmpequation = tmpequation.concat(Integer.toString(result));

		return tmpequation;//i�lemi string olarak d�nd�r�yoruz.
	}

	public void generateRandomNumber() {
		Random rand = new Random();
		int selecter = rand.nextInt(8);
		if (selecter <= 2) {//lojik a��dan en iyi buldu�umuz oranlar ile rastgele basamakl� de�i�ken �retiyoruz.
			numbers.add(rand.nextInt(10));//Ve bu de�eri arraylistimize at�yoruz.
			numberOfElements++;

		} else if (selecter <= 5) {
			numbers.add(rand.nextInt(90) + 10);
			numberOfElements += 2;
		} else if (selecter == 6) {
			numbers.add(rand.nextInt(900) + 100);
			numberOfElements += 3;
		} else {
			numbers.add(rand.nextInt(9000) + 1000);
			numberOfElements += 4;
		}

	}

	private void generateRandomOperator() {
		Random rand = new Random();
		int selecter = rand.nextInt(6);//lojik a��dan en iyi buldu�umuz oranlarla rastgele i�lem �retiyoruz.
		if (selecter == 0) {//Ve bu i�lemi arraylistimize at�yoruz.
			operators.add("+");
			numberOfElements++;
		} else if (selecter == 1 || selecter == 2) {
			operators.add("-");
			numberOfElements++;
		} else if (selecter == 3) {
			operators.add("*");
			numberOfElements++;

		} else {
			operators.add("/");
			numberOfElements++;

		}
	}

	private int generateEquationLength() {
		Random rand = new Random();//e�it olas�l�kla 7-9 de�erleri aras�nda denklem uzunlu�u �retiyoruz.
		int selecter = rand.nextInt(3);
		if (selecter == 0) {
			return 7;
		} else if (selecter == 1) {
			return 8;
		} else {
			return 9;
		}

	}

	@SuppressWarnings("unchecked")
	public int Calculate() throws ArithmeticException {
		ArrayList<Integer> tmpnumbers = new ArrayList<>();//��lem �nceli�ini kontrol etmek ve orijinal arraylistimizi-
		tmpnumbers = (ArrayList<Integer>) numbers.clone();//-kaybetmemek i�in arraylistlerimiz cloneluyoruz.

		ArrayList<String> tmpoperators = new ArrayList<>();
		tmpoperators = (ArrayList<String>) operators.clone();
		int tmp = 0;
		int number = 0;
		int i = 0;
		int numberOfOperations = 0;

		while (i != tmpoperators.size()) {
			if (tmpoperators.get(i).equals("*")) {

				tmp = tmpnumbers.get(i) * tmpnumbers.get(i + 1);//ilk �nce �arpma ve b�lme i�lemlerini yap�p clone 
				tmpnumbers.set(i, tmp);//arraylistlerimizi bu i�lemlerini sonucunu ekleyerek g�ncelliyoruz.
				tmpnumbers.remove(i + 1);//yani operat�r olarak sadece + ve - miz kalana kadar i�lemleri yap�p
				tmpoperators.remove(i);//arraylist'e i�liyoruz.
				i = 0;
				numberOfOperations++;

			} else if (tmpoperators.get(i).equals("/")) {

				if (tmpnumbers.get(i) == 0 || tmpnumbers.get(i + 1) == 0
						|| tmpnumbers.get(i) / tmpnumbers.get(i + 1) == 0//Lojik olarak g�zel olmas� i�in gerekli durumlarda exception throwluyoruz.
						|| (tmpnumbers.get(i) % tmpnumbers.get(i + 1) != 0 && numberOfOperations != 0)) {
					throw new ArithmeticException();
				}
				while (tmpnumbers.get(i) % tmpnumbers.get(i + 1) != 0) {

					if (calculateNumberOfDigits(numbers.get(i + numberOfOperations)) != calculateNumberOfDigits(
							numbers.get(i + numberOfOperations) + 1)) {//b�l�nen%b�len e�it olana kadar b�l�neni 1 art�r�yoruz.
						numberOfElements++;
					}
					numbers.set(i, numbers.get(i + numberOfOperations) + 1);
					tmpnumbers.set(i, tmpnumbers.get(i) + 1);
				}
				tmp = tmpnumbers.get(i) / tmpnumbers.get(i + 1);
				tmpnumbers.set(i, tmp);
				tmpnumbers.remove(i + 1);
				tmpoperators.remove(i);
				i = 0;
				numberOfOperations++;

			} else {
				i++;
			}
		}
		if (tmpoperators.size() == 0) {
			return tmpnumbers.get(0);
		}
		if (tmpoperators.get(0).equals("+")) {

			number = tmpnumbers.get(0) + tmpnumbers.get(1);//kalan i�lemler i�in +'ysa toplama de�ilse ��karma yap�yoruz.

		} else {

			number = tmpnumbers.get(0) - tmpnumbers.get(1);

		}

		for (i = 1; i < tmpoperators.size(); i++) {

			if (tmpoperators.get(i).equals("+")) {

				number += tmpnumbers.get(i + 1);

			} else {

				number -= tmpnumbers.get(i + 1);

			}
		}

		return number;//hesaplad���m�z de�eri d�nd�r�yoruz

	}

	public int calculateNumberOfDigits(int a) {//basamak say�s�n� geri d�nd�recek fonksiyon.
		int NumberOfDigits = 1;
		while (a / 10 != 0) {
			a /= 10;
			NumberOfDigits++;
		}
		return NumberOfDigits;
	}

	public String getEquation() {
		return this.equation;
	}

	public int getEquationLength() {
		return this.equationLength;
	}

}