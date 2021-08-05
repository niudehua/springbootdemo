package cn.niudehua.springbootdemo.mapper;

import cn.niudehua.springbootdemo.domain.entity.Customer;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

/**
 * @author deng
 * @datetime 2020/11/29 上午2:13
 */
@SpringBootTest
@Slf4j
class CustomerMapperTest {

    @Resource
    private CustomerMapper customerMapper;

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    void findTest() {
        List<Customer> customers = customerMapper.selectList(null);
        log.info(JSON.toJSONString(customers));
        Assertions.assertNotNull(customers.get(0));
    }

    @Test
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    void test() {
        Customer customer = new Customer();
        customer.setUsername("牛德滑");
        customer.setPassword("123456");
        customer.setEmail("123456@qq.com");
        customer.setAge(18);
        customer.setPhone("13871293422");
        customer.setBirthday(LocalDate.now());
        customerMapper.insert(customer);
        List<Customer> customers = customerMapper.selectList(null);
        log.info(JSON.toJSONString(customers));
    }
}