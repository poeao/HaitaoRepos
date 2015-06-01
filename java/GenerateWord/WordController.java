package com.anhry.app.szxzxk.manage.statistic.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anhry.app.ansafety.service.UtilService;
import com.anhry.app.ansafety.web.BaseController;
import com.anhry.app.szxzxk.manage.bus.bean.TbApply;
import com.anhry.app.szxzxk.manage.statistic.service.WordService;
/**
 * 生成word控制器
 *      
 * 类名称：WordController   
 * 类描述：   
 * 创建人： Su Hua Bing
 * 创建时间：2014-6-19 下午02:14:21   
 * 修改人：  Su Hua Bing   
 * 修改时间：2014-6-19 下午02:14:21   
 * 修改备注：   
 * @version    
 *
 */
@RequestMapping("/word")
@Controller
public class WordController extends BaseController {
	@Resource
	private WordService wordService;
	@Resource
	private UtilService utilService;

	@RequestMapping("/exportTrafCheckWord")
	public void exportTrafCheckWord(Integer checkId,Integer applyId, HttpServletResponse response){
		TbApply apply = (TbApply) utilService.getObject("TbApply", applyId);
		OutputStream toClient = null;
		String fileName = "关于"+apply.getApplyCode()+"的审查意见.doc";
		try {
			toClient = new BufferedOutputStream(response.getOutputStream());
			// 用vnd.ms-word形式返回数据
	        response.setContentType("application/vnd.ms-word");
	        // 用OrderMess.xls作为默认的名字保存
	        response.addHeader("Content-Disposition", "attachment;  filename="+java.net.URLEncoder.encode(fileName, "UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			wordService.exportTrafCheckWord( toClient, apply, checkId);
			toClient.flush();
			toClient.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/exportCityCertWord")
	public void exportCityCertWord(Integer applyId, HttpServletResponse response){
		TbApply apply = (TbApply) utilService.getObject("TbApply", applyId);
		OutputStream toClient = null;
		String fileName = apply.getApplyCode()+"证书数据.doc";
		try {
			toClient = new BufferedOutputStream(response.getOutputStream());
			// 用vnd.ms-word形式返回数据
	        response.setContentType("application/vnd.ms-word");
	        // 用OrderMess.xls作为默认的名字保存
	        response.addHeader("Content-Disposition", "attachment;  filename="+java.net.URLEncoder.encode(fileName, "UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try{
			wordService.exportCityCertWord( toClient, apply);
			toClient.flush();
			toClient.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
