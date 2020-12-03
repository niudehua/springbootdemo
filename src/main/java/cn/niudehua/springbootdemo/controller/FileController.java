package cn.niudehua.springbootdemo.controller;

import cn.niudehua.springbootdemo.domain.common.ResponseResult;
import cn.niudehua.springbootdemo.enums.ErrorCodeEnum;
import cn.niudehua.springbootdemo.exception.BizException;
import cn.niudehua.springbootdemo.service.impl.LocalFileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 类名称：FileController
 * ***********************
 * <p>
 * 类描述：文件服务
 *
 * @author deng on 2020/11/30上午12:20
 */
@RestController
@Slf4j
@RequestMapping("/api/files")
public class FileController {
    @Resource(name = "localFileServiceImpl")
    private LocalFileServiceImpl localFileService;

    @PostMapping("/upload")
    public ResponseResult<String> uploadFile(@NotNull MultipartFile file) {
        try {
            localFileService.upload(file.getInputStream(), file.getOriginalFilename());
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BizException(ErrorCodeEnum.FILE_UPLOAD_ERROR, e);
        }
        return ResponseResult.success(file.getOriginalFilename());
    }
}