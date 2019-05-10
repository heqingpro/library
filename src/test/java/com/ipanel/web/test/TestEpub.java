package com.ipanel.web.test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.aspectj.util.FileUtil;
import org.junit.Test;

import com.sun.jmx.mbeanserver.MetaData;
import com.sun.star.util.FileIOException;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.MediaType;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;

/**
* @author fangg
* 2017年12月26日 下午2:18:34
*/
public class TestEpub {

	@Test
	public void test() {
		
		try {  
		    EpubReader epubReader = new EpubReader();  
		    MediaType[] lazyTypes = {  
		               MediatypeService.CSS,    
		               MediatypeService.GIF, MediatypeService.JPG,  
		               MediatypeService.PNG,  
		               MediatypeService.MP3,  
		               MediatypeService.MP4};  
		      String fileName = "D:/TS00010678.epub";  
		      Book book = epubReader.readEpubLazy(fileName,"UTF-8",Arrays.asList(lazyTypes)); 
		      List<Resource> contents = book.getContents();  
		      book.getMetadata();
		} catch (Exception e) {  
		    e.printStackTrace();  
		} 
		
	}
	
	public static void main(String[] args) {

		try {  
		    EpubReader epubReader = new EpubReader();  
		    MediaType[] lazyTypes = {  
		               MediatypeService.CSS,    
		               MediatypeService.GIF, MediatypeService.JPG,  
		               MediatypeService.PNG,  
		               MediatypeService.MP3,  
		               MediatypeService.MP4};  
		      String fileName = "D:/TS00009351.epub";  
		      Book book = epubReader.readEpubLazy(fileName,"UTF-8",Arrays.asList(lazyTypes)); 
		      Metadata metadata = book.getMetadata();
		       System.out.println("标题"+metadata.getTitles());
		       System.out.println("作者："+metadata.getAuthors());
		       System.out.println("语言："+metadata.getLanguage());
		       System.out.println("出版社："+metadata.getPublishers());
		       System.out.println("类型："+metadata.getTypes());
		       System.out.println("封面图："+book.getCoverImage());
		       System.out.println("别名："+metadata.getFirstTitle());
		       System.out.println("贡献者："+metadata.getContributors());
		       System.out.println("版权："+metadata.getRights());
		       System.out.println("科目："+metadata.getSubjects());
		       String currentPath = Thread.currentThread().getClass().getResource("/").toString();
		       String epubPath = currentPath + "epub/myBook.epub";
		       // 去年路径中的前缀//file:/
		       epubPath = epubPath.substring(6, epubPath.length());

		       epubPath = epubPath.replace("/", "//");

		       System.out.println(epubPath);
		       List<Resource> contents = book.getContents();  
			    
		       System.out.println("通过spine获取线性的阅读菜单，此菜单依照阅读顺序排序  ");
		     //通过spine获取线性的阅读菜单，此菜单依照阅读顺序排序  
		       List<SpineReference> spineReferences = book.getSpine().getSpineReferences();  
		       //获取对应resource  
		       int i =0;
		       for(SpineReference spineReference:spineReferences) {
		    	   System.out.println(spineReference.getResource().getHref());   
		    	   System.out.println(spineReference.getResource().getTitle());
		    	   System.out.println(new String(spineReference.getResource().getData()));
		    	   i++;
		    	   if(i==2) {
		    		   break;
		    	   }
		       }
		       //通过TableOfContents获取树形菜单。此菜单按照章节之间的关系（树形）排序  
		       TableOfContents tableOfContents = book.getTableOfContents();  
		       
		} catch (Exception e) {  
		    e.printStackTrace();  
		} 
	}

}
