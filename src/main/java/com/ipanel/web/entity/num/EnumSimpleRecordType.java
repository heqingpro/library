package com.ipanel.web.entity.num;
/**
 * @author fangg
 * 2017年5月15日 下午2:01:19
 */
public enum EnumSimpleRecordType {
		wanna("我想做",0),
		can("我会做",1),
		done("我做过",2),
		viewingRecord("历史记录",3)  //观看记录
	;

    private String name;  
    private Integer index;
    private EnumSimpleRecordType(final String name,final Integer index) {  
        this.name = name;  
        this.index = index;
    }  
    public static String getName(final Integer index) {
        String name = "";
    	for (EnumSimpleRecordType c : EnumSimpleRecordType.values()) {  
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
