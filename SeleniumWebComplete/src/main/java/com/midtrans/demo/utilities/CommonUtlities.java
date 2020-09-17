package com.midtrans.demo.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.midtrans.demo.BaseClass.TestBase;
import com.midtrans.demo.reports.LoggerHelper;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class CommonUtlities extends TestBase{
	private final static Logger log=LoggerHelper.getLogger(CommonUtlities.class);
	public CommonUtlities() {
		super();
	}
	
	public static long PageLoadtimeOut=20;
	public static long implicitWait=20;	
	
	public static String[][] getExcelData(String fileName, String sheetName) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			Workbook wb = Workbook.getWorkbook(fs);
			Sheet sh = wb.getSheet(sheetName);

			int totalNoOfCols = sh.getColumns();
			int totalNoOfRows = sh.getRows();

			arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols];

			for (int i = 1; i < totalNoOfRows; i++) {

				for (int j = 0; j < totalNoOfCols; j++) {
					arrayExcelData[i - 1][j] = sh.getCell(j, i).getContents();
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		}
	
		return arrayExcelData;
		
	}
	public static long calculateTotalAmount(WebElement quantity, String unitPrice) {
		String Nquanity[] = quantity.getText().split(" ");

		long Totalquantity = Long.parseLong(Nquanity[1]);

		long perUnitPrice = Long.parseLong(unitPrice);

		System.out.println(Totalquantity * perUnitPrice);

		return Totalquantity * perUnitPrice;

	}
	
	public static void selectElementWithTextFromList(List<WebElement> paymentOptions, String text) {
		for (WebElement webElement : paymentOptions) {

			if (webElement.getText().contains(text)) {
				webElement.click();
				break;

			}

		}

	}
	public static String getCurrentDateWithFormat()
	{
		    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		    Date date = new Date();  
		    System.out.println(formatter.format(date)); 
		    return formatter.format(date);
	}
	public static String getCurrentDateWithTimeFormat()
	{
	       DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	       Date dateobj = new Date();
	       
	       return df.format(dateobj);
	}

	public static void scrollToElement(WebElement element)
	{
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}



	public static long convertElementToNumber(WebElement element) {
		long number = (long) Double.parseDouble(element.getText().replaceAll("[,+-]", ""));
		
		return number;

	}
	
	public static String elementWithText(WebElement element)
	{
		return element.getText();
	}
	
	public static void getScreenShot(String path,String method)
	{
		try {

			TakesScreenshot ts = (TakesScreenshot) driver;

			File source = ts.getScreenshotAs(OutputType.FILE);

			FileUtils.copyFile(source, new File(path + method +getCurrentDateWithTimeFormat()+ ".png"));

			System.out.println("Screenshot taken");
		} catch (Exception e) {

			System.out.println("Exception while taking screenshot " + e.getMessage());
		}

		
	}

}
