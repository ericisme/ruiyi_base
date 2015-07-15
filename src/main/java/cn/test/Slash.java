package cn.test;

public class Slash {

	public static void main(String[] args) {
		String str = System.getProperties().getProperty("java.io.tmpdir");
		System.out.println(str.replaceAll("\\\\","/"));
		System.out.println(replace_slash_2_backslash(str));
		System.out.println(str.replaceAll("/", "\\\\"));
		System.out.println(replace_backslash_2_slash(str));
	}
	public static String replace_slash_2_backslash(String str){
		String temp = "";
		if(str != null){
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)=='\\'){
					temp = temp + '/';
				}else{
					temp = temp + str.charAt(i);
				}
			}
		}else{
			temp = str;
		}
		return temp;
	}

	public static String replace_backslash_2_slash(String str){
		String temp = "";
		if(str!=null){
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)=='/'){
					temp = temp + '\\';
				}else{
					temp = temp + str.charAt(i);
				}
			}
		}else{
			temp = str;
		}
		return temp;
	}

}
