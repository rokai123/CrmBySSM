package com.lukai.crm.workbench.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lukai.crm.workbench.domain.FunnelVO;
import com.lukai.crm.workbench.service.FunnelVOService;

@Controller
public class ChartController {
	
	@Autowired
	private FunnelVOService funnelVOService;
	
	@RequestMapping("/workbench/chart/transaction/index.do")
	public String index() {
		
		return "workbench/chart/transaction/index";
	}
	
	@RequestMapping("workbench/chart/transaction/queryCountOfTranGroupByStage")
	@ResponseBody
	public Object queryCountOfTranGroupByStage() {
		List<FunnelVO> funnelVOList = funnelVOService.queryFunnelVOsForChart();
		return funnelVOList;
	}
}
