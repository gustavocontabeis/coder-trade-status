package br.com.codersistemas.codertradestatus.robot;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.time.LocalDateTime;

public class CapturarMouse {
	public static void main(String[] args) throws Exception {
		Robot r = new Robot();
		do {
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			System.out.println("robot.clicarEm("+x + ", " + y+");");
			Thread.sleep(1000*1);
		} while (true);
	}

}
