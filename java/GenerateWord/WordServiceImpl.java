package com.anhry.app.szxzxk.manage.statistic.service.impl;

import java.awt.Color;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anhry.app.ansafety.service.UtilService;
import com.anhry.app.szxzxk.manage.bus.bean.TbApply;
import com.anhry.app.szxzxk.manage.bus.bean.TbApplyExamine;
import com.anhry.app.szxzxk.manage.bus.bean.TbLine;
import com.anhry.app.szxzxk.manage.report.bean.TbCar;
import com.anhry.app.szxzxk.manage.statistic.service.WordService;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * itext word serviceiml
 *      
 * 类名称：WordServiceImpl   
 * 类描述：   
 * 创建人： Su Hua Bing
 * 创建时间：2014-6-20 下午02:39:45   
 * 修改人：  Su Hua Bing   
 * 修改时间：2014-6-20 下午02:39:45   
 * 修改备注：   
 * @version    
 *
 */
@Service
public class WordServiceImpl implements WordService {
	@Resource
	private UtilService utilService;

	@Override
	public void exportTrafCheckWord(OutputStream toClient, TbApply apply,Integer checkId) throws Exception {
		// TODO Auto-generated method stub
		Document document = new Document(PageSize.A4, 50, 50, 75, 50);// 创建word文档,并设置纸张的大小
		if(apply == null)
			return;
		
		List<TbApplyExamine> examineList = utilService.getHqlList("from TbApplyExamine t where t.isDele='0' and t.type=? and t.applyId=? and t.parentId=? order by t.addTime", 3, apply.getId(),Long.valueOf(checkId));
		/*封装数据*/
		StringBuffer lineIds = new StringBuffer("");
		Map<Integer, List<TbApplyExamine>> mapExamine = new HashMap<Integer, List<TbApplyExamine>>();
		if(examineList != null && examineList.size() > 0){
			for(int i=0,i_size=examineList.size();i<i_size;i++){
				TbApplyExamine examine = examineList.get(i);
				if(mapExamine.get(examine.getLineId()) == null || (mapExamine.get(examine.getLineId())).size() <= 0){
					List<TbApplyExamine> newList = new ArrayList<TbApplyExamine>();
					newList.add(examine);
					mapExamine.put(examine.getLineId(), newList);
					lineIds.append((lineIds == null || "".equals(lineIds.toString().trim()))?"":",");
					lineIds.append(examine.getLineId());
				}else{
					mapExamine.get(examine.getLineId()).add(examine);
				}
			}
		}
		
		RtfWriter2.getInstance(document, toClient);
		
		// 定义输出位置并把文档对象装入输出对象中
		// 打开文档对象
		document.open();
		if(mapExamine != null && mapExamine.size() > 0){
			int num = 1;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日"); 
			//for(Integer lineId : mapExamine.keySet()){
			for(String id : lineIds.toString().split(",")){
				int lineId = Integer.parseInt(id);
				List<TbApplyExamine> examineList_line = mapExamine.get(lineId);
				Map<Integer, TbApplyExamine> mapObject = new HashMap<Integer, TbApplyExamine>();
				for(int i=0,i_size=examineList_line.size();i<i_size;i++){
					TbApplyExamine examine = examineList_line.get(i);
					mapObject.put(examine.getItemId(), examine);
				}
				TbLine line = (TbLine) utilService.getObjectById(TbLine.class, Integer.parseInt(id));
				try {
						if(num > 1){
							document.newPage(); 
						}
						Paragraph ph = new Paragraph();
						Font f = new Font();
						Paragraph p = new Paragraph("深圳市交通运输委员会校车运行方案审查表", new Font(Font.NORMAL, 16,
						Font.BOLD, new Color(0, 0, 0)));
						p.setAlignment(Element.ALIGN_CENTER);
						document.add(p);
						ph.setFont(f);
						
						p = new Paragraph("校车许可申请编号："+apply.getCode(), new Font(Font.NORMAL, 13,
								Font.NORMAL, new Color(0, 0, 0)));
						p.setAlignment(Element.ALIGN_RIGHT);
						document.add(p);
				
						Table table = new Table(6);
						table.setWidths(new int[]{140,100,110,100,90,140});
						table.setSpaceInsideCell(15);
						table.setWidth(100);
						// 表格的主体
						Cell cell = new Cell(examineList_line.get(0).getOrgName()+"（盖章）");
						cell.setColspan(6);
						table.addCell(cell);
						
						cell = new Cell(new Paragraph("运行方案名称:", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						cell = new Cell("深圳市"+apply.getGeogName()+apply.getOrgName()+"校车运行方案");
						cell.setColspan(2);
						table.addCell(cell);
						cell = new Cell(new Paragraph("线 路：", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						cell = new Cell(line.getLineName());
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setColspan(2);
						table.addCell(cell);
						
						cell = new Cell(new Paragraph("一、途经道路通行条件", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						cell.setColspan(6);
						table.addCell(cell);
						
						cell = new Cell("1.部分途经道路缺失");
						cell.setColspan(2);
						table.addCell(cell);
						cell = new Cell((mapObject.get(1).getExamineResult() == 0)?"是 ☑  否 □":"是 □ 否 ☑");
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						cell = new Cell(trimNull(mapObject.get(1).getExamineOpinion()));
						cell.setColspan(3);
						table.addCell(cell);
						
						cell = new Cell("2.途经急弯、陡坡、临崖、临水的危险路段或因占道施工影响通行的路段");
						cell.setColspan(2);
						table.addCell(cell);
						cell = new Cell((mapObject.get(2).getExamineResult() == 0)?"是 ☑  否 □":"是 □ 否 ☑");
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						cell = new Cell(trimNull(mapObject.get(2).getExamineOpinion()));
						cell.setColspan(3);
						table.addCell(cell);
						
						cell = new Cell("3.其他");
						cell.setColspan(2);
						table.addCell(cell);
						cell = new Cell((mapObject.get(3).getExamineResult() == 0)?"是 ☑  否 □":"是 □ 否 ☑");
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						cell = new Cell(trimNull(mapObject.get(3).getExamineOpinion()));
						cell.setColspan(3);
						table.addCell(cell);
						
						cell = new Cell(new Paragraph("办理人：", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						table.addCell(cell);
						cell = new Cell(mapObject.get(3).getManageName());
						table.addCell(cell);
						cell = new Cell(new Paragraph("审核人：", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						table.addCell(cell);
						cell = new Cell(mapObject.get(3).getExamineName());
						table.addCell(cell);
						cell = new Cell(new Paragraph("时间：", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						table.addCell(cell);
						cell = new Cell(sdf.format(mapObject.get(3).getAddTime()));
						table.addCell(cell);
						
						cell = new Cell(new Paragraph("二、校车停靠", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						cell.setColspan(6);
						table.addCell(cell);
						
						cell = new Cell("1.是否涉及公交停靠站");
						cell.setColspan(2);
						table.addCell(cell);
						cell = new Cell((mapObject.get(4).getExamineResult() == 0)?"是 ☑  否 □":"是 □ 否 ☑");
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						cell = new Cell(trimNull(mapObject.get(4).getExamineOpinion()));
						cell.setColspan(3);
						table.addCell(cell);
						
						cell = new Cell("2.校车停靠站点位置是否在急弯、陡坡、临崖、临水的危险路段或正在占道施工的禁行路段");
						cell.setColspan(2);
						table.addCell(cell);
						cell = new Cell((mapObject.get(5).getExamineResult() == 0)?"是 ☑  否 □":"是 □ 否 ☑");
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						cell = new Cell(trimNull(mapObject.get(5).getExamineOpinion()));
						cell.setColspan(3);
						table.addCell(cell);
						
						cell = new Cell("3.校车是否停靠在无辅道的城市快速路、城市主干道上");
						cell.setColspan(2);
						table.addCell(cell);
						cell = new Cell((mapObject.get(6).getExamineResult() == 0)?"是 ☑  否 □":"是 □ 否 ☑");
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						cell = new Cell(trimNull(mapObject.get(6).getExamineOpinion()));
						cell.setColspan(3);
						table.addCell(cell);
						
						cell = new Cell("4.其他");
						cell.setColspan(2);
						table.addCell(cell);
						cell = new Cell((mapObject.get(7).getExamineResult() == 0)?"是 ☑  否 □":"是 □ 否 ☑");
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						cell = new Cell(trimNull(mapObject.get(7).getExamineOpinion()));
						cell.setColspan(3);
						table.addCell(cell);
						
						cell = new Cell(new Paragraph("办理人：", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						table.addCell(cell);
						cell = new Cell(mapObject.get(7).getManageName());
						table.addCell(cell);
						cell = new Cell(new Paragraph("审核人：", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						table.addCell(cell);
						cell = new Cell(mapObject.get(7).getExamineName());
						table.addCell(cell);
						cell = new Cell(new Paragraph("时间：", new Font(Font.NORMAL, 13,
								Font.BOLD, new Color(0, 0, 0))));
						table.addCell(cell);
						cell = new Cell(sdf.format(mapObject.get(7).getAddTime()));
						table.addCell(cell);
						
						
						document.add(table);
				}catch(Exception e) {
					e.printStackTrace();
				}
				
				num++;
				mapObject.clear();
			}
			mapExamine.clear();
		}
		document.close();
		return;
	}
	
	/**
	 * 判空处理
	 * @param content
	 * @return
	 */
	public String trimNull(String content){
		return (content == null)?"":content.trim();
	}

	/**
	 * 导出市教育局发证word
	 */
	@Override
	public void exportCityCertWord(OutputStream toClient, TbApply apply) throws Exception {
		// TODO Auto-generated method stub
		Document document = new Document(PageSize.A4.rotate(), 70, 70, 90, 50);// 创建word文档,并设置纸张的大小
		if(apply == null)
			return;
		
		RtfWriter2.getInstance(document, toClient);
		
		// 定义输出位置并把文档对象装入输出对象中
		// 打开文档对象
		document.open();
		Paragraph ph = new Paragraph();
		Font f = new Font();
		Paragraph p = new Paragraph();
		/**/

		
		String vehUnit = "";
		if(apply.getSynId()!=null&&!"".equals(apply.getSynId())){
			List<TbCar> carList=utilService.getHqlList("from TbCar where isDele='0' and synId=?", apply.getSynId());
			if(carList!=null&&carList.size()>0){
				TbCar car=carList.get(0);
				//车属单位
				//car.getVehProperties();
				p = new Paragraph("车牌号码："+car.getVehNum(), new Font(Font.NORMAL, 16,
						Font.NORMAL, new Color(0, 0, 0)));
				p.setFirstLineIndent(90f);
				p.setSpacingAfter(10f);
				document.add(p);
				
				if(car.getVehUnit()!=null){
					if("1".equals(car.getVehUnit())){
						vehUnit = "学校";
					}else if("2".equals(car.getVehUnit())){
						vehUnit = "幼儿园";
					}else if("3".equals(car.getVehUnit())){
						vehUnit = "社区";
					}else if("4".equals(car.getVehUnit())){
						vehUnit = "租赁公司";
					}else{
						vehUnit = "其他";
					}
				}
				p = new Paragraph("车属单位："+ vehUnit, new Font(Font.NORMAL, 16,
						Font.NORMAL, new Color(0, 0, 0)));
				p.setFirstLineIndent(90f);
				p.setSpacingAfter(10f);
				document.add(p);
				
				p = new Paragraph("核载人数："+car.getPerNum(), new Font(Font.NORMAL, 16,
						Font.NORMAL, new Color(0, 0, 0)));
				p.setFirstLineIndent(90f);
				p.setSpacingAfter(10f);
				document.add(p);
			}
		}

		
		p = new Paragraph("行驶线路和站点：", new Font(Font.NORMAL, 16,
				Font.NORMAL, new Color(0, 0, 0)));
		p.setFirstLineIndent(90f);
		p.setSpacingAfter(5f);
		document.add(p);
		
		StringBuffer lineStation = new StringBuffer("");
		List<TbLine> lineList = utilService.getHqlList("from TbLine t where t.isDele='0' and t.applyId=?", apply.getId());
		if(lineList != null && lineList.size() > 0){
			for(int i=0,i_size=lineList.size();i<i_size;i++){
				TbLine line = lineList.get(i);
				lineStation.append("行驶路线"+(i+1)+"："+line.getLineContent()+"\n");
				lineStation.append("停靠站点"+(i+1)+"："+line.getLineStation());
				lineStation.append((i == (i_size-1))?"":"\n");
			}
		}
		p = new Paragraph(lineStation.toString(), new Font(Font.NORMAL, 16,
				Font.NORMAL, new Color(0, 0, 0)));
		p.setIndentationLeft(120f);
		p.setSpacingAfter(10f);
		document.add(p);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String startTime="";
		String endTime="";
		if(apply.getSendCertTime()!=null&&!"".equals(apply.getSendCertTime())){
			startTime=sdf.format(apply.getSendCertTime());
		}
		if(apply.getCertEndTime()!=null&&!"".equals(apply.getCertEndTime())){
			endTime=sdf.format(apply.getCertEndTime());
		}
		p = new Paragraph("证件有效期："+startTime+"~"+endTime, new Font(Font.NORMAL, 16,
				Font.NORMAL, new Color(0, 0, 0)));
		p.setFirstLineIndent(90f);
		p.setSpacingAfter(10f);
		document.add(p);
		/**/
		
		ph.setFont(f);
		document.close();
		return;
	}
}
