package nerdle;

import java.util.ArrayList;
import java.util.Random;

public class Equation {

	private String equation;
	private int numberOfElements;
	private ArrayList<Integer> numbers;
	private ArrayList<String> operators;
	private int equationLength;

	public Equation() {// Üretilecek rastgele denklem için bu constructor kullanýlacaktýr.
		numberOfElements = 1;
		numbers = new ArrayList<>();// Calculate iþlemi arraylistlerde yapýlacaktýr.
		operators = new ArrayList<>();//
		equation = generateEquation();// Rastgele denklem üretmek için generateEquation metodu burada çaðrýlacaktýr.
	}

	public Equation(int equationLength, String equation) {//Kullanýcýnýn girdiði denklem deðerleri bu constructorda oluþturulacaktýr.

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

	public void clearLists() {//generateEquation'da tekrar denklem üretilecekse listeler temizlenir
		numbers.clear();	
		operators.clear();
	}

	public String generateEquation() {

		int result = 0;
		equationLength = generateEquationLength();//rastgele uzunluk üretimi
		int divisionError = 0;

		while (numberOfElements != equationLength) {//eklenen eleman sayýsý uzunluk sayýsýný eþit olana kadar sayý ve operatör ekler.
			if (numbers.isEmpty()) {
				generateRandomNumber();
				generateRandomNumber();
				generateRandomOperator();//ilk önce 2 sayý ve 1 iþlem üretir.
				try {
					result = Calculate();//bunun sonucunu hesaplar
				} catch (ArithmeticException e) {
					divisionError = 1;//bölünme hatasý calculate metodu tarafýndan throwlanýr.
				}
				numberOfElements += calculateNumberOfDigits(result);//bunun basamak sayýsý elemanlara eklenir.
			}
			while (numberOfElements < equationLength) {//bu while olduðu sürece bir sayý bir iþlem ekleyip yeniden calculate yapýyoruz.

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
				numberOfElements = 1;//herhangi bir istenmeyen durumda if'in içine gir ve her þeyi sýfýrlayýp
				numbers.clear();	//yeniden baþla.
				operators.clear();
				divisionError = 0;
				result = 0;

			}

		}
		try {
			result = Calculate();
		} catch (ArithmeticException e) {

		}

		String tmpequation = numbers.get(0).toString();//sonucu doðru bulduktan sonra hepsini birleþtirip stringe çeviriyoruz.
		for (int i = 0; i < operators.size(); i++) {
			tmpequation = tmpequation.concat(operators.get(i));
			tmpequation = tmpequation.concat(numbers.get(i + 1).toString());
		}
		tmpequation = tmpequation.concat("=");
		tmpequation = tmpequation.concat(Integer.toString(result));

		return tmpequation;//iþlemi string olarak döndürüyoruz.
	}

	public void generateRandomNumber() {
		Random rand = new Random();
		int selecter = rand.nextInt(8);
		if (selecter <= 2) {//lojik açýdan en iyi bulduðumuz oranlar ile rastgele basamaklý deðiþken üretiyoruz.
			numbers.add(rand.nextInt(10));//Ve bu deðeri arraylistimize atýyoruz.
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
		int selecter = rand.nextInt(6);//lojik açýdan en iyi bulduðumuz oranlarla rastgele iþlem üretiyoruz.
		if (selecter == 0) {//Ve bu iþlemi arraylistimize atýyoruz.
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
		Random rand = new Random();//eþit olasýlýkla 7-9 deðerleri arasýnda denklem uzunluðu üretiyoruz.
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
		ArrayList<Integer> tmpnumbers = new ArrayList<>();//Ýþlem önceliðini kontrol etmek ve orijinal arraylistimizi-
		tmpnumbers = (ArrayList<Integer>) numbers.clone();//-kaybetmemek için arraylistlerimiz cloneluyoruz.

		ArrayList<String> tmpoperators = new ArrayList<>();
		tmpoperators = (ArrayList<String>) operators.clone();
		int tmp = 0;
		int number = 0;
		int i = 0;
		int numberOfOperations = 0;

		while (i != tmpoperators.size()) {
			if (tmpoperators.get(i).equals("*")) {

				tmp = tmpnumbers.get(i) * tmpnumbers.get(i + 1);//ilk önce çarpma ve bölme iþlemlerini yapýp clone 
				tmpnumbers.set(i, tmp);//arraylistlerimizi bu iþlemlerini sonucunu ekleyerek güncelliyoruz.
				tmpnumbers.remove(i + 1);//yani operatör olarak sadece + ve - miz kalana kadar iþlemleri yapýp
				tmpoperators.remove(i);//arraylist'e iþliyoruz.
				i = 0;
				numberOfOperations++;

			} else if (tmpoperators.get(i).equals("/")) {

				if (tmpnumbers.get(i) == 0 || tmpnumbers.get(i + 1) == 0
						|| tmpnumbers.get(i) / tmpnumbers.get(i + 1) == 0//Lojik olarak güzel olmasý için gerekli durumlarda exception throwluyoruz.
						|| (tmpnumbers.get(i) % tmpnumbers.get(i + 1) != 0 && numberOfOperations != 0)) {
					throw new ArithmeticException();
				}
				while (tmpnumbers.get(i) % tmpnumbers.get(i + 1) != 0) {

					if (calculateNumberOfDigits(numbers.get(i + numberOfOperations)) != calculateNumberOfDigits(
							numbers.get(i + numberOfOperations) + 1)) {//bölünen%bölen eþit olana kadar bölüneni 1 artýrýyoruz.
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

			number = tmpnumbers.get(0) + tmpnumbers.get(1);//kalan iþlemler için +'ysa toplama deðilse çýkarma yapýyoruz.

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

		return number;//hesapladýðýmýz deðeri döndürüyoruz

	}

	public int calculateNumberOfDigits(int a) {//basamak sayýsýný geri döndürecek fonksiyon.
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