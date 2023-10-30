package com.wallparisoft.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.wallparisoft.dto.QueryDto;
import com.wallparisoft.service.IQueryService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class QueryServiceImpl implements IQueryService {

	
	public byte[] generateReport() {
		byte[] data = null;
		
		HashMap<String, Object> params = new HashMap<>();
		params.put("txt_Empresa", "Wallparisoft");
		
		try {
			File file = new ClassPathResource("/reports/consultas.jasper").getFile();
			JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, new JRBeanCollectionDataSource(this.loadData()));
			data = JasperExportManager.exportReportToPdf(print);
		}catch(Exception e) {
			e.printStackTrace();
		}	
		
		return data;
	}
	
	private List<QueryDto> loadData(){
		List<QueryDto> listQuery= new ArrayList<>();
		QueryDto queryDto = new QueryDto();
		queryDto.setCantidad(11);
		queryDto.setFecha("10-10-2015");
		listQuery.add(queryDto);
		return listQuery;
	}
}
