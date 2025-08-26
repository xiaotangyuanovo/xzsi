package com.mindskip.xzs.service;

import com.mindskip.xzs.domain.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService extends BaseService<Video> {
    Video uploadVideo(MultipartFile file, String title);
    List<Video> list();
}
