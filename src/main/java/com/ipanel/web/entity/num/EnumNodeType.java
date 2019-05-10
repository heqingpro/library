package com.ipanel.web.entity.num;
/**
 * 分类类型
 * @author fangg
 * 2017年5月11日 下午5:02:43
 */
public enum EnumNodeType {
	ORDIN("普通分类",0),
	ENTRY("单品类",1),
	BUNDLE("剧集类",2),
	ACTIVIRY("专题类",3)
	;

    private String name;  
    private Integer index;
    private EnumNodeType(final String name,final Integer index) {  
        this.name = name;  
        this.index = index;
    }  
    public static String getName(final Integer index) {
        String name = "";
    	for (EnumNodeType c : EnumNodeType.values()) {  
            if (c.getIndex().equals(index)) {  
            	name= c.name;  
            	break;
            }  
        }  
        return name;  
    } 
    
    public String getName() {  
        return name;  
    }  
    public void setName(final String name) {  
        this.name = name;  
    }
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}  
}
