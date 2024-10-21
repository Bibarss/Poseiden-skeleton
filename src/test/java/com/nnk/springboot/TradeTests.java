package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)  // JUnit 5: Extension pour les tests Spring
@SpringBootTest
public class TradeTests {

	@Autowired
	private TradeRepository tradeRepository;

	@Test
	public void tradeTest() {


		// Initialisation d'un objet Trade pour les tests

		Trade trade = new Trade();
		trade.setAccount("Trade Account");
		trade.setType("Type");    ;

		// Save
		trade = tradeRepository.save(trade);
		assertNotNull(trade.getTradeId());
        assertEquals("Trade Account", trade.getAccount());

		// Update
		trade.setAccount("Trade Account Update");
		trade = tradeRepository.save(trade);
        assertEquals("Trade Account Update", trade.getAccount());

		// Find
		List<Trade> listResult = tradeRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = trade.getTradeId();
		tradeRepository.delete(trade);
		Optional<Trade> tradeList = tradeRepository.findById(id);
		assertFalse(tradeList.isPresent());
	}
}
