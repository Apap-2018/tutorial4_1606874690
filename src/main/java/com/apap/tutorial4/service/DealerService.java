package com.apap.tutorial4.service;

import java.util.Optional;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.apap.tutorial4.model.DealerModel;
import com.apap.tutorial4.repository.DealerDb;

public interface DealerService {
	Optional<DealerModel> getDealerDetailById(Long id);
	
	void addDealer(DealerModel dealer);
	void deleteDealer(DealerModel dealer);
	void updateDealer(DealerModel dealer);
	
	DealerDb getDealerDb();

}
