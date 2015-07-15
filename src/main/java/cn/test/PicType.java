package cn.test;

public enum PicType {

	COMMODITY("商品图片"), GAME("游戏图片");
		
	private String typeName;	
	private PicType(String typeName){
		this.typeName = typeName;
	}
	public String getTypeName(){
		return this.typeName;
	}
	
	public static void main(String args[]){
		PicType pt1 = PicType.COMMODITY;
		PicType pt2 = PicType.GAME;
		for(PicType picType : PicType.values())	
			System.out.println(picType.getTypeName());
		}
}
