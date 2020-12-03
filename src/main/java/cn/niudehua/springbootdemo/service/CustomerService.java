package cn.niudehua.springbootdemo.service;

import cn.niudehua.springbootdemo.domain.common.PageQuery;
import cn.niudehua.springbootdemo.domain.common.PageResult;
import cn.niudehua.springbootdemo.domain.dto.CustomerDTO;
import cn.niudehua.springbootdemo.domain.dto.CustomerQueryDTO;
import cn.niudehua.springbootdemo.domain.entity.Customer;
import cn.niudehua.springbootdemo.domain.common.ResponseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author deng
 * @datetime 2020/11/26 下午10:59
 */
public interface CustomerService extends IService<Customer> {
    int save(CustomerDTO customerDTO);

    int update(Long id, CustomerDTO customerDTO);

    int delete(Long id);

    PageResult<List<CustomerDTO>> query(PageQuery<CustomerQueryDTO> pageQuery);

    /**
     * excel导入
     *
     * @param response response
     * @param file     导入文件
     * @return ResultBody
     * @throws IOException IOException
     */
    ResponseResult<Object> importExcel(HttpServletResponse response, MultipartFile file) throws IOException;


    /**
     * excel导出
     *
     * @param customerQueryDTO customerQueryDTO
     * @param fileName         fileName
     */
    void exportExcel(CustomerQueryDTO customerQueryDTO, String fileName);

    /**
     * excel异步导出
     *
     * @param customerQueryDTO customerQueryDTO
     * @param fileName         fileName
     */
    void asyncExportExcel(CustomerQueryDTO customerQueryDTO, String fileName);



}





