package cn.niudehua.springbootdemo.service.impl;

import cn.niudehua.springbootdemo.domain.dto.CustomerDTO;
import cn.niudehua.springbootdemo.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author deng
 * @datetime 2020/11/29 上午3:03
 */
@SpringBootTest
//@Transactional
//@Rollback
class CustomerServiceImplTest {
    @Resource
    private CustomerService customerService;

    @Test
    void saveTest() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername("niudehua");
        customerDTO.setPassword("a19920213");
        customerDTO.setEmail("657563945@qq.com");
        customerDTO.setAge(28);
        customerDTO.setPhone("13871293422");
        customerDTO.setVersion(1L);
        int save = customerService.save(customerDTO);
        Assertions.assertEquals(1, save);
    }

    @Test
    void updateTest() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setUsername("niudehua3");
        customerDTO.setVersion(1L);
        int update = customerService.update(1L, customerDTO);
        Assertions.assertEquals(1, update);
    }

    @Test
    void deleteTest() {
        int delete = customerService.delete(1L);
        Assertions.assertEquals(1, delete);
    }
}