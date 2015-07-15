package cn.test;

public class TestShutDonwHook {
	public void TestShutDownHook() {
		doShutDownWork();
	}
	private void doShutDownWork() {
		Runtime run=Runtime.getRuntime();//当前 Java 应用程序相关的运行时对象。
		run.addShutdownHook(new Thread(){ //注册新的虚拟机来关闭钩子
			@Override
			public void run() {
				//程序结束时进行的操作
				System.out.println("程序结束调用");
			}
		});
	}
	public static void main(String[] args) {
		new TestShutDonwHook();
		for (int i = 0; i < 100000000; i++) { // 在这里增添您需要处理代码 }
			System.out.println(i);
		}
	}
}