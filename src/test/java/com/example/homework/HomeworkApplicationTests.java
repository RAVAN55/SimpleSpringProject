package com.example.homework;

import com.example.homework.helpers.InvalidDateException;
import com.example.homework.helpers.UserNotFoundException;
import com.example.homework.model.Customer;
import com.example.homework.repository.CustomerRepository;
import com.example.homework.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.inOrder;


@SpringBootTest
class HomeworkApplicationTests {

	@Autowired
	private CustomerService service;

	@MockBean
	private CustomerRepository repository;


/*	@Test*/
/*	void contextLoads() {*/
/*	}*/

	/*@Test*/
	/*void getCustomer() throws UserNotFoundException, InvalidDateException {*/
	/*	String name = "aditya";*/
	/*	CustomerService customerServiceMock = Mockito.mock(CustomerService.class);*/

	/*	Mockito.when(customerServiceMock.getCustomerByName(name)).thenReturn(new Customer("jerry",12L,"male","567"));*/


	/*	Mockito.verify(customerServiceMock).getCustomerByName("i am test");*/

	/*	Mockito.verify(customerServiceMock,Mockito.times(1)).getCustomerByName(name);*/
	/*	Mockito.verify(customerServiceMock,Mockito.atLeastOnce()).getCustomerByName(name);*/
	/*	Mockito.verify(customerServiceMock,Mockito.atLeast(1)).getCustomerByName(name);*/
	/*	Mockito.verify(customerServiceMock,Mockito.atMost(1)).getCustomerByName(name);*/

	/*	Mockito.verify(customerServiceMock,Mockito.never()).getCustomerByName(name);*/

	/*	*//*this method is for to see order of execution of getCustomerByName method*/
	/*	InOrder inOrder = inOrder(customerServiceMock);*/
	/*	inOrder.verify(customerServiceMock).getCustomer();*/
	/*	inOrder.verify(customerServiceMock).getMonthRewardByCustomerName("aditya","march",2021);*/
	/*}*/

}
