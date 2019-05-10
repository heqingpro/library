package com.ipanel.web.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
* @author fangg
* 2018年1月11日 下午3:33:43
*/
public class TestEpubFormat {

	
	public static void main(String[] args) {
		   Document document = Jsoup.parse("<?xml version='1.0' encoding='utf-8'?>\r\n" + 
		   		"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"zh-CN\">\r\n" + 
		   		"  <head>\r\n" + 
		   		"    <title>前　言　俞敏洪的价值</title>\r\n" + 
		   		"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n" + 
		   		"  <link href=\"../stylesheet.css\" type=\"text/css\" rel=\"stylesheet\"/>\r\n" + 
		   		"<link href=\"../page_styles.css\" type=\"text/css\" rel=\"stylesheet\"/>\r\n" + 
		   		"</head>\r\n" + 
		   		"  <body class=\"calibre\">\r\n" + 
		   		"<p class=\"content\"></p><a id=\"chapter3\" href=\"catalog.html#title1\"><p class=\"chapterTitle\">前　言　俞敏洪的价值</p>\r\n" + 
		   		"</a><p class=\"reference\">到今天为止，我一直认为自己是一只蜗牛。我一直在爬，也许还没有爬到金字塔的顶端。只要你在爬，就足以给自己留下令生命感动的日子。</p>\r\n" + 
		   		"<p class=\"reference\">人的一生是奋斗的一生，但是有的人一生过得很伟大，有的人一生过得很琐碎。如果我们有一个伟大的思想，有一颗善良的心，我们一定能把很多琐碎的日子堆砌起来，变成一个伟大的生命。如果你每天庸庸碌碌，没有理想，从此停止进步，那你一辈子的日子堆积起来将永远是一堆琐碎。所以，我希望所有的人能把自己每天平凡的日子堆砌成伟大的人生。</p>\r\n" + 
		   		"<p class=\"right-content\">——俞敏洪</p>\r\n" + 
		   		"<p class=\"center-content\">一</p>\r\n" + 
		   		"<p class=\"content\">俞敏洪说：“新东方披着理想主义的外衣创业，这是那些在新东方没有获得既得利益的人说的。”新东方当然不只是披着理想主义的外衣，它还是理想主义者最快乐的聚会之地。拥有独立的精神、自由的氛围，能够改变中国教育和年轻人面对世界的态度，这就是新东方的理想。</p>\r\n" + 
		   		"<p class=\"center-content\">二</p>\r\n" + 
		   		"<p class=\"content\">我们曾经生活在一个理想主义的时代。</p>\r\n" + 
		   		"<p class=\"content\">20世纪80年代的中国，各种思潮碰撞，迸发着迷人的火花。在校园里，几乎每个男生都成了诗人；改革精神令人动容，中国的年轻人们激动地畅想着国家的未来。然而，理想主义毕竟是虚幻的，它最终导致的是一个理想化的结局：一个国家怎么可能一下子变成人间天堂呢？</p>\r\n" + 
		   		"<p class=\"content\">他终究还是有理想的，他希望将新东方做成“中国的哈佛”。</p>\r\n" + 
		   		"<p class=\"center-content\">三</p>\r\n" + 
		   		"<p class=\"content\">中国正处于巨大的变化之中，这是自1992年以来的又一个转机。面对众多“80后”“90后”，作为培训机构的新东方，也在观察这些孩子。新东方要持续发展，年轻人就是希望。</p>\r\n" + 
		   		"<p class=\"content\">新东方不教诲学员，只要求自己做好事情，然后给孩子们树立一个榜样。如果要挑刺，任何社会都有毛病。新东方也挑刺，但不是为了泄愤，而是教导年轻人选择更正确的态度去面对问题。</p>\r\n" + 
		   		"<p class=\"content\">这是俞敏洪的价值，他不是完美的人，却是一个难得的榜样。</p>\r\n" + 
		   		"</body></html>\r\n" + 
		   		"");
	   
	   	   System.out.println();
	   	   
		}
}
