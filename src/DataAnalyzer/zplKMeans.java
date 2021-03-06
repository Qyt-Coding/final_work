package DataAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class zplKMeans {
	final double ERROR_DATE = 0.00001;

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
		int it = 0;// 计数器
		List<docVector> originDocList = new ArrayList<docVector>();
		while (it <= 20) {// 最大遍历20次
			System.out.println("times:" + it);
			it++;
			// 重新计算每个聚类的均值
			for (int i = 0; i < _k; i++) {
				originDocList.add(_clusterSet[i].getMean());
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
//			int k = 0;
//			for (docVector set : _vectorSet.getList()) {
//				if (set.isEqAssig())
//					k++;
//			}
//			if (k == _vectorSet.getdocNum())
//				break;
			// 5、否则需要重新调整资料点和群聚类的关系，调整完毕后再重新开始循环；
			// 需要修改每个聚类的成员和表示某个数据属于哪个聚类的变量
			// 清空完
			for (int i = 0; i < _k; i++) {
				_clusterSet[i].getTerm().clearAll();
			}
			for (docVector set : _vectorSet.getList()) {
				_clusterSet[set.getnearestCluster()].addDoc(set);
				// set.setAeqN();
			}

			int count = 0;// 计数器
			for (int i = 0; i < _k; i++) {
				boolean flag = _clusterSet[i].isPass(originDocList.get(i), ERROR_DATE);
				if (flag) {
					count++;
				}
			}
			if (count == _k)
				break;// 跳出去
		}
		/**
		 * 这里明显有问题，不管数据如何，程序都只能走两遍，并不是根据具体阈值而停止工作的，
		 * 具体可以看set.setAeqN();这句话，只要k的值等于文档的值，它就不循环了
		 */
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
			intRd = Math.abs(rdm.nextInt()) % docNum + 1;// 45
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
					System.out.println("集合长度" + _clusterSet[_result[i]].getTerm().getList().size());
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

//		int size = _clusterSet[_result[_k - 1]].getNum();
//		for (int i = 0; i < size; i++) {
//			String cotString = _clusterSet[_result[_k - 1]].getTerm().getList().get(i).getWeiboCon();
//
//			System.out.println(
//					"text:" + cotString + " ID:" + _clusterSet[_result[_k - 1]].getTerm().getList().get(i).getID());
//		}

	}
	
	public void getAllClusterInfo() {
		for(int i=0;i<20;i++){
			List<docVector> docList=_clusterSet[i].getTerm().getList();
			System.out.println("第 "+i+"个");
			for(docVector doc:docList) {
				System.out.println("内容："+doc.getWeiboCon()+" ID:"+doc.getID());
			}
		}
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
}