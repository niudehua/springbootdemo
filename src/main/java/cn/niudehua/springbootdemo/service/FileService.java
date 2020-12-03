package cn.niudehua.springbootdemo.service;

import java.io.File;
import java.io.InputStream;

/**
 * 类名称：FileService
 * ***********************
 * <p>
 * 类描述：文件服务接口
 *
 * @author deng on 2020/11/30上午12:24
 */
public interface FileService {
    /**
     * 文件上传
     *
     * @param inputStream inputStream
     * @param filename    filename
     */
    void upload(InputStream inputStream, String filename);

    /**
     * 文件上传
     *
     * @param file file
     */
    void upload(File file);
}