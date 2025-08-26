package com.mindskip.xzs.controller.admin;

import com.mindskip.xzs.base.BaseApiController;
import com.mindskip.xzs.base.RestResponse;
import com.mindskip.xzs.domain.Video;
import com.mindskip.xzs.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("AdminVideoController")
@RequestMapping("/api/admin/video")
public class VideoController extends BaseApiController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @PostMapping("/upload")
    public RestResponse<Video> upload(@RequestParam("file") MultipartFile file,
                                      @RequestParam("title") String title) {
        Video video = videoService.uploadVideo(file, title);
        return RestResponse.ok(video);
    }

    @GetMapping("/list")
    public RestResponse<List<Video>> list() {
        return RestResponse.ok(videoService.list());
    }

    @GetMapping("/stream/{id}")
    public ResponseEntity<Resource> stream(@PathVariable Integer id) {
        Video video = videoService.selectById(id);
        FileSystemResource resource = new FileSystemResource(video.getFilePath());
        MediaType mediaType = MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }
}
