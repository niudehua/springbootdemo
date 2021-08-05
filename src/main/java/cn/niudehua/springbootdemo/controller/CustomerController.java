package cn.niudehua.springbootdemo.controller;

import cn.niudehua.springbootdemo.domain.common.PageQuery;
import cn.niudehua.springbootdemo.domain.common.PageResult;
import cn.niudehua.springbootdemo.domain.common.ResponseResult;
import cn.niudehua.springbootdemo.domain.dto.CustomerDTO;
import cn.niudehua.springbootdemo.domain.dto.CustomerQueryDTO;
import cn.niudehua.springbootdemo.domain.vo.CustomerVO;
import cn.niudehua.springbootdemo.enums.ErrorCodeEnum;
import cn.niudehua.springbootdemo.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类名称：CustomerController
 * ***********************
 * <p>
 * 类描述：
 *
 * @author deng on 2020/11/26下午5:08
 */

@RestController
@RequestMapping("/api/customers")
@Validated
@Slf4j
public class CustomerController {
    @Resource
    private CustomerService customerService;

    /**
     * POST /api/customers CustomerDTO
     * 新建客户
     *
     * @param customerDTO customerDTO
     * @return ResponseResult
     */
    @PostMapping
    @CacheEvict(cacheNames = "customer_cache", allEntries = true)
    public ResponseResult<CustomerDTO> save(
            @RequestBody
//            @Validated(InsertValidationGroup.class)
                    CustomerDTO customerDTO) {
        int save = customerService.save(customerDTO);
        if (save == 1) {
            return ResponseResult.success();
        }
        return ResponseResult.error(ErrorCodeEnum.INSERT_ERROR);
    }


    /**
     * PUT /api/customers/{id} CustomerDTO
     * 更新客户信息
     *
     * @param id          id
     * @param customerDTO customerDTO
     * @return ResponseResult
     */
    @PutMapping("/{id}")
    @CacheEvict(cacheNames = "customer_cache", allEntries = true)
    public ResponseResult<CustomerDTO> update(
//            @NotNull(message = "id不能为空")
            @PathVariable("id")
                    Long id,
//            @Validated(UpdateValidationGroup.class)
            @RequestBody
                    CustomerDTO customerDTO) {
        int update = customerService.update(id, customerDTO);
        if (update == 1) {
            return ResponseResult.success();
        }
        return ResponseResult.error(ErrorCodeEnum.UPDATE_ERROR);
    }

    /**
     * DELETE /api/customers/{id}
     * 删除客户信息
     *
     * @param id id
     * @return ResponseResult
     */
    @DeleteMapping("/{id}")
    @CacheEvict(cacheNames = "customer_cache", allEntries = true)
    public ResponseResult<CustomerDTO> delete(
//            @NotNull(message = "id不能为空")
            @PathVariable("id")
                    Long id) {
        int delete = customerService.delete(id);
        if (delete == 1) {
            return ResponseResult.success();
        }
        return ResponseResult.error(ErrorCodeEnum.DELETE_ERROR);
    }

    /**
     * GET /api/customers
     * 查询客户信息
     *
     * @param pageNo           pageNo
     * @param pageSize         pageSize
     * @param customerQueryDTO customerQueryDTO
     * @return ResponseResult<PageResult < List < CustomerDTO>>>
     */
    @GetMapping
//    @Cacheable(cacheNames = "customer_cache")
    public ResponseResult<PageResult<List<CustomerVO>>> query(
//            @NotNull(message = "页号不能为空")
//            @Min(value = 1, message = "页号最小为{value}")
            Integer pageNo,
//            @NotNull(message = "每页条数不能为空")
//            @Min(value = 1, message = "每页条数最小为{value}")
//            @Max(value = 100, message = "每页条数不能超过{value}")
            Integer pageSize,
//            @Validated
            CustomerQueryDTO customerQueryDTO) {
        log.info("未使用缓存~");
        PageQuery<CustomerQueryDTO> pageQuery = new PageQuery<>();
        pageQuery.setPageNo(pageNo);
        pageQuery.setPageSize(pageSize);
        pageQuery.setQuery(customerQueryDTO);
        PageResult<List<CustomerDTO>> pageResult = customerService.query(pageQuery);
        List<CustomerVO> customerVOList = Optional.ofNullable(pageResult.getData())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .map(customerDTO -> {
                    CustomerVO customerVO = new CustomerVO();
                    BeanUtils.copyProperties(customerDTO, customerVO);
                    customerVO.setPassword("******");
                    customerVO.setPhone(customerDTO.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
                    return customerVO;
                }).collect(Collectors.toList());
        // 封装返回结果
        PageResult<List<CustomerVO>> result = new PageResult<>();
        BeanUtils.copyProperties(pageResult, result);
        result.setData(customerVOList);
        return ResponseResult.success(result);
    }

    @PostMapping(value = "/importExcel")
    public ResponseResult<Object> importExcel(HttpServletResponse response, @RequestParam MultipartFile file) throws IOException {
        return customerService.importExcel(response, file);
    }

    @GetMapping(value = "/exportExcel")
    public ResponseResult<Boolean> exportExcel(@Validated CustomerQueryDTO customerQueryDTO,
                                               @NotEmpty String fileName) {
//        customerService.exportExcel(customerQueryDTO,fileName);
        customerService.asyncExportExcel(customerQueryDTO, fileName);
        return ResponseResult.success(Boolean.TRUE);
    }

}