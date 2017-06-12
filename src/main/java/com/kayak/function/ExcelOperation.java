package com.kayak.function;

import java.io.File;
import java.io.Reader;
import java.util.ArrayList;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import com.kayak.domain.User;
import com.kayak.mybatis.inter.IUserOperation;
import com.kayak.util.excel.ExcelUtil;

public class ExcelOperation {
	private static SqlSessionFactory sqlSessionFactory;
	private static Reader reader;
	static {
		try {
			reader = Resources.getResourceAsReader("conf.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Logger logger = Logger.getLogger(ExcelOperation.class);

	public void readExcel() {
		String file_address = "D:/kkdev/project/practice/excelPractice.xls";// excel地址
		String table_name = "";// 操作的表名
		try {
			logger.info("开始解析excel");
			ArrayList readExcel = ExcelUtil.readExcel(file_address);
			logger.info("解析excel完毕！");
			User user = new User();
			logger.info("开始遍历excel解析结果并插入数据库");
			for (int i = 1; i < readExcel.size(); i++) {
				// System.out.println(readExcel.get(i));
				ArrayList<String> row = (ArrayList) readExcel.get(i);
				if (!"".equals(row) && row != null) {
					user.setUserName(row.get(0));
					user.setUserPassword(row.get(1));
					user.setUserEmail(row.get(2));
					addUser(user);
				}
			}
			logger.info("全部excel数据插入数据库成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("全部excel数据插入数据库失败");
		}
	}

	private void addUser(User user) {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			IUserOperation userOperation = session
					.getMapper(IUserOperation.class);
			userOperation.addUser(user);
			session.commit();
			System.out.println("当前增加的用户 id为:" + user.getUserId());
		} catch (Exception e) {
			logger.info("excel数据" + user.getUserName() + "插入数据库失败");
		} finally {
			session.close();
		}
	}

	public static void main(String[] args) {
		ExcelOperation excelOperation = new ExcelOperation();
		excelOperation.readExcel();
	}
}
