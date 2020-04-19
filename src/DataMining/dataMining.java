package DataMining;

import java.io.IOException;

//这个是数据获取模块的主程序
public class dataMining {

	public boolean mainDataMining() throws Exception{
		initDB();
		//初始化微博与人物队列
		if(loginWeibo())
		{
			try {
				process();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			return false;
		}
		return false;
		
	}
	
	//登陆微博并且初始化人物队列
	public boolean loginWeibo() throws Exception{
		People tmp = null;
		int size = Global.usedIDList.size();
		if(size == 0){
			tmp = new People(Global.uIDString);
		}
		else {
			tmp = new People(Global.usedIDList.get(size -1));
		}
		//获得到全部粉丝
		tmp.getFriends(Global.queue);
		return true;
		
	}
	/**
	 * 改版前
	 * @return
	 */
//	public boolean process() throws Exception{
//		int count=0; //计数用的，可以忽略不记
//		int  initCount=Global.queue.getCount();//获得队列中的数
//		People tmp;
//		//处理数据，建立倒排索引   loginWeibo是根据你自身的id，获得到关注用户的id号码
//		//然后下面的方法就是根据
//		while(initCount != 0){//这里20个
//			tmp = Global.queue.getOut();
//			count++;
//			System.out.println("id    ---->"+tmp.getID()+"-----"+"-----第"+count+"个count");
//			if(tmp == null)
//				return false;
//			//数据处理   //这里的意思是，根据用户的id获得微博
//			if(tmp.getDoc() == false)
//				return false;
//			//根据id来查找关注人的好友信息
//			tmp.getFriends(Global.queue);
//			Global.usedIDList.add(tmp.getID());
//			Global.dBer.insertUsedList(tmp.getID());
//			initCount--;
//		}
//		return true;
//	}
	/**
	 * 改版后
	 */
	public boolean process() throws Exception{
		int  initCount=20;//获得队列中的数
		People tmp;
		while(initCount != 0){//循环遍历20次
			tmp = Global.queue.getOut();//从队列中获得微博用户
			if(tmp == null)//非空判断，非必要
				return false;
			if(tmp.getDoc() == false)//获得该用户的文档
				return false;
			tmp.getFriends(Global.queue);//获得该用户所关注的用户信息
			Global.usedIDList.add(tmp.getID());//把已经处理过的用户id放到队列中
			Global.dBer.insertUsedList(tmp.getID());//把用户Id插入数据库
			initCount--;
		}
		return true;
	}
	
	public boolean initDB(){
		Global.dBer.connectDB();
		Global.dBer.initUsedList();
		Global.dBer.initValue();
		return false;
	}
	
}
