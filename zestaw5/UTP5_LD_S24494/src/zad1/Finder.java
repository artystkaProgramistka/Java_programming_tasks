package zad1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Finder {
	private File file;
	private int ifCount;
	private int stringCount;
	private String comment;
	private String quote;
	private String lineOfCode;
	private ArrayList<String> fileContent;
	private ArrayList<String> allComments;
	private ArrayList<String> allQuotes;
	private ArrayList<String> code;

	public Finder(String fname) {
		file = new File(fname);
		fileContent = new ArrayList<String>();
		code = new ArrayList<String>();
		allComments = new ArrayList<String>();
		allQuotes = new ArrayList<String>();

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				fileContent.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		separateCodeFromComments();
	}

	private void separateCodeFromComments() {
		boolean insideOfAComment = false;
		for (String line : fileContent) {
			comment = "";
			lineOfCode = "";
			quote = "";

			for (int i = 0; i < line.length(); i++) {
				if (insideOfAComment) {
					// Jestem w komentarzu
					// Sprawdzam, czy koniec komentarza
					if (line.charAt(i) == '*' && i + 1 < line.length()) {
						i++;
						if (line.charAt(i) == '/')
							insideOfAComment = false;
					} else {
						comment += line.charAt(i);
						if (i == line.length() - 1 && !(comment.isBlank()))
							allComments.add(comment);
					}
				}
				// Jestem poza komentarzem
				else {
					// Sprawdzam, czy cudzyslow
					if (line.charAt(i) == '"' && i + 1 < line.length()) {
						i++;
						while (i < line.length()) {
							if (line.charAt(i) == '"') {
								if (!quote.isBlank())
									allQuotes.add(quote);
								break;
							}
							quote += line.charAt(i);
							if (i == line.length() - 1)
								if (!quote.isBlank())
									allQuotes.add(quote);
						}
					}

					if (line.charAt(i) == '/' && i + 2 < line.length()) {
						i++;
						// /**/ comments
						if (line.charAt(i) == '*') {
							insideOfAComment = true;
							i++;
							while (i < line.length()) {
								comment += line.charAt(i);
								i++;
							}
						}
						// single line // comments
						else if (line.charAt(i) == '/') {
							i++;
							while (i < line.length()) {
								comment += line.charAt(i);
								i++;
							}
						}

					} else {
						lineOfCode += line.charAt(i);
						if (i == line.length() - 1 && !(lineOfCode.isBlank()))
							code.add(lineOfCode);
					}
				}
			}
		}
	}

	public int getIfCount() {
		ifCount = 0;
		for (String line : code)
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == 'i') {
					if (i + 1 < line.length()) {
						i++;
						if (line.charAt(i) == 'f') {
							// Omijam spacje i taby przed if
							int j = i - 2;
							while (j >= 0) {
								if (line.charAt(j) == ' ' || line.charAt(j) == '\t') {
									j--;
								}
							}
							if (j == 0 || line.charAt(j) == '(' || line.charAt(j) == '{') {
								// Omijam spacje i taby po if
								while (line.charAt(i) == ' ' || line.charAt(i) == '\t') {
									if (i + 1 < line.length())
										i++;
									else
										break;
								}
								if (line.charAt(i) == '(')
									ifCount++;
							}
						}
					}
				}
			}
		return ifCount;
	}

	public int getStringCount(String toFind) {
		stringCount=0;
		boolean possible = true;
		// Szukam w cudzysłowiach
		for (String line : allQuotes)
			for (int i = 0; i < line.length(); i++) 
				if (line.charAt(i) == toFind.charAt(0)) {
					if (i + 1 == line.length()) break;
					else {
						int j=0;
						i++;
						while (i < line.length() && possible && j < toFind.length()) {
							if (line.charAt(i) == toFind.charAt(j)) {
								i++;
								j++;
							} else possible = false;
						}
						if (possible && j == toFind.length()) {
							if (i+1 == line.length()) {
								stringCount++;
							} else if (i+1 < line.length()) {
								i++;
								if (line.charAt(i) == ' ' || line.charAt(i) == '\t') {
									stringCount++;
								}
							}
							
						} else {
							possible = true;
							break;
						}
					}
				}
		// Szukam w komentarzach
		for (String line: allComments)
			for (int i = 0; i < line.length(); i++) 
				if (line.charAt(i) == toFind.charAt(0)) {
					if (i + 1 == line.length()) break;
					else {
						int j=0;
						i++;
						while (i < line.length() && possible && j < toFind.length()) {
							if (line.charAt(i) == toFind.charAt(j)) {
								i++;
								j++;
							} else possible = false;
						}
						if (possible && j == toFind.length()) {
							if (i+1 == line.length()) {
								stringCount++;
							} else if (i+1 < line.length()) {
								i++;
								if (line.charAt(i) == ' ' || line.charAt(i) == '\t') {
									stringCount++;
								}
							}
							
						} else {
							possible = true;
							break;
						}
					}
				}
		return stringCount;
	}
}
