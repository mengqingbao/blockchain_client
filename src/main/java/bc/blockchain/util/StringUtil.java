package bc.blockchain.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StringUtil {

	public static String getHDserialnumber() {

		String line = "";

		String HdSerial = "";// 定义变量 硬盘序列号

		try {

			Process proces = Runtime.getRuntime().exec("cmd /c dir c:");// 获取命令行参数

			BufferedReader buffreader = new BufferedReader(

			new InputStreamReader(proces.getInputStream()));

			while ((line = buffreader.readLine()) != null) {

				if (line.indexOf("卷的序列号是 ") != -1) { // 读取参数并获取硬盘序列号

					HdSerial = line.substring(line.indexOf("卷的序列号是 ")

					+ "卷的序列号是 ".length(), line.length());

					break;

					// System.out.println(HdSerial);

				}

			}

		} catch (IOException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return HdSerial;// 返回硬盘序列号 卷的序列 非物理
	}

	
}
