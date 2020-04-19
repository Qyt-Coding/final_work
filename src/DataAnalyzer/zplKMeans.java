package DataAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.bean.WeiboDoc;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class zplKMeans {
	private int docNum;
	private int _k;
	private int[] _result;// 结果集
	private zplCluster[] _clusterSet;// 集群
	// 原始数据
	private TermVector _vectorSet;

	public zplKMeans(int k) {
		Global._k = k;// 20
		// 按道理说这里为0的
		docNum = Global.docNum;
		_k = k;
		_clusterSet = new zplCluster[_k];// 初始化这个数组
		_result = new int[_k];

		for (int i = 0; i < _k; i++) {
			_clusterSet[i] = new zplCluster(); // 给这个数组赋值
			_result[i] = i; // 给_result数组赋值
		}
	}

	public boolean readVectorSet() {
		_vectorSet = Global.dBer.buildTermVector();// 对每个文档进行向量化
		if (_vectorSet == null)
			return false;
		docNum = Global.docNum;
		return true;
	}

	public void start() {
		int it = 0;
		while (true) {
			System.out.println("times:" + it);
			it++;
			// 重新计算每个聚类的均值
			for (int i = 0; i < _k; i++) {
				_clusterSet[i].UpdateMean();// 重新的对聚类进行计算
				System.out.println("mean:" + _clusterSet[i].getMean().computeLength());
				if (_clusterSet[i].getMean().computeLength() == 0) {
					System.out.println("id:" + i);
					return;
				}
			}

			// 计算每个数据和每个聚类的距离//这里主要是重新的对每个数据的聚类重新安排
			for (docVector set : _vectorSet.getList()) {
				for (int j = 0; j < _k; j++) {
					double dist = TermVector.getDist(set, _clusterSet[j].getMean());
					set.setDist(j, dist);
				}
			}

			// 计算每个数据离那个聚类最近
			for (docVector set : _vectorSet.getList()) {
				set.setnearestCluster();
			}
			// 4、比较每个数据最近的聚类是否就是它所属的聚类
			// 如果全相等表示所有的点已经是最佳距离了
			int k = 0;
			for (docVector set : _vectorSet.getList()) {
				if (set.isEqAssig())
					k++;
			}
			if (k == _vectorSet.getdocNum())
				break;
			// 5、否则需要重新调整资料点和群聚类的关系，调整完毕后再重新开始循环；
			// 需要修改每个聚类的成员和表示某个数据属于哪个聚类的变量
			// 清空完
			for (int i = 0; i < _k; i++) {
				_clusterSet[i].getTerm().clearAll();
			}
			for (docVector set : _vectorSet.getList()) {
				_clusterSet[set.getnearestCluster()].addDoc(set);
				set.setAeqN();
			}
		}

	}

	public int getK() {
		return _k;
	}

	public int[] getRes() {
		return _result;
	}

	public void initRandom() {
		int[] intRet = new int[_k];
		for (int i = 0; i < _k; i++) {
			intRet[i] = -1;
		}
		int intRd = 0; // 存放随机数
		int count = 0; // 记录生成的随机数个数
		int flag = 0; // 是否已经生成过标志
		while (count < _k) {
			Random rdm = new Random(System.currentTimeMillis());
			intRd = Math.abs(rdm.nextInt()) % docNum + 1;// 生成随机数
			for (int i = 0; i < _k; i++) {
				if (intRet[i] == intRd) {
					flag = 1;
					count--;
					break;
				}
			}
			if (flag == 0) {
				intRet[count] = intRd;
				docVector vector = Global.dBer.buildDocVector(intRd);
				if (vector == null) {
					count--;
				} else {
					TermVector termVector = new TermVector(vector);
					if (_clusterSet[count].setCluster(termVector) == false) {
						count--;
					} else {
						_vectorSet.removeVector(vector);
						// System.out.println("IDnum:" + intRd + ";content:" + vector.getWeiboCon());
					}
				}

			}
			count++;
			flag = 0;
		}

	}

	public void showAll() {
		sortC();
		int j = 1;
		// 打开文件
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(new File("D://result.xls"));
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet("第一页", 0);
			for (int i = _k - 1; i > 0; i--) {
				String cotString;
				int size = _clusterSet[_result[i]].getTerm().getList().size();
				if (size > 10) {
					cotString = _clusterSet[_result[i]].getTerm().getList().get(0).getWeiboCon();
					System.out.println("time:" + size);
					System.out.println(
							"text:" + cotString + " ID:" + _clusterSet[_result[i]].getTerm().getList().get(0).getID());

					// 在Label对象的构造子中指名单元格位置是第j列第i行(j,i)以及单元格内容为str[j]
					Label labe = new Label(0, j, String(j));
					Label label = new Label(1, j, cotString);
					// 将定义好的单元格添加到工作表中
					try {
						sheet.addCell(labe);
						sheet.addCell(label);
					} catch (RowsExceededException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					j++;
				}
			}
			book.write();
			try {
				book.close();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * int size = _clusterSet[_result[_k-1]].getNum(); for(int i = 0; i< size;i++){
		 * String cotString =
		 * _clusterSet[_result[_k-1]].getTerm().getList().get(i).getWeiboCon();
		 * 
		 * System.out.println("text:"+cotString+" ID:"+_clusterSet[_result[_k-1]].
		 * getTerm().getList().get(i).getID()); }
		 */
	}

	private String String(int j) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sortC() {
		int flag = 0;
		for (int j = _k - 1; j > 0; j--) {
			for (int i = 0; i < j; i++) {
				if (_clusterSet[_result[i]].getNum() > _clusterSet[_result[i + 1]].getNum()) {
					int t = _result[i];
					_result[i] = _result[i + 1];
					_result[i + 1] = t;
					flag = 1;
				}
			}
			if (flag == 0)
				break;
		}

	}

	public void calculate() {
		List<WeiBoVo> vo=new ArrayList<WeiBoVo>();
		for(int j=0;j<_clusterSet.length;j++) {
			WeiBoVo v=new WeiBoVo();
			List<WeiboDoc> weiboDocList=new ArrayList<WeiboDoc>();
			List<docVector> t=_clusterSet[j].getTerm().getList();
			for(int i=0;i<t.size();i++) {
				long total=0;
				docVector docV=t.get(i);
				 WeiboDoc weibo=Global.dBer.getDocById(docV.getID());
				 Integer repostsCount=weibo.getRepostsCount();
				 if(repostsCount==null) {
					 repostsCount=0;
				 }
				 Integer commentsCount=weibo.getCommentsCount();
				 if(commentsCount==null) {
					 commentsCount=0;
				 }
				 Integer attitudesCount=weibo.getAttitudesCount();
				 if(attitudesCount==null) {
					 attitudesCount=0;
				 }
				 total=repostsCount*3+commentsCount*2+attitudesCount;
				 weibo.setRank(total);
				 weiboDocList.add(weibo);
			}
			v.setId(j);
			v.setWeiboList(weiboDocList);
			vo.add(v);
		}
		
		//遍历
		for(WeiBoVo v1:vo) {
			List<WeiboDoc> list=v1.getWeiboList();
			WeiboDoc[] weiboTable = new WeiboDoc[list.size()];
			list.toArray(weiboTable);
			Arrays.sort(weiboTable);
			System.out.println("最大的值:"+weiboTable[0].getRank()+"   "+weiboTable[0].getText());
		}
	}

}