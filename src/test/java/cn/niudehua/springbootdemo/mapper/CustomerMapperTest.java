package cn.niudehua.springbootdemo.mapper;

import cn.niudehua.springbootdemo.domain.entity.Customer;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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
     void findTest() {
        LambdaQueryWrapper<Customer> query = Wrappers.lambdaQuery();
        query.like(Customer::getUsername, "牛德滑");
        List<Customer> customers = customerMapper.selectList(query);
        Assertions.assertNotNull(customers.get(0));
    }

}