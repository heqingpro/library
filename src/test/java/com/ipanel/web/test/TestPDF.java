package com.ipanel.web.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.examples.NewWorkbook;
import org.junit.After;
import org.junit.Test;

import com.ipanel.web.utils.FileUtil;

/**
* @author fangg
* 2017年12月26日 下午3:48:34
*/
public class TestPDF {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	public static void main(String[] args) {
//		PdfReader reader = new PdfReader ( dqdir) ;
//		System.out.println(SimpleBookmark.getBookmark ( reader ));
		
		String srcPath = "D:\\安康历代山水诗辑注.pdf";
		String descPath = "D:\\book\\安康历代山水诗辑注.pdf";
		
		File sourceFile = new File(srcPath);
		File outFile = new File(descPath);
		outFile.getParentFile().mkdirs();
//        new ExtractStreams().parse(SRC, DEST);
		try
		{
			try {
				FileUtils.writeByteArrayToFile(outFile,FileUtil.getByteArrayFromInputStream(new FileInputStream(sourceFile)));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

}
