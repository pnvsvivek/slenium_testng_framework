package reusability;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import reusability.Components;

/**
 * This class contains all methods that are operating on files such as excel, database & text files etc.
 * 
 * @author p.nvs.vivek
 *
 */
public class Utilities {

	/***
	 * This method is for reading excel file and storing them in HashMaps where key is the first row and value is from second row and returns hashMap based on the value
	 * @param path - Excel sheet path
	 * @param sheetname - sheet name from where data is fetched
	 * @param value - This can be anything unique in a row for example test case name
	 * @return
	 * @throws IOException - This is Exception that arises due to file operations
	 */
	@SuppressWarnings("resource")
	public static HashMap<String, String> readExcel(String path, String sheetname, String value) throws IOException
	{
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		FileInputStream fis = new FileInputStream(path);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheet(sheetname);
		int rd = sheet.getLastRowNum()-sheet.getFirstRowNum();
		for(int i=1; i<=rd;i++)
		{
			HashMap<String, String> hm = new HashMap<String, String>();
			Row row = sheet.getRow(i);
			if(row!=null)
			{
				for(int j=0; j<row.getLastCellNum();j++)
				{
				Cell cell = row.getCell(j);
				if(cell!=null)
				{
					hm.put(sheet.getRow(0).getCell(j).toString(), cell.toString());
				}
				}
			}
			list.add(hm);
		}
		HashMap<String, String> exphm = new HashMap<String, String>();
		for(int i=0; i< list.size();i++)
		{
			exphm = list.get(i);
			if(exphm.values().contains(value))
			{
				break;
			}
		}
		return exphm;
	}
	
	/**
	 * This method is for getting an HashMap based on the row where test criterion is 'Y' from Batch class
	 * @param path - path for excel
	 * @param sheetname - sheet name
	 * @return
	 * @throws IOException - Exception that arises due to file operations
	 */
	@SuppressWarnings("resource")
	public static HashMap<String, String> readExcel_dataDriven(String path, String sheetname) throws IOException
	{
		HashMap<String, String> hm = new HashMap<String, String>();
		FileInputStream fis = new FileInputStream(path);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheet(sheetname);
		Row row = sheet.getRow(Runner.activerow);
			if(row!=null)
			{
				for(int j=0; j<row.getLastCellNum();j++)
				{
				Cell cell = row.getCell(j);
				if(cell!=null)
				{
					hm.put(sheet.getRow(0).getCell(j).toString(), cell.toString());
				}
				}
			}
		return hm;
	}
	
	/**
	 * This method is for taking screen shot with the help of selenium libraries
	 * @param driver - WebDriver Instance
	 * @param testcaseName - test case name
	 * @return
	 * @throws IOException - This exception is raised due to file operations
	 */
	public static String screenPrints(WebDriver driver, String testcaseName) throws IOException
	{
		TakesScreenshot screen = (TakesScreenshot)driver;
		File srcFile = screen.getScreenshotAs(OutputType.FILE);
		SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy hh_mm_ss  a");
		String name = sdf.format(new Date());
		String screensLocation = System.getProperty("user.dir")+Components.getProperties("screenprints")+testcaseName+"/"+name+"."+"png";
		File destFile = new File(screensLocation);
		FileUtils.copyFile(srcFile, destFile);
		return screensLocation;
	}
	
	/**
	 * This method is for fetching data from database tables
	 * @param JDBC_Driver - JDBC Driver based on database type
	 * @param DB_Url - Database url
	 * @param username - username of database
	 * @param password - password of database
	 * @param SqlQuery - SQL Query
	 * @param uniqueValue - unique value based on this a particular hashMap or row is retrieved
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static HashMap<String, String> readDB(String JDBC_Driver, String DB_Url, String username, String password, String SqlQuery, String uniqueValue) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		Class.forName(JDBC_Driver).newInstance();
		Connection con = DriverManager.getConnection(DB_Url, username, password);
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(SqlQuery);
		ResultSetMetaData rmd = res.getMetaData();
		int columncount = rmd.getColumnCount();
		while(res.next())
		{
			HashMap<String, String> hm = new HashMap<String, String>();
			for(int i=0; i<columncount; i++)
			{
				hm.put(rmd.getColumnLabel(i),res.getString(i));
			}
			list.add(hm);
		}
		HashMap<String, String> expHm = new HashMap<String, String>();
		for(int i=0; i< list.size(); i++)
		{
			expHm = list.get(i);
			if(expHm.values().contains(uniqueValue))
			{
				break;
			}
		}
		return expHm;	
	}
	/**
	 * This method is for reading the Text file line by line
	 * @param textfilePath is the path for the text file 
	 * @param indexforline = each line is an index as it stores all the lines in lists
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public static String readTextFilelinebyLine(String textfilePath, int indexforline) throws IOException
	{
		File file = new File(textfilePath);
		List<String> readValue = FileUtils.readLines(file);
		return readValue.get(indexforline);
	}
	/**
	 * This method is for reading entire file and print as it is
	 * @param textfilePath is the path for the file
	 * @param indexforline
	 * @return
	 * @throws IOException
	 */
			
	@SuppressWarnings("deprecation")
	public static String readTextFile(String textfilePath) throws IOException
	{
		File file = new File(textfilePath);
		String readValue = FileUtils.readFileToString(file);
		return readValue;
	}
	/**
	 * This method is for writing content to Text File
	 * @param filePath
	 * @param list
	 * @throws IOException
	 */
	public static void writeTofile(String filePath, List<String> list) throws IOException
	{
		File file = new File(filePath);
		FileUtils.writeLines(file, list);
	}
}
