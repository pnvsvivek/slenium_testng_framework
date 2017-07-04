package reusability;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.testng.TestNG;

import com.google.common.collect.Lists;
import com.relevantcodes.extentreports.ExtentReports;

/**
 * This is the class which is special in this framework and this contains methods that gets class name and method name from the row where we have Test criterion 'Y'
 * in test data excel and based on the class name and method name dynamic XML is created in testng.xml and created XML will be run automatically with the help of TestNG Library
 *
 * @author p.nvs.vivek
 */

public class Runner {
		
	public static int activerow = 0;
	public static ExtentReports report = null;
	public static PrintStream ps =null;
		public static void main(String args[]) throws IOException
		{
			ps= Components.consloeToText(Components.getProperties("errorPath"));
			initializeReport();
			executeTestcases(Components.getProperties("TDMPath"),Components.getProperties("SheetName"));
			report.flush();
			report.close();
		}
		
		/**
		 * This method that fetches method name and class name based on the test criterion in test data excel
		 * @param path - Path for the excel file where test data is maintained(This can be inside the project or outside the project
		 * @param sheetName - sheet name of the excel
		 * @throws IOException - Exception that is caught while we are dealing with files
		 */
		@SuppressWarnings("resource")
		public static void executeTestcases(String path, String sheetName) throws IOException
		{
			FileInputStream fis = new FileInputStream(path);
			Workbook wb = new XSSFWorkbook(fis);
			Sheet sh = wb.getSheet(sheetName);
			int rd = sh.getLastRowNum()-sh.getFirstRowNum();
			for(int i=1; i<=rd; i++)
			{
			Row row = sh.getRow(i);
			if(row!=null)
			{
				if(row.getCell(2).toString().equalsIgnoreCase("Y"))
				{
					activerow = i;
					createXML(row.getCell(0).toString(),row.getCell(1).toString());
					triggerTestNG();
				}
			}
			}
		}
		
		/**
		 * This method that creates dynamic excel in the testng.xml location and it uses jdom libraries
		 * @param classname - class name from test data excel
		 * @param methodname - method name from test data excel
		 * @throws IOException - Exception that occurs due to file operations
		 */
		public static void createXML(String classname, String methodname) throws IOException
		{
			Element suite = new Element("suite");
			suite.setAttribute("name","Suite");
			Document doc = new Document();
			doc.setRootElement(suite);
			Element test = new Element("test");
			test.setAttribute("name","Test");
			Element classes = new Element("classes");
			Element clas = new Element("class");
			clas.setAttribute("name",classname);
			Element methods = new Element("methods");
			Element include = new Element("include");
			include.setAttribute("name",methodname);
			doc.getRootElement().addContent(test);
			test.addContent(classes);
			classes.addContent(clas);
			clas.addContent(methods);
			methods.addContent(include);
			XMLOutputter xmloutput = new XMLOutputter();
			xmloutput.setFormat(Format.getPrettyFormat());
			xmloutput.output(doc, new FileWriter(Components.getProperties("testngxml")));			
		}
		
		/**
		 * This method runs the testng.xml automatically using TestNG libraries
		 */
		public static void triggerTestNG()
		{
			TestNG tng = new TestNG();
			List<String> list = Lists.newArrayList();
			list.add(Components.getProperties("testngxml"));
			tng.setTestSuites(list);
			tng.run();
		}
		
		/**
		 * This method is for initializing extent reports
		 */
		public static void initializeReport()
		{
			report = new ExtentReports(Components.getProperties("LogPath"));
			report.loadConfig(new File(System.getProperty("user.dir")+Components.getProperties("extent-config")));
		}
}
