package com.example.mail.MailServiceKYC;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


public class ConvertFile {
	
	public static void main(String[] args) throws IOException {
		
		

	byte[] array = Files.readAllBytes(Paths.get("C:\\Users\\hemant.mittal\\Desktop\\Backup\\IRIS_Resume_Hemant Mittal.doc"));
	
	
	String encodedBase64 = new String(Base64.getEncoder().encode(array));
	System.out.println(encodedBase64);
	
	}
}