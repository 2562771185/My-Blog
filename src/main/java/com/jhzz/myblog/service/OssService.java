package com.jhzz.myblog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/5/26
 * \* Time: 15:00
 * \* Description:
 * \
 */
public interface OssService {
    String uploadFile(MultipartFile file,String host);
    void deleteFile(String fileName);
}
