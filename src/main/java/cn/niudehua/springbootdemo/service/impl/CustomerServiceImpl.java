package cn.niudehua.springbootdemo.service.impl;

import cn.niudehua.springbootdemo.domain.common.ExcelImportErrorDTO;
import cn.niudehua.springbootdemo.domain.common.ExcelImportResult;
import cn.niudehua.springbootdemo.domain.common.ExcelImportSuccessDTO;
import cn.niudehua.springbootdemo.domain.common.PageQuery;
import cn.niudehua.springbootdemo.domain.common.PageResult;
import cn.niudehua.springbootdemo.domain.common.ResponseResult;
import cn.niudehua.springbootdemo.domain.dto.CustomerDTO;
import cn.niudehua.springbootdemo.domain.dto.CustomerQueryDTO;
import cn.niudehua.springbootdemo.domain.dto.excel.CustomerExportExcel;
import cn.niudehua.springbootdemo.domain.dto.excel.CustomerImportErrorInfoExcel;
import cn.niudehua.springbootdemo.domain.dto.excel.CustomerImportExcel;
import cn.niudehua.springbootdemo.domain.entity.Customer;
import cn.niudehua.springbootdemo.mapper.CustomerMapper;
import cn.niudehua.springbootdemo.service.CustomerService;
import cn.niudehua.springbootdemo.service.FileService;
import cn.niudehua.springbootdemo.util.EasyExcelUtils;
import cn.niudehua.springbootdemo.util.EasyExcelValidatorUtils;
import cn.niudehua.springbootdemo.util.InsertValidationGroup;
import cn.niudehua.springbootdemo.util.UpdateValidationGroup;
import cn.niudehua.springbootdemo.util.ValidatorUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 客户服务实现类
 *
 * @author deng
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    private final CustomerMapper customerMapper;
    @Resource(name = "localFileServiceImpl")
    private FileService fileService;

    @Override
    public int save(CustomerDTO customerDTO) {
        ValidatorUtils.validateEntity(customerDTO, InsertValidationGroup.class);
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customerMapper.insert(customer);
    }

    @Override
    public int update(Long id, CustomerDTO customerDTO) {
        ValidatorUtils.validateEntity(customerDTO, UpdateValidationGroup.class);
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer.setId(id);
        return customerMapper.updateById(customer);
    }

    @Override
    public int delete(Long id) {
        return customerMapper.deleteById(id);
    }

    @Override
    public PageResult<List<CustomerDTO>> query(PageQuery<CustomerQueryDTO> pageQuery) {
        // 校验
        ValidatorUtils.validateEntity(pageQuery);
        Page<Customer> page = new Page<>(pageQuery.getPageNo(), pageQuery.getPageSize(), true);
        Customer customer = new Customer();
        BeanUtils.copyProperties(pageQuery.getQuery(), customer);
        QueryWrapper<Customer> query = Wrappers.query(customer);
        Page<Customer> customerPage = customerMapper.selectPage(page, query);
        PageResult<List<CustomerDTO>> pageResult = new PageResult<>();
        pageResult.setPageNo(customerPage.getCurrent());
        pageResult.setPageSize(customerPage.getSize());
        pageResult.setTotal(customerPage.getTotal());
        pageResult.setPageNum(customerPage.getPages());
        List<CustomerDTO> customerDTOList = Optional.ofNullable(customerPage.getRecords())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(e -> {
                    CustomerDTO customerDTO = new CustomerDTO();
                    BeanUtils.copyProperties(e, customerDTO);
                    return customerDTO;
                })
                .collect(Collectors.toList());
        pageResult.setData(customerDTOList);
        return pageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult<Object> importExcel(HttpServletResponse response, MultipartFile file) throws IOException {
        List<CustomerImportErrorInfoExcel> list = new ArrayList<>();
        int threshold = 3000;
        // 业务处理
        Consumer<List<CustomerImportExcel>> consumer = customerExcels -> {
            ExcelImportResult<CustomerImportExcel> importResult = EasyExcelValidatorUtils.checkImportExcel(customerExcels);
            List<ExcelImportSuccessDTO<CustomerImportExcel>> successDTOList = importResult.getSuccessDTOList();
            List<ExcelImportErrorDTO<CustomerImportExcel>> errorDTOList = importResult.getErrorDTOList();
            // 处理校验失败的数据集
            List<CustomerImportErrorInfoExcel> collect = errorDTOList.stream().map(e -> {
                CustomerImportErrorInfoExcel errorResult = new CustomerImportErrorInfoExcel();
                BeanUtils.copyProperties(e.getObject(), errorResult);
                errorResult.setErrMsg(e.getErrMsg());
                return errorResult;
            }).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect) && Integer.sum(collect.size(), list.size()) <= threshold) {
                list.addAll(collect);
            }

            // 保存校验成功的数据集
            this.saveBatch(successDTOList.stream()
                    .map(e -> {
                        Customer customer = new Customer();
                        BeanUtils.copyProperties(e.getObject(), customer);
                        customer.setBirthday(LocalDate.parse(e.getObject().getBirthday(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        return customer;
                    }).collect(Collectors.toList()));
        };

        EasyExcelUtils.webReadExcel(file, EasyExcelUtils.getListener(CustomerImportExcel.class, consumer, threshold), CustomerImportExcel.class, 0);

        if (CollectionUtils.isNotEmpty(list)) {
            // 导出校验失败的错误数据集
            EasyExcelUtils.webWriteExcel(response, list, CustomerImportErrorInfoExcel.class, "错误数据");
        }
        return ResponseResult.success();
    }

    @Override
    public void exportExcel(CustomerQueryDTO customerQueryDTO, String fileName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 数据导入到Excel中
        exportExcel(outputStream, customerQueryDTO);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        // 实现文件上传
        fileService.upload(inputStream, fileName);
    }

    @Async("exportExcelServiceExecutor")
    @Override
    public void asyncExportExcel(CustomerQueryDTO customerQueryDTO, String fileName) {
        exportExcel(customerQueryDTO, fileName);
    }

    /**
     * 执行数据库查询和Excel 导出，将数据写入 outputStream中
     *
     * @param outputStream outputStream
     * @param queryDTO     queryDTO
     */
    private void exportExcel(OutputStream outputStream, CustomerQueryDTO queryDTO) {
        PageQuery<CustomerQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageSize(1);
        pageQuery.setQuery(queryDTO);
        long pageNo = 0;
        PageResult<List<CustomerDTO>> pageResult = new PageResult<>();
        do {
            pageResult.setPageNo(++pageNo);
            pageResult = this.query(pageQuery);
            List<CustomerExportExcel> exportExcels = Optional.ofNullable(pageResult.getData())
                    .map(List::stream)
                    .orElseGet(Stream::empty)
                    .map(customerDTO -> {
                        CustomerExportExcel customerExportExcel = new CustomerExportExcel();
                        BeanUtils.copyProperties(customerDTO, customerExportExcel);
                        return customerExportExcel;
                    }).collect(Collectors.toList());

            log.info("开始分页导出，第{}页", pageNo);
            EasyExcelUtils.writerExcel(exportExcels, CustomerExportExcel.class
                    , "第" + pageNo + "页", outputStream);

        } while (pageResult.getPageNum() > pageNo);
        log.info("导出完成，共导出了{}页", pageNo);
    }


}





