package com.mindskip.xzs.service.impl;

import com.mindskip.xzs.configuration.property.SystemConfig;
import com.mindskip.xzs.domain.Video;
import com.mindskip.xzs.repository.VideoMapper;
import com.mindskip.xzs.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@Service
public class VideoServiceImpl extends BaseServiceImpl<Video> implements VideoService {

    private final VideoMapper videoMapper;
    private final SystemConfig systemConfig;

    @Autowired
    public VideoServiceImpl(VideoMapper videoMapper, SystemConfig systemConfig) {
        super(videoMapper);
        this.videoMapper = videoMapper;
        this.systemConfig = systemConfig;
    }

    @Override
    public Video uploadVideo(MultipartFile file, String title) {
        String folder = systemConfig.getVideoPath();
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(folder, fileName);
        try {
            file.transferTo(path.toFile());
        } catch (IOException e) {
            throw new RuntimeException("save video error", e);
        }
        Video video = new Video();
        video.setTitle(title);
        video.setFilePath(path.toString());
        video.setCreateTime(new Date());
        video.setDeleted(false);
        videoMapper.insertSelective(video);
        return video;
    }

    @Override
    public List<Video> list() {
        return videoMapper.selectAll();
    }
}
