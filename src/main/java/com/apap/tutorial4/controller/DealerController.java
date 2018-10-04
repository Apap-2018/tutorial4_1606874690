package com.apap.tutorial4.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial4.model.CarModel;
import com.apap.tutorial4.model.DealerModel;
import com.apap.tutorial4.repository.DealerDb;
import com.apap.tutorial4.service.CarService;
import com.apap.tutorial4.service.DealerService;

@Controller
public class DealerController {
	private long idSementara;
	
	@Autowired
	private DealerService dealerService;

	@Autowired
	private CarService carService;

	@RequestMapping("/")
	private String home() {
		return "home";
	}

	@RequestMapping(value= "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";	
	}

	@RequestMapping(value= "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}

	@RequestMapping(value= "/dealer/view", method = RequestMethod.GET)
	private String viewDealer(@RequestParam ("dealerId") long dealerId,  Model model) {
		
		DealerModel archive = dealerService.getDealerDetailById(dealerId).get();
		
		List<CarModel> listCar = archive.getListCar();
		Collections.sort(listCar, new sortCar());

		model.addAttribute("dealer", archive);
		model.addAttribute("listCar", listCar);
		
		return "view-dealer";
	}

	@RequestMapping("/dealer/viewall")
	public String viewAll (Model model) {
		
		DealerDb dealerDb = dealerService.getDealerDb();
		List<DealerModel> listDealer = dealerDb.findAll();
		
		model.addAttribute("listDealer", listDealer);
		return "viewall-dealer";	
	}
	
	@RequestMapping(value= "/dealer/delete/{dealerId}", method = RequestMethod.GET)
	private String delete(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		
		DealerModel archive = dealerService.getDealerDetailById(dealerId).get();
		dealerService.deleteDealer(archive);
		
		return "delete-dealer";
				
	}
	
	@RequestMapping(value= "/dealer/update/{dealerId}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		System.out.println("masukkk dealer");

		this.idSementara = dealerId;
		model.addAttribute("dealer", new DealerModel());
		
		return "updateDealer";			
	}
	
	@RequestMapping(value= "/dealer/update", method = RequestMethod.POST)
	private String updateDealerSubmit(@ModelAttribute DealerModel dealer) {
		
		dealer.setId(idSementara);
		dealerService.updateDealer(dealer);
		return "update";
	}
	
}

class sortCar implements Comparator<CarModel> { 

	public int compare(CarModel a, CarModel b) { 
		return (int) (a.getPrice() - b.getPrice()); 
	} 
} 