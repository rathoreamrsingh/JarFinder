package com.general.scanJar;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ScanJar {
	public static void main(String args[]) {

		System.out.print("Enter jar files location : ");
		Scanner in = new Scanner(System.in);
		String classpath = in.nextLine().trim();

		List<String> listOfJars = new ArrayList<String>();
		try {
			File folder = new File(classpath);
			if (!folder.isDirectory()) {
				System.out.println("This is not a correct directory exiting!!!");
				System.exit(1);
			}
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					if (listOfFiles[i].getName().toString().endsWith(".jar")) {
						// System.out.println(listOfFiles[i].getName().toString());
						listOfJars.add(listOfFiles[i].getName().toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.print("Enter class name preceded by package name : ");
		List<String> classNames = new ArrayList<String>();
		// String searchString = "org.apache.commons.io.DirectoryWalker";
		String searchString = in.nextLine().trim();
		if (searchString != null && searchString.length() == 0) {
			System.out.println("Class name to be searched was not correct hence exiting the application!!!");
			System.exit(1);
		}
		List<String> jarNames = new ArrayList<String>();

		try {
			for (String s : listOfJars) {
				ZipInputStream zip = new ZipInputStream(new FileInputStream(classpath + "\\" + s));
				for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
					if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
						String className = entry.getName().replace('/', '.');
						classNames.add(className.substring(0, className.length() - ".class".length()));
						if (className.substring(0, className.length() - ".class".length())
								.equalsIgnoreCase(searchString)) {
							// System.out.println(s);
							jarNames.add(s);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("=====================================================");
		System.out.println("                     output");
		System.out.println("=====================================================");
		if (jarNames.size() == 0) {
			System.out.println("No jars found");
		} else {
			for (String s : jarNames) {
				System.out.println(s);
			}
		}
		// E:\Softwares\jars\commons-io-2.5-bin\commons-io-2.5
		// org.apache.commons.io.DirectoryWalker
		in.nextLine();
	}
}
