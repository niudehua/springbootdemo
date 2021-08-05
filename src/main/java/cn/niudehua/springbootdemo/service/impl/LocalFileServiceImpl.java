package cn.niudehua.springbootdemo.service.impl;

import cn.niudehua.springbootdemo.enums.ErrorCodeEnum;
import cn.niudehua.springbootdemo.exception.BizException;
import cn.niudehua.springbootdemo.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 类名称：LocalFileServiceImpl
 * ***********************
 * <p>
 * 类描述：本地文件上传服务实现类
 *
 * @author deng on 2020/11/30上午12:26
 */
@Slf4j
@Service("localFileServiceImpl")
public class LocalFileServiceImpl implements FileService {
    private static final String BUCKET = "uploads/";

    @Override
    public void upload(InputStream inputStream, String filename) {
        // 拼接文件存储路径
        String storagePath = BUCKET + filename;
        // JDK8 TWR不能关闭外部资源 用内部资源接收
        try (
                InputStream innerInputStream = inputStream;
                OutputStream outputStream = new FileOutputStream(storagePath);
        ) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = innerInputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BizException(ErrorCodeEnum.FILE_UPLOAD_ERROR, e);
        }
    }

    @Override
    public void upload(File file) {
        try {
            upload(new FileInputStream(file), file.getName());
        } catch (FileNotFoundException e) {
            log.error("文件上传失败", e);
            throw new BizException(ErrorCodeEnum.FILE_UPLOAD_ERROR, e);
        }
    }
}