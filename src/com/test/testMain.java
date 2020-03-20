package com.test;

import java.util.Random;
import java.util.Scanner;

import com.bean.PPoint;

public class testMain {

	PPoint pc[] = null;
	PPoint pcore[] = null;
	PPoint pcoren[] = null;

	public void init() {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入生成随机点个数");
		int num = sc.nextInt();
		pc = new PPoint[num];
		// 防止生成重复点
		float x0 = new Random().nextInt(10);
		float y0 = new Random().nextInt(10);
		pc[0] = new PPoint();
		pc[0].x = x0;
		pc[0].y = y0;
		for (int i = 1; i < num; i++) {
			int flag = 0;
			float x = new Random().nextInt(10);
			float y = new Random().nextInt(10);
			for (int j = 0; j < i; j++) {
				if (pc[j].x == x && pc[j].y == y) {
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				i--;
			} else {
				pc[i] = new PPoint();
				pc[i].x = x;
				pc[i].y = y;
			}
		}
		System.out.println("请输入聚类中心个数");
		int core = sc.nextInt();
		pcore = new PPoint[core];
		pcoren = new PPoint[core];
		// 防止生成重复中心
		int temp[] = new int[core];
		temp[0] = new Random().nextInt(num);
		pcore[0] = new PPoint();
		pcore[0].x = pc[temp[0]].x;
		pcore[0].y = pc[temp[0]].y;
		for (int i = 1; i < core; i++) {
			int flag = 0;
			int tempRandom = new Random().nextInt(num);
			for (int j = 0; j < i; j++) {
				if (temp[j] == tempRandom) {
					flag = 1;
					break;
				}
			}
			if (flag == 1) {
				i--;
			} else {
				temp[i] = tempRandom;
				pcore[i] = new PPoint();
				pcore[i].x = pc[tempRandom].x;
				pcore[i].y = pc[tempRandom].y;
				pcore[i].flag = 0; // 0表示聚类中心
			}
		}

		System.out.println("生成随机点如下：");
		for (int i = 0; i < num; i++) {
			System.out.println(pc[i].x + "," + pc[i].y);
		}
		System.out.println("生成聚类中心如下");
		for (int i = 0; i < pcore.length; i++) {
			System.out.println("<" + pcore[i].x + "," + pcore[i].y + ">");
		}

	}

	public void moveCore() {
		searchBelong();
		calAverage();
		double moveDist = 0;
		int flag = 0;
		for (int i = 0; i < pcore.length; i++) {
			flag = 0;
			moveDist = distPPoint(pcore[i], pcoren[i]);
			if (moveDist > 0.01) {
				flag = 1;
				break;
			}
		}
		if (flag == 0) {
			System.out.println("迭代完毕");
		} else {
			copyCore(pcore, pcoren);
			moveCore();
		}
	}

	public void copyCore(PPoint[] oldCore, PPoint[] newCore) {
		for (int i = 0; i < pcore.length; i++) {
			oldCore[i].x = newCore[i].x;
			oldCore[i].y = newCore[i].y;
			oldCore[i].flag = 0;
		}
	}

	public void searchBelong() {
		for (int i = 0; i < pc.length; i++) {
			double dist = 999;
			int label = -1;
			for (int j = 0; j < pcore.length; j++) {
				double distance = distPPoint(pc[i], pcore[j]);
				if (distance < dist) {
					dist = distance;
					label = j;
				}
			}
			pc[i].flag = label + 1;
		}
	}

	public double distPPoint(PPoint i, PPoint j) {
		return Math.sqrt(Math.pow(i.x - j.x, 2) + Math.pow(i.y - j.y, 2));
	}

	public void calAverage() {
		for (int i = 0; i < pcore.length; i++) {
			System.out.println("属于<" + pcore[i].x + "," + pcore[i].y + ">的点有：");
			float lengthX = 0;
			float lengthY = 0;
			int number = 0;
			for (int j = 0; j < pc.length; j++) {
				if (pc[j].flag == (i + 1)) {
					System.out.println(pc[j].x + "," + pc[j].y);
					lengthX += pc[j].x;
					lengthY += pc[j].y;
					number++;
				}
			}
			pcoren[i] = new PPoint();
			pcoren[i].x = lengthX / number;
			pcoren[i].y = lengthY / number;
			pcoren[i].flag = 0;
			System.out.println("新的聚类中心为<" + pcoren[i].x + "," + pcoren[i].y + ">");

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testMain test = new testMain();
		test.init();
		test.moveCore();
	}
}
